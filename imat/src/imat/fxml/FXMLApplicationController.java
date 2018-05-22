/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml;

import imat.fxml.button.FXMLCategoryButton;
import imat.fxml.container.FXMLCategoryContainer;
import imat.fxml.button.FXMLCheckoutButton;
import imat.fxml.button.FXMLInventoryButton;
import imat.fxml.button.FXMLUserPageButton;
import imat.fxml.container.FXMLProductContainer;
import imat.fxml.container.FXMLInventoryContainer;
import imat.fxml.container.FXMLMyPageContainer;
import java.net.URL;
import java.util.ResourceBundle;
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
import se.chalmers.cse.dat216.project.Customer;

/**
 *
 * @author Zoizer
 */
public class FXMLApplicationController extends Customer implements Initializable {
    private static FXMLApplicationController singleton;
    
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
    private FXMLProductContainer productCtn;
    private FXMLMyPageContainer mypageCtn;
    private FXMLInventoryContainer invCtn;
    
    private ToggleGroup t;
    private Customer customer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert singleton == null;
        singleton = this;
        
        t = new ToggleGroup();

        userPageBtn = new FXMLUserPageButton();
        inventoryBtn = new FXMLInventoryButton();
        userPageBtn.setToggleGroup(t);
        inventoryBtn.setToggleGroup(t);
        
        checkoutBtn = new FXMLCheckoutButton();
        
        productCtn = new FXMLProductContainer();
        mypageCtn = new FXMLMyPageContainer();
        invCtn = new FXMLInventoryContainer();
        
        idTopRight.getChildren().add(userPageBtn);
        idMainTopLeft.getChildren().add(new FXMLCategoryContainer(t));
        idMainTopRight.getChildren().add(inventoryBtn);
        idMainBottomLeft.getChildren().add(productCtn);
        idMainBottomRight.getChildren().add(invCtn);
        idMainBottomRight.getChildren().add(checkoutBtn);
        
        
        t.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (t.getSelectedToggle() != null) {
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
                } else if (t.getSelectedToggle() instanceof FXMLUserPageButton) {
                    SetMainBody(mypageCtn);
                }
                
            } else {
                productCtn.SetCategory(null);
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
            default: return null;
        }
    }
}
