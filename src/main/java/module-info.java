module br.sapiens {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.sapiens to javafx.fxml;
    exports br.sapiens;
    exports br.sapiens.configs;
    opens br.sapiens.models to javafx.base;
    opens br.sapiens.controllers to javafx.fxml;
    exports br.sapiens.controllers;
}