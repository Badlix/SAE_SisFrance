package fr.groupeF.sae_sisfrance;

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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import com.gluonhq.maps.MapView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.control.RangeSlider;

public class DataPageController extends BorderPane implements Initializable {

    @FXML
    TableView<Earthquake> table;
    @FXML
    TableColumn<Object, Object> dateColumn;
    @FXML
    TableColumn<Object, Object> regionColumn;
    @FXML
    TableColumn<Object, Object> intensityColumn;
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
    TextField rechercherTextField;
    @FXML
    DatePicker startDateFilter;
    @FXML
    DatePicker endDateFilter;

    @FXML
    VBox map;
    private static DataFilter dataEarthquakes;

    public static DataFilter getDataEarthquakes() {
        return dataEarthquakes;
    }

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
        dataEarthquakes.getAllEarthquakes().addListener(new ListChangeListener<Earthquake>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                System.out.println("OKKKKK");
                ObservableList<String> regions = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!regions.contains(earthquake.getRegion()))
                        regions.add(earthquake.getRegion());
                }
                regions.sort(String::compareToIgnoreCase);
                regions.add(0, "");
                regionFilter.setItems(regions);
                table.setItems(dataEarthquakes.getFilteredEarthquakes());
            }
        });
        // --------- BNDING DES VALEURS DU TABLEAU ---------------
        dataEarthquakes.getFilteredEarthquakes().addListener(new ListChangeListener<Earthquake>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                System.out.println("ELEMENT TABLE : " + dataEarthquakes.getFilteredEarthquakes().size());
                table.setItems(dataEarthquakes.getFilteredEarthquakes());
            }
        });
        rechercherTextField.setDisable(true); // on verra plus tard
        createBindings();
        searchBar();
    }

    public void createBindings() {
        /* Region Filter */
        regionFilter.valueProperty().bindBidirectional(dataEarthquakes.selectedRegionProperty());

        /* Coordinate Filter */
        Bindings.bindBidirectional(latFilter.textProperty(), dataEarthquakes.selectedLatitudeProperty(), MyBindings.converterDoubleToString);
        Bindings.bindBidirectional(longFilter.textProperty(), dataEarthquakes.selectedLongitudeProperty(), MyBindings.converterDoubleToString);
        Bindings.bindBidirectional(rayonFilter.textProperty(), dataEarthquakes.selectedRayonProperty(), MyBindings.converterIntToString);

        /* Date Filter */
        startDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataEarthquakes.getSelectedStartDate().dateProperty().set(startDateFilter.valueProperty().getValue().toString());
        });
        dataEarthquakes.getSelectedStartDate().dateProperty().addListener((observable, oldValue, newValue) -> {
            startDateFilter.setValue(LocalDate.parse(dataEarthquakes.getSelectedStartDate().toString()));
        });

        endDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataEarthquakes.getSelectedEndDate().dateProperty().set(endDateFilter.valueProperty().getValue().toString());
        });
        dataEarthquakes.getSelectedEndDate().dateProperty().addListener((observable, oldValue, newValue) -> {
            endDateFilter.setValue(LocalDate.parse(dataEarthquakes.getSelectedEndDate().toString()));
        });

        /* Intensity Filter */
        dataEarthquakes.selectedMinIntensensityProperty().bindBidirectional(intensityFilter.lowValueProperty());
        dataEarthquakes.selectedMaxIntensensityProperty().bindBidirectional(intensityFilter.highValueProperty());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DataPageController initialized");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
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
                if (element.getDate().toString().indexOf(rechercheTexte) > -1) {
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