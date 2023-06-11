package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.time.LocalDate;
import java.util.ArrayList;

public class GraphicsPageController extends BorderPane {
    private FXMLLoader dataPageLoader;
    private FXMLLoader uploadPageLoader;
    private Scene dataPageScene;
    private DataFilter dataEarthquakes;
    @FXML
    private LineChart<String, Number> lineChartSeismPerYear;
    @FXML
    private LineChart<String, Number> lineChartDatePerIntensity;
    //@FXML
    //private LineChart<String, Number> lineChartSeismPerRegion;
    @FXML
    private ChoiceBox choiceBox;
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
        createBindings();
        //searchBar();
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

    public void initialize() throws IOException {
        System.out.println("GraphicsPageController initialized");
        // Ajoutez des options supplémentaires au ChoiceBox si nécessaire
        //choiceBox.getItems().addAll("Option 1", "Option 2", "Option 3");
    }

    @FXML
    public void changingToDataPage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(dataPageScene);
        stage.show();
        graphicsSeismPerYear(dataEarthquakes.getFilteredEarthquakes());
        graphicsIntensityPerYear(dataEarthquakes.getFilteredEarthquakes());
        //graphicsSeismPerRegion(dataEarthquakes.getFilteredEarthquakes());

    }
    @FXML
    public void newFile(){

    }
    public void graphicsSeismPerYear(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        int size = dataEarthquakes.getFilteredEarthquakes().size();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getYear()),Double.valueOf(size)));
        }
        lineChartSeismPerYear.getData().add(series);
    }
    public void graphicsIntensityPerYear(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getYear()),Double.valueOf(element.getIntensity())));
            //Year.textProperty().bindBidirectional(number);
        }
        lineChartDatePerIntensity.getData().add(series);
    }
    /*public void graphicsSeismPerRegion(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getShock()),Double.valueOf(element.getRegion())));
        lineChartSeismPerRegion.getData().add(series);
    }
    public void showGraphics() {
        String selectedOption = choiceBox.getValue().toString();

        ObservableList<Earthquake> dataGraphics = getDataForOption(selectedOption);

        // Efface les données
        lineChart.getData().clear();

        // Génère le graphique avec les nouvelles données
        graphicsIntensityPerRegion(dataGraphics);
    }
*/
/*    private ObservableList<Earthquake> getDataForOption(String selectedOption) {
        ObservableList<Earthquake> data = FXCollections.observableArrayList();

        if (selectedOption.equals("Option 1")) {
            graphicsIntensityPerRegion(DataFilter.getFilteredEarthquakes());

        } else if (selectedOption.equals("Option 2")) {
            graphicsDatePerIntensity(DataFilter.getFilteredEarthquakes());

        } else if (selectedOption.equals("Option 3")) {
            graphicsSeismPerRegion(DataFilter.getFilteredEarthquakes());

        }

        return data;
    }
*/

}