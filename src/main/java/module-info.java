module com.jldevelops.tipcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.jldevelops.tipcalculator to javafx.fxml;
    exports com.jldevelops.tipcalculator;
}
