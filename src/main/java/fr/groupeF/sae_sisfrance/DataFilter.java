package fr.groupeF.sae_sisfrance;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataFilter {
    final private ObservableList<Earthquake> allEarthquakes; // List of all Earthquakes extracted from the csv file
    private ObservableList<Earthquake> filteredEarthquakes; // Filtered list of Earthquakes
    private SimpleStringProperty selectedZone; // Value of region filter
    private SimpleDoubleProperty selectedLatitude; // Value of latitude filter
    private SimpleDoubleProperty selectedLongitude; // Value of longitude filter
    private SimpleIntegerProperty selectedRayon; // Value of rayon filter
    private MyDate selectedStartDate; // Value of the start date filter
    private MyDate selectedEndDate; // Value of the end date filter
    private SimpleDoubleProperty selectedMinIntensity; // Value of the minimal intensity
    private SimpleDoubleProperty selectedMaxIntensity; // Value of the maximal intensity
    private TreeMap<String, SimpleBooleanProperty> selectedQuality;
    private SimpleBooleanProperty filterApplied;

    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        // Initialization of the two earthquakes list
        allEarthquakes = allEarthquake;
        filteredEarthquakes = FXCollections.observableArrayList(allEarthquakes);
        // Initialization of the filter's values
        selectedZone = new SimpleStringProperty("");
        selectedLatitude = new SimpleDoubleProperty();
        selectedLongitude = new SimpleDoubleProperty(0);
        selectedRayon = new SimpleIntegerProperty(0);
        selectedStartDate = new MyDate("");
        selectedEndDate = new MyDate("");
        selectedMinIntensity = new SimpleDoubleProperty(2);
        selectedMaxIntensity = new SimpleDoubleProperty(12);
        selectedQuality = new TreeMap<>();
        filterApplied = new SimpleBooleanProperty(false);
        /* Listener allow the region filter to update the filtered list
        without having to click on the button "Filtrer" */
        selectedZone.addListener((observable, oldValue, newValue) -> applyFilter());
    }

    // ---------- PROPERTY ----------

    public SimpleStringProperty selectedZoneProperty() {
        return selectedZone;
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
    public SimpleDoubleProperty selectedMinIntensityProperty() {
        return selectedMinIntensity;
    }
    public SimpleDoubleProperty selectedMaxIntensensityProperty() {return selectedMaxIntensity; }

    public TreeMap<String, SimpleBooleanProperty> getSelectedQuality() {return selectedQuality;}

    public SimpleBooleanProperty filterAppliedProperty() {return filterApplied;}

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

    public void setSelectedZone(String selectedZone) {
        this.selectedZone.setValue(selectedZone);
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

    public void setSelectedQuality(List<String> options) {
        for (String option:options) {
            selectedQuality.put(option, new SimpleBooleanProperty(false));
        }
    }

    // ---------- OTHERS ----------

    private boolean isInRegion(Earthquake earthquake) {
        if (this.selectedZone.getValue().isEmpty()) {
            return true;
        }
        return earthquake.getRegion().contentEquals(this.selectedZone.getValue());
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

    private boolean isQuality(Earthquake earthquake) {
        if (this.selectedQuality.containsKey(earthquake.getQuality())) {
            if (this.selectedQuality.get(earthquake.getQuality()).getValue() == true) {
                return true;
            }
        }
        return false;
    }

    private boolean IsOneOptionsOfQualityChecked() {
        for (Map.Entry<String, SimpleBooleanProperty> entry : selectedQuality.entrySet()) {
            if (entry.getValue().getValue() == true) {
                return true;
            }
        }
        return false;
    }

    public void applyFilter() {
        filterApplied.set(false);
        filteredEarthquakes.clear();
        boolean atLestOneQualityOptionIsChecked = IsOneOptionsOfQualityChecked();
        for (Earthquake earthquake: allEarthquakes) {
            if (isInRegion(earthquake)) {
                if (isInCoordinate(earthquake)) {
                    if (isBetweenDates(earthquake)) {
                        if (isBetweenIntensity(earthquake)) {
                            if (atLestOneQualityOptionIsChecked) {
                                if (isQuality(earthquake)) {
                                    filteredEarthquakes.add(earthquake);
                                }
                            } else {
                                filteredEarthquakes.add(earthquake);
                            }
                        }
                    }
                }
            }
        }
        /* Call all filteredEarthquakes listener */
        filterApplied.set(false);
        filterApplied.set(true);
    }
}