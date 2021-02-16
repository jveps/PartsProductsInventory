/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies the Inventory.
 * Allows the user to modify the contents of the inventory.
 * @author jveps
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
    *
    * Adds a part to allParts. 
    * @param newPart Adds a part to the inventory.
    */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
    *
    * Adds a product to allProducts. 
    * @param newProduct Adds a product to the inventory.
    */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
    *
    * Looks up a part by matching partId to the Id of each part in allParts. 
    * Finds a part using the part's ID.
    * @param partId The part ID of the part the user is searching for.
    * @return Returns the part with the entered part ID.
    */
    public static Part lookupPart(int partId){
        //return allParts.get(partId);
        
        int partLocation = 0;
        for (int i = 0; i < allProducts.size(); i++){
            if (allParts.get(i).getId() == partId){
                
                partLocation = i;
            }
            
        }
        return allParts.get(partLocation);
    }
    /**
     * Looks up Product using the product ID. 
     * Finds a product using the product's ID. 
    *My initial instance of lookupProduct contained a logical error that caused it to return 
    * incorrect products. 
    * Initially, the productId was used incorrectly with allProducts.get() and returned the
    * list item with the index equal to productId rather than matched productId with the result of
    * getId(). To solve this error, I modified the method with a for loop that checks the Id of each product with 
    * productId.
    * @param productId The product ID of the product the user is looking for.
    * @return Returns a Product with the entered product ID.
    */
    public static Product lookupProduct(int productId){
        //Not correct
        //allProducts.get(productId);
        
        int productLocation = 0;
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getId() == productId){
                
                productLocation = i;
            }
            
        }
        return allProducts.get(productLocation);
        
        
    }
    
    /**
    *Looks up a part by searching the names of each part in allParts. 
    * Searches for a part using the name of the part. 
    * @param partName The name of the part searched for.
    * @return Returns the part found.
    */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> matchingPart = FXCollections.observableArrayList();
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getName().contains(partName)){
                matchingPart.add(allParts.get(i));
            }
        }
        
        return matchingPart;
    }
    
    /**
    * Looks up a product by searching all names of products in allProducts. 
    * Searches for a product using the name of the product.
    * @param productName The name of the product searched for.
    * @return Returns the product found.
    * 
    */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProduct = FXCollections.observableArrayList();
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getName().contains(productName)){
                matchingProduct.add(allProducts.get(i));
            }
        }
        
        return matchingProduct;
        
    }
    
    /**
    *
    * Matches the Id of a part in the inventory and replaces it with an updated part. 
    * Replaces a part with a modified version of that part.
    * @param selectedPart The part to be replaced.
    */
    public static void updatePart(int index, Part selectedPart){
        
        index = -1;
        for (Part part : Inventory.getAllParts()){
            index++;
            if (part.getId() == selectedPart.getId()){
                
                Inventory.getAllParts().set(index, selectedPart);
            }
            
        }
    }
    
    /**
    *
    * Matches the Id of a product in the inventory and replaces it with an updated product. 
    * Replaces a product with a modified version of that product.
    * @param selectedProduct The product to be replaced.
    */
    public static void updateProduct(int index, Product selectedProduct){
        index = -1;
        for (Product product : Inventory.getAllProducts()){
            index++;
            if (product.getId() == selectedProduct.getId()){
                
                Inventory.getAllProducts().set(index, selectedProduct);
            }
            
        }
    }
    
    /**
    *
    * Deletes a part from allParts. 
    * Removes a particular part from the inventory.
    * @param selectedPart 
    * @return Returns a boolean depending on whether part was deleted.
    */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    
    /**
    *
    * Deletes a product from allProducts. 
    * Removes a particular product from the inventory.
    * @param selectedProduct 
    * @return Returns a boolean depending on whether product was deleted.
    */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
    
    /**
    *
    * Returns ObservableList containing parts in allParts. 
    * Returns an ObservableList of all parts in the inventory. 
    * @return Returns an ObservableList of Parts. 
    */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
    *
    * Returns ObservableList containing products in allProducts. 
    * Returns an ObservableList of all products in the inventory.
    * @return Returns an ObservableList of Products. 
    */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
            
}
