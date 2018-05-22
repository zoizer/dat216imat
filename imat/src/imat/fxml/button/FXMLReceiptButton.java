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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLReceiptButton extends ToggleButton {
    @FXML
    private ImageView img;
    @FXML
    private Label text;
    @FXML
    private GridPane imgGrid;
    
    public FXMLReceiptButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/button/FXMLReceiptButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        img.fitWidthProperty().bind(imgGrid.widthProperty());
        img.fitHeightProperty().bind(imgGrid.heightProperty());
    }
}
