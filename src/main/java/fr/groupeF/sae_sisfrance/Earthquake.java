package fr.groupeF.sae_sisfrance;

public class Earthquake {
    private String identifiant; // Identifiant du tremblement de terre
    private String date; // Date du tremblement de terre
    private String hour; // Heure du tremblement de terre
    private String name; // Nom du tremblement de terre
    private String region; // endroit où le tremblement de terre s'est produit
    private String shock; // Type de choc causé par le tremblement de terre
    private String xRGF; // Coordonnée X (système de référence géographique)
    private String yRGF; // Coordonnée Y (système de référence géographique)
    private String latitude; // Latitude du lieu du tremblement de terre
    private String longitude; // Longitude du lieu du tremblement de terre
    private String intensity; // Intensité du tremblement de terre
    private String quality; // Qualité des données relatives au tremblement de terre

    // Constructeur de la classe Earthquake
public Earthquake(String[] data) {
    this.identifiant = data[0].replace("\"", "");
    this.date = data[1].replace("\"", "");
    this.hour = data[2].replace("\"", "");
    this.name = data[3].replace("\"", "");
    this.region = data[4].replace("\"", "");
    this.shock = data[5].replace("\"", "");
    this.xRGF = data[6].replace("\"", "");
    this.yRGF = data[7].replace("\"", "");
    this.latitude = data[8].replace("\"", "");
    this.longitude = data[9].replace("\"", "");
    this.intensity = data[10].replace("\"", "");
    this.quality = data[11].replace("\"", "");
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

    public boolean is_between_dates(String date1, String date2) {
        return this.getDate().compareTo(date1) >= 0 && this.getDate().compareTo(date2) <= 0;
    }
}

