package com.example.graddualkids.ui.home;

public class DataDaysWeeks {
    private int  year;
    private String titleDays, titleWeeks,months, numberDay, numberWeek;

//    public DataDaysWeeks(String numberDay, String numberWeek, int year, String titleDays, String titleWeeks, String months) {
    public DataDaysWeeks(String numberDay, String numberWeek, String titleDays, String titleWeeks, String months) {
        this.numberDay = numberDay;
        this.numberWeek = numberWeek;
        //this.year = year;
        this.titleDays = titleDays;
        this.titleWeeks = titleWeeks;
        this.months = months;
    }

    public DataDaysWeeks( String numberWeek,String numberDay, String titleDays) {
        this.numberWeek = numberWeek;
        this.numberDay = numberDay;
        this.titleDays = titleDays;
    }

    public String getNumberWeek() {
        return numberWeek;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public String getTitleDays() {
        return titleDays;
    }
}
