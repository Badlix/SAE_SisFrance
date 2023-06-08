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
    private float intensityMin;
    private float intensityMax;

    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        this.allEarthquakes = allEarthquake;
        this.filteredEarthquakes = FXCollections.observableArrayList();
        this.filteredEarthquakes.setAll(allEarthquake);
        // TEMPORAIRE -> QD LES FILTRES DE LA PAGE UPLOAD SERONT FONCITONNEL
        // LEURS VALEURS SERONT PASSER EN PARAMETRE A CE CONSTRUCTEUR POUR INITALIASER LES VARIABLE DE FILTRES
        this.regionFilter = "";
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
        applyFilter();
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
        applyFilter();
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

    public float getIntensityMin() {
        return intensityMin;
    }

    public void setIntensityMin(float intensite) {
        this.intensityMin = intensite;
        applyFilter();
    }

    public float getIntensityMax() {
        return intensityMax;
    }

    public void setIntensityMax(float intensite) {
        this.intensityMax = intensite;
        applyFilter();
    }

    private boolean isInRegion(Earthquake earthquake) {
        if (this.regionFilter == "") {
            return true;
        }
        return earthquake.getRegion() == this.regionFilter;
    }

    private boolean isInCoordinate(Earthquake earthquake) {
        if (this.longitude == 0 || this.latitude == 0 || this.rayon == -1) {
            return true;
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