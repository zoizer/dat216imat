/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import imat.fxml.FXMLApplicationController;
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
    private FXMLProductItem pi;
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
        
        pi = ((FXMLProductContainer)FXMLApplicationController.Get().GetSceneNode(FXMLApplicationController.SceneNode.PRODUCT_CONTAINER)).GetProductItem(p);
        this.p = p;
        this.active = false;
        
        nameLabel.setText(p.getName());
    }
    
    public void SetAmount(double am) {
        pi.SetAmount(am);
    }
    
    public double GetAmount() {
        return pi.GetAmount();
    }
    
    public void Update() {
        amountLabel.setText(pi.GetAmountString());
        priceLabel.setText(NumberFormat.getCurrencyInstance().format(p.getPrice() * pi.GetAmount()));
    }
    
    public void Enable() {
        active = true;
    }
    
    public void Disable() {
        active = false;
        pi.SetAmount(0.0);
    }
    
    public boolean IsActive() {
        return active;
    }
    
    @FXML
    protected void onMouseClick(Event event) {
        FXMLProductContainer tmp = (FXMLProductContainer)FXMLApplicationController.Get().GetSceneNode(FXMLApplicationController.SceneNode.PRODUCT_CONTAINER);
        FXMLApplicationController.Get().DeselectButtons();
        tmp.SetDisplayOne(p);
        tmp.Reload();
        
        FXMLApplicationController.Get().SetMainBody(FXMLApplicationController.SceneNode.PRODUCT_CONTAINER);
    }
}
