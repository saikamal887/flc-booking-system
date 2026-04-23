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

    // ── Seed sample data (25 attended bookings for reports) ──
    public void seedSampleData(Timetable timetable) {
        preBook("M01", "W1-SAT-AM", timetable, "Excellent yoga session, very calming", 5);
        preBook("M02", "W1-SAT-AM", timetable, "Very relaxing and enjoyable", 4);
        preBook("M03", "W1-SAT-AM", timetable, "Best start to the weekend", 5);
        preBook("M04", "W1-SAT-PM", timetable, "Great dance workout, loved it", 4);
        preBook("M05", "W1-SAT-PM", timetable, "Good but quite intense", 3);
        preBook("M06", "W1-SUN-AM", timetable, "Loved the water exercises", 4);
        preBook("M07", "W1-SUN-AM", timetable, "So refreshing and fun", 5);
        preBook("M08", "W1-SUN-PM", timetable, "Tough but very rewarding", 4);
        preBook("M09", "W1-SUN-PM", timetable, "Very challenging session", 3);
        preBook("M01", "W2-SAT-AM", timetable, "Amazing zumba class", 5);
        preBook("M02", "W2-SAT-AM", timetable, "Really fun session", 4);
        preBook("M10", "W2-SUN-AM", timetable, "Incredible box fit class", 5);
        preBook("M03", "W2-SUN-AM", timetable, "Great high intensity workout", 4);
        preBook("M04", "W3-SAT-AM", timetable, "Best box fit session so far", 5);
        preBook("M05", "W3-SAT-AM", timetable, "Really pushed my limits", 4);
        preBook("M06", "W3-SAT-PM", timetable, "Calming and restorative yoga", 5);
        preBook("M07", "W3-SUN-PM", timetable, "Full body challenge, loved it", 4);
        preBook("M08", "W4-SAT-AM", timetable, "Outstanding body blitz class", 5);
        preBook("M09", "W4-SAT-AM", timetable, "Very hard but effective", 3);
        preBook("M10", "W4-SUN-AM", timetable, "Perfect end to month one", 5);
        preBook("M01", "W4-SUN-PM", timetable, "Nice cool-down aquacise session", 4);
        preBook("M02", "W5-SAT-AM", timetable, "Lovely aqua session", 4);
        preBook("M03", "W5-SAT-PM", timetable, "Intense and very effective", 5);
        preBook("M04", "W6-SAT-AM", timetable, "Mindful and energising yoga", 5);
        preBook("M05", "W6-SUN-AM", timetable, "Great cardio zumba session", 4);
    }

    private void preBook(String memberId, String lessonId, Timetable timetable, String review, int rating) {
        Member member = getMemberById(memberId);
        Lesson lesson = timetable.getLessonById(lessonId);
        if (member == null || lesson == null) return;
        String bookingId = "B" + String.format("%03d", bookingCounter++);
        Booking booking = new Booking(bookingId, member, lesson);
        booking.attend(review, rating);
        lesson.addBooking(booking);
        allBookings.add(booking);
    }
}
