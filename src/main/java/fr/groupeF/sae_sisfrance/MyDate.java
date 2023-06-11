package fr.groupeF.sae_sisfrance;

import javafx.beans.property.SimpleIntegerProperty;

public class MyDate {
    private SimpleIntegerProperty year;

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    private SimpleIntegerProperty month;
    private SimpleIntegerProperty day;

    public MyDate(String date) {
        this.year = new SimpleIntegerProperty(0);
        this.month = new SimpleIntegerProperty(0);
        this.day = new SimpleIntegerProperty(0);
        setMyDate(date);
    }

    public void setMyDate(String date) {
        String[] numbers = {};
        if (date.contains("/")) {
            numbers = date.split("/");
        } else if (date.contains("-")) {
            numbers = date.split("-");
        }

        if (numbers.length == 1 && numbers[0].isEmpty() == false) {
            this.year.set(Integer.valueOf(numbers[0]));
            this.month.set(0);
            this.day.set(0);
        } else if (numbers.length == 2 && numbers[1].isEmpty() == false) {
            this.year.set(Integer.valueOf(numbers[0]));
            this.month.set(Integer.valueOf(numbers[1]));
            this.day.set(0);
        } else if (numbers.length >= 3 && numbers[2].isEmpty() == false) {
            this.year.set(Integer.valueOf(numbers[0]));
            this.month.set(Integer.valueOf(numbers[1]));
            this.day.set(Integer.valueOf(numbers[2]));
        }
    }

    public Integer getYear() {
        return year.getValue();
    }

    public Integer getMonth() {
        return month.getValue();
    }

    public Integer getDay() {
        return day.getValue();
    }

    public boolean isBetween(MyDate afterDate, MyDate beforeDate) {
        return isAfter(afterDate) && isBefore(beforeDate);
    }
    public boolean isBefore(MyDate aDate) {
        // Compare Years
        if (this.year.getValue() == 0 || aDate.year.getValue() == 0) {
            return true;
        }
        if (this.year.getValue() > aDate.year.getValue()) {
            return false;
        } else if (this.year.getValue() < aDate.year.getValue()) {
            return true;
        }
        // Compare Months
        if (this.month.getValue() == 0 || aDate.month.getValue() == 0) {
            return true;
        }
        if (this.month.getValue() > aDate.month.getValue()) {
            return false;
        } else if (this.month.getValue() < aDate.month.getValue()) {
            return true;
        }
        // Compare Days
        if (this.day.getValue() == 0 || aDate.day.getValue() == 0) {
            return true;
        }
        if (this.day.getValue() > aDate.day.getValue()) {
            return false;
        }
        return true;
    }
    public boolean isAfter(MyDate aDate) {
        // Compare Years
        if (this.year.getValue() == 0 || aDate.year.getValue() == 0) {
            return true;
        }
        if (this.year.getValue() < aDate.year.getValue()) {
            return false;
        } else if (this.year.getValue() > aDate.year.getValue()) {
            return true;
        }
        // Compare Months
        if (this.month.getValue() == 0 || aDate.month.getValue() == 0) {
            return true;
        }
        if (this.month.getValue() < aDate.month.getValue()) {
            return false;
        } else if (this.month.getValue() > aDate.month.getValue()) {
            return true;
        }
        // Compare Days
        if (this.day.getValue() == 0 || aDate.day.getValue() == 0) {
            return true;
        }
        if (this.day.getValue() < aDate.day.getValue()){
            return false;
        }
        return true;
    }

    public String toString() {
        String txt = "";
        txt += String.valueOf(year);
        if (month.getValue() < 10) {
            txt += "/0" + String.valueOf(month);
        } else {
            txt += "/" + String.valueOf(month);
        }
        if (day.getValue() < 10) {
            txt += "/0" + String.valueOf(day);
        } else {
            txt += "/" + String.valueOf(day);
        }
        return txt;
    }
}
