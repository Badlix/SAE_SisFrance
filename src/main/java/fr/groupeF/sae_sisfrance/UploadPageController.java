package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

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
    @FXML
    private ComboBox regionFilter;
    @FXML
    private TextField latFilter;
    @FXML
    private TextField longFilter;
    @FXML
    private TextField rayonFilter;
    @FXML
    private RangeSlider intensityFilter;
    @FXML
    private DatePicker dateDebutFilter;
    @FXML
    private DatePicker dateFinFilter;
    @FXML
    private Button changingFXMLButton;
    private DataFilter dataEarthquakes;

    public void setGraphicsPageLoad(FXMLLoader graphicsPageLoader) {
        this.graphicsPageLoader = graphicsPageLoader;
    }

    public void setDataPageLoad(FXMLLoader dataPageLoader) {
        this.dataPageLoader = dataPageLoader;
    }

    public void setDataPageScene(Scene dataPageScene) {
        this.dataPageScene = dataPageScene;
    }

    public DataFilter getDataEarthquakes() {
        return dataEarthquakes;
    }

    public void initialize() throws IOException {
        dataEarthquakes = new DataFilter(FXCollections.observableArrayList());
        System.out.println("UploadPageController initialized");
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
            ArrayList<Earthquake> data = DataImporter.readCSV(selectedFile);
            dataEarthquakes.getEarthquake().addAll(FXCollections.observableArrayList(data));
            dataEarthquakes.getFilteredEarthquakes().addAll(FXCollections.observableArrayList(data));
            if(dataEarthquakes.getEarthquake().size() > 0) {
                fileReadableLabel.setText("file uploaded");
                fileReadableLabel.setStyle("-fx-text-fill: green");
                enableFilter();
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

    public void enableFilter() {
        regionFilter.setDisable(false);
        latFilter.setDisable(false);
        longFilter.setDisable(false);
        rayonFilter.setDisable(false);
        intensityFilter.setDisable(false);
        dateDebutFilter.setDisable(false);
        dateFinFilter.setDisable(false);
        changingFXMLButton.setDisable(false);
    }

}