package fr.groupeF.sae_sisfrance;
import fr.groupeF.sae_sisfrance.utils.MyDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyDateTest {
    @Test
    public void testInitializationComplete() {
        MyDate date = new MyDate("2004/12/02");
        assertEquals(2004, date.getYear());
        assertEquals(12, date.getMonth());
        assertEquals(2, date.getDay());
    }
    @Test
    public void testInitializationSansJour() {
        MyDate date = new MyDate("789/05");
        assertEquals(789, date.getYear());
        assertEquals(5, date.getMonth());
        assertEquals(0, date.getDay());
    }
    @Test
    public void testInitializationSansJourEtSansMois() {
        MyDate date = new MyDate("2023");
        assertEquals(2023, date.getYear());
        assertEquals(0, date.getMonth());
        assertEquals(0, date.getDay());
    }
    @Test
    public void testIsBeforeDate() {
        MyDate date1 = new MyDate("2004/12/02");
        MyDate date2 = new MyDate("2012/01");
        MyDate date3 = new MyDate("2023");
        assertEquals(true, date1.isBefore(date1));
        assertEquals(true, date1.isBefore(date2));
        assertEquals(true, date1.isBefore(date3));
        assertEquals(false, date2.isBefore(date1));
        assertEquals(true, date2.isBefore(date2));
        assertEquals(true, date2.isBefore(date3));
        assertEquals(false, date3.isBefore(date1));
        assertEquals(false, date3.isBefore(date2));
        assertEquals(true, date3.isBefore(date3));
    }
    @Test
    public void testIsAfterDate() {
        MyDate date1 = new MyDate("2004-12-02");
        MyDate date2 = new MyDate("2012/01");
        MyDate date3 = new MyDate("2023");
        assertEquals(true, date1.isAfter(date1));
        assertEquals(false, date1.isAfter(date2));
        assertEquals(false, date1.isAfter(date3));
        assertEquals(true, date2.isAfter(date1));
        assertEquals(true, date2.isAfter(date2));
        assertEquals(false, date2.isAfter(date3));
        assertEquals(true, date3.isAfter(date1));
        assertEquals(true, date3.isAfter(date2));
        assertEquals(true, date3.isAfter(date3));
    }
    @Test
    public void testIsBetween() {
        MyDate date1 = new MyDate("2004/12/02");
        MyDate date2 = new MyDate("2004/03");
        MyDate date3 = new MyDate("2005");
        MyDate date4 = new MyDate("2004/06/30");
        MyDate date5 = new MyDate("2005/01");
        MyDate date6 = new MyDate("2006");
        assertEquals(true, date4.isBetween(date2, date1));
        assertEquals(true, date5.isBetween(date3, date6));
        assertEquals(false, date2.isBetween(date4, date5));
    }
}
