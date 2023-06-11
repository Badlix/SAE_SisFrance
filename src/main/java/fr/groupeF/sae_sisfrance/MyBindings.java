package fr.groupeF.sae_sisfrance;

import javafx.util.StringConverter;

import java.text.DecimalFormat;

public class MyBindings {
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
}
