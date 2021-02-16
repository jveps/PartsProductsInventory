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
 * Controls the Add Product Form. 
 * Allows the user to add products to the inventory.
 *
 * @author Jessie Van Epps
 */
public class AddProductFormController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TextField addProductIdTextField;
    @FXML
    private TextField addProductNameTextField;
    @FXML
    private TextField addProductInvTextField;
    @FXML
    private TextField addProductPriceTextField;
    @FXML
    private TextField addProductMaxTextField;
    @FXML
    private TextField addProductMinTextField;
    @FXML
    private TableView<Part> addProductAllPartsTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInv;
    @FXML
    private TableColumn<Part, Integer> partPrice;
    
    @FXML
    private TableView<Part> selectedPartsTable;
    @FXML
    private TableColumn<Part, Integer> selectedPartId;
    @FXML
    private TableColumn<Part, String> selectedPartName;
    @FXML
    private TableColumn<Part, Integer> selectedPartInv;
    @FXML
    private TableColumn<Part, Integer> selectedPartPrice;

    /**
     * Initializes the controller class.
     */
    
    private ObservableList<Part> tableSelectedParts = FXCollections.observableArrayList();
    @FXML
    private TextField addProductSearchText;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int genId = 0;
        for (Product prod : Inventory.getAllProducts()){
            
            if (prod.getId() > genId){
                genId = prod.getId();
            }
        }
        
        genId += 1;
        addProductIdTextField.setText(String.valueOf(genId));
        addProductIdTextField.setEditable(false);
        
        //Populates Part Table
        addProductAllPartsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        selectedPartsTable.setItems(tableSelectedParts);
        selectedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        
    }    

    /**
    *When pressed, the user returns to the main screen. 
    * This cancels the creation of a product and returns the user to the main screen. 
    */
    @FXML
    private void addProductCancelHandler(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
    *When pressed, a new product is created using the data entered by the user.
    * The data is checked to see if Max is greater than Min and that the inventory is not greater than Max
    * and that the inventory is not less than Min. The Product is added to the Inventory, then the parts are associated
    * with the product.
    */
    @FXML
    private void addProductSaveHandler(ActionEvent event) throws IOException {
        
        try{
            Product p = new Product(
                Integer.parseInt(addProductIdTextField.getText()),
                addProductNameTextField.getText(),
                Double.parseDouble(addProductPriceTextField.getText()),
                Integer.parseInt(addProductInvTextField.getText()),
                Integer.parseInt(addProductMinTextField.getText()),
                Integer.parseInt(addProductMaxTextField.getText())
        );
        
        if (p.getMax() < p.getMin()){
            Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
            alertMaxMin.setTitle("ERROR");
            alertMaxMin.setContentText("Min must be less than Max");
            alertMaxMin.showAndWait();
        } else if(p.getStock() > p.getMax() || p.getStock() < p.getMin()){
            Alert alertMaxMin = new Alert(Alert.AlertType.ERROR);
            alertMaxMin.setTitle("ERROR");
            alertMaxMin.setContentText("Inv must be between Max and Min");
            alertMaxMin.showAndWait();
        } else{
            Inventory.addProduct(p);
        
        
            for (Part addedPs : tableSelectedParts){

                Inventory.lookupProduct(Integer.parseInt(addProductIdTextField.getText())).addAssociatedPart(addedPs);

            }

            tableSelectedParts.clear();




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
    *
    * This adds the user's selection to tableSelectedParts to be displayed on the 
    * table of parts to be associated with the product.
    * Adds a part to the bottom table of the screen, associating them with the new product.
    */
    @FXML
    private void addPartHandler(ActionEvent event) {
        
        tableSelectedParts.add(addProductAllPartsTable.getSelectionModel().getSelectedItem());
        //Populates Part Table
        addProductAllPartsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProductSearchText.clear();
        
    }

    /**
    *This removes a part from association with the  product being created. 
    * Removes a part from the bottom table, disassociating it from the new product.
    */
    @FXML
    private void removeAssociatedPartHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
                
            tableSelectedParts.remove(addProductAllPartsTable.getSelectionModel().getSelectedItem());
        }
        
    }

    /**
    *Searches inventory for string in text field, displays relevant info from list to table. 
    * Searches the inventory for a part that matches the text in the search field. Displays relevant results on table.
    */
    @FXML
    private void AddProductSearchHandler(ActionEvent event) {
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        searchParts.clear();
        for (Part p : Inventory.getAllParts()){
            
            if (p.getName().toLowerCase().contains(addProductSearchText.getText().toLowerCase())){
                searchParts.add(p);
                
                
            } else if(String.valueOf(p.getId()).toLowerCase().contains(addProductSearchText.getText().toLowerCase())){
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
        
        addProductAllPartsTable.setItems(searchParts);
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Highlight selected item if only 1
        if (searchParts.size() == 1){
            addProductAllPartsTable.getSelectionModel().select(searchParts.get(0));
        }
    }
    
}
