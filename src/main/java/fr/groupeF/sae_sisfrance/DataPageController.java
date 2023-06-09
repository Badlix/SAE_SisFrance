package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import com.gluonhq.maps.MapView;
import javafx.stage.Stage;

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
    Slider intensityFilter;
    @FXML
    TextField rechercherTextField;
    @FXML
    VBox map;
    private DataFilter dataEarthquakes;
    private Scene graphicsPageScene;
    private FXMLLoader graphicsPageLoader;
    private FXMLLoader uploadPageLoader;

    public void setGraphicsPageScene(Scene graphicsPageScene) {
        this.graphicsPageScene = graphicsPageScene;
    }

    public void setGraphicsPageLoad(FXMLLoader graphicsPageLoader) {
        this.graphicsPageLoader = graphicsPageLoader;
    }

    public void setUploadPageLoad(FXMLLoader uploadPageLoader) {
        this.uploadPageLoader = uploadPageLoader;
    }

    public void setDataEarthquakes(DataFilter dataFilter) {
        dataEarthquakes = dataFilter;
        dataEarthquakes.getEarthquake().addListener(new ListChangeListener<Earthquake>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                System.out.println("OKKKKK");
                ObservableList<String> regions = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getEarthquake()) {
                    if (!regions.contains(earthquake.getRegion()))
                        regions.add(earthquake.getRegion());
                }
                regions.sort(String::compareToIgnoreCase);
                regions.add(0, "");
                regionFilter.setItems(regions);
                table.setItems(dataEarthquakes.getFilteredEarthquakes());
            }
        });
        // --------- INIT DU FILTRAGE---------------
        table.setItems(dataEarthquakes.getFilteredEarthquakes());
        searchBar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DataPageController initialized");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensiteColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        initMapView();
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
        FilteredList<Earthquake> filteredList = new FilteredList<>(dataEarthquakes.getFilteredEarthquakes(), element -> true);
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
    public void actualizeFilter() {
        // Region
        dataEarthquakes.setRegionFilter(regionFilter.getValue());
        // Localisation
        if (longFilter.getText().isEmpty() == false && latFilter.getText().isEmpty() == false && rayonFilter.getText().isEmpty() == false) {
            dataEarthquakes.setLongitude(Float.valueOf(longFilter.getText()));
            dataEarthquakes.setLatitude(Float.valueOf(latFilter.getText()));
            dataEarthquakes.setRayon(Integer.valueOf(rayonFilter.getText()));
        }
        // Intensity -> A MODIFIER
        dataEarthquakes.setIntensityMin(intensityFilter.getValue());
        table.setItems(dataEarthquakes.getFilteredEarthquakes());
    }

    @FXML
    public void changingToGraphicsPage(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(graphicsPageScene);
        stage.show();
    }
}