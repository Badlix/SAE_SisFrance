package fr.groupeF.sae_sisfrance;

public class Earthquake {


    private String identifiant;
    private String date;
    private String hour;
    private String name;
    private String region;
    private String shock;
    private String x;
    private String y;
    private String latitude;
    private String longitude;
    private String intensity;
    private String quality;

public Earthquake(String identifiant, String date, String hour, String name, String region, String shock, String x, String y, String latitude, String longitude, String intensity, String quality) {
        this.identifiant = identifiant;
        this.date = date;
        this.hour = hour;
        this.name = name;
        this.region = region;
        this.shock = shock;
        this.x = x;
        this.y = y;
        this.latitude = latitude;
        this.longitude = longitude;
        this.intensity = intensity;
        this.quality = quality;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getShock() {
        return shock;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getIntensity() {
        return intensity;
    }

    public String getQuality() {
        return quality;
    }
}

