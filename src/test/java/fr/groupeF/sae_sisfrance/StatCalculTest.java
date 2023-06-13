package fr.groupeF.sae_sisfrance;

import fr.groupeF.sae_sisfrance.utils.Earthquake;
import fr.groupeF.sae_sisfrance.utils.StatCalculations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatCalculTest {

    public DataFilter testOnlyOneEartquake() {
        String[] data = new String[] {"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "BOUCHE DU RHONE","", "495317.86", "6481234.51", "45.40", "0.37", "4", "SURE"};
        Earthquake earthquake = new Earthquake(new ArrayList<>(Arrays.asList(data)));
        DataFilter dataFilter = new DataFilter(FXCollections.observableArrayList(earthquake));
        return dataFilter;
    }

    public DataFilter testEartquakeList() {
        ObservableList<Earthquake> earthquakesList = FXCollections.observableArrayList();
        // Process the data
        String[] data1 = new String[]{"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "AQUITAINE","", "495317.86", "6481234.51", "45.40", "0.38", "5", "SURE"};
        String[] data2 = new String[]{"640562","2000/03/13","4 h 16 min 8 sec","PAYS BASQUE (LOHITZUN-OYHERCQ)","PYRENEES OCCIDENTALES","","382038.76","6250571.66","43.28","-0.92","5","SURE"};
        String[] data3 = new String[]{"370036","2007/01/08","11 h 36 min 55 sec","CHINONAIS (S-E. CHINON)","TOURAINE","","502775.52","6668034.02","47.08","0.40","3.5","ASSEZ SURE"};
        String[] data4 = new String[]{"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "AQUITAINE","", "495317.86", "6481234.51", "45.40", "0.38", "5", "SURE"};
        String[] data5 = new String[]{"370032","2002/08/06","17 h 25 min 14 sec","VALLEE DE LA LOIRE (LANGEAIS)","TOURAINE","","506325.94","6699406.93","47.37","0.43","4","SURE"};
        String[] data6 = new String[]{"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "BOUCHE DU RHONE","", "495317.86", "6481234.51", "45.40", "0.37", "4", "SURE"};
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data1))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data2))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data3))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data4))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data5))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data6))));
        return new DataFilter(earthquakesList);
    }

    public DataFilter testEartquakeListWithSomeMissingValues() {
        ObservableList<Earthquake> earthquakesList = FXCollections.observableArrayList();
        // Process the data
        String[] data1 = new String[] {"240004", "2002/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "","", "495317.86", "6481234.51", "45.40", "0.38", "5", "SURE"};
        String[] data2 = new String[] {"640562","2003/03/13","4 h 16 min 8 sec","PAYS BASQUE (LOHITZUN-OYHERCQ)","PYRENEES OCCIDENTALES","","382038.76","6250571.66","43.28","-0.92","5","SURE"};
        String[] data3 = new String[] {"370036","2003/01/08","11 h 36 min 55 sec","CHINONAIS (S-E. CHINON)","TOURAINE","","502775.52","6668034.02","47.08","0.40","3.5","ASSEZ SURE"};
        String[] data4 = new String[] {"240004", "2000", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "AQUITAINE","", "495317.86", "6481234.51", "45.40", "0.38", "5", "SURE"};
        String[] data5 = new String[] {"370032","2002/08/06","17 h 25 min 14 sec","VALLEE DE LA LOIRE (LANGEAIS)","TOURAINE","","506325.94","6699406.93","47.37","0.43","","SURE"};
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data1))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data2))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data3))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data4))));
        earthquakesList.add(new Earthquake(new ArrayList<>(Arrays.asList(data5))));
        return new DataFilter(earthquakesList);
    }

    @Test
    public void totalNumberOfEarthquakes() {
        DataFilter dataFilter = testOnlyOneEartquake();
        assertEquals(1 , StatCalculations.totalNumberOfEarthquakes(dataFilter));

        dataFilter = testEartquakeList();
        assertEquals(6, StatCalculations.totalNumberOfEarthquakes(dataFilter));

        dataFilter = testEartquakeListWithSomeMissingValues();
        assertEquals(5, StatCalculations.totalNumberOfEarthquakes(dataFilter));
    }

    @Test
    public void testGlobalAverageIntensity() {
        DataFilter dataFilter = testOnlyOneEartquake();
        assertEquals(4.0 , StatCalculations.globalAverageIntensity(dataFilter));

        dataFilter = testEartquakeList();
        assertEquals(4.42, StatCalculations.globalAverageIntensity(dataFilter));

        dataFilter = testEartquakeListWithSomeMissingValues();
        assertEquals(4.63, StatCalculations.globalAverageIntensity(dataFilter));
    }

    @Test
    public void testGlobalAverageEarthquakeByRegion() {
        DataFilter dataFilter = testOnlyOneEartquake();
        assertEquals(1 , StatCalculations.globalAverageEarthquakeByZone(dataFilter));

        dataFilter = testEartquakeList();
        assertEquals(1.5, StatCalculations.globalAverageEarthquakeByZone(dataFilter));

        dataFilter = testEartquakeListWithSomeMissingValues();
        assertEquals(1.33, StatCalculations.globalAverageEarthquakeByZone(dataFilter));
    }

    @Test
    public void testGlobalAverageEarthquakeByYear() {
        DataFilter dataFilter = testOnlyOneEartquake();
        assertEquals(1 , StatCalculations.globalAverageEarthquakesByYear(dataFilter));

        dataFilter = testEartquakeList();
        assertEquals(0.75, StatCalculations.globalAverageEarthquakesByYear(dataFilter));

        dataFilter = testEartquakeListWithSomeMissingValues();
        assertEquals(1.25, StatCalculations.globalAverageEarthquakesByYear(dataFilter));
    }

    @Test
    public void testMostAffectedZone() {
        Map.Entry<String, Integer> result;

        DataFilter dataFilter = testOnlyOneEartquake();
        result = new AbstractMap.SimpleEntry<>("BOUCHE DU RHONE", 1);
        assertEquals(result , StatCalculations.mostAffectedZone(dataFilter));

        dataFilter = testEartquakeList();
        result = new AbstractMap.SimpleEntry<>("AQUITAINE", 2);
        assertEquals(result , StatCalculations.mostAffectedZone(dataFilter));

        dataFilter = testEartquakeListWithSomeMissingValues();
        result = new AbstractMap.SimpleEntry<>("TOURAINE", 2);
        assertEquals(result , StatCalculations.mostAffectedZone(dataFilter));
    }

    @Test
    public void testMostAffectedYear() {
        Map.Entry<Integer, Integer> result;

        DataFilter dataFilter = testOnlyOneEartquake();
        result = new AbstractMap.SimpleEntry<>(2000, 1);
        assertEquals(result , StatCalculations.mostAffectedYear(dataFilter));

        dataFilter = testEartquakeList();
        result = new AbstractMap.SimpleEntry<>(2000, 4);
        assertEquals(result , StatCalculations.mostAffectedYear(dataFilter));

        dataFilter = testEartquakeListWithSomeMissingValues();
        result = new AbstractMap.SimpleEntry<>(2002, 2);
        assertEquals(result , StatCalculations.mostAffectedYear(dataFilter));
    }
}
