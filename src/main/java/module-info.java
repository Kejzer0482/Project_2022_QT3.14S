module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens presentation to javafx.fxml;
    exports presentation;
}