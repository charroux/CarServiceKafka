package com.example;

public class CarEvent {

    String plateNumber;
    public enum State {
        RENTED,
        AVAILABLE
    }

    State state;

    public CarEvent(String plateNumber, State state) {
        this.plateNumber = plateNumber;
        this.state = state;
    }

    public CarEvent() {
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
