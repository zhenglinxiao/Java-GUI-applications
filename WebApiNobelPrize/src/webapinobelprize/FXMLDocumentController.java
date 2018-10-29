/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapinobelprize;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import webapinobelprize.NobelPrizeController.NobelPrizeLaureate;

/**
 *
 * @author bergeron
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ComboBox year;
    
    @FXML
    private ComboBox category;
    
    @FXML
    private Label laureates;
    
    @FXML
    private Button loadData;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        // Change inputs for combobox inputs.
        
        String cat = (String) category.getValue().toString();
        Integer y = Integer.parseInt(year.getValue().toString());
        
        try{
            NobelPrizeLaureate[] prizeWinners = new NobelPrizeLaureate[3];
            prizeWinners = NobelPrizeController.getNobelPrizeLaureates(cat, y);
            String laureateText = "";
        
            for(NobelPrizeLaureate e : prizeWinners){
                laureateText += e.toString();
            }
            laureates.setText(laureateText);
        }
        catch(Exception e){
            System.out.println("Error in setting text");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        category.getItems().addAll("chemistry", "economics", "medicine", "peace", "physics");
        
        category.setValue("chemistry");
        
        ArrayList<String> yearList = new ArrayList<>();
        
        for (int i = 2018; i >= 1901; i--) {
            yearList.add(i+"");
        }
        
        year.getItems().addAll(yearList);
        year.setValue("2018"); 
    }    
    
}
