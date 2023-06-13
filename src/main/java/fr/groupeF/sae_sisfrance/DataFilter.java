package fr.groupeF.sae_sisfrance;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Map;

public class DataFilter {
    private ObservableList<Earthquake> allEarthquakes; // List of all Earthquakes extracted from the csv file
    private ObservableList<Earthquake> filteredEarthquakes; // Filtered list of Earthquakes
    private SimpleStringProperty selectedRegion; // Value of region filter
    private SimpleDoubleProperty selectedLatitude; // Value of latitude filter
    private SimpleDoubleProperty selectedLongitude; // Value of longitude filter
    private SimpleIntegerProperty selectedRayon; // Value of rayon filter
    private MyDate selectedStartDate; // Value of the start date filter
    private MyDate selectedEndDate; // Value of the end date filter
    private SimpleDoubleProperty selectedMinIntensity; // Value of the minimal intensity
    private SimpleDoubleProperty selectedMaxIntensity; // Value of the maximal intensity

    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        // Initialization of the two eartquakes list
        allEarthquakes = allEarthquake;
        filteredEarthquakes = FXCollections.observableArrayList(allEarthquakes);
        // Initialization of the filter's values
        selectedRegion = new SimpleStringProperty("");
        selectedLatitude = new SimpleDoubleProperty();
        selectedLongitude = new SimpleDoubleProperty(0);
        selectedRayon = new SimpleIntegerProperty(0);
        selectedStartDate = new MyDate("");
        selectedEndDate = new MyDate("");
        selectedMinIntensity = new SimpleDoubleProperty(2);
        selectedMaxIntensity = new SimpleDoubleProperty(12);

        /* Listener allow the region filter to update the filtered list
        without having to click on the button "Filtrer" */
        selectedRegion.addListener((observable, oldValue, newValue) -> {
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

    public MyDate getSelectedStartDate() {
        return selectedStartDate;
    }

    public MyDate getSelectedEndDate() {
        return selectedEndDate;
    }

    // ---------- SETTER ----------

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion.setValue(selectedRegion);
    }

    public void setSelectedLatitude(double selectedLatitude) {
        this.selectedLatitude.set(selectedLatitude);
    }

    public void setSelectedLongitude(double selectedLongitude) {
        this.selectedLongitude.set(selectedLongitude);
    }

    public void setSelectedRayon(int selectedRayon) {
        this.selectedRayon.set(selectedRayon);
    }

    public void setSelectedMinIntensity(double intensite) {
        this.selectedMinIntensity.set(intensite);
    }

    public void setSelectedMaxIntensity(double intensite) {
        this.selectedMaxIntensity.set(intensite);
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
        if (selectedMinIntensity.getValue() == 2 && selectedMaxIntensity.getValue() == 12) {
            return true;
        } else if (earthquake.getIntensity().isEmpty()) {
            return false;
        }
        return (selectedMinIntensity.getValue() <= Float.valueOf(earthquake.getIntensity()) && selectedMaxIntensity.getValue() >= Float.valueOf(earthquake.getIntensity()));
    }

    private boolean isBetweenDates(Earthquake earthquake) {
        if (selectedStartDate.getDateValue().isEmpty() && selectedEndDate.getDateValue().isEmpty()) {
            return true;
        } else if (earthquake.getDate().isEmpty()) {
            return false;
        }
        MyDate earthquakeDate = new MyDate(earthquake.getDate());
        return earthquakeDate.isBetween(selectedStartDate, selectedEndDate);
    }

    public void applyFilter() {
        filteredEarthquakes.clear();
        boolean regionFilterEmpty = selectedRegion.getValue().isEmpty();
        boolean coorFilterEmpty = (selectedLatitude.getValue() == 0 || selectedLongitude.getValue() == 0 || selectedRayon.getValue() == 0);
        if (regionFilterEmpty && coorFilterEmpty) {
            for (Earthquake earthquake: allEarthquakes) {
                if (isBetweenDates(earthquake)) {
                    if (isBetweenIntensity(earthquake)) {
                        filteredEarthquakes.add(earthquake);
                    }
                }
            }
        } else {
            for (Earthquake earthquake: allEarthquakes) {
                if (isInRegion(earthquake)) {
                    if (isInCoordinate(earthquake)) {
                        if (isBetweenDates(earthquake)) {
                            if (isBetweenIntensity(earthquake)) {
                                filteredEarthquakes.add(earthquake);
                            }
                        }
                    }
                }
            }
        }
    }
}