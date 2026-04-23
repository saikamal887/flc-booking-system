package com.flc.model;

import com.flc.model.TimeSlot;
import com.flc.model.Booking;
import com.flc.model.Day;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private static final int MAX_CAPACITY = 4;

    private final String id;
    private final ExerciseType exerciseType;
    private final Day day;
    private final TimeSlot timeSlot;
    private final int weekNumber;
    private final List<Booking> bookings;

    public Lesson(String id, ExerciseType exerciseType, Day day, TimeSlot timeSlot, int weekNumber) {
        this.id = id;
        this.exerciseType = exerciseType;
        this.day = day;
        this.timeSlot = timeSlot;
        this.weekNumber = weekNumber;
        this.bookings = new ArrayList<>();
    }

    public boolean hasSpace() {
        return bookings.size() < MAX_CAPACITY;
    }

    public int getAvailableSpaces() {
        return MAX_CAPACITY - bookings.size();
    }

    public int getAttendedCount() {
        int count = 0;
        for (Booking b : bookings) {
            if (b.getStatus() == BookingStatus.ATTENDED) count++;
        }
        return count;
    }

    public double getAverageRating() {
        List<Booking> attended = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getStatus() == BookingStatus.ATTENDED && b.getRating() > 0) {
                attended.add(b);
            }
        }
        if (attended.isEmpty()) return 0.0;
        double total = 0;
        for (Booking b : attended) total += b.getRating();
        return total / attended.size();
    }

    public double getTotalIncome() {
        double income = 0;
        for (Booking b : bookings) {
            if (b.getStatus() == BookingStatus.ATTENDED) {
                income += exerciseType.getPrice();
            }
        }
        return income;
    }

    public void addBooking(Booking booking)    { bookings.add(booking); }
    public void removeBooking(Booking booking) { bookings.remove(booking); }

    public String getId()                 { return id; }
    public ExerciseType getExerciseType() { return exerciseType; }
    public Day getDay()                   { return day; }
    public TimeSlot getTimeSlot()         { return timeSlot; }
    public int getWeekNumber()            { return weekNumber; }
    public List<Booking> getBookings()    { return bookings; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s %s | Week %d | £%.2f | Spaces: %d/4",
                id, exerciseType, day, timeSlot, weekNumber,
                exerciseType.getPrice(), getAvailableSpaces());
    }
}