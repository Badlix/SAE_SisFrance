package fr.groupeF.sae_sisfrance;

public class Coordinate {
    private static final double EARTH_RADIUS = 6371; // Rayon de la Terre en kilomètres

    /**
     * Vérifie si des coordonnées (latitude/longitude) se trouvent à l'intérieur d'un rayon autour d'un point donné.
     *
     * @param pointLatitude   La latitude du point central.
     * @param pointLongitude  La longitude du point central.
     * @param targetLatitude  La latitude des coordonnées à vérifier.
     * @param targetLongitude La longitude des coordonnées à vérifier.
     * @param radius          Le rayon en kilomètres.
     * @return true si les coordonnées se trouvent à l'intérieur du rayon, false sinon.
     */
    public static boolean isCoordinateWithinRadius(double pointLatitude, double pointLongitude,
                                                   double targetLatitude, double targetLongitude, double radius) {
        double distance = calculateDistance(pointLatitude, pointLongitude, targetLatitude, targetLongitude);
        return distance <= radius;
    }

    /**
     * Calcule la distance en kilomètres entre deux points géographiques (latitude/longitude) en utilisant la formule haversine.
     *
     * @param latitude1  La latitude du premier point.
     * @param longitude1 La longitude du premier point.
     * @param latitude2  La latitude du deuxième point.
     * @param longitude2 La longitude du deuxième point.
     * @return La distance en kilomètres entre les deux points.
     */
    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
