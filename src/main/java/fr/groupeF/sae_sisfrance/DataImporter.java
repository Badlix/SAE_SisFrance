package fr.groupeF.sae_sisfrance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataImporter is a utility class that provides methods for parsing and reading earthquake data from a CSV file.
 */
public class DataImporter {

    /**
     * Parses a comma-separated string and returns a list of strings.
     * @param inputString The input string to parse.
     * @return A list of strings parsed from the input string.
     */
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

    /**
     * Reads earthquake data from a CSV file and returns a list of Earthquake objects.
     * @param csvFile The CSV file to read.
     * @return A list of Earthquake objects read from the CSV file.
     */
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

