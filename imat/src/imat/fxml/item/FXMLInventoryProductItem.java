/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import imat.Product;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLInventoryProductItem extends AnchorPane {
    public FXMLInventoryProductItem(Product p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLInventoryProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
