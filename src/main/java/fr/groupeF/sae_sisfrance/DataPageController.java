package fr.groupeF.sae_sisfrance;

import com.gluonhq.maps.MapPoint;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.gluonhq.maps.MapView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;


/**
 * DataPageController is a controller class that manages the functionality of the data page in the application.
 * It handles the display and filtering of earthquake data, the menu and with the map view.
 */
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
    TableColumn<Object, Object> qualityColumn;
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
    VBox qualityFilter;
    List<CheckBox> qualityCheckboxs;
    List<String> qualityLabels;
    @FXML
    Label rangeLabel;
    /*@FXML
    TextField rechercherTextField;*/
    @FXML
    DatePicker startDateFilter;
    @FXML
    DatePicker endDateFilter;
    @FXML
    MapView mapView;
    CustomMapLayer mapLayer;
    private Scene graphicsPageScene;
    private static DataFilter dataEarthquakes;


    /**
     * Initializes the controller after its root element
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zone"));
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        qualityColumn.setCellValueFactory(new PropertyValueFactory<>("quality"));
        initMapView();
    }

    /**
     * Sets the scene of the graphics page.
     * @param graphicsPageScene The scene of the graphics page.
     */
    public void setGraphicsPageScene(Scene graphicsPageScene) {
        this.graphicsPageScene = graphicsPageScene;
    }

    /**
     * Sets the data earthquakes for filtering and display.
     * @param dataFilter The data earthquakes for filtering and display.
     */
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

                // init des options des checkbox de qualité
                qualityCheckboxs = new ArrayList<>();
                qualityLabels = new ArrayList<>();
                ObservableList<String> quality = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!quality.contains(earthquake.getQuality()) && !earthquake.getQuality().isEmpty())
                        quality.add(earthquake.getQuality());
                }
                quality.sort(String::compareToIgnoreCase);
                for (String str : quality) {
                    CheckBox checkbox = new CheckBox();
                    checkbox.setSelected(true);
                    qualityCheckboxs.add(checkbox);
                    qualityLabels.add(str);
                    HBox hBox = new HBox(checkbox, new Label(str));
                    hBox.setSpacing(5);
                    qualityFilter.getChildren().add(hBox);
                }
                dataFilter.setSelectedQuality(quality);

                // init des elements de la table
                table.setItems(dataEarthquakes.getFilteredEarthquakes());
                createBindings();
            }
        });
        // --------- BNDING DES VALEURS DU TABLEAU ET DE LA MAP ---------------
        dataEarthquakes.filterAppliedProperty().addListener((observable, oldValue, newValue) -> {
            if (dataEarthquakes.filterAppliedProperty().getValue() == true) {
                changeEarthquakesOnMap();
            }
        });
        //searchBar();
    }
    /**
     * Creates bindings between Nodes and the DateFilter filters.
     */
    public void createBindings() {
        MyBindings.createBindingZone(dataEarthquakes, zoneFilter);
        MyBindings.createBindingCoordinate(dataEarthquakes, longFilter, latFilter, rayonFilter);
        MyBindings.createBindingDates(dataEarthquakes, startDateFilter, endDateFilter);
        MyBindings.createBindingIntensity(dataEarthquakes, intensityFilter);
        MyBindings.createBindingQuality(dataEarthquakes, qualityCheckboxs, qualityLabels);
    }

    /**
     * Initializes the map view with and adds a custom map layer.
     */
    public void initMapView() {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        MapPoint mapPoint = new MapPoint(45.98,2.78);
        mapLayer = new CustomMapLayer();
        mapView.addLayer(mapLayer);
        mapView.setCenter(mapPoint);
        mapView.setZoom(5);
    }

    /**
     * Updates the display of earthquakes on the map based on the filtered earthquakes.
     */
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

    /**
     * Zooms in the map view.
     */
    @FXML
    public void zoomIn() {
        mapView.setZoom(mapView.getZoom()+1);
        mapLayer.update();
    }

    /**
     * Zooms out the map view.
     */
    @FXML
    public void zoomOut() {
        mapView.setZoom(mapView.getZoom()-1);
        mapLayer.update();
    }

    /**
     * Performs a search based on the entered text in the search bar and updates the table display.
     */
    /*@FXML
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
    }*/

    /**
     * Applies the selected filters to the data earthquakes.
     */
    @FXML
    public void applyFilter() {
        dataEarthquakes.applyFilter();
    }

    /**
     * Changes the current scene to the graphics page.
     * @param event The action event that triggered the method call.
     */
    @FXML
    public void changingToGraphicsPage(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(graphicsPageScene);
        stage.show();
    }
}