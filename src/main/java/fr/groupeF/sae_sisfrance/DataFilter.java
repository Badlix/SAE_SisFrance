package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataFilter {

    private ObservableList<Earthquake> allEarthquakes;
    private ObservableList<Earthquake> filteredEarthquakes;

    private String regionFilter;
    private double latitude;
    private double longitude;

    private int rayon;
    private String dateDebut;
    private String dateFin;
    private double intensityMin;
    private double intensityMax;

    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        this.allEarthquakes = allEarthquake;
        this.filteredEarthquakes = FXCollections.observableArrayList();
        this.filteredEarthquakes.setAll(allEarthquake);
        // TEMPORAIRE -> QD LES FILTRES DE LA PAGE UPLOAD SERONT FONCITONNEL
        // LEURS VALEURS SERONT PASSER EN PARAMETRE A CE CONSTRUCTEUR POUR INITALIASER LES VARIABLE DE FILTRES
        this.regionFilter = null;
        this.latitude = 0;
        this.longitude = 0;
        this.rayon = -1;
        this.dateDebut = "";
        this.dateFin = "";
        this.intensityMin = 0;
        this.intensityMax = 10;
    }

    public ObservableList<Earthquake> getEarthquake() {
        return allEarthquakes;
    }

    public ObservableList<Earthquake> getFilteredEarthquakes() {
        return filteredEarthquakes;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRegionFilter() {
        return regionFilter;
    }

    public void setRegionFilter(String regionFilter) {
        this.regionFilter = regionFilter;
        System.out.println("FILTRE REGION APPLIQUÉ : " + regionFilter);
        applyFilter();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
        applyFilter();
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
        applyFilter();
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
        applyFilter();
    }

    public double getIntensityMin() {
        return intensityMin;
    }

    public void setIntensityMin(double intensite) {
        this.intensityMin = intensite;
        applyFilter();
    }

    public double getIntensityMax() {
        return intensityMax;
    }

    public void setIntensityMax(double intensite) {
        this.intensityMax = intensite;
        applyFilter();
    }

    private boolean isInRegion(Earthquake earthquake) {
        if (this.regionFilter == null || this.regionFilter.isEmpty()) {
            return true;
        }
        return earthquake.getRegion().contentEquals(this.regionFilter);
    }

    private boolean isInCoordinate(Earthquake earthquake) {
        if (this.longitude == 0 || this.latitude == 0 || this.rayon == -1) {
            return true;
        }
        if (earthquake.getLatitude().isEmpty() || earthquake.getLongitude().isEmpty()) {
            return false;
        }
        if (Double.valueOf(earthquake.getLatitude()) > 48 && Double.valueOf(earthquake.getLatitude()) < 49) {
            System.out.println(Double.valueOf(earthquake.getLatitude()));
            System.out.println(this.latitude);
            System.out.println(Double.valueOf(earthquake.getLongitude()));
            System.out.println(this.longitude);
            System.out.println(Coordinate.isCoordinateWithinRadius(this.latitude, this.longitude, Double.valueOf(earthquake.getLatitude()), Double.valueOf(earthquake.getLongitude()), this.rayon));
        }
        double earthquakeLat = Double.valueOf(earthquake.getLatitude());
        double earthquakeLong = Double.valueOf(earthquake.getLongitude());
        return Coordinate.isCoordinateWithinRadius(this.latitude, this.longitude, earthquakeLat, earthquakeLong, this.rayon);
    }

    private boolean isBetweenIntensity(Earthquake earthquake) {
        return (intensityMin <= Float.valueOf(earthquake.getIntensity()) && intensityMax >= Float.valueOf(earthquake.getIntensity()));
    }

    private void applyFilter() {
        filteredEarthquakes.clear();
        for (Earthquake earthquake: allEarthquakes) {
            if (isInRegion(earthquake)) {
                if (isInCoordinate(earthquake)) {
                    if (isBetweenIntensity(earthquake)) {
                        filteredEarthquakes.add(earthquake);
                    }
                }
            }
        }
    }
}