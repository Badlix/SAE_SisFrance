package fr.groupeF.sae_sisfrance.controller;

import fr.groupeF.sae_sisfrance.DataFilter;
import fr.groupeF.sae_sisfrance.utils.DataImporter;
import fr.groupeF.sae_sisfrance.utils.Earthquake;
import fr.groupeF.sae_sisfrance.MyBindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;
import java.io.File;
import java.util.ArrayList;

/**
 * A controller class for the upload page, that handle the upload of the csv file.
 */
public class UploadPageController extends BorderPane {
    private Scene dataPageScene;
    @FXML
    private Button uploadButton;
    @FXML
    private Label fileReadableLabel;
    @FXML
    private ComboBox<String> zoneFilter;
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

    /**
     * Sets the scene for the data page.
     * @param dataPageScene The scene for the data page.
     */
    public void setDataPageScene(Scene dataPageScene) {
        this.dataPageScene = dataPageScene;
    }

    /**
     * Returns the data filter for earthquakes.
     * @return The data filter for earthquakes.
     */
    public DataFilter getDataEarthquakes() {
        return dataEarthquakes;
    }

    /**
     * Initializes the controller and sets up the data filter.
     */
    public void initialize() {
        dataEarthquakes = new DataFilter(FXCollections.observableArrayList());

        dataEarthquakes.getAllEarthquakes().addListener(new ListChangeListener<Earthquake>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                ObservableList<String> zones = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!zones.contains(earthquake.getZone()))
                        zones.add(earthquake.getZone());
                }
                zones.sort(String::compareToIgnoreCase);
                zoneFilter.setValue("ZONE");
                zones.add(0, "ZONE");
                zoneFilter.setItems(zones);
                createBindings();
            }
        });
    }


    /**
     * Creates data bindings between filters and the data filter object.
     */
    public void createBindings() {
        MyBindings.createBindingZone(dataEarthquakes, zoneFilter);
        MyBindings.createBindingCoordinate(dataEarthquakes, longFilter, latFilter, rayonFilter);
        MyBindings.createBindingDates(dataEarthquakes, startDateFilter, endDateFilter);
        MyBindings.createBindingIntensity(dataEarthquakes, intensityFilter);
    }

    /**
     * Handle the upload button.
     */
    @FXML
    public void upload(){
        fileReadableLabel.setText("");

        // Open a file selection dialog box

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        fileChooser.setTitle("Sélectionner un fichier");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier csv", "*.csv"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );

        // Open the selected file

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            ArrayList<Earthquake> data = new ArrayList<>();
            try {
                data = DataImporter.readCSV(selectedFile);
            } catch (RuntimeException e) {
                fileReadableLabel.setText("Fichier non lisible");
                fileReadableLabel.setStyle("-fx-text-fill: red");
            }
            dataEarthquakes.getAllEarthquakes().addAll(FXCollections.observableArrayList(data));
            dataEarthquakes.getFilteredEarthquakes().addAll(FXCollections.observableArrayList(data));
            if(dataEarthquakes.getAllEarthquakes().size() > 0) {
                fileReadableLabel.setText("Fichier importé");
                fileReadableLabel.setStyle("-fx-text-fill: green");
                enableFilter();
            }else {
                fileReadableLabel.setText("Fichier invalide");
                fileReadableLabel.setStyle("-fx-text-fill: red");
            }
        }else {
            fileReadableLabel.setText("Fichier non importé");
            fileReadableLabel.setStyle("-fx-text-fill: red");
        }
    }

    /**
     * Handles the changingFXMLButton action to navigate to the data page.
     * @param event The action event.
     */
    @FXML
    public void changingToDataPage(ActionEvent event) {
        dataEarthquakes.applyFilter();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(dataPageScene);
        stage.show();
    }

    /**
     * Enables the filter controls.
     */
    public void enableFilter() {
        zoneFilter.setDisable(false);
        latFilter.setDisable(false);
        longFilter.setDisable(false);
        rayonFilter.setDisable(false);
        intensityFilter.setDisable(false);
        startDateFilter.setDisable(false);
        endDateFilter.setDisable(false);
        changingFXMLButton.setDisable(false);

    }
}