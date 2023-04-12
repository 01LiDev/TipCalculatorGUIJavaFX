package com.jldevelops.tipcalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class PrimaryController {

    private final DecimalFormat percentageFormat = new DecimalFormat("#.#");
    @FXML
    private Button primaryButton;
    @FXML
    private Label SliderLabel;
    @FXML
    private TextField AmountTextField;
    @FXML
    private TextField TipTextField;
    @FXML
    private TextField TotalTextFIeld;
    @FXML
    private Slider SliderMov;
    @FXML
    private Button CalculateBT;
    @FXML
    private TextField peopleTxtField;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize(URL url, ResourceBundle rb) {
        // set slider range and default value
        SliderMov.setMin(0);
        SliderMov.setMax(30);
        SliderMov.setValue(15);
        updateSliderLabel(15);
        // add listener to slider to update tip percentage
        SliderMov.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateSliderLabel(newValue.doubleValue());
        });
        // clears the input field of amount text when mouse clicks on field
        AmountTextField.setOnMouseClicked(event -> AmountTextField.clear());
    }

    private void updateSliderLabel(double value) {
        SliderLabel.setText(percentageFormat.format(value) + "%");
    }

    @FXML
    private void CalculateBTClicked(ActionEvent event) {
        System.out.println("Calculate Function is running");
        String input = AmountTextField.getText().trim().replace("$", "");
        try {
            int numPeople = Integer.parseInt(peopleTxtField.getText());
            BigDecimal amount = new BigDecimal(input);
            // Check that input is numeric
            if (input.matches("\\d+(\\.\\d+)?")) {
                BigDecimal tipPercentage = new BigDecimal(SliderMov.getValue());
                BigDecimal tip = amount.multiply(tipPercentage).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                BigDecimal total = amount.add(tip);
                BigDecimal perPersonTotal = total.divide(new BigDecimal(numPeople), 2, RoundingMode.HALF_UP);
                // update text fields with formatted values
                TipTextField.setText("$" + tip.toString());
                TotalTextFIeld.setText("$" + perPersonTotal.toString() + " per person");
                AmountTextField.setText("$" + amount.toString());
            } else {
                // input is not numeric
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // handle invalid input gracefully by setting tip and total to 0
            TipTextField.setText(percentageFormat.format(0));
            TotalTextFIeld.setText("0");
        }
    }

}
