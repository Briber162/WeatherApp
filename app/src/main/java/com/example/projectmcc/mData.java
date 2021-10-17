package com.example.projectmcc;

public class mData {
    public String condition;
    public String currentTemp;
    public String minTemp;
    public String maxTemp;
    public String feelsLike;
    public String pressure;
    public String humidity;
    public String date;

    public mData(String condition,String currentTemp,String minTemp,String maxTemp ,String feelsLike,String pressure,String humidity,String date)
    {
        this.condition=condition;
        this.currentTemp=currentTemp;
        this.minTemp=minTemp;
        this.maxTemp=maxTemp;
        this.feelsLike=feelsLike;
        this.pressure=pressure;
        this.humidity=humidity;
        this.date=date;
    }

    public String getCondition() {
        return condition;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }
    public String getDate()
    {
        return date;
    }
}
