module com.example.sae_sisfrance {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.gluonhq.maps;

    opens fr.groupeF.sae_sisfrance to javafx.fxml;
    exports fr.groupeF.sae_sisfrance;
    opens fr.groupeF.sae_sisfrance.utils to javafx.fxml;
    exports fr.groupeF.sae_sisfrance.utils;
    exports fr.groupeF.sae_sisfrance.controller;
    opens fr.groupeF.sae_sisfrance.controller to javafx.fxml;
}