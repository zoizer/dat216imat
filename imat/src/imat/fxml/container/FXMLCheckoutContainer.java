/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.fxml.FXMLApplicationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.*;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;
import java.util.function.UnaryOperator;
import javafx.event.Event;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLCheckoutContainer extends AnchorPane {

    //myPage
    @FXML
    private VBox user;
    
    @FXML
    private Button cancelButton, okButton;
    @FXML
    private Label priceLabel;

    private FXMLMyPageContainer mypage;
    private FXMLInventoryContainer inv;
    private ShoppingCart shoppingCart;
    private boolean userOK;
    private boolean cartOK;

    public FXMLCheckoutContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLCheckoutContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        IMatDataHandler handler = IMatDataHandler.getInstance();
        Customer customer = handler.getCustomer();
        CreditCard creditcard = handler.getCreditCard();
        shoppingCart = handler.getShoppingCart();
        priceLabel.setText(Double.toString(Math.round(shoppingCart.getTotal()*1d)) + " kr"); //initiera
        mypage = (FXMLMyPageContainer)FXMLApplicationController.Get().GetSceneNode(FXMLApplicationController.SceneNode.MY_PAGE_CONTAINER);
        inv = (FXMLInventoryContainer)FXMLApplicationController.Get().GetSceneNode(FXMLApplicationController.SceneNode.INVENTORY_CONTAINER);
        SetUserOK(mypage.IsOK());
        
    }

    public void Activate() {
        inv.CreateBackendShoppingCart();
        user.getChildren().clear();
        user.getChildren().add(mypage);
        priceLabel.setText(Double.toString(shoppingCart.getTotal()) + " kr"); //initiera
        cartOK = !inv.IsEmpty();
        okButton.setDisable(!(userOK && cartOK));
    }
    
    @FXML
    protected void abort(Event ev) {
        FXMLApplicationController.Get().DeselectButtons();
    }
    
    @FXML
    protected void confirm(Event ev) {
        Order o = IMatDataHandler.getInstance().placeOrder(false);
        inv.ClearShoppingCart();
        FXMLApplicationController a = FXMLApplicationController.Get();
        FXMLConfirmationContainer c = (FXMLConfirmationContainer)a.GetSceneNode(FXMLApplicationController.SceneNode.CONFIRM_CONTAINER);
        c.SetOrderDate(o.getDate());
        a.SetMainBody(c);
    }
    
    public void SetUserOK(boolean b) {
        userOK = b;
        okButton.setDisable(!(b && cartOK));
    } 
}

