package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphicsPageController extends BorderPane {
    private FXMLLoader dataPageLoader;
    private FXMLLoader uploadPageLoader;
    private Scene dataPageScene;
    private ListView<Earthquake> earthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;
    private ObservableList<String> filtersList;

    public void setDataPageScene(Scene dataPageScene) {
        this.dataPageScene = dataPageScene;
    }
    public void setDataPageLoad(FXMLLoader dataPageLoader) {
        this.dataPageLoader = dataPageLoader;
    }

    public void setUploadPageLoad(FXMLLoader uploadPageLoader) {
        this.uploadPageLoader = uploadPageLoader;
    }

    public ListView<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public ObservableList<Earthquake> getFilteredEarthquakes() {
        return filteredEarthquakes;
    }

    public ObservableList<String> getFiltersList() {
        return filtersList;
    }

    public void initialize() throws IOException {
        System.out.println("GraphicsPageController initialized");
        earthquakes = new ListView<>();
        filteredEarthquakes = FXCollections.observableArrayList();
        filtersList = FXCollections.observableArrayList();
    }

    @FXML
    public void changingToDataPage(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(dataPageScene);
        stage.show();
    }

}
