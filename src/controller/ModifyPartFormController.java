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
import model.Part;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * This class controls the Modify Part Form. 
 * Allows the user to modify parts in the inventory.
 *
 * @author jveps
 */
public class ModifyPartFormController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private Label modifyPartsFormLabel;
    
    private boolean modifyIsInHouse;
    private String modifyInHouseLabel = "Machine ID";
    private String modifyOutsourcedLabel = "Company Name";
    @FXML
    private ToggleGroup modifyPartTG;
    @FXML
    private TextField modPartIdTextField;
    @FXML
    private TextField modPartNameField;
    @FXML
    private TextField modPartInvField;
    @FXML
    private TextField modPartPriceCostField;
    @FXML
    private TextField modPartMaxField;
    @FXML
    private TextField modPartMachineIdCompNameField;
    @FXML
    private TextField modPartMinField;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modPartIdTextField.setEditable(false);
        
    }    

    /**
    * Sets default selection to the modify part form radio buttons.
    * Applies the default status to the radio button and text field label.
    */
    @FXML
    private void inHouseRadioMod(ActionEvent event) {
        
        modifyIsInHouse = true;
        modifyPartsFormLabel.setText(modifyInHouseLabel);
        modPartMachineIdCompNameField.requestFocus();
    }
    /** Sets default selectio to modify part from radio buttons. 
     * Applies the default status to the radio button and text field label.
     */
    @FXML
    private void isOutsourcedRadioMod(ActionEvent event) {
        
        modifyIsInHouse = false;
        modifyPartsFormLabel.setText(modifyOutsourcedLabel);
        modPartMachineIdCompNameField.requestFocus();
    }

    /**
    * Returns the user to the main form.
    * Returns the user to the main form without modifying a part.
    */
    @FXML
    private void modifyPartCancelHandler(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
    *Applies the attributes of the part to the text fields on the Modify Part form.
    * Checks whether part is InHouse or Outsourced and sets data to appropriate text field.
    */
    public void sendPart(Part part){
        modPartIdTextField.setText(String.valueOf(part.getId()));
        modPartNameField.setText(part.getName());
        modPartInvField.setText(String.valueOf(part.getStock()));
        modPartPriceCostField.setText(String.valueOf(part.getPrice()));
        modPartMaxField.setText(String.valueOf(part.getMax()));
        modPartMinField.setText(String.valueOf(part.getMin()));
        

        
        
        if (part instanceof InHouse){
            modifyIsInHouse = true;
            inHouseRadioButton.setSelected(true);
            inHouseRadioButton.requestFocus();
            modPartMachineIdCompNameField.setText(String.valueOf(((InHouse) part).getMachineId()));
            
            
            
        }
        
        else if (part instanceof Outsourced){
            modifyIsInHouse = false;
            outsourcedRadioButton.setSelected(true);
            outsourcedRadioButton.requestFocus();
            modPartMachineIdCompNameField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            
        }
        
        
        
    }

    /**
     * Saves the part to be modified. 
    * When pressed, the Save button will get the data entered in the text fields and check them
    * to be sure max is greater than min and inv is between max and min. The part is checked to see if
    * it is InHouse or Outsourced and given the appropriate attribute.
    */
    @FXML
    private void saveHandler(ActionEvent event) throws IOException {
        
        try{
            int genId = Integer.parseInt(modPartIdTextField.getText());
            String genName = modPartNameField.getText();
            Double genPrice = Double.parseDouble(modPartPriceCostField.getText());
            int genInv = Integer.parseInt(modPartInvField.getText());
            int genMax = Integer.parseInt(modPartMaxField.getText());
            int genMin = Integer.parseInt(modPartMinField.getText());

            if (genMax < genMin){
                Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
                alertMaxMin.setTitle("ERROR");
                alertMaxMin.setContentText("Min must be less than Max");
                alertMaxMin.showAndWait();
            } else if(genInv > genMax || genInv < genMin){
                Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
                alertMaxMin.setTitle("ERROR");
                alertMaxMin.setContentText("Inv must be between Min and Max");
                alertMaxMin.showAndWait();
            } else if (modifyIsInHouse) {
                int genMachId = Integer.parseInt(modPartMachineIdCompNameField.getText());
                InHouse genericPart = new InHouse(genId,genName,genPrice,genInv,genMax,genMin,genMachId);
                Inventory.updatePart(0, genericPart);
                //Return to main screen
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else if (!modifyIsInHouse) {
                String genCompName = modPartMachineIdCompNameField.getText();
                Outsourced genericPart = new Outsourced(genId,genName,genPrice,genInv,genMax,genMin,genCompName);
                Inventory.updatePart(0, genericPart);
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
