package fr.groupeF.sae_sisfrance;

public class Earthquake {


    private String identifiant;
    private String date;
    private String hour;
    private String name;
    private String region;
    private String shock;
    private String xRGF;
    private String yRGF;
    private String latitude;
    private String longitude;
    private String intensity;
    private String quality;

public Earthquake(String[] data) {
    this.identifiant = data[0];
    this.date = data[1];
    this.hour = data[2];
    this.name = data[3];
    this.region = data[4];
    this.shock = data[5];
    this.xRGF = data[6];
    this.yRGF = data[7];
    this.latitude = data[8];
    this.longitude = data[9];
    this.intensity = data[10];
    this.quality = data[11];
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

    public String getxRGF() {
        return xRGF;
    }

    public String getyRGF() {
        return yRGF;
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

