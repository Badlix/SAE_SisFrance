package fr.groupeF.sae_sisfrance.utils;

import fr.groupeF.sae_sisfrance.DataFilter;

import java.util.*;

/**
 * A class use for calculating various statistics related to earthquakes.
 */
public class StatCalculations {

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
    static public TreeMap<Integer, Double> globalAverageintensityByYear(DataFilter dataFilter) {
        TreeMap<Integer, Integer> nbEarthquakeWithIntensityByYear = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            if (earthquake.getIntensity().isEmpty() == false) {
                nbEarthquakeWithIntensityByYear.put(earthquake.getYear(), nbEarthquakeWithIntensityByYear.getOrDefault(earthquake.getYear(), 0) + 1);
            }
        }
        TreeMap<Integer, Double> sumIntensityByYear = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            if (earthquake.getIntensity().isEmpty() == false) {
                sumIntensityByYear.put(earthquake.getYear(), sumIntensityByYear.getOrDefault(earthquake.getYear(), 0.0) + Double.valueOf(earthquake.getIntensity()));
            }
        }
        TreeMap<Integer, Double> avgIntensityByYear = new TreeMap<>();
        for(Map.Entry<Integer, Integer> entry : nbEarthquakeWithIntensityByYear.entrySet()) {
            double res = sumIntensityByYear.get(entry.getKey()) / entry.getValue();
            res = Math.round(res * 100.0) / 100.0;
            avgIntensityByYear.put(entry.getKey(), res);
        }
        return avgIntensityByYear;
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
    static public TreeMap<Integer, Integer> yearOcc(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            yearOcc.put(earthquake.getYear(), yearOcc.getOrDefault(earthquake.getYear(), 0) + 1);
        }
        return yearOcc;
    }
}