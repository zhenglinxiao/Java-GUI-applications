/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotifyplayer;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bergeron
 */
public class SpotifyPlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        URL sceneFile = getClass().getResource("FXMLDocument.fxml");
        FXMLLoader loader = new FXMLLoader(sceneFile);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        FXMLDocumentController controller = loader.getController();
        stage.setOnHidden(event -> controller.shutdown());
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
