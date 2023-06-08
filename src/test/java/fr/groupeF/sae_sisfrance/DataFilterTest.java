package fr.groupeF.sae_sisfrance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DataFilterTest {

    public ObservableList<Earthquake> testEartquakeList() {
        ObservableList<Earthquake> earthquakesList = FXCollections.observableArrayList();
        // Process the data
        earthquakesList.add(new Earthquake(new String[]{"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "AQUITAINE","", "495317.86", "6481234.51", "45.40", "0.38", "5", "SURE"}));
        earthquakesList.add(new Earthquake(new String[]{"640562","2000/03/13","4 h 16 min 8 sec","PAYS BASQUE (LOHITZUN-OYHERCQ)","PYRENEES OCCIDENTALES","","382038.76","6250571.66","43.28","-0.92","5","SURE"}));
        earthquakesList.add(new Earthquake(new String[]{"370036","2007/01/08","11 h 36 min 55 sec","CHINONAIS (S-E. CHINON)","TOURAINE","","502775.52","6668034.02","47.08","0.40","3.5","ASSEZ SURE"}));
        earthquakesList.add(new Earthquake(new String[]{"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "AQUITAINE","", "495317.86", "6481234.51", "45.40", "0.38", "5", "SURE"}));
        earthquakesList.add(new Earthquake(new String[]{"370032","2002/08/06","17 h 25 min 14 sec","VALLEE DE LA LOIRE (LANGEAIS)","TOURAINE","","506325.94","6699406.93","47.37","0.43","4","SURE"}));
        earthquakesList.add(new Earthquake(new String[]{"240004", "2000/02/23", "15 h 46 min 40 sec", "RIBERACOIS (CHAMPAGNE-ET-FONTAINE)", "BOUCHE DU RHONE","", "495317.86", "6481234.51", "45.40", "0.37", "4", "SURE"}));
        return earthquakesList;
    }

    @Test
    public void testSomeMethod() {
        DataFilter data = new DataFilter(testEartquakeList());
        assertEquals(data.getEarthquake(), testEartquakeList());
    }
    @Test
    public void testRegionFilter() {
        DataFilter data = new DataFilter(testEartquakeList());
        data.setRegionFilter("AQUITAINE");
        ObservableList<Earthquake> expectedList = FXCollections.observableArrayList();
        expectedList.add(testEartquakeList().get(0));
        expectedList.add(testEartquakeList().get(3));
        assertEquals(expectedList, data.getFilteredEarthquakes());
    }

    @Test
    public void testCoordinateFilter() {
        DataFilter data = new DataFilter(testEartquakeList());
        data.setLatitude(45.00);
        data.setLongitude(0.37);
        data.setRayon(50);
        ObservableList<Earthquake> expectedList = FXCollections.observableArrayList();
        expectedList.add(testEartquakeList().get(0));
        expectedList.add(testEartquakeList().get(3));
        expectedList.add(testEartquakeList().get(5));
        assertEquals(expectedList, data.getFilteredEarthquakes());
    }

    @Test
    public void testintensityFilter() {
        DataFilter data = new DataFilter(testEartquakeList());
        data.setIntensityMin(0);
        data.setIntensityMax(4);
        ObservableList<Earthquake> expectedList = FXCollections.observableArrayList();
        expectedList.add(testEartquakeList().get(2));
        expectedList.add(testEartquakeList().get(4));
        expectedList.add(testEartquakeList().get(5));
        assertEquals(expectedList, data.getFilteredEarthquakes());
    }

    @Test
    public void testManyFilter() {
        DataFilter data = new DataFilter(testEartquakeList());
        data.setRegionFilter("BOUCHE DU RHONE");
        data.setLatitude(45.00);
        data.setLongitude(0.37);
        data.setRayon(50);
        data.setIntensityMin(4);
        data.setIntensityMax(4);
        ObservableList<Earthquake> expectedList = FXCollections.observableArrayList();
        expectedList.add(testEartquakeList().get(5));
        assertEquals(expectedList, data.getFilteredEarthquakes());
    }

    @Test
    public void testUpdateFilter() {
        DataFilter data = new DataFilter(testEartquakeList());
        data.setRegionFilter("BOUCHE DU RHONE");
        data.setIntensityMin(7);
        data.setIntensityMax(10);
        ObservableList<Earthquake> expectedList = FXCollections.observableArrayList();
        expectedList.add(testEartquakeList().get(5));
        data.setRegionFilter("BOUCHE DU RHONE");
        data.setLatitude(45.00);
        data.setLongitude(0.37);
        data.setRayon(50);
        data.setIntensityMin(4);
        data.setIntensityMax(4);
        assertEquals(expectedList, data.getFilteredEarthquakes());
    }
}