/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.fxml.item.FXMLInventoryProductItem;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLInventoryContainer extends AnchorPane {
    
    private HashMap<Product, FXMLInventoryProductItem> items;
    
    @FXML
    private VBox vbox;
    @FXML
    private GridPane header;
    
    @FXML
    private HBox noinventory;
    
    private HBox varNoInventory;
    private boolean hasInventory;
    
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
        
        varNoInventory = noinventory;
        
        List<Product> p = IMatDataHandler.getInstance().getProducts();
        for (Product e : p) {
           items.put(e, new FXMLInventoryProductItem(e));
        }
    }
    
    public final void UpdateInventory(Product p, double change) {
        FXMLInventoryProductItem tmp = items.get(p);
        if (tmp == null) return;
        else if (!tmp.IsActive()) {
            if (change > 0) {
                tmp.SetAmount(change);
                tmp.Update();
                DisplayProduct(tmp);
            } else return;
        } else {
            tmp.SetAmount(tmp.GetAmount() + change);
            if (tmp.GetAmount() <= 0.0001) {
                HideProduct(tmp);
            } else {
                tmp.Update();
            }
        }
    }
    
    private void HideProduct(FXMLInventoryProductItem p) { // hide product from flow.
        p.Disable();
        vbox.getChildren().remove(p);
        
        if (vbox.getChildren().isEmpty()) {
            vbox.getChildren().add(varNoInventory);
            hasInventory = false;
        }
    }
    
    private void DisplayProduct(FXMLInventoryProductItem p) {
        if (!hasInventory) {
            vbox.getChildren().clear();
        }
        
        p.Enable();
        vbox.getChildren().add(p);
        hasInventory = true;
    }
    
    public void CreateBackendShoppingCart() {
        ShoppingCart cart = IMatDataHandler.getInstance().getShoppingCart();
        cart.clear();
        for (Map.Entry<Product, FXMLInventoryProductItem> e : items.entrySet()) {
            if (e.getValue().IsActive()) {
                cart.addProduct(e.getKey(), e.getValue().GetAmount());
            }
        }
    }
    
    public void ClearShoppingCart() {
        ShoppingCart cart = IMatDataHandler.getInstance().getShoppingCart();
        cart.clear();
        for (Map.Entry<Product, FXMLInventoryProductItem> e : items.entrySet()) {
            if (e.getValue().IsActive()) {
                HideProduct(e.getValue());
            }
        }
    }
    
    public boolean IsEmpty() {
        for (Map.Entry<Product, FXMLInventoryProductItem> e : items.entrySet()) {
            if (e.getValue().IsActive()) return false;
        }
        return true;
    }
}
