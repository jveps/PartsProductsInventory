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
 * This class controls the Main Form.
 * Allows a user to manage the inventory of parts and products.
 *
 * @author jveps
 */
public class MainFormJFXController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private Button Search;
    @FXML
    private TextField SearchText;
    @FXML
    private TableColumn<Part, Integer> ID;
    @FXML
    private TableColumn<Part, String> Name;
    @FXML
    private TableColumn<Part, Integer> InStock;
    @FXML
    private TableColumn<Part, Double> Price;
    @FXML
    private TextField SearchText1;
    @FXML
    private TableColumn<Product, Integer> ID1;
    @FXML
    private TableColumn<Product, String> Name1;
    @FXML
    private TableColumn<Product, Integer> InStock1;
    @FXML
    private TableColumn<Product, Double> Price1;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Product> productTable;
    
    
    

    /**
     * Initializes the controller class. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Populates Part Table
        partsTable.setItems(Inventory.getAllParts());
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        InStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Populates Products table
        productTable.setItems(Inventory.getAllProducts());
        ID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        InStock1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Price1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    

    /**
    *Searches Inventory for string in text field. 
    * Adds relevant parts to a list and adds data to table. 
    */
    @FXML
    private void searchHandler(ActionEvent event) {
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        searchParts.clear();
        for (Part p : Inventory.getAllParts()){
            
            if (p.getName().toLowerCase().contains(SearchText.getText().toLowerCase())){
                searchParts.add(p);
                
                
            } else if(String.valueOf(p.getId()).toLowerCase().contains(SearchText.getText().toLowerCase())){
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
        
        
        
        partsTable.setItems(searchParts);
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        InStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Highlight selected item if only 1
        if (searchParts.size() == 1){
            partsTable.getSelectionModel().select(searchParts.get(0));
        }
        
    }

    /**
    * This takes user to the Add part form. 
    * When pressed, the user is taken to a screen where they can add a new part. 
    */
    @FXML
    private void addHandler(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
    * This takes the user to the modify part form. 
    * When pressed, the user is taken to a screen where they can modify a part.
    */
    @FXML
    private void modifyHandler(ActionEvent event) throws IOException {
        if (partsTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a part");
            a.showAndWait();
        } else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            loader.load();
            ModifyPartFormController MPFController = loader.getController();
            MPFController.sendPart(partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            //scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            //stage.showAndWait();
            stage.show();
        }
        /*stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();*/
        
    }

    /**
    * This deletes a part from the table after asking for confirmation. 
    * When pressed, this deletes a selected part from the table.
    */
    @FXML
    private void deleteHandler(ActionEvent event) {
        if (partsTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a part");
            a.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
            }
        }
        //Populates Part Table
        partsTable.setItems(Inventory.getAllParts());
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        InStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        SearchText.clear();
        
        
    }


    /**
    * This deletes a product from the table after asking for confirmation. 
    * When pressed, this deletes a selected product from the products table.
    */
    @FXML
    private void deleteHandler1(ActionEvent event) {
        if (productTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a product");
            a.showAndWait();
        } else{
            Product p = productTable.getSelectionModel().getSelectedItem();
            if (Inventory.lookupProduct(p.getId()).getAllAssociatedParts().size() > 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Can't delete product that contains parts");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                }

            }
        }
        //Refresh product table
        productTable.setItems(Inventory.getAllProducts());
        ID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        InStock1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Price1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }

    /**
    * This closes the application. 
    * Closes the application.
    */
    @FXML
    private void exitHandler(ActionEvent event) {
        System.exit(0);
    }

    /**
    * This takes a user to the Add product form. 
    * This takes the user to a screen where they are able to add a new product.
    */
    @FXML
    private void addProductHandler(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
    * This takes the user to the Modify product form. 
    * When pressed, this allows the user to modify an existing product.
    */
    @FXML
    private void modifyProductHandler(ActionEvent event) throws IOException {
        if (productTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a product");
            a.showAndWait();
        } else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();
            ModifyProductFormController MPFController = loader.getController();
            MPFController.sendProduct(productTable.getSelectionModel().getSelectedItem());


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            //scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            //stage.showAndWait();
            stage.show();
        }
        
        
    }

    /**
    * This searches for products and displays the relevant ones on the table. 
    * Allows the user to narrow down the items on the table to only ones relevant in the search field.
    */
    @FXML
    private void searchHandler1(ActionEvent event) {
        ObservableList<Product> searchProducts = FXCollections.observableArrayList();
        searchProducts.clear();
        for (Product p : Inventory.getAllProducts()){
            
            if (p.getName().toLowerCase().contains(SearchText1.getText().toLowerCase())){
                searchProducts.add(p);
                
                
            } else if(String.valueOf(p.getId()).toLowerCase().contains(SearchText1.getText().toLowerCase())){
                searchProducts.add(p);
            }
        }
        
        //Notification if no part found
        if (searchProducts.size() == 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Alert");
            a.setContentText("Product not found");
            a.showAndWait();
        }
        
        
        
        productTable.setItems(searchProducts);
        ID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        InStock1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Price1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Highlight selected item if only 1
        if (searchProducts.size() == 1){
            productTable.getSelectionModel().select(searchProducts.get(0));
        }
    }
    

    
}
