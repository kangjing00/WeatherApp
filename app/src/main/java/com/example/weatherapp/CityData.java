package com.example.weatherapp;

import java.util.List;

public class CityData {

    private List<Weather> weather;
    private main main;
    private String name;

    public CityData(List<Weather> weather, main main, String name){
        this.weather = weather;
        this.main = main;
        this.name = name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public com.example.weatherapp.main getMain() {
        return main;
    }

    public void setMain(com.example.weatherapp.main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
