package fr.groupeF.sae_sisfrance;

import java.util.*;

public class StatCalcul {

    // ---------- GLOBAL STAT ----------
    static public int totalNumberOfEarthquakes(DataFilter dataFilter) {
        return dataFilter.getFilteredEarthquakes().size();
    }

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
/*    static public double globalAverageIntensityPerYear(DataFilter dataFilter) {
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
    }*/

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

    static public double globalAverageEarthquakeByZone(DataFilter dataFilter) {
        TreeMap<String, Integer> zoneOcc = zoneOcc(dataFilter);
        double sum = zoneOcc.values().stream().mapToInt(Integer::intValue).sum();
        double res = sum / zoneOcc.size();
        return Math.round(res * 100.0) / 100.0;
    }

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

    static private TreeMap<String, Integer> zoneOcc(DataFilter dataFilter) {
        TreeMap<String, Integer> zoneOcc = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            if (!earthquake.getZone().isEmpty()) {
                zoneOcc.put(earthquake.getZone(), zoneOcc.getOrDefault(earthquake.getZone(), 0) + 1);
            }
        }
        return zoneOcc;
    }

    static private TreeMap<Integer, Integer> yearOcc(DataFilter dataFilter) {
        TreeMap<Integer, Integer> yearOcc = new TreeMap<>();
        for (Earthquake earthquake : dataFilter.getFilteredEarthquakes()) {
            yearOcc.put(earthquake.getYear(), yearOcc.getOrDefault(earthquake.getYear(), 0) + 1);
        }
        return yearOcc;
    }

}
