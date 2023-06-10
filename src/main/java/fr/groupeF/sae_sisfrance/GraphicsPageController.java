package fr.groupeF.sae_sisfrance;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPageController extends BorderPane {
    private FXMLLoader dataPageLoader;
    private FXMLLoader uploadPageLoader;
    private Scene dataPageScene;
    private DataFilter dataEarthquakes;
    @FXML
    private LineChart<String, Number> lineChartIntensityPerRegion;
    @FXML
    private LineChart<String, Number> lineChartDatePerIntensity;
    @FXML
    private LineChart<String, Number> lineChartSeismPerRegion;
    @FXML
    private ChoiceBox choiceBox;

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
        graphicsIntensityPerRegion(DataFilter.getFilteredEarthquakes());
        graphicsDatePerIntensity(DataFilter.getFilteredEarthquakes());
        graphicsSeismPerRegion(DataFilter.getFilteredEarthquakes());

    }
    @FXML
    public void newFile(){

    }
/*    public void loadCSVData() {
        try {
            //ArrayList<Earthquake> data = DataImporter.readCSV(selectedFile);
            CSVReader reader = new CSVReader(new FileReader("chemin_vers_votre_fichier.csv"));
            String[] nextLine;

            // Ignorer la première ligne si elle contient des en-têtes de colonne
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                // Récupérez les valeurs appropriées à partir de chaque ligne du fichier CSV
                String xValue = nextLine[0];
                double yValue = Double.parseDouble(nextLine[1]);

                // Ajoutez les données au graphique
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.getData().add(new XYChart.Data<>(xValue, yValue));
                lineChart.getData().add(series);
            }*/

//}}
    public void graphicsIntensityPerRegion(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getRegion()),Double.valueOf(element.getIntensity())));
            //System.out.println(element.getDate());

        }
        lineChartIntensityPerRegion.getData().add(series);
    }
    public void graphicsDatePerIntensity(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getDate()),Double.valueOf(element.getIntensity())));
            //System.out.println(element.getDate());

        }
        lineChartDatePerIntensity.getData().add(series);
    }
    public void graphicsSeismPerRegion(ObservableList<Earthquake> dataGraphics){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Earthquake element : dataGraphics) {
            series.getData().add(new XYChart.Data<>(String.valueOf(element.getIdentifiant()),Double.valueOf(element.getRegion())));
            //System.out.println(element.getDate());

        }
        lineChartSeismPerRegion.getData().add(series);
    }
/*    public void showGraphics() {
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