/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This class creates Outsourced parts.
 * This class creates and modifies parts and their attributes.
 * @author Jessie Van Epps
 */
public class Outsourced extends Part {
    
    private String companyName;
    
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** Returns the companyName of the part.
     * Gets the String companyName
     * @return Returns the String companyName.
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /** Sets the companyName String.
     * Sets the String companyName to text entered.
     * @param companyName Sets the String companyName of the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
    
}
