package fr.groupeF.sae_sisfrance;

import javafx.beans.property.SimpleIntegerProperty;

import java.time.Year;
import java.util.List;

/**
 * Earthquake represents a single earthquake event.
 * It stores information such as the identifier, date, location, latitude, longitude, intensity, and data quality of the earthquake.
 */
public class Earthquake {
    private final Integer identifiant; // Identifiant du tremblement de terre
    private final String date; // Date du tremblement de terre
    private final String zone; // endroit où le tremblement de terre s'est produit
    private final String latitude; // Latitude du lieu du tremblement de terre
    private final String longitude; // Longitude du lieu du tremblement de terre
    private final String intensity; // Intensité du tremblement de terre
    private final String quality; // Qualité des données relatives au tremblement de terre


    /**
     * Constructs an Earthquake object with the provided data.
     * @param data A list of strings containing the earthquake data.
     */
    public Earthquake(List<String> data) {
        this.identifiant = Integer.valueOf(data.get(0).replace("\"", ""));
        this.date = data.get(1).replace("\"", "");
        this.zone = data.get(4).replace("\"", "");
        this.latitude = data.get(8).replace("\"", "");
        this.longitude = data.get(9).replace("\"", "");
        this.intensity = data.get(10).replace("\"", "");
        this.quality = data.get(11).replace("\"", "");
    }

    /**
     * Returns the identifiant of the earthquake.
     * @return The identifiant of the earthquake.
     */
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**
     * Returns the date of the earthquake.
     * @return The date of the earthquake.
     */
    public String getDate() {
    return date;
    }

    /**
     * Returns the year of the earthquake.
     * @return The year of the earthquake.
     */
    public Integer getYear(){
        String[] numbers = date.split("/");
        return Integer.valueOf(numbers[0]);
    }

    /**
     * Returns the zone where the earthquake occurred.
     * @return The zone where the earthquake occurred.
     */
    public String getZone() {
        return zone;
    }

    /**
     * Returns the latitude of the earthquake location.
     * @return The latitude of the earthquake location.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the earthquake location.
     * @return The longitude of the earthquake location.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Returns the intensity of the earthquake.
     * @return The intensity of the earthquake.
     */
    public String getIntensity() {
        return intensity;
    }

    /**
     * Returns the quality of the earthquake data.
     * @return The quality of the earthquake data.
     */
    public String getQuality() {return quality;}

    /**
     * Checks if the provided object is equal to this Earthquake.
     * @param o The object to compare.
     * @return true if the object is equal to this Earthquake, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Earthquake)) {
            return false;
        }
        Earthquake e = (Earthquake) o;
        return e.getIdentifiant().equals(this.getIdentifiant());
    }
}

