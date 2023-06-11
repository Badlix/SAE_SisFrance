package fr.groupeF.sae_sisfrance;

import javafx.beans.property.SimpleStringProperty;

public class MyDate {
    private SimpleStringProperty date;
    private int year;
    private int month;
    private int day;

    public MyDate(String dateTxt) {
        date = new SimpleStringProperty(dateTxt);
        update();
        date.addListener((observable, oldValue, newValue) -> {
            System.out.println("VALEUR DATE A CHANGÉ");
            update();
        });
    }

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
            year = Integer.valueOf(numbers[0]);
        } if (numbers.length >= 2) {
            month = Integer.valueOf(numbers[1]);
            if (numbers.length >= 3) {
                day = Integer.valueOf(numbers[2]);
            } else {
                day = 0;
            }
        } else {
            month = 0;
            day = 0;
        }
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public boolean isBetween(MyDate afterDate, MyDate beforeDate) {
        return isAfter(afterDate) && isBefore(beforeDate);
    }
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

    public String toString() {
        String txt = "";
        txt += String.valueOf(year);
        if (month < 10) {
            txt += "-0" + String.valueOf(month);
        } else {
            txt += "-" + String.valueOf(month);
        }
        if (day < 10) {
            txt += "-0" + String.valueOf(day);
        } else {
            txt += "-" + String.valueOf(day);
        }
        return txt;
    }
}
