package com.flc.service;

import com.flc.model.*;
import com.flc.model.Booking;
import com.flc.model.BookingStatus;
import java.util.*;

public class BookingManager {
    private final List<Booking> allBookings = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private int bookingCounter = 1;

    public BookingManager() {
        initializeMembers();
    }

    // ── Pre-registered members ───────────────────────────
    private void initializeMembers() {
        members.add(new Member("M01", "Alice Johnson"));
        members.add(new Member("M02", "Ben Carter"));
        members.add(new Member("M03", "Clara Smith"));
        members.add(new Member("M04", "David Lee"));
        members.add(new Member("M05", "Emma Brown"));
        members.add(new Member("M06", "Farhan Khan"));
        members.add(new Member("M07", "Grace Wilson"));
        members.add(new Member("M08", "Harry Evans"));
        members.add(new Member("M09", "Isla Turner"));
        members.add(new Member("M10", "James White"));
    }

    // ── Find member by ID ────────────────────────────────
    public Member getMemberById(String id) {
        for (Member m : members) {
            if (m.getId().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    // ── Get all members ──────────────────────────────────
    public List<Member> getAllMembers() {
        return members;
    }

    // ── Book a lesson ────────────────────────────────────
    public String bookLesson(Member member, Lesson lesson) {

        // Check capacity
        if (!lesson.hasSpace()) {
            return "FAILED: Lesson " + lesson.getId() + " is full.";
        }

        // Check duplicate booking
        for (Booking b : allBookings) {
            if (b.getMember().getId().equals(member.getId())
                    && b.getLesson().getId().equals(lesson.getId())
                    && b.getStatus() != BookingStatus.CANCELLED) {
                return "FAILED: You already have a booking for this lesson.";
            }
        }

        // Check time conflict
        for (Booking b : allBookings) {
            if (b.getMember().getId().equals(member.getId())
                    && b.getStatus() != BookingStatus.CANCELLED
                    && b.getLesson().getDay() == lesson.getDay()
                    && b.getLesson().getTimeSlot() == lesson.getTimeSlot()
                    && b.getLesson().getWeekNumber() == lesson.getWeekNumber()) {
                return "FAILED: You already have a booking at this time slot.";
            }
        }

        String bookingId = "B" + String.format("%03d", bookingCounter++);
        Booking booking = new Booking(bookingId, member, lesson);
        lesson.addBooking(booking);
        allBookings.add(booking);
        return "SUCCESS: Booking confirmed. Booking ID: " + bookingId;
    }

    // ── Change a booking ─────────────────────────────────
    public String changeBooking(String bookingId, Lesson newLesson) {

        Booking booking = getBookingById(bookingId);

        if (booking == null) {
            return "FAILED: Booking ID not found.";
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return "FAILED: Cannot change a cancelled booking.";
        }
        if (booking.getStatus() == BookingStatus.ATTENDED) {
            return "FAILED: Cannot change an already attended booking.";
        }
        if (!newLesson.hasSpace()) {
            return "FAILED: New lesson is full.";
        }

        // Check duplicate
        for (Booking b : allBookings) {
            if (b.getMember().getId().equals(booking.getMember().getId())
                    && b.getLesson().getId().equals(newLesson.getId())
                    && b.getStatus() != BookingStatus.CANCELLED) {
                return "FAILED: You already have a booking for the new lesson.";
            }
        }

        // Release space from old lesson
        booking.getLesson().removeBooking(booking);

        // Move to new lesson
        newLesson.addBooking(booking);
        booking.changeTo(newLesson);

        return "SUCCESS: Booking " + bookingId + " changed to lesson " + newLesson.getId();
    }

    // ── Cancel a booking ─────────────────────────────────
    public String cancelBooking(String bookingId) {

        Booking booking = getBookingById(bookingId);

        if (booking == null) {
            return "FAILED: Booking ID not found.";
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return "FAILED: Booking is already cancelled.";
        }
        if (booking.getStatus() == BookingStatus.ATTENDED) {
            return "FAILED: Cannot cancel an attended lesson.";
        }

        booking.getLesson().removeBooking(booking);
        booking.cancel();
        return "SUCCESS: Booking " + bookingId + " has been cancelled.";
    }

    // ── Attend a lesson ──────────────────────────────────
    public String attendLesson(String bookingId, String review, int rating) {

        Booking booking = getBookingById(bookingId);

        if (booking == null) {
            return "FAILED: Booking ID not found.";
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return "FAILED: Cannot attend a cancelled booking.";
        }
        if (booking.getStatus() == BookingStatus.ATTENDED) {
            return "FAILED: You have already attended this lesson.";
        }
        if (rating < 1 || rating > 5) {
            return "FAILED: Rating must be between 1 and 5.";
        }

        booking.attend(review, rating);
        return "SUCCESS: Attended lesson " + booking.getLesson().getId()
                + ". Thank you for your review!";
    }

    // ── Get booking by ID ────────────────────────────────
    public Booking getBookingById(String id) {
        for (Booking b : allBookings) {
            if (b.getId().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    // ── Get all bookings for a member ────────────────────
    public List<Booking> getBookingsByMember(String memberId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : allBookings) {
            if (b.getMember().getId().equalsIgnoreCase(memberId)) {
                result.add(b);
            }
        }
        return result;
    }

    // ── Get all bookings ─────────────────────────────────
    public List<Booking> getAllBookings() {
        return allBookings;
    }
}
