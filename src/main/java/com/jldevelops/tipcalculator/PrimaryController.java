package com.jldevelops.tipcalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    private final DecimalFormat percentageFormat = new DecimalFormat("#.#");

    @FXML
    private Label SliderLabelP;
    @FXML
    private TextField AmountTextFieldP;
    @FXML
    private TextField TipTextFieldP;
    @FXML
    private TextField TotalTextFIeldP;
    @FXML
    private Slider SliderMovP;
    @FXML
    private Button CalculateBTP;
    @FXML
    private Button primaryButton;
    @FXML
    private TextField peopleTxtFieldP;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

    }

    public void initialize(URL url, ResourceBundle rb) {        // set slider range and default value
        SliderMovP.setMin(0);
        SliderMovP.setMax(30);
        SliderMovP.setValue(15);
        updateSliderLabel(15);
        // add listener to slider to update tip percentage
        SliderMovP.valueProperty().addListener((observableValue, oldValue, newValue) -> {

            updateSliderLabel(newValue.doubleValue());
        });
        // clears the input field of amount text when mouse clicks on field
        AmountTextFieldP.setOnMouseClicked(event -> AmountTextFieldP.clear());
    }

    private void updateSliderLabel(double value) {
        SliderLabelP.setText(percentageFormat.format(value) + "%");

    }

    @FXML
    private void CalculateBTPClicked(ActionEvent event) {
        System.out.println("Calculate Function is running");
        String input = AmountTextFieldP.getText().trim().replace("$", "");
        try {
            int numPeople = Integer.parseInt(peopleTxtFieldP.getText());
            BigDecimal amount = new BigDecimal(input);
            // Check that input is numeric
            if (input.matches("\\d+(\\.\\d+)?")) {
                BigDecimal tipPercentage = new BigDecimal(SliderMovP.getValue());
                BigDecimal tip = amount.multiply(tipPercentage).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                BigDecimal total = amount.add(tip);
                BigDecimal perPersonTotal = total.divide(new BigDecimal(numPeople), 2, RoundingMode.HALF_UP);
                // update text fields with formatted values
                TipTextFieldP.setText("$" + tip.toString());
                TotalTextFIeldP.setText("$" + perPersonTotal.toString() + " per person");
                AmountTextFieldP.setText("$" + amount.toString());
            } else {
                // input is not numeric
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // handle invalid input gracefully by setting tip and total to 0
            TipTextFieldP.setText(percentageFormat.format(0));
            TotalTextFIeldP.setText("0");
        }
    }

}
