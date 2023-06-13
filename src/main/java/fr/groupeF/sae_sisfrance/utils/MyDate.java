package fr.groupeF.sae_sisfrance.utils;

import javafx.beans.property.SimpleStringProperty;

/**
 * A class representing a custom date object.
 * It provides methods for parsing and manipulating dates.
 */
public class MyDate {
    private final SimpleStringProperty date;
    private int year;
    private int month;
    private int day;

    /**
     * Constructs a new `MyDate` object with the specified date string.
     * @param dateTxt The date string in the format "YYYY-MM-DD".
     */
    public MyDate(String dateTxt) {
        date = new SimpleStringProperty(dateTxt);
        update();
        date.addListener((observable, oldValue, newValue) -> {
            update();
        });
    }

    /**
     * Updates the year, month, and day values based on the current date string.
     * This method is called automatically when the date property changes.
     */
    public void update() {
        String[] numbers = {};
        if (date.getValue().contains("/")) {
            numbers = date.getValue().split("/");
        } else if (date.getValue().contains("-")) {
            numbers = date.getValue().split("-");
        } else {
            String tmp = date.getValue() + "/";
            numbers = tmp.split("/");
        }

        if (numbers.length >= 1 && !numbers[0].isEmpty()) {
            year = Integer.parseInt(numbers[0]);
        } if (numbers.length >= 2) {
            month = Integer.parseInt(numbers[1]);
            if (numbers.length >= 3) {
                day = Integer.parseInt(numbers[2]);
            } else {
                day = 0;
            }
        } else {
            month = 0;
            day = 0;
        }
    }

    /**
     * Returns the property representing the date string.
     * @return The date property.
     */
    public SimpleStringProperty dateProperty() {
        return date;
    }

    /**
     * Returns the year value of the date.
     * @return The year value.
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Returns the month value of the date.
     * @return The month value.
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * Returns the day value of the date.
     * @return The day value.
     */
    public Integer getDay() {
        return day;
    }

    /**
     * Returns the date string value.
     * @return The date string.
     */
    public String getDateValue() {
        return date.getValue();
    }

    /**
     * Checks if this date is between the specified after and before date.
     * @param afterDate  The after date to compare.
     * @param beforeDate The before date to compare.
     * @return `true` if this date is between the after date and before date, `false` otherwise.
     */
    public boolean isBetween(MyDate afterDate, MyDate beforeDate) {
        return isAfter(afterDate) && isBefore(beforeDate);
    }

    /**
     * Checks if this date is before the other date.
     * @param aDate The date to compare.
     * @return `true` if this date is before the specified date, `false` otherwise.
     */
    public boolean isBefore(MyDate aDate) {
        // Compare Years
        if (year == 0 || aDate.year == 0) {
            return true;
        }
        if (year > aDate.year) {
            return false;
        } else if (year < aDate.year) {
            return true;
        }
        // Compare Months
        if (month == 0 || aDate.month == 0) {
            return true;
        }
        if (month > aDate.month) {
            return false;
        } else if (month < aDate.month) {
            return true;
        }
        // Compare Days
        if (day == 0 || aDate.day == 0) {
            return true;
        }
        if (day > aDate.day) {
            return false;
        }
        return true;
    }

    /**
     * Checks if this date is after the other date.
     * @param aDate The date to compare.
     * @return `true` if this date is after the specified date, `false` otherwise.
     */
    public boolean isAfter(MyDate aDate) {
        // Compare Years
        if (year == 0 || aDate.year == 0) {
            return true;
        }
        if (year < aDate.year) {
            return false;
        } else if (year > aDate.year) {
            return true;
        }
        // Compare Months
        if (month == 0 || aDate.month == 0) {
            return true;
        }
        if (month < aDate.month) {
            return false;
        } else if (month > aDate.month) {
            return true;
        }
        // Compare Days
        if (this.day == 0 || aDate.day == 0) {
            return true;
        }
        if (this.day < aDate.day){
            return false;
        }
        return true;
    }

    /**
     * Returns the string representation of the date in the format "YYYY-MM-DD".
     * @return The string of the date.
     */
    @Override
    public String toString() {
        String txt = "";
        txt += String.valueOf(year);
        if (month < 10) {
            txt += "-0" + month;
        } else {
            txt += "-" + month;
        }
        if (day < 10) {
            txt += "-0" + day;
        } else {
            txt += "-" + day;
        }
        return txt;
    }
}
