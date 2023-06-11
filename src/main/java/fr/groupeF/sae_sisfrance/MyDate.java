package fr.groupeF.sae_sisfrance;

import javafx.beans.property.SimpleIntegerProperty;

public class MyDate {
    private SimpleIntegerProperty year;
    private SimpleIntegerProperty month;
    private SimpleIntegerProperty day;

    public MyDate(String date) {
        this.year = new SimpleIntegerProperty(0);
        this.month = new SimpleIntegerProperty(0);
        this.day = new SimpleIntegerProperty(0);
        setMyDate(date);
    }

    public void setMyDate(String date) {
        if (date.contains("/")) {
            String[] numbers = date.split("/");
            if (numbers.length >= 1 && numbers[0].isEmpty() == false) {
                this.year.set(Integer.valueOf(numbers[0]));
            }
            if (numbers.length >= 2 && numbers[1].isEmpty() == false) {
                this.month.set(Integer.valueOf(numbers[1]));
            }
            if (numbers.length == 3 && numbers[2].isEmpty() == false) {
                this.day.set(Integer.valueOf(numbers[2]));
            }
        } else if (date.contains("-")) {
            String[] numbers = date.split("-");
            if (numbers.length >= 1 && numbers[0].isEmpty() == false) {
                this.year.set(Integer.valueOf(numbers[0]));
            }
            if (numbers.length >= 2 && numbers[1].isEmpty() == false) {
                this.month.set(Integer.valueOf(numbers[1]));
            }
            if (numbers.length == 3 && numbers[2].isEmpty() == false) {
                this.day.set(Integer.valueOf(numbers[2]));
            }
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
        if (this.year == null || aDate.year == null) {
            return true;
        }
        if (this.year.getValue() > aDate.year.getValue()) {
            return false;
        } else if (this.year.getValue() < aDate.year.getValue()) {
            return true;
        }
        // Compare Months
        if (this.month == null || aDate.month == null) {
            return true;
        }
        if (this.month.getValue() > aDate.month.getValue()) {
            return false;
        } else if (this.month.getValue() < aDate.month.getValue()) {
            return true;
        }
        // Compare Days
        if (this.day == null || aDate.day == null) {
            return true;
        }
        if (this.day.getValue() > aDate.day.getValue()) {
            return false;
        }
        return true;
    }
    public boolean isAfter(MyDate aDate) {
        // Compare Years
        if (this.year == null || aDate.year == null) {
            return true;
        }
        if (this.year.getValue() < aDate.year.getValue()) {
            return false;
        } else if (this.year.getValue() > aDate.year.getValue()) {
            return true;
        }
        // Compare Months
        if (this.month == null || aDate.month == null) {
            return true;
        }
        if (this.month.getValue() < aDate.month.getValue()) {
            return false;
        } else if (this.month.getValue() > aDate.month.getValue()) {
            return true;
        }
        // Compare Days
        if (this.day == null || aDate.day == null) {
            return true;
        }
        if (this.day.getValue() < aDate.day.getValue()){
            return false;
        }
        return true;
    }

    public String toString() {
        String txt = "";
        if (year != null) {
            txt += String.valueOf(year);
        }
        if (month != null) {
            if (month.getValue() < 10) {
                txt += "/0" + String.valueOf(month);
            } else {
                txt += "/" + String.valueOf(month);
            }
        }
        if (day != null) {
            if (day.getValue() < 10) {
                txt += "/0" + String.valueOf(day);
            } else {
                txt += "/" + String.valueOf(day);
            }
        }
        return txt;
    }
}
