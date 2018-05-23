/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.container;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLMyPageContainer extends AnchorPane {

    //myPage
    @FXML
    private TextField firstName, lastName, streetText, postcodeText, cityText, phoneText, CardNumberText, CVCText;
    @FXML
    private ComboBox cardTypeText, ValidMonthText, ValidYearText;
    @FXML
    private HBox personAlert, cardAlert;

    private Node personAlertText, cardAlertText;

    public FXMLMyPageContainer() {
        IMatDataHandler handler = IMatDataHandler.getInstance();
        Customer customer = handler.getCustomer();
        CreditCard creditcard = handler.getCreditCard();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/container/FXMLMyPageContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        
        UnaryOperator<Change> numberFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) return change;
            else return null;
        };
        
        personAlertText = personAlert.getChildren().get(0);
        cardAlertText = cardAlert.getChildren().get(0);
        
        //For cardtype
        cardTypeText.getItems().addAll("Visa", "MasterCard", "American Express", "Diners Club");
        
        if (creditcard.getCardType().isEmpty()) cardTypeText.getSelectionModel().select("Visa");
        else cardTypeText.getSelectionModel().select(creditcard.getCardType());
        
        cardTypeText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditcard.setCardType(newValue);
            }
        });
        //For Valid Month
        ValidMonthText.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        
        if (creditcard.getValidMonth() > 0 && creditcard.getValidMonth() <= 12) ValidMonthText.getSelectionModel().select(creditcard.getValidMonth() - 1); // using index not object.
        else ValidMonthText.getSelectionModel().select(0);
            
        ValidMonthText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                creditcard.setValidMonth(newValue);
            }
        });
        //For Valid Year
        ValidYearText.getItems().addAll(2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025);
        
        if (creditcard.getValidYear() >= 2018 && creditcard.getValidYear() <= 2025) ValidYearText.getSelectionModel().select(creditcard.getValidYear() - 2018);
        else ValidYearText.getSelectionModel().select(0);
        
        ValidYearText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                creditcard.setValidYear(newValue);
            }
        });
        // Cardnumber
        CardNumberText.setTextFormatter(new TextFormatter<>(numberFilter));
        
        if (!creditcard.getCardNumber().isEmpty()) CardNumberText.setText(creditcard.getCardNumber());
        else CardNumberText.setText("");
        
        CardNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                
                creditcard.setCardNumber(newValue);
            }
        });
        //cvc
        CVCText.setTextFormatter(new TextFormatter<>(numberFilter));
        
        if (creditcard.getVerificationCode() >= 0 && creditcard.getVerificationCode() <= 999) CVCText.setText(String.format("%03d", creditcard.getVerificationCode()));
        else CVCText.setText("");
        
        CVCText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                creditcard.setVerificationCode(Integer.parseInt(newValue));
            }
        });

        // For name
        if (!customer.getFirstName().isEmpty()) firstName.setText(customer.getFirstName());
        else firstName.setText("");
        
        firstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setFirstName(newValue);
            }
        });
        
        if (!customer.getLastName().isEmpty()) lastName.setText(customer.getLastName());
        else lastName.setText("");
        lastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setLastName(newValue);
            }
        });
        
        // For street
        if (!customer.getAddress().isEmpty()) streetText.setText(customer.getAddress());
        else streetText.setText("");
        streetText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setAddress(newValue);
            }
        });
        
        // For poctcode
        if (!customer.getPostCode().isEmpty()) postcodeText.setText(customer.getPostCode());
        else postcodeText.setText("");
        
        postcodeText.setTextFormatter(new TextFormatter<>(numberFilter));
        postcodeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                
            }
        });
        
        // For city
        if (!customer.getPostAddress().isEmpty()) cityText.setText(customer.getPostAddress());
        else cityText.setText("");
        
        cityText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setPostAddress(newValue);
            }
        });
        
        // For phone
        if (!customer.getPhoneNumber().isEmpty()) phoneText.setText(customer.getPhoneNumber());
        else phoneText.setText("");
        phoneText.setTextFormatter(new TextFormatter<>(numberFilter));
        phoneText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setPhoneNumber(newValue);
            }
        });
    }
    
    
}

