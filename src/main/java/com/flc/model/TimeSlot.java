package com.flc.model;

public enum TimeSlot {
    MORNING("9.00"),
    AFTERNOON("13.00"),
    EVENING("17.00");

    private final String time;

    TimeSlot(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
