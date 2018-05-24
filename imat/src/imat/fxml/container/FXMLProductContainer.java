/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.fxml.item.FXMLProductItem;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    @FXML
    private HBox empty;
    
    private ProductCategory activeFilter;
    private Map<Product, FXMLProductItem> productMap = new HashMap<>();
    private String searchString = null;
    private Product singleProduct = null;
    private boolean favFilter = false;
    private boolean shoppingCartFilter = false;
    
    private HBox varEmpty;
    
    public FXMLProductContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLProductContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        varEmpty = empty;
        
        
        List<Product> p = IMatDataHandler.getInstance().getProducts();
        for (Product e : p) {
            productMap.put(e, new FXMLProductItem(e));
        }
        
        Reload();
    }
    
    public FXMLProductItem GetProductItem(Product p) {
        return productMap.get(p);
    }
    
    public void SetCategory(ProductCategory cat) {
        nullSearchVariables();
        activeFilter = cat;
    }
    
    public void SetSearchString(String search){
        nullSearchVariables();
        searchString = search;
    }
    
    public void SetDisplayOne(Product p) {
        nullSearchVariables();
        singleProduct = p;
    }
    
    public void ShowFavorites() {
        nullSearchVariables();
        favFilter = true;
    }
    
    public void ShowShoppingCart() {
        nullSearchVariables();
        shoppingCartFilter = true;
    }
    
    public void nullSearchVariables(){
        searchString = null;
        activeFilter = null;
        singleProduct = null;
        favFilter = false;
        shoppingCartFilter = false;
    }
    
    public final void Reload() {
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
        } else if (singleProduct != null) {
            vb.getChildren().add(productMap.get(singleProduct));
        } else if (favFilter) {
            for (Map.Entry<Product, FXMLProductItem> entry : productMap.entrySet()) {
                if (entry.getValue().IsFavorite()) vb.getChildren().add(entry.getValue());
            }
        } else if(shoppingCartFilter) {
            for (Map.Entry<Product, FXMLProductItem> entry : productMap.entrySet()) {
                if (entry.getValue().GetAmount() > 0.0001) vb.getChildren().add(entry.getValue());
            }
        } else {
            List<Product> l = IMatDataHandler.getInstance().getProducts();
            for (Product e : l) {
                vb.getChildren().add(productMap.get(e));
            }
        }
        if (vb.getChildren().isEmpty()) {
            vb.getChildren().add(varEmpty);
        }
    }
}
