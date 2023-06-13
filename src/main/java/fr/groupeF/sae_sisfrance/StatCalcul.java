package fr.groupeF.sae_sisfrance;

import java.util.*;

public class StatCalcul {

    // ---------- GLOBAL STAT ----------
    static public int totalNumberOfEarthquakes(DataFilter dataFilter) {
        return dataFilter.getAllEarthquakes().size();
    }

    static public float globalAverageIntensity(DataFilter dataFilter) {
        int nbEartquakes = 0;
        int sumIntensity = 0;
        for (Earthquake earthquake : dataFilter.getAllEarthquakes()) {
            if (!earthquake.getIntensity().isEmpty()) {
                ++nbEartquakes;
                sumIntensity += Float.valueOf(earthquake.getIntensity());
            }
        }
        return sumIntensity/nbEartquakes;
    }

    static public Map.Entry<String, Integer> mostAffectedRegion(DataFilter dataFilter) {
        TreeMap<String, Integer> regionOcc = regionOcc(dataFilter);
        Map.Entry<String, Integer> result = new AbstractMap.SimpleEntry<String, Integer>(regionOcc.firstEntry());
        for (Map.Entry<String, Integer> entry : regionOcc.entrySet()) {
            if (entry.getValue() > result.getValue()) {
                result = entry;
            }
        }
        return result;
    }

    static public float globalAverageEartquakeByRegion(DataFilter dataFilter) {
        TreeMap<String, Integer> regionOcc = regionOcc(dataFilter);
        int sum = regionOcc.values().stream().mapToInt(Integer::intValue).sum();
        return sum/regionOcc.size();
    }

    static public Map.Entry<Integer, Integer> mostAffectedYear(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = yearOcc(dataFilter);
        Map.Entry<Integer, Integer> result = new AbstractMap.SimpleEntry<Integer, Integer>(yearOcc.firstEntry());;
        for (Map.Entry<Integer, Integer> entry : yearOcc.entrySet()) {
            if (entry.getValue() > result.getValue()) {
                result = entry;
            }
        }
        return result;
    }

    static public float globalAverageEartquakesByYear(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = yearOcc(dataFilter);
        int sumEarthquake = 0;
        int nbYear = yearOcc.lastKey() - 1000; // There is not enough data about earthquakes that happened before the year 1000
        for(Map.Entry<Integer, Integer> entry : yearOcc.entrySet()) {
            if (entry.getKey() >= 1000) {
                ++sumEarthquake;
            }
        }
        return sumEarthquake/nbYear;
    }

    // ---------- GET OCCURENCES MAP ----------

    static private TreeMap<String, Integer> regionOcc(DataFilter dataFilter) {
        TreeMap<String, Integer> regionOcc = new TreeMap<String, Integer>();
        for (Earthquake earthquake : dataFilter.getAllEarthquakes()) {
            if (!earthquake.getRegion().isEmpty()) {
                if (regionOcc.containsKey(earthquake.getRegion())) {
                    regionOcc.replace(earthquake.getRegion(), regionOcc.get(earthquake.getRegion()) + 1);
                } else {
                    regionOcc.put(earthquake.getRegion(), 1);
                }
            }
        }
        return regionOcc;
    }

    static private TreeMap<Integer, Integer> yearOcc(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = new TreeMap<Integer, Integer>();
        for (Earthquake earthquake : dataFilter.getAllEarthquakes()) {
            if (yearOcc.containsKey(earthquake.getDate().getYear())) {
                yearOcc.replace(earthquake.getDate().getYear(), yearOcc.get(earthquake.getDate().getYear()) + 1);
            } else {
                yearOcc.put(earthquake.getDate().getYear(), 1);
            }
        }
        return yearOcc;
    }

}
