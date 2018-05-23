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

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLMyPageContainer extends AnchorPane {

    //myPage
    @FXML
    private TextField nameText, streetText, postcodeText, cityText, phoneText, CardNumberText, CVCText;
    @FXML
    private ComboBox cardTypeText, ValidMonthText, ValidYearText;


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
        //For cardtype
        cardTypeText.getItems().addAll("Visa", "MasterCard", "American Express", "Diners Club");
        cardTypeText.getSelectionModel().select("Visa");
        cardTypeText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditcard.setCardType(newValue);
            }
        });
        //For Valid Month
        ValidMonthText.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        ValidMonthText.getSelectionModel().select(0);
        ValidMonthText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                creditcard.setValidMonth(newValue);
            }
        });
        //For Valid Year
        ValidYearText.getItems().addAll(2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025);
        ValidYearText.getSelectionModel().select(0);
        ValidYearText.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                creditcard.setValidYear(newValue);
            }
        });
        // Cardnumber
        CardNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                creditcard.setCardNumber(newValue);
            }
        });
        //cvc
        CVCText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                creditcard.setVerificationCode(Integer.parseInt(newValue));
            }
        });

        // For name
        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setFirstName(newValue);
            }
        });
        // For street
        streetText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setAddress(newValue);
            }
        });
        // For poctcode
        postcodeText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setPostCode(newValue);
            }
        });
        // For city
        cityText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setPostAddress(newValue);
            }
        });
        // For phone
        phoneText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                customer.setPhoneNumber(newValue);
            }
        });

    }

}

