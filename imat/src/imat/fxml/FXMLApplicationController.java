/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml;

import imat.Imat;
import imat.fxml.button.FXMLCategoryButton;
import imat.fxml.container.*;
import imat.fxml.button.FXMLCheckoutButton;
import imat.fxml.button.FXMLInventoryButton;
import imat.fxml.button.FXMLReceiptButton;
import imat.fxml.button.FXMLUserPageButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;


/**
 *
 * @author Zoizer
 */
public class FXMLApplicationController implements Initializable {
    private static FXMLApplicationController singleton;
    
    @FXML
    private TextField idSearchBar;
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
    private AnchorPane mainBtn0;
    @FXML
    private AnchorPane mainBtn1;
    @FXML
    private AnchorPane mainBtn2;
    @FXML
    private VBox idMainBottomLeft;
    @FXML
    private VBox idMainBottomRight;

    
    private FXMLUserPageButton userPageBtn;
    private FXMLReceiptButton receiptBtn;
    private FXMLInventoryButton inventoryBtn;
    private FXMLCheckoutButton checkoutBtn;
    private FXMLProductContainer productCtn;
    private FXMLMyPageContainer mypageCtn;
    private FXMLCheckoutContainer checkoutCtn;
    private FXMLInventoryContainer invCtn;
    private FXMLReceiptContainer receiptCtn;
    private FXMLConfirmationContainer confirmCtn;
    
    private ToggleGroup t;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert singleton == null;
        singleton = this;
        
        t = new ToggleGroup();

        userPageBtn = new FXMLUserPageButton();
        inventoryBtn = new FXMLInventoryButton();
        receiptBtn = new FXMLReceiptButton();
        checkoutBtn = new FXMLCheckoutButton();
        userPageBtn.setToggleGroup(t);
        inventoryBtn.setToggleGroup(t);
        receiptBtn.setToggleGroup(t);
        checkoutBtn.setToggleGroup(t);
        
        productCtn = new FXMLProductContainer();
        mypageCtn = new FXMLMyPageContainer();
        invCtn = new FXMLInventoryContainer();
        checkoutCtn = new FXMLCheckoutContainer();
        receiptCtn = new FXMLReceiptContainer();
        confirmCtn = new FXMLConfirmationContainer();
        
        mainBtn1.getChildren().add(receiptBtn);
        mainBtn2.getChildren().add(userPageBtn);
        idMainTopLeft.getChildren().add(new FXMLCategoryContainer(t));
        mainBtn0.getChildren().add(inventoryBtn);
        idMainBottomLeft.getChildren().add(productCtn);
        idMainBottomRight.getChildren().add(invCtn);
        idMainBottomRight.getChildren().add(checkoutBtn);
        
        
        t.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (t.getSelectedToggle() != null) {
                idSearchBar.setText("");
                if (t.getSelectedToggle() instanceof FXMLCategoryButton) {
                    FXMLCategoryButton selected = (FXMLCategoryButton) t.getSelectedToggle();
                    SetMainBody(productCtn);
                    if (selected == null) {
                        productCtn.SetCategory(null);
                        System.out.println("WILL THIS EVER HAPPEN?????");
                    } else {
                        if (selected.GetCategory() == null) {
                            productCtn.ShowFavorites();
                        } else productCtn.SetCategory(selected.GetCategory());
                    }
                    productCtn.Reload();
                } else if (t.getSelectedToggle() instanceof FXMLInventoryButton) {
                    productCtn.ShowShoppingCart();
                    productCtn.Reload();
                    SetMainBody(productCtn);
                } else if (t.getSelectedToggle() instanceof FXMLUserPageButton) {
                    SetMainBody(mypageCtn);
                }
                else if (t.getSelectedToggle() instanceof FXMLCheckoutButton) {
                    SetMainBody(checkoutCtn);
                    checkoutCtn.Activate();
                }
                else if (t.getSelectedToggle() instanceof FXMLReceiptButton) {
                    receiptCtn.Update();
                    SetMainBody(receiptCtn);
                }
                
            } else {
                productCtn.SetCategory(null);
                productCtn.Reload();
                SetMainBody(productCtn);
            }
                
        });
        
        idSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // NOOP
            } else {
                t.selectToggle(null);
                productCtn.SetSearchString(newValue);
                productCtn.Reload();
                SetMainBody(productCtn);
            }
        });
    }    
    
    public void DeselectButtons() {
        t.selectToggle(null);
    }
    
    public static FXMLApplicationController Get() {
        assert singleton != null;
        return singleton;
    }
    
    public enum SceneNode {
        PRODUCT_CONTAINER,
        MY_PAGE_CONTAINER,
        USER_PAGE_BUTTON,
        INVENTORY_BUTTON,
        CHECKOUT_BUTTON,
        INVENTORY_CONTAINER,
        RECEIPT_CONTAINER,
        CHECKOUT_CONTAINER,
        CONFIRM_CONTAINER,
    }
    
    public void SetMainBody(Node n) {
        if (idMainBottomLeft.getChildren().get(0).equals(n)) return;
        idMainBottomLeft.getChildren().clear();
        idMainBottomLeft.getChildren().add(n);
    }
    
    public void SetMainBody(SceneNode t) {
        SetMainBody(GetSceneNode(t));
    }
    
    public Node GetSceneNode(SceneNode t) {
        switch (t) {
            case PRODUCT_CONTAINER: return productCtn;
            case MY_PAGE_CONTAINER: return mypageCtn;
            case USER_PAGE_BUTTON: return userPageBtn;
            case INVENTORY_BUTTON: return inventoryBtn;
            case CHECKOUT_BUTTON: return checkoutBtn;
            case INVENTORY_CONTAINER: return invCtn;
            case RECEIPT_CONTAINER: return receiptCtn;
            case CHECKOUT_CONTAINER: return checkoutCtn;
            case CONFIRM_CONTAINER: return confirmCtn;
            default: return null;
        }
    }
}
