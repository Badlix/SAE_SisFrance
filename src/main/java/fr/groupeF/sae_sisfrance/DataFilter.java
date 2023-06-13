package fr.groupeF.sae_sisfrance;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * DataFilter is a class that represents a data filter for Earthquake objects.
 * It allows filtering a list of Earthquakes based on various criteria such as zone, coordinates, intensity, dates, and quality.
 */
public class DataFilter {
    private ObservableList<Earthquake> allEarthquakes; // List of all Earthquakes extracted from the csv file
    private ObservableList<Earthquake> filteredEarthquakes; // Filtered list of Earthquakes
    private SimpleStringProperty selectedZone; // Value of zone filter
    private SimpleDoubleProperty selectedLatitude; // Value of latitude filter
    private SimpleDoubleProperty selectedLongitude; // Value of longitude filter
    private SimpleIntegerProperty selectedRayon; // Value of rayon filter
    private MyDate selectedStartDate; // Value of the start date filter
    private MyDate selectedEndDate; // Value of the end date filter
    private SimpleDoubleProperty selectedMinIntensity; // Value of the minimal intensity
    private SimpleDoubleProperty selectedMaxIntensity; // Value of the maximal intensity
    private TreeMap<String, SimpleBooleanProperty> selectedQuality;
    private SimpleBooleanProperty filterApplied;


