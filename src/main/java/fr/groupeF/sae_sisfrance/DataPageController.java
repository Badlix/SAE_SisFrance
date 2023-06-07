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
    Menu regionMenu;
    @FXML
    TextField rechercherTextField;

    @FXML
    Button uploadButton;
    @FXML
    Button changingFXMLButton;
    @FXML
    Label fileReadable;
    @FXML
    VBox map;

    private ObservableList<Earthquake> earthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;
    private boolean isRegionFiltered = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }