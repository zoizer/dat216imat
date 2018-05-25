/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import imat.fxml.button.FXMLCategoryButton;
import imat.fxml.button.FXMLInventoryButton;
import imat.fxml.button.FXMLReceiptButton;
import imat.fxml.button.FXMLUserPageButton;
import imat.fxml.item.FXMLReceiptItem;
import imat.fxml.item.FXMLReceiptProductItem;
import java.io.IOException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLReceiptContainer extends AnchorPane {
    
    private List<Order> old;
    private ToggleGroup t;
    
    @FXML
    private VBox receipt;
    
    @FXML
    private VBox receiptProduct;
    
    @FXML
    private HBox noreceipt;
    
    @FXML
    private HBox noselect;
    
    @FXML
    private VBox selectedReceiptCtn;
    
    @FXML
    private Label ordertext;
    
    @FXML
    private VBox orderid;
    
    private HBox varNoReceipt;
    private HBox varNoSelect;
    
    public FXMLReceiptContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLReceiptContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        varNoReceipt = noreceipt;
        varNoSelect = noselect;
        t = new ToggleGroup();
        selectedReceiptCtn.getChildren().remove(orderid);
        /*old = IMatDataHandler.getInstance().getOrders();
        for (Order e : old) {
            receipt.getChildren().add(new FXMLReceiptItem(e, t, this));
        }*/
        Update();
        // doubt it really matter that the old ReceiptItems are memory leaked in ToggleGroup.
        
        t.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            selectedReceiptCtn.getChildren().remove(orderid);
            if (t.getSelectedToggle() != null) {
                FXMLReceiptItem i = (FXMLReceiptItem)t.getSelectedToggle();
                UpdateReceiptProducts(i);
                selectedReceiptCtn.getChildren().add(orderid);
                ordertext.setText(String.valueOf(i.GetOrder().getOrderNumber()));
            } else {
                UpdateReceiptProducts(null);
            }
        });
        
        
    }
    
    public void Update() {
        List<Order> l = IMatDataHandler.getInstance().getOrders();
        System.out.println(l);
        if (true) { // used useless optimization here that contained a bug.
            receipt.getChildren().clear();
            old = l;
            for (Order e : old) {
                receipt.getChildren().add(new FXMLReceiptItem(e, t, this));
            }
            UpdateReceiptProducts(null);
        }
        
        if (receipt.getChildren().isEmpty()) {
            receipt.getChildren().add(varNoReceipt);
        }
    }
    
    private void UpdateReceiptProducts(FXMLReceiptItem i) {
        receiptProduct.getChildren().clear();
        if (i == null) {
            // 
        } else {
            for (ShoppingItem e : i.GetOrder().getItems()) {
                receiptProduct.getChildren().add(new FXMLReceiptProductItem(e.getProduct(), e.getAmount()));
            }
        }
        
        if (receiptProduct.getChildren().isEmpty()) {
            receiptProduct.getChildren().add(varNoSelect);
        }
    }
    
    public void clickReceipt(Event ev) {
        // probably useless.
    }
}
