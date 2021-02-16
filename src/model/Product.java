/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates Products.
 * This class creates and modifies products.
 * @author jveps
 * 
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;    
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets the ID of a product. 
     * Returns the ID of a product.
     * @return Returns the ID of a product.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of a product.
     * Sets the ID of a product.
     * @param id the id to be set to the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of a product.
     * Returns the name of a product.
     * @return Returns the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a product.
     * Sets the name of a product to the name entered by the user.
     * @param name the name to set the product to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of a product.
     * Returns the price of a product.
     * @return Returns the price of a product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of a product.
     * Sets the price of a product to the double entered by the user.
     * @param price The price to be applied to the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Returns the inventory level of the product.
     * Returns the int associated with the inventory level.
     * @return Returns the int of the amount in stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock integer.
     * Sets the stock integer to the int entered by the user.
     * @param stock The integer to be set as stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the int associated with min.
     * Returns the int associated with min.
     * @return Returns int min.
     */
    public int getMin() {
        return min;
    }

    /** Sets the min attribute. 
     * Sets the int min to the int entered by the user.
     * @param min The int min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Returns the int associated with max. 
     * Returns the int associated with max.
     * @return Returns the int max.
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the int max.
     * Sets the int max to the int entered by the user.
     * @param max The int entered by the user to set the int max to.
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Adds a part to a product. 
     * Associated a part to a particular product.
     * @param part The part the user wants to add to a product.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /** Deletes a part from a product.
     * Removes a part associated with a product.
     * @return Returns a boolean whether or not the part as removed.
     */
    public boolean deleteAssociatedPart(Part part){
        return associatedParts.remove(part);
        
    }
    
    /** Gets all parts associated with product.
     * Gets ObservableList of all parts associated with a product.
     * @return Returns an ObservableList of all parts associated with a product.
     */
     public  ObservableList<Part> getAllAssociatedParts(){
         return associatedParts;
     }
}
