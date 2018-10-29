/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataparsing;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author thund
 */
public class FXMLDocumentController implements Initializable {

    String json = readJsonFile("./user.json");
    JsonElement rootElement = new JsonParser().parse(json);
    JsonObject rootObject = rootElement.getAsJsonObject();
    
    
    @FXML
    private Label firstNameLabel;
    
    @FXML
    private Label lastNameLabel;
    
    @FXML
    private Label ageLabel;
    
    @FXML
    private Label addressLabel;
    
    @FXML
    private Label cityLabel;
    
    @FXML
    private Label stateLabel;
    
    @FXML
    private Label postalLabel;
    
    @FXML
    private Label homeLabel;
    
    @FXML
    private Label faxLabel;
    
    @FXML
    private Label genderLabel;
    
    @FXML
    private Button retrieveBtn;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        // Parse from JSON String & set first name
        String firstName = rootObject.get("firstName").getAsString();
        firstNameLabel.setText("First Name: "+ firstName);
        
        String lastName = rootObject.get("lastName").getAsString();
        lastNameLabel.setText("Last Name: " + lastName);
        
        int age = rootObject.get("age").getAsInt();
        ageLabel.setText("Age: " + age);
        
        
        //Address as an object with different attributes
        JsonObject addressObject = rootObject.get("address").getAsJsonObject();
        
        String address = addressObject.get("streetAddress").getAsString();
        addressLabel.setText("Address: " + address);
        
        String city = addressObject.get("city").getAsString();
        cityLabel.setText("City: " + city);
        
        String state = addressObject.get("state").getAsString();
        stateLabel.setText("State: " + state);
        
        String postal = addressObject.get("postalCode").getAsString();
        postalLabel.setText("Postal Code: " + postal);

        // Parse Array - Phone number is an array, let's retrieve each number
        JsonArray phoneArray = rootObject.getAsJsonArray("phoneNumber");
        
        JsonObject homeObject = phoneArray.get(0).getAsJsonObject();
        String home = homeObject.get("number").getAsString();
        homeLabel.setText("Home Number: " + home);
        
        JsonObject faxObject = phoneArray.get(1).getAsJsonObject();
        String fax = faxObject.get("number").getAsString();
        faxLabel.setText("Fax Number: "+ fax);
        
//        for (int i = 0; i < phoneArray.size(); i++) {
//            JsonObject phoneObject = phoneArray.get(i).getAsJsonObject();
//            String number = phoneObject.get("number").toString();
//            System.out.println("Number: " + number);
//        }
        
        JsonObject genderObject = rootObject.get("gender").getAsJsonObject();
        String gender = genderObject.get("type").getAsString();
        genderLabel.setText("Gender: " + gender);
    }

    private String readJsonFile(String filename) {

        try {
            // Read JSON File

            FileInputStream fileIn = new FileInputStream(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fileIn));

            String inputLine;
            String jsonString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                jsonString += inputLine;
            }
            in.close();
            fileIn.close();

            return jsonString;
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
