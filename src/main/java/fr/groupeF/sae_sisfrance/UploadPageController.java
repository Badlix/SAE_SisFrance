package fr.groupeF.sae_sisfrance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.tools.Borders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UploadPageController extends BorderPane {
    @FXML
    Button uploadButton;
    @FXML
    Button changingFXMLButton;
    @FXML
    Label fileReadableLabel;
    public static ArrayList<Earthquake> earthquakes;
    private FXMLLoader loader;
    public void initialize() throws IOException {
        this.loader = new FXMLLoader(Main.class.getResource("DataPage.fxml"));
        loader.load();
    }

    @FXML
    public void upload(){
        // Ouvrir une boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        fileChooser.setTitle("Sélectionner un fichier");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier csv", "*.csv"),
                new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        // Obtenir le fichier sélectionné
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            earthquakes = new DataImporter().readCSV(selectedFile);
            if(earthquakes.size() > 0) {
                fileReadableLabel.setText("file uploaded");
                fileReadableLabel.setStyle("-fx-text-fill: green");

                DataPageController controller = loader.getController();
                controller.setEarthquakes(earthquakes);
            }else {
                fileReadableLabel.setText("invalid file");
                fileReadableLabel.setStyle("-fx-text-fill: red");
            }
        }else {
            fileReadableLabel.setText("file not uploaded");
            fileReadableLabel.setStyle("-fx-text-fill: red");
        }
    };
    @FXML
    public void changingFXML(ActionEvent event) throws IOException {
        Parent root = this.loader.getRoot();
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
