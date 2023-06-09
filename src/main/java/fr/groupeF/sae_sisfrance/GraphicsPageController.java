package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
public class GraphicsPageController extends BorderPane {
    private FXMLLoader dataPageLoader;
    private FXMLLoader uploadPageLoader;
    private Scene dataPageScene;
    private DataFilter dataEarthquakes;

    public void setDataPageScene(Scene dataPageScene) {
        this.dataPageScene = dataPageScene;
    }
    public void setDataPageLoad(FXMLLoader dataPageLoader) {
        this.dataPageLoader = dataPageLoader;
    }

    public void setUploadPageLoad(FXMLLoader uploadPageLoader) {
        this.uploadPageLoader = uploadPageLoader;
    }

    public void setDataEarthquakes(DataFilter dataFilter) {
        dataEarthquakes = dataFilter;
    }

    public void initialize() throws IOException {
        System.out.println("GraphicsPageController initialized");
    }

    @FXML
    public void changingToDataPage(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(dataPageScene);
        stage.show();
    }

}