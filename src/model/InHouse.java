/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * This class creates InHouse parts.
 * This class creates and modifies parts and their attributes.
 * @author Jessie Van Epps
 */
public class InHouse extends Part {
    
    private int machineId;
    
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /** Gets the machineId of a part. 
     * Returns the machineId of the part.
     * @return Returns the machineId of the part.
     */
    public int getMachineId() {
        return machineId;
    }
    
    /** Sets the machienId of a part. 
     * Sets the machineId of a part.
     * @param machineId Sets the machineId with the entered int.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    
    
    
}
