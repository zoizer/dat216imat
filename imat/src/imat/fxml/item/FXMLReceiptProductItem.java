/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import java.io.IOException;
import java.text.NumberFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLReceiptProductItem extends AnchorPane {
    @FXML
    private Label name, amount, price;
    
    public FXMLReceiptProductItem(Product p, double amount) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLReceiptProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        name.setText(p.getName());
        String tmp = p.getUnitSuffix();
        if (tmp.equals("kg")) {
            this.amount.setText("" + String.format("%.2f", amount) + " " + tmp);
        } else /*if (tmp.equals("st"))*/ {
            this.amount.setText("" + Math.round(amount) + " " + tmp);
        }
        price.setText(NumberFormat.getCurrencyInstance().format(p.getPrice() * amount));
    }
}
