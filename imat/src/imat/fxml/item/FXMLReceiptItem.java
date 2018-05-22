/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import imat.fxml.container.FXMLReceiptContainer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLReceiptItem extends ToggleButton {
    private Order order;
    
    public FXMLReceiptItem(Order o, ToggleGroup t, FXMLReceiptContainer p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLReceiptItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(p);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        order = o;
        super.setToggleGroup(t);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        super.setText(dateFormat.format(o.getDate()));
    }
    
    public Order GetOrder() {
        return order;
    }
}
