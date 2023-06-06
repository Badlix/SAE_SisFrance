package fr.groupeF.sae_sisfrance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        // faire le lien avec CSV Reader
            // ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        eartquakes = new ArrayList<ArrayList<String>>();
        eartquakes.add(new ArrayList<>(Arrays.asList("date 1", "ville 1", "2")));
        eartquakes.add(new ArrayList<>(Arrays.asList("date 2", "ville 2", "7")));
        eartquakes.add(new ArrayList<>(Arrays.asList("date 3", "ville 3", "5")));
        updateTable();
    }

    public void updateTable() {
//        dateColumn.getColumns().clear();
//        regionColumn.getColumns().clear();
//        intensiteColumn.getColumns().clear();
        for (ArrayList<String> eartquake : eartquakes) {
            dateColumn.getColumns().add(eartquake.get(0));
            regionColumn.getColumns().add(eartquake.get(1));
            intensiteColumn.getColumns().add(eartquake.get(2));
        }
    }
}