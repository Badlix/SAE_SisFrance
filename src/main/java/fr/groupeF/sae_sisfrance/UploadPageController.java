package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private ComboBox<String> regionFilter;
    @FXML
    private TextField latFilter;
    @FXML
    private TextField longFilter;
    @FXML
    private TextField rayonFilter;
    @FXML
    private RangeSlider intensityFilter;
    @FXML
    private DatePicker startDateFilter;
    @FXML
    private DatePicker endDateFilter;
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
        // ---------ACTUALISATION DES FILTRES---------------
        regionFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        // ---------FILTRE DATE ------------------------
        startDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        endDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        // ---------FILTRE INTENSITE A MODIFIER---------------
        intensityFilter.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        intensityFilter.highValueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        dataEarthquakes.getAllEarthquakes().addListener(new ListChangeListener<Earthquake>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                ObservableList<String> regions = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!regions.contains(earthquake.getRegion()))
                        regions.add(earthquake.getRegion());
                }
                regions.sort(String::compareToIgnoreCase);
                regions.add(0, "");
                regionFilter.setItems(regions);
            }
        });
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
            dataEarthquakes.getAllEarthquakes().addAll(FXCollections.observableArrayList(data));
            dataEarthquakes.getFilteredEarthquakes().addAll(FXCollections.observableArrayList(data));
            if(dataEarthquakes.getAllEarthquakes().size() > 0) {
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

    public void actualizeFilter() {
        // Region
        dataEarthquakes.setSelectedRegion(regionFilter.getValue());
        // Localisation
        if (longFilter.getText().isEmpty() == false && latFilter.getText().isEmpty() == false && rayonFilter.getText().isEmpty() == false) {
            dataEarthquakes.setSelectedLongitude(Float.valueOf(longFilter.getText()));
            dataEarthquakes.setSelectedLatitude(Float.valueOf(latFilter.getText()));
            dataEarthquakes.setSelectedRayon(Integer.valueOf(rayonFilter.getText()));
        }
        // Date -> NE MARCHE PAS
        dataEarthquakes.setSelectedStartDate(new MyDate(String.valueOf(startDateFilter.getValue())));
        dataEarthquakes.setSelectedEndDate(new MyDate(String.valueOf(endDateFilter.getValue())));

        // Intensity -> A MODIFIER
        dataEarthquakes.setSelectedMinIntensity(intensityFilter.getLowValue());
        dataEarthquakes.setSelectedMaxIntensity(intensityFilter.getHighValue());
    }

    public void enableFilter() {
        regionFilter.setDisable(false);
        latFilter.setDisable(false);
        longFilter.setDisable(false);
        rayonFilter.setDisable(false);
        intensityFilter.setDisable(false);
        startDateFilter.setDisable(false);
        endDateFilter.setDisable(false);
        changingFXMLButton.setDisable(false);
    }
}