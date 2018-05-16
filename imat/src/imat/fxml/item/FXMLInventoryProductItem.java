/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import imat.Product;
import java.io.IOException;
import java.text.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    private int amount;
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
        NumberFormat f = NumberFormat.getCurrencyInstance();
        
        this.amount = 0;
        this.p = p;
        this.active = false;
        
        nameLabel.setText(p.toString());
        priceLabel.setText(f.format(p.GetPrice()));
    }
    
    public void SetAmount(int am) {
        amount = am;
    }
    
    public int GetAmount() {
        return amount;
    }
    
    public void Update() {
        amountLabel.setText("" + amount);
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
}
