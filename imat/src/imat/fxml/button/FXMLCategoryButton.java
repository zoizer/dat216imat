/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.button;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLCategoryButton extends AnchorPane {
    @FXML
    private ImageView img;
    @FXML
    private Label text;
    @FXML
    private ToggleButton tb;
    
    
    public FXMLCategoryButton(Image i, String s) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/button/FXMLCategoryButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        img.setImage(i);
        text.setText(s);
    }
    
    public ToggleButton GetButton() {
        return tb;
    }
}
