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
    
    @FXML
    private GridPane grid;
    
    public FXMLCategoryContainer(ToggleGroup t) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLCategoryContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        n = 0;
        cat = new HashMap<>();
        this.t = t;
        
        // We should really just merge some of these categories, every product has only one category but, CABBAGE
        Add(new FXMLCategoryButton(null, "Bär", ProductCategory.BERRY));
        Add(new FXMLCategoryButton(null, "Bröd", ProductCategory.BREAD));
        Add(new FXMLCategoryButton(null, "Sallad", ProductCategory.CABBAGE));
        Add(new FXMLCategoryButton(null, "Citrus frukt", ProductCategory.CITRUS_FRUIT));
        Add(new FXMLCategoryButton(null, "Kalla drycker", ProductCategory.COLD_DRINKS));
        Add(new FXMLCategoryButton(null, "Mejeri", ProductCategory.DAIRIES));
        Add(new FXMLCategoryButton(null, "Exotisk frukt", ProductCategory.EXOTIC_FRUIT));
        Add(new FXMLCategoryButton(null, "Fisk", ProductCategory.FISH));
        Add(new FXMLCategoryButton(null, "Bakning", ProductCategory.FLOUR_SUGAR_SALT));
        Add(new FXMLCategoryButton(null, "Frukt", ProductCategory.FRUIT));
        Add(new FXMLCategoryButton(null, "Örter", ProductCategory.HERB));
        Add(new FXMLCategoryButton(null, "Varma drycker", ProductCategory.HOT_DRINKS));
        Add(new FXMLCategoryButton(null, "Kött", ProductCategory.MEAT));
        Add(new FXMLCategoryButton(null, "Melon", ProductCategory.MELONS));
        Add(new FXMLCategoryButton(null, "Nötter", ProductCategory.NUTS_AND_SEEDS));
        Add(new FXMLCategoryButton(null, "Pasta", ProductCategory.PASTA));
        Add(new FXMLCategoryButton(null, "Baljväxter", ProductCategory.POD));
        Add(new FXMLCategoryButton(null, "Potatis ris", ProductCategory.POTATO_RICE));
        Add(new FXMLCategoryButton(null, "Rotfrukter", ProductCategory.ROOT_VEGETABLE));
        Add(new FXMLCategoryButton(null, "Sött", ProductCategory.SWEET));
        Add(new FXMLCategoryButton(null, "Grönsaker frukt", ProductCategory.VEGETABLE_FRUIT));
        Add(new FXMLCategoryButton(null, "Favoriter", null));
        
    }
    
    private void Add(FXMLCategoryButton b) {
        b.setToggleGroup(t);
        cat.put(b, b.GetCategory());
        grid.add(b, n/2, n%2);
        n++;
    }
}
