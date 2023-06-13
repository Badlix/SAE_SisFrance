package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * A class that provides methods for creating bidirectional bindings between Nodes and data filters.
 */
public class MyBindings {


    /**
     * Creates a bidirectional binding between the start/end date filter and the selected start/end date in the data filter.
     * Changes in the start date filter will update the selected start date in the data filter, and vice versa.
     * @param dataEarthquakes The data filter containing the selected start date.
     * @param startDateFilter The UI control for the start date filter.
     */
    static void createBindingDates(DataFilter dataEarthquakes, DatePicker startDateFilter, DatePicker endDateFilter) {
        /* Start Date Filter */
        startDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataEarthquakes.getSelectedStartDate().dateProperty().set(startDateFilter.valueProperty().getValue().toString());
        });
        dataEarthquakes.getSelectedStartDate().dateProperty().addListener((observable, oldValue, newValue) -> {
            startDateFilter.setValue(LocalDate.parse(dataEarthquakes.getSelectedStartDate().toString()));
        });

        /* End Date Filter */
        endDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataEarthquakes.getSelectedEndDate().dateProperty().set(endDateFilter.valueProperty().getValue().toString());
        });
        dataEarthquakes.getSelectedEndDate().dateProperty().addListener((observable, oldValue, newValue) -> {
            endDateFilter.setValue(LocalDate.parse(dataEarthquakes.getSelectedEndDate().toString()));
        });
    }

    /**
     * Creates a bidirectional binding between the zone filter and the selected zone in the data filter.
     * Changes in the zone filter will update the selected zone in the data filter, and vice versa.
     * @param dataFilter  The data filter containing the selected zone.
     * @param zoneFilter  The UI control for the zone filter.
     */
    static void createBindingZone(DataFilter dataFilter, ComboBox<String> zoneFilter) {
        dataFilter.selectedZoneProperty().bindBidirectional(zoneFilter.valueProperty());
    }

    /**
     * Creates bidirectional bindings between the coordinate filters and the selected coordinates in the data filter.
     * Changes in the coordinate filters will update the selected coordinates in the data filter, and vice versa.
     * @param dataFilter   The data filter containing the selected coordinates.
     * @param longFilter   The UI control for the longitude filter.
     * @param latFilter    The UI control for the latitude filter.
     * @param rayonFilter  The UI control for the rayon filter.
     */
    static void createBindingCoordinate(DataFilter dataFilter, TextField longFilter, TextField latFilter, TextField rayonFilter) {
        Bindings.bindBidirectional(latFilter.textProperty(), dataFilter.selectedLatitudeProperty(), MyBindings.converterDoubleToString);
        Bindings.bindBidirectional(longFilter.textProperty(), dataFilter.selectedLongitudeProperty(), MyBindings.converterDoubleToString);
        Bindings.bindBidirectional(rayonFilter.textProperty(), dataFilter.selectedRayonProperty(), MyBindings.converterIntToString);
    }

    /**
     * Creates bidirectional bindings between the intensity filter and the selected intensity range in the data filter.
     * Changes in the intensity filter will update the selected intensity range in the data filter, and vice versa.
     * @param dataFilter       The data filter containing the selected intensity range.
     * @param intensityFilter  The UI control for the intensity filter.
     */
    static void createBindingIntensity(DataFilter dataFilter, RangeSlider intensityFilter) {
        dataFilter.selectedMinIntensensityProperty().bindBidirectional(intensityFilter.lowValueProperty());
        dataFilter.selectedMaxIntensensityProperty().bindBidirectional(intensityFilter.highValueProperty());
    }

    /**
     * Creates bidirectional bindings between the quality filters and the selected quality options in the data filter.
     * Changes in the quality filters will update the selected quality options in the data filter, and vice versa.
     * @param dataFilter      The data filter containing the selected quality options.
     * @param qualityFilter   The CheckBox for the quality filters.
     * @param labels          The labels associated with the quality filters.
     */
    static void createBindingQuality(DataFilter dataFilter, List<CheckBox> qualityFilter, List<String> labels) {
        for (int i = 0; i < qualityFilter.size(); i++) {
            dataFilter.getSelectedQuality().get(labels.get(i)).bindBidirectional(qualityFilter.get(i).selectedProperty());
        }
    }

    /**
     * A StringConverter fonction that converts a Number object to a formatted string representation.
     * It is used for bindind a String and a Double Property with a condition.
     */
    static StringConverter<Number> converterDoubleToString = new StringConverter<Number>() {
        @Override
        public String toString(Number object) {
            Double round = Math.round(object.doubleValue() * 100.0) / 100.0;
            return object == null || object.doubleValue() == 0 ? "" :  round.toString();
        }

        @Override
        public Number fromString(String string) {
            if (string == null) {
                return 0;
            } else {
                try {
                    return Double.valueOf(string);
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        }
    };

    /**
     * A StringConverter fonction that converts a Number object to a formatted string representation.
     * It is used for bindind a String and a Interger Property with a condition.
     */
    static StringConverter<Number> converterIntToString = new StringConverter<Number>() {
        @Override
        public String toString(Number object) {;
            return object == null || object.intValue() == 0 ? "" :  object.toString();
        }

        @Override
        public Number fromString(String string) {
            if (string == null) {
                return 0;
            } else {
                try {
                    return Integer.valueOf(string);
                } catch (NumberFormatException ex) {
                    return 0;
                }
            }
        }

    };
}
