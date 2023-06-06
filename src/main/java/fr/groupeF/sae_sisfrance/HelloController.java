package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController extends BorderPane implements Initializable {

    @FXML
    TableView table;
    @FXML
    TableColumn dateColumn;
    @FXML
    TableColumn regionColumn;
    @FXML
    TableColumn intensiteColumn;
    ArrayList<ArrayList<String>> eartquakes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String path = "src/main/resources/fr/groupeF/sae_sisfrance/SisFrance.csv";

        this.earthquakes = FXCollections.observableArrayList(DataImporter.read(path));
        this.filteredEarthquakes = FXCollections.observableArrayList(earthquakes);
        isRegionFiltered = false;
        table.setItems(filteredEarthquakes);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensite"));
//        updateTable();
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