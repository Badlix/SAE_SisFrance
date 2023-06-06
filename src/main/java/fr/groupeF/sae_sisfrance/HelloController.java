package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList(CSVReader.read(path));
//        table.setItems(earthquakes);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensite"));
//        updateTable();
    }

    public void updateTable() {
        for (ArrayList<String> eartquake : eartquakes) {
            dateColumn.getColumns().add(eartquake.get(0));
            regionColumn.getColumns().add(eartquake.get(1));
            intensiteColumn.getColumns().add(eartquake.get(2));
        }
    }
}