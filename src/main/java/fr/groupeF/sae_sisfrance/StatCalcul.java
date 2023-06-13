package fr.groupeF.sae_sisfrance;

import java.util.*;

/**
 * A class use for calculating various statistics related to earthquakes.
 */
public class StatCalcul {

    /**
     * Returns the total number of earthquakes based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The total number of earthquakes.
     */
    static public int totalNumberOfEarthquakes(DataFilter dataFilter) {
        return dataFilter.getFilteredEarthquakes().size();
    }

    /**
     * Returns the global average intensity of earthquakes based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The global average intensity.
     */
    static public double globalAverageIntensity(DataFilter dataFilter) {
        int nbEarthquakes = 0;
        double sumIntensity = 0;
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            if (!earthquake.getIntensity().isEmpty()) {
                ++nbEarthquakes;
                sumIntensity += Float.parseFloat(earthquake.getIntensity());
            }
        }
        double res = sumIntensity /nbEarthquakes;
        return Math.round(res * 100.0) / 100.0;
    }

    /**
     * Returns the global average intensity per year based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The global average intensity per year.
     */
    static public double globalAverageIntensityPerYear(DataFilter dataFilter) {
        HashMap<String, Double> sumIntensityPerYear = new HashMap<>();
        HashMap<String, Integer> nbEarthquakesPerYear = new HashMap<>();

        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            if (!earthquake.getIntensity().isEmpty()) {
                String year = earthquake.getYear().toString();
                double intensity = Double.parseDouble(earthquake.getIntensity());

                if (sumIntensityPerYear.containsKey(year)) {
                    double sum = sumIntensityPerYear.get(year) + intensity;
                    sumIntensityPerYear.put(year, sum);
                    nbEarthquakesPerYear.put(year, nbEarthquakesPerYear.get(year) + 1);
                } else {
                    sumIntensityPerYear.put(year, intensity);
                    nbEarthquakesPerYear.put(year, 1);
                }
            }
        }

        double totalAverageIntensity = 0;
        int totalNbEarthquakes = 0;

        for (Map.Entry<String, Double> entry : sumIntensityPerYear.entrySet()) {
            String year = entry.getKey();
            double sumIntensity = entry.getValue();
            int nbEarthquakes = nbEarthquakesPerYear.get(year);
            double averageIntensity = sumIntensity / nbEarthquakes;

            totalAverageIntensity += averageIntensity;
            totalNbEarthquakes++;
        }

        double globalAverage = totalAverageIntensity / totalNbEarthquakes;
        return Math.round(globalAverage * 100.0) / 100.0;
    }

    /**
     * Returns the most affected zone based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The most affected zone.
     */
    static public Map.Entry<String, Integer> mostAffectedZone(DataFilter dataFilter) {
        TreeMap<String, Integer> zoneOcc = zoneOcc(dataFilter);
        Map.Entry<String, Integer> result = new AbstractMap.SimpleEntry<>("",0);
        for (Map.Entry<String, Integer> entry : zoneOcc.entrySet()) {
            if (entry.getValue() > result.getValue()) {
                result = entry;
            }
        }
        return result;
    }

    /**
     * Returns the global average number of earthquakes per zone based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The global average number of earthquakes per zone.
     */
    static public double globalAverageEarthquakeByZone(DataFilter dataFilter) {
        TreeMap<String, Integer> zoneOcc = zoneOcc(dataFilter);
        double sum = zoneOcc.values().stream().mapToInt(Integer::intValue).sum();
        double res = sum / zoneOcc.size();
        return Math.round(res * 100.0) / 100.0;
    }

    /**
     * Returns the most affected year based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The most affected year.
     */
    static public Map.Entry<Integer, Integer> mostAffectedYear(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = yearOcc(dataFilter);
        Map.Entry<Integer, Integer> result = new AbstractMap.SimpleEntry<>(0,0);
        for (Map.Entry<Integer, Integer> entry : yearOcc.entrySet()) {
            if (entry.getValue() > result.getValue()) {
                result = entry;
            }
        }
        return result;
    }

    /**
     * Returns the global average number of earthquakes per year based on the data filter.
     * @param dataFilter The data filter to apply.
     * @return The global average number of earthquakes per year.
     */
    static public double globalAverageEarthquakesByYear(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = yearOcc(dataFilter);
        float sumEarthquake = 0;
        float nbYear = 1;
        // There is not enough data in any csv about earthquakes that happened before the year 1000
        if (!yearOcc.isEmpty()) {
            if (yearOcc.firstKey() < 1000) {
                nbYear = yearOcc.lastKey()+1 - 1000;
            } else {
                nbYear = yearOcc.lastKey()+1 - yearOcc.firstKey();
            }
            for(Map.Entry<Integer, Integer> entry : yearOcc.entrySet()) {
                if (entry.getKey() >= 1000) {
                    sumEarthquake += entry.getValue();
                }
            }
        }
        double res = sumEarthquake /nbYear;
        return Math.round(res * 100.0) / 100.0;
    }

    // ---------- GET OCCURRENCES MAP ----------

    /**
     * Returns a map containing the occurences of eartquakes per zone.
     * @param dataFilter The data filter to apply.
     * @return A map with zone occurrences.
     */
    static private TreeMap<String, Integer> zoneOcc(DataFilter dataFilter) {
        TreeMap<String, Integer> zoneOcc = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            if (!earthquake.getZone().isEmpty()) {
                zoneOcc.put(earthquake.getZone(), zoneOcc.getOrDefault(earthquake.getZone(), 0) + 1);
            }
        }
        return zoneOcc;
    }

    /**
     * Returns a map containing the occurences of earthquakes per year.
     * @param dataFilter The data filter to apply.
     * @return A map with year occurrences.
     */
    static private TreeMap<Integer, Integer> yearOcc(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            yearOcc.put(earthquake.getYear(), yearOcc.getOrDefault(earthquake.getYear(), 0) + 1);
        }
        return yearOcc;
    }
}
