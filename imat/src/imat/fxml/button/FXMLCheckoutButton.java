/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.button;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLCheckoutButton extends AnchorPane {
    public FXMLCheckoutButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/button/FXMLCheckoutButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
