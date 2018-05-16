/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.Product;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLReceiptContainer extends AnchorPane {
    private static class ProductReceipt {
        public Integer index;
        public Integer amount;
    }
    
    
//    @FXML
//    private GridPane grid;
    
    private HashMap<Product, ProductReceipt> items;
    private int currentRow;
    private static final int NAME_COL = 0;
    private static final int AMOUNT_COL = 1;
    private static final int PRICE_COL = 2;
    
    /*
    
        TODO
        REMOVE EVERYTHING
        USE FLOWPANE AND MANUALLY ADD NEW GRIDS INSTEAD,
        MIGHT NOT ALIGN FULLY, BUT I CAN USE MANUAL PROPORTIONAL SIZES FOR EACH COLUMN.
    
    */
    
    public FXMLReceiptContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLReceiptContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        currentRow = 0;
        
        items = new HashMap<>();
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    /*public void ChangeReceipt(Product p, Integer change) {
        ProductReceipt tmp = items.get(p);
        if (tmp == null) {
            AddItems(p, change);
        } else {
            tmp.amount += change;
            if (tmp.amount <= 0) {
                RemoveItems(tmp, p);
            } else {
                // update displayed amount.
            }
        }
    }
    
    private void AddItems(Product p, Integer change) {
        Label name = new Label(p.toString());
        Label amount = new Label("" + change);
        Label price = new Label(p.GetPrice());
        
        grid.add(name, currentRow, NAME_COL);
    }
    
    private void RemoveItems(ProductReceipt r, Product p) {
        grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == r.index);
        items.remove(p);
    }*/
    
}
