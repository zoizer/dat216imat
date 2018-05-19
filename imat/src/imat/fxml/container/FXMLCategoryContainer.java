/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.fxml.button.FXMLCategoryButton;
import java.io.IOException;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.ProductCategory;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLCategoryContainer extends AnchorPane {
    /*private static class Category {
        public final ProductCategory cat;
        public final Image img;
        public final String text;
        public final FXMLCategoryButton;
    }*/
    private HashMap<FXMLCategoryButton, ProductCategory> cat;
    private int n;
    private ToggleGroup t;
    private FXMLProductContainer p;
    
    @FXML
    private GridPane grid;
    
    public FXMLCategoryContainer(FXMLProductContainer p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLCategoryContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.p = p;
        n = 0;
        cat = new HashMap<>();
        t = new ToggleGroup();
        
        Add(new FXMLCategoryButton(null, "Pasta"), ProductCategory.PASTA);
        Add(new FXMLCategoryButton(null, "Bröd"), ProductCategory.BREAD);
        Add(new FXMLCategoryButton(null, "Mejeri"), ProductCategory.DAIRIES);
        Add(new FXMLCategoryButton(null, "Fisk"), ProductCategory.FISH);
        Add(new FXMLCategoryButton(null, "Bakning"), ProductCategory.FLOUR_SUGAR_SALT);
        Add(new FXMLCategoryButton(null, "Frukt"), ProductCategory.FRUIT);
        Add(new FXMLCategoryButton(null, "Kött"), ProductCategory.MEAT);
        Add(new FXMLCategoryButton(null, "Sött"), ProductCategory.SWEET);
        Add(new FXMLCategoryButton(null, "Favoriter"), null);
        
        t.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (t.getSelectedToggle() != null) {
                FXMLCategoryButton selected = (FXMLCategoryButton) t.getSelectedToggle();
                if (selected == null) {
                    p.SetCategory(null);
                } else {
                    p.SetCategory(cat.get(selected));
                }
            } else {
                p.SetCategory(null);
            }
                
            p.Reload();
        });
    }
    
    private void Add(FXMLCategoryButton b, ProductCategory c) {
        b.setToggleGroup(t);
        cat.put(b, c);
        grid.add(b, n/2, n%2);
        n++;
    }
}
