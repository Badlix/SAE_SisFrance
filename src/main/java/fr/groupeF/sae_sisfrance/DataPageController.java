package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import com.gluonhq.maps.MapView;
import org.controlsfx.control.RangeSlider;

public class DataPageController extends BorderPane implements Initializable {

    @FXML
    TableView<Earthquake> table;
    @FXML
    TableColumn<Object, Object> dateColumn;
    @FXML
    TableColumn<Object, Object> regionColumn;
    @FXML
    TableColumn<Object, Object> intensiteColumn;
    @FXML
    ChoiceBox<String> regionFilter;
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
    Menu regionMenu;
    @FXML
    TextField rechercherTextField;
    @FXML
    DatePicker filtreDateDebut;
    @FXML
    DatePicker filtreDateFin;

    @FXML
    Button uploadButton;
    @FXML
    Button changingFXMLButton;
    @FXML
    Label fileReadable;
    @FXML
    VBox map;
    private String filePath;
    private ObservableList<Earthquake> earthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;
//    private boolean isRegionFiltered = false;

    private DataFilter dataFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filePath = "src/main/resources/fr/groupeF/sae_sisfrance/SisFrance.csv";
        this.earthquakes = FXCollections.observableArrayList(DataImporter.readCSV(filePath));

//        this.earthquakes = FXCollections.observableArrayList();
        this.filteredEarthquakes = FXCollections.observableArrayList(earthquakes);

//        isRegionFiltered = false;

        ObservableList<String> regions = FXCollections.observableArrayList();
        for (Earthquake earthquake : earthquakes) {
            if (!regions.contains(earthquake.getRegion()))
                regions.add(earthquake.getRegion());
        }
        regions.sort(String::compareToIgnoreCase);
        regions.add(0, "");
        regionFilter.setItems(regions);

        // ---------INIT DU FILTRAGE---------------
        dataFilter = new DataFilter(earthquakes);
        table.setItems(filteredEarthquakes);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        // -----------------------------------------

        // ---------ACTUALISATION DES FILTRES---------------
        regionFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        // ---------FILTRE DATE A MODIFIER---------------
        filtreDateDebut.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        filtreDateFin.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });
        // ---------FILTRE DATE A MODIFIER---------------
        intensityFilter.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });

        intensityFilter.highValueProperty().addListener((observable, oldValue, newValue) -> {
            actualizeFilter();
        });


        // -----------------------------------------

        initMapView();
        searchBar();
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
        FilteredList<Earthquake> filteredList = new FilteredList<>(earthquakes, element -> true);
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
    public void setEarthquakes(ArrayList<Earthquake> data) {
        this.earthquakes.setAll(data);
        this.filteredEarthquakes.setAll(data);
    }

    @FXML
    public void actualizeFilter() {
        // Region
        dataFilter.setRegionFilter(regionFilter.getValue());
        // Localisation
        if (longFilter.getText().isEmpty() == false && latFilter.getText().isEmpty() == false && rayonFilter.getText().isEmpty() == false) {
            dataFilter.setLongitude(Float.valueOf(longFilter.getText()));
            dataFilter.setLatitude(Float.valueOf(latFilter.getText()));
            dataFilter.setRayon(Integer.valueOf(rayonFilter.getText()));
        }
        // Date -> NE MARCHE PAS
        dataFilter.setDateDebut(String.valueOf(filtreDateDebut.getValue()));
        dataFilter.setDateFin(String.valueOf(filtreDateFin.getValue()));

        // Intensity -> A MODIFIER
        dataFilter.setIntensityMin(intensityFilter.getLowValue());
        dataFilter.setIntensityMax(intensityFilter.getHighValue());
        rangeLabel.setText(dataFilter.getIntensityMin() + " - " + dataFilter.getIntensityMax());
        this.filteredEarthquakes.setAll(dataFilter.getFilteredEarthquakes());
        table.setItems(filteredEarthquakes);
    }
}