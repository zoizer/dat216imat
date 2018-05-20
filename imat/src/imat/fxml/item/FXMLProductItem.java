/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imat.fxml.item;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

/**
 * FXML extension class
 *
 * @author Zoizer
 */
public class FXMLProductItem extends AnchorPane {
    
    @FXML
    private ImageView img;
    
    @FXML
    private Label name;
    
    @FXML
    private Label cartinfo;
    
    @FXML
    private Label price;
    
    @FXML
    private ImageView imgputback;
    
    @FXML
    private ImageView imgtake;
    
    @FXML
    private Spinner spin;
    
    private Product p;
    
    public FXMLProductItem(Product p) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/imat/fxml/item/FXMLProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.p = p;
        if (p.getUnitSuffix().equals("kg")) {
            SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 10.0, 0.1, 0.1);

            valueFactory.setConverter(new StringConverter<Double>() {
                @Override
                public String toString(Double value) {
                    return value.toString()+" " + p.getUnitSuffix();
                }

                @Override
                public Double fromString(String string) {
                    String valueWithoutUnits = string.replaceAll(p.getUnitSuffix(), "").trim();
                    valueWithoutUnits = valueWithoutUnits.replaceAll(" ", "").trim();
                    if (valueWithoutUnits.isEmpty()) return 0.0;
                    else return Double.valueOf(valueWithoutUnits);
                }
            });

            spin.setValueFactory(valueFactory);
        } else {
            SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1, 1);
            valueFactory.setConverter(new StringConverter<Integer>() {
                @Override
                public String toString(Integer value) {
                    return value.toString()+" " + p.getUnitSuffix();
                }

                @Override
                public Integer fromString(String string) {
                    String valueWithoutUnits = string.replaceAll(p.getUnitSuffix(), "").trim();
                    valueWithoutUnits = valueWithoutUnits.replaceAll(" ", "").trim();
                    if (valueWithoutUnits.isEmpty()) return 0;
                    else return Integer.valueOf(valueWithoutUnits);
                }
            });
            spin.setValueFactory(valueFactory);
        }
        
        name.setText(p.getName());
        price.setText(String.format("%.2f", p.getPrice()) + " " + p.getUnit());
        img.setImage(IMatDataHandler.getInstance().getFXImage(p, 64.0, 64.0));
    }
    
    public Product GetProduct() {
        return p;
    }
}
