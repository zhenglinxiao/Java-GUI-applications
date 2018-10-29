
package temperatureconverter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TemperatureConverter extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Temperature Converter");
        
        // Setup Scene 1
        
        TextField temperatureInput = new TextField();
        Button convert = new Button("Convert Temperature!");
        Label degrees = new Label("\u00b0C");
        
        //StackPane root = new StackPane();
        VBox scene1Layout = new VBox();
        scene1Layout.getChildren().add(temperatureInput);
        scene1Layout.getChildren().add(degrees);
        scene1Layout.getChildren().add(convert);
        Scene celsius = new Scene(scene1Layout, 300, 250);
        
        // Scene 2
        
        Label fahrenheitLabel = new Label();
        Button backButton = new Button("Back");
        
        VBox scene2Layout = new VBox();
        scene2Layout.getChildren().add(fahrenheitLabel);
        scene2Layout.getChildren().add(backButton);
        Scene fahrenheit = new Scene(scene2Layout, 300, 250);
        
        // Set action handlers
        
        convert.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(fahrenheit);
                double value = Double.parseDouble(temperatureInput.getText());
                value = 1.8*value + 32;
                fahrenheitLabel.setText(value + " \u00b0F");
            }
        });
        
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(celsius);
                temperatureInput.setText("");
            }
            
        });
       
        // Set initial scene
        
        primaryStage.setScene(celsius);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
