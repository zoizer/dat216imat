/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLProductItem extends AnchorPane {
    
    @FXML
    private ImageView img;
    
    @FXML
    private Label name;
    
    @FXML
    private Label cartinfo;
    
    @FXML
    private Label price;
    
    @FXML
    private ImageView imgputback;
    
    @FXML
    private ImageView imgtake;
    
    private Product p;
    
    public FXMLProductItem(Product p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.p = p;
        name.setText(p.getName());
        price.setText(String.format("%.2f", p.getPrice()) + " " + p.getUnit());
        img.setImage(IMatDataHandler.getInstance().getFXImage(p, 64.0, 64.0));
    }
    
    public Product GetProduct() {
        return p;
    }
}
