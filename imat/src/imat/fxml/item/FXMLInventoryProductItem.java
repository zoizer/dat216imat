/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import imat.fxml.container.FXMLProductContainer;
import java.io.IOException;
import java.text.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLInventoryProductItem extends AnchorPane {
    @FXML
    private Label nameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label priceLabel;
    
    private Product p;
    private double amount;
    private boolean active;
    
    public FXMLInventoryProductItem(Product p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLInventoryProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.amount = 0;
        this.p = p;
        this.active = false;
        
        nameLabel.setText(p.getName());
    }
    
    public void SetAmount(double am) {
        amount = am;
    }
    
    public double GetAmount() {
        return amount;
    }
    
    public void Update() {
        String tmp = p.getUnitSuffix();
        if (tmp.equals("kg")) {
            amountLabel.setText("" + String.format("%.2f", amount) + " " + tmp);
        } else /*if (tmp.equals("st"))*/ {
            amountLabel.setText("" + Math.round(amount) + " " + tmp);
        } /*else {
           // amountLabel.setText("Unknown");
      //     amountLabel.setText(tmp);
        }*/
        priceLabel.setText(NumberFormat.getCurrencyInstance().format(p.getPrice() * amount));
    }
    
    public void Enable() {
        active = true;
    }
    
    public void Disable() {
        active = false;
        amount = 0;
    }
    
    public boolean IsActive() {
        return active;
    }
    
    @FXML
    protected void onMouseClick(Event event) {
        System.out.println("Clicked: " + this.toString());
        FXMLProductContainer.Get().SetDisplayOne(p);
        FXMLProductContainer.Get().Reload();
    }
}
