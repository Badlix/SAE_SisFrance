package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import com.gluonhq.maps.MapView;
import javafx.stage.Stage;

public class DataPageController extends BorderPane{

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
    TextField rechercherTextField;
    @FXML
    VBox map;
    private FXMLLoader graphicsPageLoader;
    private FXMLLoader dataPageLoader;
    private FXMLLoader uploadPageLoader;
    private ListView<Earthquake> earthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;
    private ObservableList<String> filtersList;

    public void setGraphicsPageLoad(FXMLLoader graphicsPageLoader) {
        this.graphicsPageLoader = graphicsPageLoader;
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
        System.out.println("dataPageController initialized");
        earthquakes = new ListView<>();
        filteredEarthquakes = FXCollections.observableArrayList();
        filtersList = FXCollections.observableArrayList();

        table.setItems(filteredEarthquakes);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));

//        ArrayList<String> regions = new ArrayList<>();
//        for (Earthquake earthquake : earthquakes) {
//            if (!regions.contains(earthquake.getRegion()))
//                regions.add(earthquake.getRegion());
//        }
//        regions.sort(String::compareToIgnoreCase);
//        for (String region : regions) {
//            MenuItem menuItem = new MenuItem(region);
//            menuItem.setId(region.replace(" ", "").replace("_",""));
//            menuItem.setOnAction(this::buttonFilterRegion);
//            regionMenu.getItems().add(menuItem);
//        }
        initMapView();
        //searchBar();
    }

    public void initMapView() {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        MapView mapView = new MapView();
        //MapPoint mapPoint = new MapPoint(46.227638, 2.213749);
        mapView.setZoom(5);
        //mapView.flyTo(0, mapPoint, 0.1);
        map.getChildren().add(mapView);
    }

    @FXML
    public void searchBar() {
        FilteredList<Earthquake> filteredList = new FilteredList<>(filteredEarthquakes, element -> true);
        rechercherTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(element -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Afficher tous les éléments si la recherche est vide
                }

                String rechercheTexte = newValue.toLowerCase();

                if (element.getDate().toLowerCase().indexOf(rechercheTexte) > -1) {
                    return true;
                } else if (element.getRegion().toLowerCase().indexOf(rechercheTexte) > -1) {
                    return true;
                } else if (element.getIntensity().toLowerCase().indexOf(rechercheTexte) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Earthquake> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }
    @FXML
    public void changingToGraphicsPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GraphicsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}