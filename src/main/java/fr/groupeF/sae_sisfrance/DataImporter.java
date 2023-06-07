package fr.groupeF.sae_sisfrance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataImporter {
    public static ArrayList<Earthquake> readCSV(File csvFile) {
        String line;
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
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
    public static ArrayList<Earthquake> readCSV(String csvFile){
        String line;
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
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

