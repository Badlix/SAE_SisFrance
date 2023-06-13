package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;
import java.util.*;
import java.io.IOException;

public class GraphicsPageController extends BorderPane {
    private Scene dataPageScene;
    private DataFilter dataEarthquakes;
    @FXML
    private LineChart<String, Number> lineChartSeismPerYear;
    @FXML
    private LineChart<String, Number> lineChartIntensityPerYear;
    private ObservableList<String> intensity;
    private ListView<Double> listView;
    //@FXML
    //private LineChart<String, Number> lineChartSeismPerZone;
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
    List<String> quelityLabels;
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

    public void setDataEarthquakes(DataFilter dataFilter) {
        dataEarthquakes = dataFilter;
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
                ObservableList<String> quality = FXCollections.observableArrayList();
                qualityCheckboxs = new ArrayList<>();
                quelityLabels = new ArrayList<>();
                quality.sort(String::compareToIgnoreCase);
                for (String str : quality) {
                    CheckBox checkbox = new CheckBox();
                    qualityCheckboxs.add(checkbox);
                    quelityLabels.add(str);
                    qualityFilter.getChildren().add(new HBox(checkbox, new Label(str)));
                }
                System.out.println("ok");
                dataFilter.setSelectedQuality(quality);
                createBindings();
            }
        });
        dataEarthquakes.filterAppliedProperty().addListener((observable, oldValue, newValue) -> {
            if (dataEarthquakes.filterAppliedProperty().getValue() == true) {
                System.out.println("CHANGED : SIZE = " + dataFilter.getFilteredEarthquakes().size());
                /* Change Labels values */
                numberLabel1.setText(StatCalcul.totalNumberOfEarthquakes(dataFilter) + "\nNB TOTAL");
                numberLabel2.setText(StatCalcul.globalAverageIntensity(dataFilter) + "\nAVG INTENSITY");
                numberLabel3.setText(StatCalcul.mostAffectedZone(dataFilter).getKey() + "\nMOST AFFECTED REGION (" + StatCalcul.mostAffectedZone(dataFilter).getValue() + ")");
                numberLabel4.setText(StatCalcul.globalAverageEarthquakeByZone(dataFilter) + "\nAVG NB BY REGION");
                numberLabel5.setText(StatCalcul.mostAffectedYear(dataFilter) + "\nMOST AFFECTED YEAR");
                numberLabel6.setText(StatCalcul.globalAverageEarthquakesByYear(dataFilter) + "\nAVG NB BY YEAR");
                graphicsSeismPerYear(dataEarthquakes.getFilteredEarthquakes());
//                graphicsIntensityPerYear(dataEarthquakes.getFilteredEarthquakes())
            }
        });
        createBindings();
        //searchBar();
    }

    public void createBindings() {
        MyBindings.createBindingZone(dataEarthquakes, zoneFilter);
        MyBindings.createBindingCoordinate(dataEarthquakes, longFilter, latFilter, rayonFilter);
        MyBindings.createBindingDates(dataEarthquakes, startDateFilter, endDateFilter);
        MyBindings.createBindingIntensity(dataEarthquakes, intensityFilter);
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
        // graphicsSeismPerYear(dataEarthquakes.getFilteredEarthquakes());
        // graphicsIntensityPerYear(dataEarthquakes.getFilteredEarthquakes());
        // graphicsSeismPerZone(dataEarthquakes.getFilteredEarthquakes());
    }
    @FXML
    public void newFile(){
    }
    public void graphicsSeismPerYear(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        HashMap<String, Integer> nbEarthquakesDuringAYear = new HashMap<>();
        for (Earthquake element : dataGraphics) {
            if (nbEarthquakesDuringAYear.containsKey(element.getYear().toString())) {
                nbEarthquakesDuringAYear.put(element.getYear().toString(), nbEarthquakesDuringAYear.get(element.getYear().toString()) + 1);
            } else {
                nbEarthquakesDuringAYear.put(element.getYear().toString(), 0);
            }
        }
        // TreeMap permet de trier une hashMap selon les clés
        Map<String, Integer> sortedMap = new TreeMap<>(nbEarthquakesDuringAYear);
        //Parcour de la Hashmap
        for (Map.Entry m : sortedMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(m.getKey().toString(), Integer.valueOf(m.getValue().toString())));
        }
        lineChartSeismPerYear.setData(FXCollections.observableArrayList(series));
    }
    public void graphicsIntensityPerYear(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        HashMap<String, Double> averageIntensityPerYear = new HashMap<>();

        // Somme des intensités pr chaque année
        for (Earthquake element : dataGraphics) {
            String year = element.getYear().toString();
            double intensity = Double.parseDouble(element.getIntensity());

            if (averageIntensityPerYear.containsKey(year)) {
                double sum = averageIntensityPerYear.get(year) + intensity;
                averageIntensityPerYear.put(year, sum);
            } else {
                averageIntensityPerYear.put(year, intensity);
            }
        }
        // TreeMap permet de trier une hashMap selon les clés
        Map<String, Double> sortedMap = new TreeMap<>(averageIntensityPerYear);
        for (Map.Entry m : sortedMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(m.getKey().toString(), Integer.valueOf(m.getValue().toString())));
        }

        System.out.println("Intensité moyenne globale : " + StatCalcul.globalAverageIntensityPerYear((DataFilter) dataEarthquakes.getFilteredEarthquakes()));
        lineChartIntensityPerYear.setData(FXCollections.observableArrayList(series));
        }


    /*public void graphicsSeismPerZone(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getShock()),Double.valueOf(element.getZone())));
        lineChartSeismPerZone.getData().add(series);
    }
    public void showGraphics() {
        String selectedOption = choiceBox.getValue().toString();

        ObservableList<Earthquake> dataGraphics = getDataForOption(selectedOption);

        // Efface les données
        lineChart.getData().clear();

        // Génère le graphique avec les nouvelles données
        graphicsIntensityPerZone(dataGraphics);
    }
*/
/*    private ObservableList<Earthquake> getDataForOption(String selectedOption) {
        ObservableList<Earthquake> data = FXCollections.observableArrayList();

        if (selectedOption.equals("Option 1")) {
            graphicsIntensityPerZone(DataFilter.getFilteredEarthquakes());

        } else if (selectedOption.equals("Option 2")) {
            graphicsDatePerIntensity(DataFilter.getFilteredEarthquakes());

        } else if (selectedOption.equals("Option 3")) {
            graphicsSeismPerZone(DataFilter.getFilteredEarthquakes());

        }

        return data;
    }
*/

    @FXML
    public void applyFilter() {
        dataEarthquakes.applyFilter();
    }


}