package fr.groupeF.sae_sisfrance;
import java.util.List;

public class Earthquake {
    private final String identifiant; // Identifiant du tremblement de terre
    private final String date; // Date du tremblement de terre
//    private final String hour; // Heure du tremblement de terre
//    private final String name; // Nom du tremblement de terre
    private final String region; // endroit où le tremblement de terre s'est produit
//    private final String shock; // Type de choc causé par le tremblement de terre
//    private final String xRGF; // Coordonnée X (système de référence géographique)
//    private final String yRGF; // Coordonnée Y (système de référence géographique)
    private final String latitude; // Latitude du lieu du tremblement de terre
    private final String longitude; // Longitude du lieu du tremblement de terre
    private final String intensity; // Intensité du tremblement de terre
    private final String quality; // Qualité des données relatives au tremblement de terre

    // Constructeur de la classe Earthquake
public Earthquake(List<String> data) {
    this.identifiant = data.get(0).replace("\"", "");
   // this.date = new MyDate(data.get(1).replace("\"", ""));
    this.date = data.get(1).replace("\"", "");
//    this.hour = data.get(2).replace("\"", "");
//    this.name = data.get(3).replace("\"", "");
    this.region = data.get(4).replace("\"", "");
//    this.shock = data.get(5).replace("\"", "");
//    this.xRGF = data.get(6).replace("\"", "");
//    this.yRGF = data.get(7).replace("\"", "");
    this.latitude = data.get(8).replace("\"", "");
    this.longitude = data.get(9).replace("\"", "");
    this.intensity = data.get(10).replace("\"", "");
    this.quality = data.get(11).replace("\"", "");
}

    public String getIdentifiant() {
        return identifiant;
    }

//    public MyDate getDate() {
//        return date;
//    }
    public String getDate() {
    return date;
    }
    public Integer getYear(){
        String[] numbers = date.split("/");
        return Integer.valueOf(numbers[0]);
    }

    public String getRegion() {
        return region;
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
}

