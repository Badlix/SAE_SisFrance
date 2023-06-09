package fr.groupeF.sae_sisfrance;

public class MyDate {
    private Integer year;
    private Integer month;
    private Integer day;

    public MyDate(String date) {
        this.year = null;
        this.month = null;
        this.day = null;
        String[] numbers = date.split("/");
        if (numbers.length >= 1 && numbers[0].isEmpty() == false) {
            this.year = Integer.valueOf(numbers[0]);
        }
        if (numbers.length >= 2 && numbers[1].isEmpty() == false) {
            this.month = Integer.valueOf(numbers[1]);
        }
        if (numbers.length == 3 && numbers[2].isEmpty() == false) {
            this.day = Integer.valueOf(numbers[2]);
        }
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
        if (this.year > aDate.year) {
            return false;
        } else if (this.year < aDate.year) {
            return true;
        }
        // Compare Months
        if (this.month == null || aDate.month == null) {
            return true;
        }
        if (this.month > aDate.month) {
            return false;
        } else if (this.month < aDate.month) {
            return true;
        }
        // Compare Days
        if (this.day == null || aDate.day == null) {
            return true;
        }
        if (this.day > aDate.day) {
            return false;
        }
        return true;
    }
    public boolean isAfter(MyDate aDate) {
        // Compare Years
        if (this.year < aDate.year) {
            return false;
        } else if (this.year > aDate.year) {
            return true;
        }
        // Compare Months
        if (this.month == null || aDate.month == null) {
            return true;
        }
        if (this.month < aDate.month) {
            return false;
        } else if (this.month > aDate.month) {
            return true;
        }
        // Compare Days
        if (this.day == null || aDate.day == null) {
            return true;
        }
        if (this.day < aDate.day) {
            return false;
        }
        return true;
    }
}
