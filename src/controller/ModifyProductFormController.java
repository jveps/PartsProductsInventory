/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * This class controls the Modify Product Form. 
 * Allows the user to modify products.
 *
 * @author Jessie Van Epps
 */
public class ModifyProductFormController implements Initializable {
    
    Stage stage;
    Parent scene;
    @FXML
    private TextField modifyProductIdTextField;
    @FXML
    private TextField modifyProductNameTextField;
    @FXML
    private TextField modifyProductInvTextField;
    @FXML
    private TextField modifyProductPriceTextField;
    @FXML
    private TextField modifyProductMaxTextField;
    @FXML
    private TextField modifyProductMinTextField;
    @FXML
    private TableView<Part> modifyProductAllPartsTable;
    @FXML
    private TableView<Part> modifyProductSelectedPartsTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInv;
    @FXML
    private TableColumn<Part, Integer> partPrice;
    @FXML
    private TableColumn<Part, Integer> selectedPartId;
    @FXML
    private TableColumn<Part, String> selectedPartName;
    @FXML
    private TableColumn<Part, Integer> selectedPartInv;
    @FXML
    private TableColumn<Part, Integer> selectedPartPrice;
    
    Product testProd;
    private ObservableList<Part> tableSelectedParts = FXCollections.observableArrayList();
    @FXML
    private TextField modifyProductSearchText;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        modifyProductIdTextField.setEditable(false);
        
        //Populates Part Table
        modifyProductAllPartsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    

    /**
    * Returns the user to the main form.
    * Returns the user to the main form without modifying a product.
    */
    @FXML
    private void modifyProductCancelHandler(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Applies the associated attributes of product to text fields.
    * Applies product attributes to the text fields, populating them with relevant data. Populates the table with associated parts.
    */
    public void sendProduct(Product product){
        modifyProductIdTextField.setText(String.valueOf(product.getId()));
        modifyProductNameTextField.setText(product.getName());
        modifyProductInvTextField.setText(String.valueOf(product.getStock()));
        modifyProductPriceTextField.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTextField.setText(String.valueOf(product.getMax()));
        modifyProductMinTextField.setText(String.valueOf(product.getMin()));
        testProd = product;
        
        //Test for part data from product
        modifyProductSelectedPartsTable.setItems(Inventory.lookupProduct(testProd.getId()).getAllAssociatedParts());
        selectedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }

    /**
    * Removes parts from the bottom table after asking confirmation.
    * Removes a part from the bottom table, disassociating it from a product.
    */
    @FXML
    private void modProductRemovePartHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
                
            testProd.deleteAssociatedPart(modifyProductSelectedPartsTable.getSelectionModel().getSelectedItem());
        }
        
        
    }

    /**
    * Adds parts to the bottom table.
    * Adds parts to the bottom table, associating it with the product.
    */
    @FXML
    private void modifyProductAddHandler(ActionEvent event) {
        testProd.addAssociatedPart(modifyProductAllPartsTable.getSelectionModel().getSelectedItem());
        
         //Populates Part Table
        modifyProductAllPartsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modifyProductSearchText.clear();
    }

    /**
    * Gets the data from the text fields and adds them to a product. 
    * The attributes of this product are checked to see if max is greater than min and that inv is between max and min. The product is then added to 
    * the inventory. The user is returned to the main screen.
    */
    @FXML
    private void modifyProductSaveHandler(ActionEvent event) throws IOException {
        
        try{
            testProd.setId(Integer.parseInt(modifyProductIdTextField.getText()));
            testProd.setName(modifyProductNameTextField.getText());
            testProd.setStock(Integer.parseInt(modifyProductInvTextField.getText()));
            testProd.setPrice(Double.parseDouble(modifyProductPriceTextField.getText()));
            testProd.setMin(Integer.parseInt(modifyProductMinTextField.getText()));
            testProd.setMax(Integer.parseInt(modifyProductMaxTextField.getText()));

            if (testProd.getMax() < testProd.getMin()){
                Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
                alertMaxMin.setTitle("ERROR");
                alertMaxMin.setContentText("Min must be less than Max");
                alertMaxMin.showAndWait();
            } else if(testProd.getStock() > testProd.getMax() || testProd.getStock() < testProd.getMin()){
                Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
                alertMaxMin.setTitle("ERROR");
                alertMaxMin.setContentText("Inv must be between Max and Min");
                alertMaxMin.showAndWait();
            } else{
                Inventory.updateProduct(0, testProd);


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

    /**
    * Searches inventory for relevant parts, displays them on the table.
    * Searches inventory for parts based on data entered by the user.
    */
    @FXML
    private void modifyProductSearchHandler(ActionEvent event) {
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        searchParts.clear();
        for (Part p : Inventory.getAllParts()){
            
            if (p.getName().toLowerCase().contains(modifyProductSearchText.getText().toLowerCase())){
                searchParts.add(p);
                
                
            } else if(String.valueOf(p.getId()).toLowerCase().contains(modifyProductSearchText.getText().toLowerCase())){
                searchParts.add(p);
            }
        }
        
         //Notification if no part found
        if (searchParts.size() == 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Alert");
            a.setContentText("Part not found");
            a.showAndWait();
        }
        
        modifyProductAllPartsTable.setItems(searchParts);
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
         //Highlight selected item if only 1
        if (searchParts.size() == 1){
            modifyProductAllPartsTable.getSelectionModel().select(searchParts.get(0));
        }
    }
    
    
    
    
    
}
