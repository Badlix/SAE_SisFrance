package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.ListView;
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

import java.util.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.time.LocalDate;

public class GraphicsPageController extends BorderPane {
    private FXMLLoader dataPageLoader;
    private FXMLLoader uploadPageLoader;
    private Scene dataPageScene;
    private DataFilter dataEarthquakes;
    @FXML
    private LineChart<String, Number> lineChartSeismPerYear;
    @FXML
    private LineChart<String, Number> lineChartDatePerIntensity;
    private ObservableList<String> intensity;
    private ListView<Double> listView;
    //@FXML
    //private LineChart<String, Number> lineChartSeismPerRegion;
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
        dataEarthquakes.getAllEarthquakes().addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                ObservableList<String> regions = FXCollections.observableArrayList();
                for (Earthquake earthquake : dataEarthquakes.getAllEarthquakes()) {
                    if (!regions.contains(earthquake.getRegion()))
                        regions.add(earthquake.getRegion());
                }
                regions.sort(String::compareToIgnoreCase);
                regions.add(0, "");
                regionFilter.setItems(regions);
            }
        });
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
        /* Moyennes */
        /*DoubleBinding averageBinding = Bindings.createDoubleBinding(() -> {
            double sum = 0;
            for (String value : intensity) {
                double doubleValue = Double.parseDouble(value);
                sum += doubleValue;
            }
            return sum / intensity.size();
        }, values);*/

/*        private void calculateAverage() {
            List<Double> intensities = Earthquake.getIntensity();

            if (!intensities.isEmpty()) {
                double sum = 0;
                for (double intensity : intensities) {
                    sum += intensity;
                }
                double average = sum / intensities.size();
                numberLabel2.setText("Moyenne : " + average);
            } else {
                numberLabel2.setText("Moyenne :");
            }
        }

        private List<Double> getIntensity() {
            List<Double> intensities = new ArrayList<>();
            intensities.addAll(listView.getItems());
            return intensities;
        }
*/
       /* private String calculateSum(String[] intensityValues) {
            String sum = "";
            for (String intensity : intensityValues) {
                sum += intensity + " ";
            }
            return sum;
        }*/

        /* Labels */
        // Liaison dynamique entre le nombre total de séismes et le texte des Labels (les rectangles bleus)
        StringBinding totalSeismBinding = Bindings.createStringBinding(()-> {
            int size = dataEarthquakes.getFilteredEarthquakes().size();
            return "Nombre total de séismes: " + size;
        }, dataEarthquakes.getFilteredEarthquakes());

        // Liaison dynamique entre l'année avec le plus de séismes et le texte du 5ème label
        int yearWithMostSeisms = YearWithMostSeisms(dataEarthquakes.getFilteredEarthquakes());
        StringBinding yearWithMostSeismsBinding = Bindings.createStringBinding(()-> {
                    return "Année avec le plus de séismes : " + yearWithMostSeisms;
        },dataEarthquakes.getFilteredEarthquakes());



        //numberLabel1.textProperty().bind(ObservableValueof(dataEarthquakes.getAllEarthquakes()));
        numberLabel1.textProperty().bind(totalSeismBinding);
        numberLabel5.textProperty().bind(yearWithMostSeismsBinding);
        // Liaison du texte du Label avec la valeur moyenne
//        numberLabel2.textProperty().bind(averageBinding.asString("Average: %.2f"));
        // ---------- BINDINGS - DASHBOARD ----------
        dataEarthquakes.getFilteredEarthquakes().addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Earthquake> change) {
                graphicsSeismPerYear(dataEarthquakes.getFilteredEarthquakes());
                graphicsIntensityPerYear(dataEarthquakes.getFilteredEarthquakes());
            }
        });
    }
   /* private ObservableValue<String> ObservableValueof(ObservableList<Earthquake> allEarthquakes) {
        dataEarthquakes.getAllEarthquakes();
        return ObservableValueof(allEarthquakes);
    }*/

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
        // graphicsSeismPerRegion(dataEarthquakes.getFilteredEarthquakes());
    }
    @FXML
    public void newFile(){

    }
    public int YearWithMostSeisms(ObservableList<Earthquake> dataGraphics) {
        HashMap<Integer, Integer> nbEarthquakesDuringAYear = new HashMap<>();

        // Compter le nombre de séismes pour chaque année
        for (Earthquake element : dataGraphics) {
            int year = element.getYear();
            nbEarthquakesDuringAYear.put(year, nbEarthquakesDuringAYear.getOrDefault(year, 0) + 1);
        }

        // Trouver l'année avec le nombre maximal de séismes
        int maxCount = 0;
        int maxYear = 0;
        // TreeMap permet de trier une hashMap selon les clés
        Map<Integer, Integer> sortedMap = new TreeMap<>(nbEarthquakesDuringAYear);
        // Parcourir la Map et trouver l'année avec le plus de séismes
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            int year = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                maxYear = year;
            }
        }

        return maxYear;
    }
    public void graphicsSeismPerYear(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        HashMap<String, Integer> nbEarthquakesDuringAYear = new HashMap<String, Integer>();
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
            series.getData().add(new XYChart.Data<>(m.getKey().toString(), Integer.valueOf((String) m.getValue().toString())));
        }
        lineChartSeismPerYear.setData(FXCollections.observableArrayList(series));
        //lineChartSeismPerYear.getData().add(series);
    }
    public void graphicsIntensityPerYear(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getYear()),Double.valueOf(element.getIntensity())));
        }
        lineChartDatePerIntensity.setData(FXCollections.observableArrayList(series));
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

    @FXML
    public void applyFilter() {
        dataEarthquakes.applyFilter();
    }


}