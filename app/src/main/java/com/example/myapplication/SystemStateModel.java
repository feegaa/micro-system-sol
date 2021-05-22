package com.example.myapplication;

public class SystemStateModel {

    private int temperature;
    private int envMoist;
    private int woodMoist;
    private int param1;
    private int param2;
    private boolean airSucker;
    private boolean airBlower;
    private boolean airHumidifier;

    public SystemStateModel(int temperature, int env_moist, int wood_moist, int id, int param1, int param2, boolean airSucker, boolean airBlower, boolean airHumidifier) {
        this.temperature = temperature;
        this.envMoist = env_moist;
        this.woodMoist = wood_moist;
        this.param1 = param1;
        this.param2 = param2;
        this.airSucker = airSucker;
        this.airBlower = airBlower;
        this.airHumidifier = airHumidifier;
    }


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getEnvMoist() {
        return envMoist;
    }

    public void setEnvMoist(int env_moist) {
        this.envMoist = env_moist;
    }

    public int getWoodMoist() {
        return woodMoist;
    }

    public void setWoodMoist(int wood_moist) {
        this.woodMoist = wood_moist;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public boolean isAirSucker() {
        return airSucker;
    }

    public void setAirSucker(boolean airSucker) {
        this.airSucker = airSucker;
    }

    public boolean isAirBlower() {
        return airBlower;
    }

    public void setAirBlower(boolean airBlower) {
        this.airBlower = airBlower;
    }

    public boolean isAirHumidifier() {
        return airHumidifier;
    }

    public void setAirHumidifier(boolean airHumidifier) {
        this.airHumidifier = airHumidifier;
    }
}
