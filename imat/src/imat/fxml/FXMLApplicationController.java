/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml;

import imat.fxml.container.FXMLCategoryContainer;
import imat.fxml.button.FXMLCheckoutButton;
import imat.fxml.button.FXMLInventoryButton;
import imat.fxml.button.FXMLUserPageButton;
import imat.fxml.container.FXMLProductContainer;
import imat.fxml.container.FXMLInventoryContainer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private HBox idMainTopLeft;
    @FXML
    private AnchorPane idMainTopRight;
    @FXML
    private VBox idMainBottomLeft;
    @FXML
    private VBox idMainBottomRight;
    
    
    private FXMLUserPageButton userPageBtn;
    private FXMLInventoryButton inventoryBtn;
    private FXMLCheckoutButton checkoutBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userPageBtn = new FXMLUserPageButton();
        inventoryBtn = new FXMLInventoryButton();
        checkoutBtn = new FXMLCheckoutButton();
        
        idTopRight.getChildren().add(userPageBtn);
        idMainTopLeft.getChildren().add(new FXMLCategoryContainer());
        idMainTopRight.getChildren().add(inventoryBtn);
        idMainBottomLeft.getChildren().add(new FXMLProductContainer());
        idMainBottomRight.getChildren().add(new FXMLInventoryContainer());
        idMainBottomRight.getChildren().add(checkoutBtn);
    }    
    
}
