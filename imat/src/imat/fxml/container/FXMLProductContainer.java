/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.fxml.item.FXMLLargeProductItem;
import imat.fxml.item.FXMLProductItem;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLProductContainer extends AnchorPane {
    @FXML
    private VBox vb;
    private ProductCategory activeFilter;
    private Map<Product, FXMLProductItem> productMap = new HashMap<>();
    private String searchString = null;
    
    public FXMLProductContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLProductContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        List<Product> p = IMatDataHandler.getInstance().getProducts();
        for (Product e : p) {
            productMap.put(e, new FXMLProductItem(e));
            vb.getChildren().add(productMap.get(e));
        }
        
        
        FXMLProductItem tmp = (FXMLProductItem)vb.getChildren().remove(5);
        vb.getChildren().add(4, new FXMLLargeProductItem(tmp.GetProduct()));
    }
    
    public void SetCategory(ProductCategory cat) {
        activeFilter = cat;
        searchString = null;
    }
    
    public void SetSearchString(String search){
        searchString = search;
        activeFilter = null;
    }
    
    public void nullSearchVariables(){
        searchString = null;
        activeFilter = null;
    }
    
    public void Reload() {
        vb.getChildren().clear();
        if (activeFilter != null) {
            List<Product> l = IMatDataHandler.getInstance().getProducts(activeFilter);
            for (Product e : l) {
                vb.getChildren().add(productMap.get(e));
            }
        } else if(searchString != null) {
            List<Product> l = IMatDataHandler.getInstance().findProducts(searchString);
            for (Product e : l) {
                vb.getChildren().add(productMap.get(e));
            }
        } else {
            List<Product> l = IMatDataHandler.getInstance().getProducts();
            for (Product e : l) {
                vb.getChildren().add(productMap.get(e));
            }
        }
    }
}
