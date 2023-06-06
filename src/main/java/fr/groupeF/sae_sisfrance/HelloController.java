package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javafx.stage.Stage;

public class HelloController extends BorderPane implements Initializable {

    @FXML
    TableView<Earthquake> table;
    @FXML
    TableColumn<Object, Object> dateColumn;
    @FXML
    TableColumn<Object, Object> regionColumn;
    @FXML
    TableColumn<Object, Object> intensiteColumn;

    @FXML
    Menu regionMenu;
    @FXML
    Button uploadButton;

    private ObservableList<Earthquake> earthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;
    private boolean isRegionFiltered = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        this.earthquakes = FXCollections.observableArrayList();
//                uploadButton.setOnAction(event -> {
//            // Ouvrir une boîte de dialogue de sélection de fichier
//            FileChooser fileChooser = new FileChooser();
//            Stage stage = (Stage) uploadButton.getScene().getWindow();
//            fileChooser.setTitle("Sélectionner un fichier");
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"),
//                    new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
//            );
//            // Obtenir le fichier sélectionné
//            File selectedFile = fileChooser.showOpenDialog(stage);
//            if (selectedFile != null) {
//                System.out.println("Fichier sélectionné : ");
//                // Traitement du fichier sélectionné
//                this.earthquakes = FXCollections.observableArrayList(DataImporter.read(selectedFile.getPath()));
//            }
//        });

        String path = "src/main/resources/fr/groupeF/sae_sisfrance/SisFrance.csv";
        this.earthquakes = FXCollections.observableArrayList(DataImporter.read(path));

        this.filteredEarthquakes = FXCollections.observableArrayList(earthquakes);
        isRegionFiltered = false;
        table.setItems(filteredEarthquakes);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));

        ArrayList<String> regions = new ArrayList<>();
        for (Earthquake earthquake : earthquakes) {
            if (!regions.contains(earthquake.getRegion()))
                regions.add(earthquake.getRegion());
        }
        regions.sort(String::compareToIgnoreCase);
        for (String region : regions) {
            MenuItem menuItem = new MenuItem(region);
            menuItem.setId(region.replace(" ", "").replace("_",""));
            menuItem.setOnAction(this::buttonFilterRegion);
            regionMenu.getItems().add(menuItem);
        }


//        MapView mapView = new MapView();
//        MapPoint mapPoint = new MapPoint(46.227638, 2.213749);
//        mapView.setZoom(5);
//        mapView.flyTo(0, mapPoint, 0.1);
//        map.getChildren().add(mapView);
        //FilteredList<Earthquake> filteredList = new FilteredList<>();
    }
    @FXML
    private void changingPageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("filePickerPage.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 500);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void buttonFilterRegion(ActionEvent actionEvent) {
        MenuItem source = (MenuItem) actionEvent.getSource();
        if (isRegionFiltered) {
            this.filteredEarthquakes.clear();
            this.filteredEarthquakes.addAll(earthquakes);
        }
        filteredEarthquakes.removeIf(filteredEarthquakes -> !filteredEarthquakes.getRegion().replace(" ", "").replace("_","").equals(source.getId()));
        isRegionFiltered = true;
    }

    @FXML
    public void searchBar(ActionEvent actionEvent) {
    }
}