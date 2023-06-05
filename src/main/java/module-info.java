module com.example.sae_sisfrance {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.sae_sisfrance to javafx.fxml;
    exports com.example.sae_sisfrance;
}