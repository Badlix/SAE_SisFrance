package fr.groupeF.sae_sisfrance;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class DataFilter {

    private ObservableList<Earthquake> allEarthquakes;
    private static ObservableList<Earthquake> filteredEarthquakes;

    private SimpleStringProperty selectedRegion;
    private SimpleDoubleProperty selectedLatitude;
    private SimpleDoubleProperty selectedLongitude;

    private SimpleIntegerProperty selectedRayon;
    private MyDate selectedStartDate;
    private MyDate selectedEndDate;
    private SimpleDoubleProperty selectedMinIntensity;
    private SimpleDoubleProperty selectedMaxIntensity;

    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        this.allEarthquakes = allEarthquake;
        this.filteredEarthquakes = FXCollections.observableArrayList();
        this.filteredEarthquakes.setAll(allEarthquake);
        this.selectedRegion = new SimpleStringProperty("");
        this.selectedLatitude = new SimpleDoubleProperty();
        this.selectedLongitude = new SimpleDoubleProperty(0);
        this.selectedRayon = new SimpleIntegerProperty(0);
        this.selectedStartDate = new MyDate(" ");
        this.selectedEndDate = new MyDate(" ");
        this.selectedMinIntensity = new SimpleDoubleProperty(2);
        this.selectedMaxIntensity = new SimpleDoubleProperty(12);
        selectedRegion.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        selectedLongitude.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        selectedLatitude.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        selectedRayon.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        selectedLongitude.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        selectedMinIntensity.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        selectedMaxIntensity.addListener((observable, oldValue, newValue) -> {
            System.out.println("MIN INTENSITY A CHANGÉ");
            applyFilter();
        });
    }

    // ---------- PROPERTY ----------

    public SimpleStringProperty selectedRegionProperty() {
        return selectedRegion;
    }
    public SimpleDoubleProperty selectedLatitudeProperty() {
        return selectedLatitude;
    }
    public SimpleDoubleProperty selectedLongitudeProperty() {
        return selectedLongitude;
    }
    public SimpleIntegerProperty selectedRayonProperty() {
        return selectedRayon;
    }
    public SimpleDoubleProperty selectedMinIntensensityProperty() {
        return selectedMinIntensity;
    }
    public SimpleDoubleProperty selectedMaxIntensensityProperty() {return selectedMaxIntensity; }

    // ---------- GETTER ----------

    public ObservableList<Earthquake> getAllEarthquakes() {
        return allEarthquakes;
    }

    public ObservableList<Earthquake> getFilteredEarthquakes() {
        return filteredEarthquakes;
    }

    public String getSelectedRegion() {
        return selectedRegion.get();
    }

    public double getSelectedLatitude() {
        return selectedLatitude.get();
    }

    public double getSelectedLongitude() {
        return selectedLongitude.get();
    }

    public int getSelectedRayon() {
        return selectedRayon.get();
    }

    public MyDate getSelectedStartDate() {
        return selectedStartDate;
    }

    public MyDate getSelectedEndDate() {
        return selectedEndDate;
    }

    public double getSelectedMinIntensity() {
        return selectedMinIntensity.getValue();
    }


    public double getSelectedMaxIntensity() {
        return selectedMaxIntensity.getValue();
    }

    // ---------- SETTER ----------

    public void setAllEarthquakes(ObservableList<Earthquake> earthquakes) {
        this.allEarthquakes = earthquakes;
        this.filteredEarthquakes = FXCollections.observableArrayList(earthquakes);
        this.filteredEarthquakes.addAll(allEarthquakes);
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion.setValue(selectedRegion);
        applyFilter();
    }

    public void setSelectedLatitude(double selectedLatitude) {
        this.selectedLatitude.set(selectedLatitude);
    }

    public void setSelectedLongitude(double selectedLongitude) {
        this.selectedLongitude.set(selectedLongitude);
    }

    public void setSelectedRayon(int selectedRayon) {
        this.selectedRayon.set(selectedRayon);
        applyFilter();
    }

    public void setSelectedStartDate(MyDate selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
        applyFilter();
    }

    public void setSelectedEndDate(MyDate selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
        applyFilter();
    }

    public void setSelectedMinIntensity(double intensite) {
        this.selectedMinIntensity.set(intensite);
        applyFilter();
    }

    public void setSelectedMaxIntensity(double intensite) {
        this.selectedMaxIntensity.set(intensite);
        applyFilter();
    }

    // ---------- OTHERS ----------

    private boolean isInRegion(Earthquake earthquake) {
        if (this.selectedRegion.getValue().isEmpty()) {
            return true;
        }
        return earthquake.getRegion().contentEquals(this.selectedRegion.getValue());
    }

    private boolean isInCoordinate(Earthquake earthquake) {
        if (this.selectedLongitude.getValue() == 0.0 || this.selectedLatitude.getValue() == 0.0 || this.selectedRayon.getValue() == 0.0) {
            return true;
        }
        if (earthquake.getLatitude().isEmpty() || earthquake.getLongitude().isEmpty()) {
            return false;
        }
        double earthquakeLat = Double.valueOf(earthquake.getLatitude());
        double earthquakeLong = Double.valueOf(earthquake.getLongitude());
        return Coordinate.isCoordinateWithinRadius(this.selectedLatitude.getValue(), this.selectedLongitude.getValue(), earthquakeLat, earthquakeLong, this.selectedRayon.getValue());
    }

    private boolean isBetweenIntensity(Earthquake earthquake) {
        return (selectedMinIntensity.getValue() <= Float.valueOf(earthquake.getIntensity()) && selectedMaxIntensity.getValue() >= Float.valueOf(earthquake.getIntensity()));
    }

    private void applyFilter() {
        System.out.println("APPLY FILTER : lat=" + selectedLatitude + " - long=" + selectedLongitude + " - ray=" + selectedRayon);
        filteredEarthquakes.clear();
        for (Earthquake earthquake: allEarthquakes) {
            if (isInRegion(earthquake)) {
                if (isInCoordinate(earthquake)) {
                    if (earthquake.isBetweenDates(selectedStartDate, selectedEndDate)) {
                        if (isBetweenIntensity(earthquake)) {
                            filteredEarthquakes.add(earthquake);
                        }
                    }
                }
            }
        }
    }
}