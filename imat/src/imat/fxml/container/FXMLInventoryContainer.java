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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

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
        List<Product> p = IMatDataHandler.getInstance().getProducts();
        for (Product e : p) {
            UpdateInventory(e, 1);
        }
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
        vbox.getChildren().remove(p);
    }
    
    private void DisplayProduct(FXMLInventoryProductItem p) {
        p.Enable();
        vbox.getChildren().add(p);
    }
}