    /**
     * Constructs a new DataFilter object with a list of all Earthquakes found in the csv file.
     * Initializes the filter's values and sets up the initial filteredEarthquakes list.
     *
     * @param allEarthquake The list of all Earthquakes.
     */
    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        // Initialization of the two eartquakes list
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
        /* Listener allow the zone filter to update the filtered list
        without having to click on the button "Filtrer" */
        selectedZone.addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
    }

    // ---------- PROPERTY ----------

    /**
     * Returns the selectedZone property object.
     * @return The selectedZone property object.
     */
    public SimpleStringProperty selectedZoneProperty() {
        return selectedZone;
    }

    /**
     * Returns the selectedLatitude property object.
     * @return The selectedLatitude property object.
     */
    public SimpleDoubleProperty selectedLatitudeProperty() {
        return selectedLatitude;
    }

    /**
     * Returns the selectedLongitude property object.
     * @return The selectedLongitude property object.
     */
    public SimpleDoubleProperty selectedLongitudeProperty() {
        return selectedLongitude;
    }

    /**
     * Returns the selectedRayon property object.
     * @return The selectedRayon property object.
     */
    public SimpleIntegerProperty selectedRayonProperty() {
        return selectedRayon;
    }

    /**
     * Returns the selectedMinIntensity property object.
     * @return The selectedMinIntensity property object.
     */
    public SimpleDoubleProperty selectedMinIntensensityProperty() {
        return selectedMinIntensity;
    }

    /**
     * Returns the selectedMaxIntensity property object.
     * @return The selectedMaxIntensity property object.
     */
    public SimpleDoubleProperty selectedMaxIntensensityProperty() {return selectedMaxIntensity; }

    /**
     * Returns the selectedQuality map.
     * @return The selectedQuality map.
     */
    public TreeMap<String, SimpleBooleanProperty> getSelectedQuality() {return selectedQuality;}

    /**
     * Returns the filterApplied property object.
     * @return The filterApplied property object.
     */
    public SimpleBooleanProperty filterAppliedProperty() {return filterApplied;}

    // ---------- GETTER ----------

    /**
     * Returns the list of all Earthquakes.
     * @return The list of all Earthquakes.
     */
    public ObservableList<Earthquake> getAllEarthquakes() {
        return allEarthquakes;
    }

    /**
     * Returns the filtered list of Earthquakes.
     * @return The filtered list of Earthquakes.
     */
    public ObservableList<Earthquake> getFilteredEarthquakes() {
        return filteredEarthquakes;
    }

    /**
     * Returns the selected start date for filtering.
     * @return The selected start date.
     */
    public MyDate getSelectedStartDate() {
        return selectedStartDate;
    }

    /**
     * Returns the selected end date for filtering.
     * @return The selected end date.
     */
    public MyDate getSelectedEndDate() {
        return selectedEndDate;
    }

    // ---------- SETTER ----------


    /**
     * Sets the selected zone for filtering.
     * @param selectedZone The selected zone value.
     */
    public void setSelectedZone(String selectedZone) {
        this.selectedZone.setValue(selectedZone);
    }

    /**
     * Sets the selected latitude for filtering.
     * @param selectedLatitude The selected latitude value.
     */
    public void setSelectedLatitude(double selectedLatitude) {
        this.selectedLatitude.set(selectedLatitude);
    }

    /**
     * Sets the selected longitude for filtering.
     * @param selectedLongitude The selected longitude value.
     */
    public void setSelectedLongitude(double selectedLongitude) {
        this.selectedLongitude.set(selectedLongitude);
    }

    /**
     * Sets the selected rayon for filtering.
     * @param selectedRayon The selected rayon value.
     */
    public void setSelectedRayon(int selectedRayon) {
        this.selectedRayon.set(selectedRayon);
    }

    /**
     * Sets the selected minimum intensity for filtering.
     * @param intensityMin The selected minimum intensity value.
     */
    public void setSelectedMinIntensity(double intensityMin) {this.selectedMinIntensity.set(intensityMin);}

    /**
     * Sets the selected maximum intensity for filtering.
     * @param intensityMax The selected maximum intensity value.
     */
    public void setSelectedMaxIntensity(double intensityMax) {
        this.selectedMaxIntensity.set(intensityMax);
    }

    /**
     * Sets the selected quality options for filtering.
     * @param options The selected quality options.
     */
    public void setSelectedQuality(List<String> options) {
        for (String option:options) {
            selectedQuality.put(option, new SimpleBooleanProperty(false));
        }
    }

    // ---------- OTHERS ----------

    /**
     * Checks if the earthquake is in the selected zone.
     * @param earthquake The earthquake to check.
     * @return True if the earthquake is in the selected zone, false otherwise.
     */
    private boolean isInZone(Earthquake earthquake) {
        if (this.selectedZone.getValue().equals("ZONE")) {
            return true;
        }
        return earthquake.getZone().contentEquals(this.selectedZone.getValue());
    }

    /**
     * Checks if the earthquake is within the selected coordinates and radius.
     * @param earthquake The earthquake to check.
     * @return True if the earthquake is within the selected coordinates and radius, false otherwise.
     */
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

    /**
     * Checks if the earthquake's intensity is within the selected range.
     * @param earthquake The earthquake to check.
     * @return True if the earthquake's intensity is within the selected range, false otherwise.
     */
    private boolean isBetweenIntensity(Earthquake earthquake) {
        if (selectedMinIntensity.getValue() == 2 && selectedMaxIntensity.getValue() == 12) {
            return true;
        } else if (earthquake.getIntensity().isEmpty()) {
            return false;
        }
        return (selectedMinIntensity.getValue() <= Float.valueOf(earthquake.getIntensity()) && selectedMaxIntensity.getValue() >= Float.valueOf(earthquake.getIntensity()));
    }

    /**
     * Checks if the earthquake's date is within the selected start and end dates.
     * @param earthquake The earthquake to check.
     * @return True if the earthquake's date is within the selected start and end dates, false otherwise.
     */
    private boolean isBetweenDates(Earthquake earthquake) {
        if (selectedStartDate.getDateValue().isEmpty() && selectedEndDate.getDateValue().isEmpty()) {
            return true;
        } else if (earthquake.getDate().isEmpty()) {
            return false;
        }
        MyDate earthquakeDate = new MyDate(earthquake.getDate());
        return earthquakeDate.isBetween(selectedStartDate, selectedEndDate);
    }

    /**
     * Checks if the earthquake's quality is selected.
     * @param earthquake The earthquake to check.
     * @return True if the earthquake's quality is selected, false otherwise.
     */
    private boolean isQuality(Earthquake earthquake) {
        if (this.selectedQuality.containsKey(earthquake.getQuality())) {
            if (this.selectedQuality.get(earthquake.getQuality()).getValue() == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if at least one quality option is checked.
     * @return True if at least one quality option is checked, false otherwise.
     */
    private boolean IsOneOptionsOfQualityChecked() {
        for (Map.Entry<String, SimpleBooleanProperty> entry : selectedQuality.entrySet()) {
            if (entry.getValue().getValue() == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Applies the filter to the list of earthquakes.
     * Clears the filteredEarthquakes list and adds earthquakes that pass the filter criteria.
     */
    public void applyFilter() {
        filterApplied.set(false);
        filteredEarthquakes.clear();
        boolean atLestOneQualityOptionIsChecked = IsOneOptionsOfQualityChecked();
        for (Earthquake earthquake: allEarthquakes) {
            if (isInZone(earthquake)) {
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
        filterApplied.set(false);
        filterApplied.set(true);
    }

}