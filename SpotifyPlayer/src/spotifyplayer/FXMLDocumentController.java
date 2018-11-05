/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotifyplayer;

import com.sun.javafx.collections.ObservableListWrapper;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import javafx.util.Duration;


public class FXMLDocumentController implements Initializable {
    @FXML
    TableView tracksTableView;
    
    @FXML
    Slider trackSlider;
    
    @FXML
    Label artistLabel;
    
    @FXML
    Label albumLabel;
    
    @FXML
    Label songLengthLabel;
    
    @FXML
    Label songTimeLabel;
    
    @FXML
    TextField searchField;
    
    @FXML
    Button playButton;
    
    @FXML
    Button previousAlbumButton;
    
    @FXML
    Button nextAlbumButton;
    
    @FXML
    ImageView albumCover;
    
    @FXML 
    ProgressIndicator progress;
    
    // Other Fields...
    ScheduledExecutorService sliderExecutor = null;
    MediaPlayer mediaPlayer = null;
    
    ArrayList<Album> albums = null;
    int currentAlbumIndex = 0;
    
    
    public void shutdown(){
        if(sliderExecutor != null){
            sliderExecutor.shutdown();
        }
        Platform.exit();
    }
    
    @FXML
    private void onEnter(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER) && searchField.isFocused()){
            // Search artist
            progress.setVisible(true);
            progress.setProgress(-1.0d);
            sliderExecutor = Executors.newSingleThreadScheduledExecutor();
            sliderExecutor.submit(new Task<Void>(){
                @Override
                protected Void call() throws Exception {
                    searchAlbumsFromArtist(searchField.getText());
                    return null;
                }
                @Override
                protected void succeeded(){
                    try{
                        displayAlbum(currentAlbumIndex);
                    }
                    catch(Exception e){
                        artistLabel.setText("Error!");
                        albumLabel.setText("Invalid artist.");
                        progress.setProgress(1d);
                    }
                    
                }
                @Override
                protected void cancelled(){
                    albumLabel.setText("Error");
                    artistLabel.setText("Searching failed.");
                    progress.setProgress(0d);
                }
            });
            
        }
    }
    
    @FXML
    private void nextAlbum(ActionEvent event){
        displayAlbum(++currentAlbumIndex);
    }
    
    @FXML
    private void previousAlbum(ActionEvent event){
        displayAlbum(--currentAlbumIndex);
    }

    private void playPauseTrackPreview( Button source, String trackPreviewUrl)
    {
        try
        {
            if (source.getText().equals("Play"))
            {
                if (mediaPlayer != null)
                {
                    mediaPlayer.stop();                
                }

                source.setText("Stop");
                trackSlider.setDisable(false);
                trackSlider.setValue(0.0);

                // Start playing music
                Media music = new Media(trackPreviewUrl);
                mediaPlayer = new MediaPlayer(music);
                mediaPlayer.play();
                
                
                // This runnable object will be called
                // when the track is finished or stopped
                Runnable stopTrackRunnable = new Runnable(){
                    @Override
                    public void run() {
                        source.setText("Play");
                        if (sliderExecutor != null)
                        {
                            sliderExecutor.shutdownNow();
                        }
                    }                
                };                
                mediaPlayer.setOnEndOfMedia(stopTrackRunnable);
                mediaPlayer.setOnStopped(stopTrackRunnable);

                // Schedule the slider to move right every second
                sliderExecutor = Executors.newSingleThreadScheduledExecutor();
                sliderExecutor.scheduleAtFixedRate(new Runnable(){
                    @Override
                    public void run() {
                        // We can't update the GUI elements on a separate thread... 
                        // Let's call Platform.runLater to do it in main thread!!
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                // Move slider
                                trackSlider.setValue(trackSlider.getValue() + 1.0);
                            }
                        });
                    }
                }, 1, 1, TimeUnit.SECONDS);
            }
            else
            {
                if (mediaPlayer != null)
                {
                    mediaPlayer.stop();
                }                
            }
        }
        catch(Exception e)
        {
            System.err.println("error with slider executor... this should not happen!");
        }
    }
    
    
    private void displayAlbum(int albumNumber)
    {   
        // Display Tracks for the album passed as parameter
        if (albumNumber >=0 && albumNumber < albums.size())
        {
            currentAlbumIndex = albumNumber;
            Album album = albums.get(albumNumber);
            
            artistLabel.setText(album.getArtistName());
            albumLabel.setText(album.getAlbumName());
            albumCover.setImage(new Image(album.getImageURL()));
            if(albums.size() > 1){
                if(albumNumber == albums.size() - 1){
                    nextAlbumButton.setDisable(true);
                    previousAlbumButton.setDisable(false);
                }
                else if(albumNumber == 0){
                    nextAlbumButton.setDisable(false);
                    previousAlbumButton.setDisable(true);
                }
                else{
                    nextAlbumButton.setDisable(false);
                    previousAlbumButton.setDisable(false);
                }
            }
            
            // Set tracks
            ArrayList<TrackForTableView> tracks = new ArrayList<>();
            for (int i=0; i<album.getTracks().size(); ++i)
            {
                TrackForTableView trackForTable = new TrackForTableView();
                Track track = album.getTracks().get(i);
                trackForTable.setTrackNumber(track.getNumber());
                trackForTable.setTrackTitle(track.getTitle());
                trackForTable.setTrackPreviewUrl(track.getUrl());
                tracks.add(trackForTable);
            }
            tracksTableView.setItems(new ObservableListWrapper(tracks));

            trackSlider.setDisable(true);
            trackSlider.setValue(0.0);                       
        }
    }
    
    private void searchAlbumsFromArtist(String artistName)
    {
        // TODO - Make sure this is not blocking the UI
        
        currentAlbumIndex = 0;
        try{
            String artistId = SpotifyController.getArtistId(artistName);
            albums = SpotifyController.getAlbumDataFromArtist(artistId);   
//            displayAlbum(currentAlbumIndex);
        }
        catch(Exception e){
            artistLabel.setText("Error!");
            albumLabel.setText("Invalid artist.");
        }
       
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Setup Table View
        TableColumn<TrackForTableView, Number> trackNumberColumn = new TableColumn("#");
        trackNumberColumn.setCellValueFactory(new PropertyValueFactory("trackNumber"));
        trackNumberColumn.setPrefWidth(28);
        
        TableColumn trackTitleColumn = new TableColumn("Title");
        trackTitleColumn.setCellValueFactory(new PropertyValueFactory("trackTitle"));
        trackTitleColumn.setPrefWidth(220);
        
        TableColumn playColumn = new TableColumn("Preview");
        playColumn.setCellValueFactory(new PropertyValueFactory("trackPreviewUrl"));
        Callback<TableColumn<TrackForTableView, String>, TableCell<TrackForTableView, String>> cellFactory = new Callback<TableColumn<TrackForTableView, String>, TableCell<TrackForTableView, String>>(){
            @Override
            public TableCell<TrackForTableView, String> call(TableColumn<TrackForTableView, String> param) {
                final TableCell<TrackForTableView, String> cell = new TableCell<TrackForTableView, String>(){
                    final Button playButton = new Button("Play");

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        if (item != null && item.equals("") == false){
                            playButton.setOnAction(event -> {
                                playPauseTrackPreview(playButton, item);
                            });
    
                            setGraphic(playButton);
                        }
                        else{                        
                            setGraphic(null);
                        }

                        setText(null);                        
                    }
                };
                
                return cell;
            }
        };
        playColumn.setCellFactory(cellFactory);
        tracksTableView.getColumns().setAll(trackNumberColumn, trackTitleColumn, playColumn);

        // When slider is released, we must seek in the song
        trackSlider.setOnMouseReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (mediaPlayer != null)
                {
                    mediaPlayer.seek(Duration.seconds(trackSlider.getValue()));
                }
            }
        });
        
    }        
}
