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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.function.UnaryOperator;
import javafx.scene.Node;
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
    
    private int customerErrorCount, cardErrorCount;
    
    private boolean bFirstName, bLastName, bStreet, bPostcode, bCity, bPhone, bCardnbr, bCvc;
    
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
        
        customerErrorCount = 6;
        cardErrorCount = 2;
        firstName.setStyle("-fx-border-color: red;");
        lastName.setStyle("-fx-border-color: red;");
        streetText.setStyle("-fx-border-color: red;");
        postcodeText.setStyle("-fx-border-color: red;");
        cityText.setStyle("-fx-border-color: red;");
        phoneText.setStyle("-fx-border-color: red;");
        CardNumberText.setStyle("-fx-border-color: red;");
        CVCText.setStyle("-fx-border-color: red;");
        
        UnaryOperator<Change> numberFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) return change;
            else return null;
        };
        
        personAlertText = personAlert.getChildren().get(0);
        cardAlertText = cardAlert.getChildren().get(0);
        
        //For cardtype
        cardTypeText.getItems().addAll("Visa", "MasterCard", "American Express", "Diners Club");
        
        cardTypeText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditcard.setCardType(newValue);
            }
        });
        
        if (creditcard.getCardType().isEmpty()) cardTypeText.getSelectionModel().select("Visa");
        else cardTypeText.getSelectionModel().select(creditcard.getCardType());
        
        //For Valid Month
        ValidMonthText.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        
        ValidMonthText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                creditcard.setValidMonth(newValue);
            }
        });
        
        if (creditcard.getValidMonth() > 0 && creditcard.getValidMonth() <= 12) ValidMonthText.getSelectionModel().select(creditcard.getValidMonth() - 1); // using index not object.
        else ValidMonthText.getSelectionModel().select(0);
            
        //For Valid Year
        ValidYearText.getItems().addAll(2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025);
        
        ValidYearText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                creditcard.setValidYear(newValue);
            }
        });
        
        if (creditcard.getValidYear() >= 2018 && creditcard.getValidYear() <= 2025) ValidYearText.getSelectionModel().select(creditcard.getValidYear() - 2018);
        else ValidYearText.getSelectionModel().select(0);
        
        // Cardnumber
        CardNumberText.setTextFormatter(new TextFormatter<>(numberFilter));
        
        CardNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bCardnbr ^ !(newValue.isEmpty() || newValue.length() != 16)) {
                    bCardnbr = !(newValue.isEmpty() || newValue.length() != 16);
                    if (bCardnbr) { // OK
                        cardErrorCount--;
                        CardNumberText.setStyle(null);
                    } else { // ERROR
                        cardErrorCount++;
                        CardNumberText.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                
                creditcard.setCardNumber(newValue);
            }
        });
        
        if (!creditcard.getCardNumber().isEmpty()) CardNumberText.setText(creditcard.getCardNumber());
        else CardNumberText.setText("");
        
        //cvc
        CVCText.setTextFormatter(new TextFormatter<>(numberFilter));
        
        CVCText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bCvc ^ !(newValue.isEmpty() || newValue.length() != 3)) {
                    bCvc = !(newValue.isEmpty() || newValue.length() != 3);
                    if (bCvc) { // OK
                        cardErrorCount--;
                        CVCText.setStyle(null);
                    } else { // ERROR
                        cardErrorCount++;
                        CVCText.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                if (newValue.isEmpty()) creditcard.setVerificationCode(-1);
                else creditcard.setVerificationCode(Integer.parseInt(newValue));
            }
        });
        
        if (creditcard.getVerificationCode() >= 0 && creditcard.getVerificationCode() <= 999) CVCText.setText(String.format("%03d", creditcard.getVerificationCode()));
        else CVCText.setText("");

        // For name
        
        firstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bFirstName ^ !newValue.isEmpty()) {
                    bFirstName = !newValue.isEmpty();
                    if (bFirstName) { // OK
                        customerErrorCount--;
                        firstName.setStyle(null);
                    } else { // ERROR
                        customerErrorCount++;
                        firstName.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                customer.setFirstName(newValue);
            }
        });
        
        if (!customer.getFirstName().isEmpty()) firstName.setText(customer.getFirstName());
        else firstName.setText("");
        
        lastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bLastName ^ !newValue.isEmpty()) {
                    bLastName = !newValue.isEmpty();
                    if (bLastName) { // OK
                        customerErrorCount--;
                        lastName.setStyle(null);
                    } else { // ERROR
                        customerErrorCount++;
                        lastName.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                customer.setLastName(newValue);
            }
        });
        
        if (!customer.getLastName().isEmpty()) lastName.setText(customer.getLastName());
        else lastName.setText("");
        
        // For street
        streetText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bStreet ^ !newValue.isEmpty()) {
                    bStreet = !newValue.isEmpty();
                    if (bStreet) { // OK
                        customerErrorCount--;
                        streetText.setStyle(null);
                    } else { // ERROR
                        customerErrorCount++;
                        streetText.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                customer.setAddress(newValue);
            }
        });
        
        if (!customer.getAddress().isEmpty()) streetText.setText(customer.getAddress());
        else streetText.setText("");
        
        // For poctcode
        
        postcodeText.setTextFormatter(new TextFormatter<>(numberFilter));
        postcodeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bPostcode ^ !newValue.isEmpty()) {
                    bPostcode = !newValue.isEmpty();
                    if (bPostcode) { // OK
                        customerErrorCount--;
                        postcodeText.setStyle(null);
                    } else { // ERROR
                        customerErrorCount++;
                        postcodeText.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                customer.setPostCode(newValue);
            }
        });
        
        if (!customer.getPostCode().isEmpty()) postcodeText.setText(customer.getPostCode());
        else postcodeText.setText("");
        
        // For city
        
        cityText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (bCity ^ !newValue.isEmpty()) {
                    bCity = !newValue.isEmpty();
                    if (bCity) { // OK
                        customerErrorCount--;
                        cityText.setStyle(null);
                    } else { // ERROR
                        customerErrorCount++;
                        cityText.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                customer.setPostAddress(newValue);
            }
        });
        
        if (!customer.getPostAddress().isEmpty()) cityText.setText(customer.getPostAddress());
        else cityText.setText("");
        
        // For phone
        phoneText.setTextFormatter(new TextFormatter<>(numberFilter));
        phoneText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (bPhone ^ !newValue.isEmpty()) {
                    bPhone = !newValue.isEmpty();
                    if (bPhone) { // OK
                        customerErrorCount--;
                        phoneText.setStyle(null);
                    } else { // ERROR
                        customerErrorCount++;
                        phoneText.setStyle("-fx-border-color: red;");
                    }
                    UpdateErrorState();
                }
                customer.setPhoneNumber(newValue);
            }
        });
        
        if (!customer.getPhoneNumber().isEmpty()) phoneText.setText(customer.getPhoneNumber());
        else phoneText.setText("");
        
        UpdateErrorState();
    }
    
    final void UpdateErrorState() {
        personAlert.getChildren().clear();
        cardAlert.getChildren().clear();
        
        if (customerErrorCount > 0) {
            personAlert.getChildren().add(personAlertText);
        }
        
        if (cardErrorCount > 0) {
            cardAlert.getChildren().add(cardAlertText);
        }
        
        FXMLCheckoutContainer co = (FXMLCheckoutContainer)FXMLApplicationController.Get().GetSceneNode(FXMLApplicationController.SceneNode.CHECKOUT_CONTAINER);
        if (co != null) {
            co.SetUserOK(IsOK());
        }
    }
    
    public boolean IsOK() {
        return customerErrorCount == 0 && cardErrorCount == 0;
    }
}

