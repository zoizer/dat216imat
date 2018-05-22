/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLReceiptProductItem extends AnchorPane {
    public FXMLReceiptProductItem(Product p, double amount) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLReceiptProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
