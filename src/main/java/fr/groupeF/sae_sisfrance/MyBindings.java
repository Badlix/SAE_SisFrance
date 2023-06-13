package fr.groupeF.sae_sisfrance;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class MyBindings {

    static void createBindingDates(DataFilter dataEarthquakes, DatePicker startDateFilter, DatePicker endDateFilter) {
        /* Start Date Filter */
        startDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataEarthquakes.getSelectedStartDate().dateProperty().set(startDateFilter.valueProperty().getValue().toString());
        });
        dataEarthquakes.getSelectedStartDate().dateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(dataEarthquakes.getSelectedStartDate().toString());
            //startDateFilter.setValue(LocalDate.parse(dataEarthquakes.getSelectedStartDate().toString()));
        });

        /* End Date Filter */
        endDateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataEarthquakes.getSelectedEndDate().dateProperty().set(endDateFilter.valueProperty().getValue().toString());
        });
        dataEarthquakes.getSelectedEndDate().dateProperty().addListener((observable, oldValue, newValue) -> {
            endDateFilter.setValue(LocalDate.parse(dataEarthquakes.getSelectedEndDate().toString()));
        });
    }

    static void createBindingZone(DataFilter dataFilter, ComboBox<String> zoneFilter) {
        zoneFilter.valueProperty().bindBidirectional(dataFilter.selectedZoneProperty());
    }

    static void createBindingCoordinate(DataFilter dataFilter, TextField longFilter, TextField latFilter, TextField rayonFilter) {
        Bindings.bindBidirectional(latFilter.textProperty(), dataFilter.selectedLatitudeProperty(), MyBindings.converterDoubleToString);
        Bindings.bindBidirectional(longFilter.textProperty(), dataFilter.selectedLongitudeProperty(), MyBindings.converterDoubleToString);
        Bindings.bindBidirectional(rayonFilter.textProperty(), dataFilter.selectedRayonProperty(), MyBindings.converterIntToString);
    }

    static void createBindingIntensity(DataFilter dataFilter, RangeSlider intensityFilter) {
        dataFilter.selectedMinIntensensityProperty().bindBidirectional(intensityFilter.lowValueProperty());
        dataFilter.selectedMaxIntensensityProperty().bindBidirectional(intensityFilter.highValueProperty());
    }

    static void createBindingQuality(DataFilter dataFilter, List<CheckBox> qualityFilter, List<String> labels) {
        for (int i = 0; i < qualityFilter.size(); i++) {
            dataFilter.getSelectedQuality().get(labels.get(i)).bindBidirectional(qualityFilter.get(i).selectedProperty());
        }
    }

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
