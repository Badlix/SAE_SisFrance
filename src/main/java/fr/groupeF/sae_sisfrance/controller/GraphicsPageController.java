package fr.groupeF.sae_sisfrance.controller;

import fr.groupeF.sae_sisfrance.DataFilter;
import fr.groupeF.sae_sisfrance.utils.Earthquake;
import fr.groupeF.sae_sisfrance.MyBindings;
import fr.groupeF.sae_sisfrance.utils.StatCalculations;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;
import java.util.*;

/**
 * GraphicsPageController is a controller class for the graphics page.
 * It handles the display of various charts, labels, the menu and some data bindings.
 */
public class GraphicsPageController extends BorderPane {
    private Scene dataPageScene;
    private DataFilter dataEarthquakes;
    @FXML
    private LineChart<String, Number> lineChartSeismPerYear;
    @FXML
    private LineChart<String, Number> lineChartIntensityPerYear;
    @FXML
    private Label numberLabel1;
    @FXML
    private Label numberLabel2;
    @FXML
    private Label numberLabel3;
    @FXML
    private Label numberLabel4;
    @FXML
    private Label numberLabel5;
    @FXML
    private Label numberLabel6;
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
//    @FXML
//    TextField rechercherTextField;
    @FXML
    DatePicker startDateFilter;
    @FXML
    DatePicker endDateFilter;

    /**
     * Sets the scene for the data page.
     * @param dataPageScene The scene for the data page.
     */
    public void setDataPageScene(Scene dataPageScene) {
        this.dataPageScene = dataPageScene;
    }

    /**
     * Sets the data filter for earthquakes.
     * @param dataFilter The data filter for earthquakes.
     */
    public void setDataEarthquakes(DataFilter dataFilter) {
        dataEarthquakes = dataFilter;
        intensityFilter.highValueProperty().addListener((observable, oldValue, newValue) -> {
            rangeLabel.setText(intensityFilter.getLowValue() + " - " + intensityFilter.getHighValue());
        });
        intensityFilter.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            rangeLabel.setText(intensityFilter.getLowValue() + " - " + intensityFilter.getHighValue());
        });
        dataEarthquakes.getAllEarthquakes().addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                /* Zone comBox Options */
                ObservableList<String> zones = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!zones.contains(earthquake.getZone()))
                        zones.add(earthquake.getZone());
                }
                zones.sort(String::compareToIgnoreCase);
                zones.add(0, "ZONE");
                zoneFilter.setValue("ZONE");
                zoneFilter.setItems(zones);
                // init des options des checkbox de qualitÃ©
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
                createBindings();
            }
        });
        dataEarthquakes.filterAppliedProperty().addListener((observable, oldValue, newValue) -> {
            if (dataEarthquakes.filterAppliedProperty().getValue() == true) {
                /* Change Labels values */
                numberLabel1.setText(Integer.toString(StatCalculations.totalNumberOfEarthquakes(dataFilter)));
                numberLabel2.setText(Double.toString(StatCalculations.globalAverageIntensity(dataFilter)));
                numberLabel3.setText(StatCalculations.mostAffectedZone(dataFilter).getKey() + " (" + StatCalculations.mostAffectedZone(dataFilter).getValue() + ")");
                numberLabel4.setText(Double.toString(StatCalculations.globalAverageEarthquakeByZone(dataFilter)));
                numberLabel5.setText(StatCalculations.mostAffectedYear(dataFilter).getKey().toString() + " (" + StatCalculations.mostAffectedYear(dataFilter).getValue().toString() + ")");
                numberLabel6.setText(Double.toString(StatCalculations.globalAverageEarthquakesByYear(dataFilter)));
                graphicsSeismPerYear();
                graphicsIntensityPerYear();
            }
        });

        //searchBar();
    }

    /**
     * Creates data bindings between filters and the data filter object.
     */
    public void createBindings() {
        MyBindings.createBindingZone(dataEarthquakes, zoneFilter);
        MyBindings.createBindingCoordinate(dataEarthquakes, longFilter, latFilter, rayonFilter);
        MyBindings.createBindingDates(dataEarthquakes, startDateFilter, endDateFilter);
        MyBindings.createBindingIntensity(dataEarthquakes, intensityFilter, rangeLabel);
        MyBindings.createBindingQuality(dataEarthquakes, qualityCheckboxs, qualityLabels);
    }

    /**
     * Switches to the data page.
     * @param event The action event.
     */
    @FXML
    public void changingToDataPage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(dataPageScene);
        stage.show();
    }

    /**
     * Displays the number of seismes per year chart based on the provided earthquake data.
     */
    public void graphicsSeismPerYear(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        TreeMap<Integer, Integer> nbEarthquakeByYear = StatCalculations.yearOcc(dataEarthquakes);
        for (Map.Entry entry : nbEarthquakeByYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), Integer.valueOf(entry.getValue().toString())));
        }
        lineChartSeismPerYear.setData(FXCollections.observableArrayList(series));
        // Désactivation de la légende
        lineChartSeismPerYear.legendVisibleProperty().set(false);
    }

    /**
     * Displays the intensity per year chart based on the provided earthquake data.
     */
    public void graphicsIntensityPerYear(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        TreeMap<Integer, Double> averageIntensityPerYear = StatCalculations.globalAverageintensityByYear(dataEarthquakes);
        for (Map.Entry entry : averageIntensityPerYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), Double.valueOf(entry.getValue().toString())));
        }
        lineChartIntensityPerYear.setData(FXCollections.observableArrayList(series));
        // Désactivation de la légende
        lineChartIntensityPerYear.legendVisibleProperty().set(false);
    }

    /**
     * Applies the filter to the earthquake data.
     */
    @FXML
    public void applyFilter() {
        dataEarthquakes.applyFilter();
    }
}