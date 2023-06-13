package fr.groupeF.sae_sisfrance;

import com.gluonhq.maps.MapPoint;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

import com.gluonhq.maps.MapView;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

public class DataPageController extends BorderPane implements Initializable {

    @FXML
    TableView<Earthquake> table;
    @FXML
    TableColumn<Object, Object> idColumn;
    @FXML
    TableColumn<Object, Object> dateColumn;
    @FXML
    TableColumn<Object, Object> zoneColumn;
    @FXML
    TableColumn<Object, Object> intensityColumn;
    @FXML
    ComboBox<String> zoneFilter;
    @FXML
    TextField longFilter;
    @FXML
    TextField latFilter;
    @FXML
    TextField rayonFilter;
    @FXML
    RangeSlider intensityFilter;
    @FXML
    Label rangeLabel;
    @FXML
    TextField rechercherTextField;
    @FXML
    DatePicker startDateFilter;
    @FXML
    DatePicker endDateFilter;
    @FXML
    MapView mapView;
    CustomMapLayer mapLayer;
    private Scene graphicsPageScene;
    private static DataFilter dataEarthquakes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DataPageController initialized");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zone"));
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        initMapView();
    }

    public void setGraphicsPageScene(Scene graphicsPageScene) {
        this.graphicsPageScene = graphicsPageScene;
    }

    public void setDataEarthquakes(DataFilter dataFilter) {
        dataEarthquakes = dataFilter;
        dataEarthquakes.getAllEarthquakes().addListener(new ListChangeListener<Earthquake>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                ObservableList<String> zones = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!zones.contains(earthquake.getZone()))
                        zones.add(earthquake.getZone());
                }
                zones.sort(String::compareToIgnoreCase);
                zones.add(0, "ZONE");
                zoneFilter.setValue("ZONE");
                zoneFilter.setItems(zones);
                table.setItems(dataEarthquakes.getFilteredEarthquakes());
            }
        });
        // --------- BNDING DES VALEURS DU TABLEAU ET DE LA MAP ---------------
        dataEarthquakes.filterAppliedProperty().addListener((observable, oldValue, newValue) -> {
            if (dataEarthquakes.filterAppliedProperty().getValue() == true) {
                changeEarthquakesOnMap();
            }
        });
        rechercherTextField.setDisable(true); // on verra plus tard
        createBindings();
        searchBar();
    }

    public void createBindings() {
        MyBindings.createBindingZone(dataEarthquakes, zoneFilter);
        MyBindings.createBindingCoordinate(dataEarthquakes, longFilter, latFilter, rayonFilter);
        MyBindings.createBindingDates(dataEarthquakes, startDateFilter, endDateFilter);
        MyBindings.createBindingIntensity(dataEarthquakes, intensityFilter);
    }

    public void initMapView() {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        MapPoint mapPoint = new MapPoint(45.98,2.78);
        mapLayer = new CustomMapLayer();
        mapView.addLayer(mapLayer);
        mapView.setCenter(mapPoint);
        mapView.setZoom(5);
    }

    private void changeEarthquakesOnMap() {
        mapLayer.clear();
        for (Earthquake earthquake : dataEarthquakes.getFilteredEarthquakes()) {
            if (!earthquake.getLatitude().isEmpty() && !earthquake.getLongitude().isEmpty() && !earthquake.getIntensity().isEmpty()) {
                MapPoint mapPoint = new MapPoint(Float.parseFloat(earthquake.getLatitude()),Float.parseFloat(earthquake.getLongitude()));
                mapLayer.addMapPoint(mapPoint, Float.parseFloat(earthquake.getIntensity()));
            }
        }
        mapLayer.update();
    }


    @FXML
    public void zoomIn() {
        mapView.setZoom(mapView.getZoom()+1);
    }
    @FXML
    public void zoomOut() {
        mapView.setZoom(mapView.getZoom()-1);
    }

    @FXML
    public void searchBar() {
        FilteredList<Earthquake> filteredList = new FilteredList<>(dataEarthquakes.getFilteredEarthquakes(), element -> true);
        rechercherTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(element -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Afficher tous les éléments si la recherche est vide
                }
                String rechercheTexte = newValue.toLowerCase();
                if (element.getDate().toString().indexOf(rechercheTexte) > -1) {
                    return true;
                } else if (element.getZone().toLowerCase().indexOf(rechercheTexte) > -1) {
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
    public void applyFilter() {
        dataEarthquakes.applyFilter();
    }

    @FXML
    public void changingToGraphicsPage(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(graphicsPageScene);
        stage.show();
    }
    @FXML
    public void newFile(){
    }
}