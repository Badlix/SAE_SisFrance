package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UploadPageController extends BorderPane {
    private FXMLLoader graphicsPageLoader;
    private FXMLLoader dataPageLoader;
    private Scene dataPageScene;

    @FXML
    private Button uploadButton;

    @FXML
    private Label fileReadableLabel;
    private ObservableList<Earthquake> earthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;
    private ObservableList<String> filtersList;

    public void setGraphicsPageLoad(FXMLLoader graphicsPageLoader) {
        this.graphicsPageLoader = graphicsPageLoader;
    }

    public void setDataPageLoad(FXMLLoader dataPageLoader) {
        this.dataPageLoader = dataPageLoader;
    }

    public void setDataPageScene(Scene dataPageScene) {
        this.dataPageScene = dataPageScene;
    }

    public ObservableList<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public ObservableList<Earthquake> getFilteredEarthquakes() {
        return filteredEarthquakes;
    }

    public ObservableList<String> getFiltersList() {
        return filtersList;
    }

    public void initialize() throws IOException {
        earthquakes = FXCollections.observableArrayList();
        earthquakes = FXCollections.observableArrayList(DataImporter.readCSV(new File("src/main/resources/fr/groupeF/sae_sisfrance/SisFrance.csv"))); //LIGNE POUR SETUP CSV DE BASE

        filteredEarthquakes = FXCollections.observableArrayList(earthquakes);
        filtersList = FXCollections.observableArrayList();
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
            System.out.println("settt");
            this.earthquakes.setAll(DataImporter.readCSV(selectedFile));
            this.filteredEarthquakes.setAll(earthquakes);

            if(earthquakes.size() > 0) {
                fileReadableLabel.setText("file uploaded");
                fileReadableLabel.setStyle("-fx-text-fill: green");
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
    public void changingToDataPage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(dataPageScene);
        stage.show();
    }

}
