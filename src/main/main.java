package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * This is the main class. 
 * This class starts the application and adds test data to the tables.
 * Compatible feature for the next version:
 * Information that categorizes parts would be useful, as well as the ability to check if the parts associated
 * with a product makes sense. If this application was used at a store that sells Apple products, a user could accidentally
 * add the part "Logic Board" to the product "AirPods" with no error, although there should be one. Adding additional
 * attributes to parts that only allow them to be associated with certain products could prevent this from happening.
 *
 * @author Jessie Van Epps
 * 
 
 */
public class main extends Application{

    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        //Test data
        InHouse part1 = new InHouse(758,"Screw",10.41,99,1,12,684);
        InHouse part2 = new InHouse(358,"Wrench",9.99,99,1,12,654);
        InHouse part3 = new InHouse(558,"Screwdriver",13.99,99,1,12,331);
        Outsourced part4 = new Outsourced(975, "Fan", 14.99,1,12,66,"COMPNAME");
        
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        
        Product prod1 = new Product(45,"TestProd1",55.55,100,1,1000);
        Product prod2 = new Product(94,"TestProd2",2.55,100,1,1000);
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
       
        
        
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
