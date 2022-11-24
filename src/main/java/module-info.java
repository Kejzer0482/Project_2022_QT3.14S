module com.example.streamingproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.streamingproject to javafx.fxml;
    exports com.example.streamingproject;
}