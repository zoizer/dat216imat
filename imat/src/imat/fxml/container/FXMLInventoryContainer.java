/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.Product;
import imat.fxml.item.FXMLInventoryProductItem;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLInventoryContainer extends AnchorPane {
    
    private HashMap<Product, FXMLInventoryProductItem> items;
    
    @FXML
    private FlowPane flow;
    @FXML
    private GridPane header;
    
    public FXMLInventoryContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLInventoryContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    //    currentRow = 0;
        
        items = new HashMap<>();
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        /**************** TEMPORARY  *******************/
        Product p = new Product("Mjölk", 10.0);
        Product p2 = new Product("Smör", 15.0);
        UpdateInventory(p, 1);
        UpdateInventory(p, 4);
        UpdateInventory(p2, -1);
        UpdateInventory(p2, 3);
    }
    
    public final void UpdateInventory(Product p, Integer change) {
        FXMLInventoryProductItem tmp = items.get(p);
        if (tmp == null) {
            if (change > 0) {
                tmp = new FXMLInventoryProductItem(p);
                tmp.SetAmount(change);
                tmp.Update();
                items.put(p, tmp);
                DisplayProduct(tmp);
            } else return;
        } else if (!tmp.IsActive()) {
            if (change > 0) {
                tmp.SetAmount(change);
                tmp.Update();
                DisplayProduct(tmp);
            } else return;
        } else {
            tmp.SetAmount(tmp.GetAmount() + change);
            if (tmp.GetAmount() <= 0) {
                HideProduct(tmp);
            } else {
                tmp.Update();
            }
        }
    }
    
    private void HideProduct(FXMLInventoryProductItem p) { // hide product from flow.
        p.Disable();
        flow.getChildren().remove(p);
    }
    
    private void DisplayProduct(FXMLInventoryProductItem p) {
        p.Enable();
        flow.getChildren().add(p);
    }
}
