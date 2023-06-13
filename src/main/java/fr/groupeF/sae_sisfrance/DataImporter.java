package fr.groupeF.sae_sisfrance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataImporter {

    public static List<String> parseString(String inputString) {
        List<String> result = new ArrayList<>();
        StringBuilder currentItem = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : inputString.toCharArray()) {
            if (c == ',' && !insideQuotes) {
                if (currentItem.length() == 0) {
                    result.add("");
                } else {
                    if (currentItem.charAt(currentItem.length()-1) == '/')
                        currentItem.deleteCharAt(currentItem.length()-1);
                    result.add(currentItem.toString().trim());
                    currentItem = new StringBuilder();
                }
            } else if (c == '"') {
                insideQuotes = !insideQuotes;
            } else {
                currentItem.append(c);
            }
        }
        result.add(currentItem.toString().trim());
        return result;
    }
    public static ArrayList<Earthquake> readCSV(File csvFile) {
        String line;
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                List<String> data = parseString(line);
                // Process the data
                Earthquake earthquake = new Earthquake(data);
                earthquakes.add(earthquake);
            }
            return earthquakes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

