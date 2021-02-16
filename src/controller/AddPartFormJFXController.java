/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * Controls the Add Part Form.
 * Allows the user to add parts to the inventory.
 *
 * @author jveps
 */
public class AddPartFormJFXController implements Initializable {
    Stage stage;
    Parent scene;
    

    @FXML
    private ToggleGroup AddPartTG;
    @FXML
    private Label addPartsFormLabel;
    
    private boolean inHousePart;
    private String macIDLbl = "Machine ID";
    private String compNameLbl = "Company Name";
    @FXML
    private TextField addPartIdTextField;
    @FXML
    private TextField addPartNameTextField;
    @FXML
    private TextField addPartInvTextField;
    @FXML
    private TextField addPartPriceCostTextField;
    @FXML
    private TextField addPartMaxTextField;
    
    @FXML
    private TextField addPartMinTextField;
    @FXML
    private TextField addPartMachineIdCompNameTextField;
    @FXML
    private RadioButton inHouseRb;
    @FXML
    private RadioButton outsourcedRb;

    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inHousePart = true;
        inHouseRb.setSelected(true);
        inHouseRb.requestFocus();
        
        //Not allow editing of part Id
        addPartIdTextField.setEditable(false);
        
        //Create Id - Highest Id + 1
        int genId = 0;
        for (Part part : Inventory.getAllParts()){
            
            if (part.getId() > genId){
                genId = part.getId();
            }
        genId += 1;
        
        //Add Id to text field
        addPartIdTextField.setText(String.valueOf(genId));
            
        }
    }    
    /** Changes text on the screen. 
     * Changes the label on the final text field to "Machine ID"
    */
    @FXML
    private void inHouseRadio(ActionEvent event) {
        inHousePart = true;
        addPartsFormLabel.setText(macIDLbl);
    }
    
    /** Changes label on the last text field. 
     * Changes label to "Company Name". 
     */
    @FXML
    private void isOutsourcedRadio(ActionEvent event) {
        inHousePart = false;
        addPartsFormLabel.setText(compNameLbl);
    }
    
    /**
    *Returns the user to the main screen when pressed.
    * Takes the user back to the main screen without creating a new part.
    */
    @FXML
    private void addPartCancelHandler(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
    * Creates a new part from user-entered data. 
    * This allows the user to create a new part from data entered by the user.
    * 
    */
    @FXML
    private void addPartSaveHandler(ActionEvent event) throws IOException {
        try{
            int id = Integer.parseInt(addPartIdTextField.getText());
            String name = addPartNameTextField.getText();
            int stock = Integer.parseInt(addPartInvTextField.getText());
            double price = Double.parseDouble(addPartPriceCostTextField.getText());
            int max = Integer.parseInt(addPartMaxTextField.getText());
            int min = Integer.parseInt(addPartMinTextField.getText());

            if (max < min){
                Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
                alertMaxMin.setTitle("ERROR");
                alertMaxMin.setContentText("Min must be less than Max");
                alertMaxMin.showAndWait();
            }
                else if (stock > max || stock < min){
                    Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
                    alertMaxMin.setTitle("ERROR");
                    alertMaxMin.setContentText("Inv must be less than or equal to Max and greater than or equal to min");
                    alertMaxMin.showAndWait();
                } 

                else if (inHousePart == true){
                    int machineId = Integer.parseInt(addPartMachineIdCompNameTextField.getText());
                    Inventory.addPart(new InHouse (id, name, price, stock, min, max, machineId));
                    //Return to main screen
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                }

                else if (inHousePart == false){
                    String companyName = addPartMachineIdCompNameTextField.getText();
                    Inventory.addPart(new Outsourced (id, name, price, stock, min, max, companyName));
                    //Return to main screen
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                }
        }
        catch(NumberFormatException e){
            Alert dataAlert = new Alert(Alert.AlertType.ERROR);
            dataAlert.setTitle("ERROR");
            dataAlert.setContentText("All fields must contain correct data");
            dataAlert.showAndWait();
        }
        
        
        
        
        
        
    }
    
    
}
