module com.example.variant_diagram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.variant_diagram to javafx.fxml;
    exports com.example.variant_diagram;
}