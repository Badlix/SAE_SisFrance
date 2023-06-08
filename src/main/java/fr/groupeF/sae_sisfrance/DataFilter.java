package fr.groupeF.sae_sisfrance;

import javafx.collections.ObservableList;

public class DataFilter {

    private ObservableList<Earthquake> earthquake;
    private ObservableList<Earthquake> filteredEarthquakes;

    private String regionFilter;
    private double latitude;
    private double longitude;

    private int rayon;
    private String dateDebut;
    private String dateFin;
    private float intensite;

    public DataFilter(ObservableList<Earthquake> allEarthquake) {
        this.earthquake = allEarthquake;
        this.filteredEarthquakes = allEarthquake;
        // TEMPORAIRE -> QD LES FILTRES DE LA PAGE UPLOAD SERONT FONCITONNEL
        // LEURS VALEURS SERONT PASSER EN PARAMETRE A CE CONSTRUCTEUR POUR INITALIASER LES VARIABLE DE FILTRES
        this.regionFilter = "";
        this.latitude = 0;
        this.longitude = 0;
        this.rayon = 0;
        this.dateDebut = "";
        this.dateFin = "";
        this.intensite = 0;
    }

    public ObservableList<Earthquake> getEarthquake() {
        return earthquake;
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
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public float getIntensite() {
        return intensite;
    }

    public void setIntensite(float intensite) {
        this.intensite = intensite;
    }
}