package com.vincent.applicationmeteo.model;

import java.io.Serializable;
import java.util.Date;

public class WeatherInfo implements Serializable {
    private String city;
    private String temperature;
    private String temperatureRessenti;
    private String humidity;
    private String windSpeed;
    private String windDeg;
    private Date date;
    private String image;

    public WeatherInfo(String city, String temperature, Date date, String image) {
        this.city = city;
        this.temperature = temperature;
        this.date = date;
        this.image = image;
    }

    public WeatherInfo(String city, String temperature, Date date, String image, String temperatureRessenti, String humidity, String windSpeed, String windDeg) {
        this.city = city;
        this.temperature = temperature;
        this.temperatureRessenti = temperatureRessenti;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.date = date;
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTemperatureRessenti() {
        return temperatureRessenti;
    }

    public void setTemperatureRessenti(String temperatureRessenti) {
        this.temperatureRessenti = temperatureRessenti;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }


    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }


}


