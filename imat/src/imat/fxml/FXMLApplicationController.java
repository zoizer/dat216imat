/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml;

import imat.fxml.button.FXMLUserPageButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 *
 * @author Zoizer
 */
public class FXMLApplicationController implements Initializable {
    @FXML
    private TextField idSearchBar;
    @FXML
    private AnchorPane idTopRight;
    @FXML
    private GridPane idMainBody;
    @FXML
    private ColumnConstraints idMainBodyC0;
    @FXML
    private ColumnConstraints idMainBodyC1;
    @FXML
    private RowConstraints idMainBodyR0;
    @FXML
    private RowConstraints idMainBodyR1;
    @FXML
    private VBox idBottomRight;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTopRight.getChildren().add(new FXMLUserPageButton());
    }    
    
}
