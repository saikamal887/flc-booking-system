package com.flc;

import com.flc.model.*;
import com.flc.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerTest {

    private BookingManager bookingManager;
    private Timetable timetable;
    private Member member;
    private Lesson lesson;

    @BeforeEach
    void setUp() {
        bookingManager = new BookingManager();
        timetable = new Timetable();
        member = bookingManager.getMemberById("M01");
        lesson = timetable.getLessonById("W1-SAT-AM");
    }

    // ── Test 1: Successful booking ───────────────────────
    @Test
    void testBookLessonSuccess() {
        String result = bookingManager.bookLesson(member, lesson);
        assertTrue(result.startsWith("SUCCESS"));
    }

    // ── Test 2: Duplicate booking not allowed ────────────
    @Test
    void testBookLessonDuplicate() {
        bookingManager.bookLesson(member, lesson);
        String result = bookingManager.bookLesson(member, lesson);
        assertTrue(result.startsWith("FAILED"));
    }

    // ── Test 3: Lesson capacity cannot exceed 4 ──────────
    @Test
    void testLessonCapacity() {
        Member m1 = bookingManager.getMemberById("M01");
        Member m2 = bookingManager.getMemberById("M02");
        Member m3 = bookingManager.getMemberById("M03");
        Member m4 = bookingManager.getMemberById("M04");
        Member m5 = bookingManager.getMemberById("M05");

        bookingManager.bookLesson(m1, lesson);
        bookingManager.bookLesson(m2, lesson);
        bookingManager.bookLesson(m3, lesson);
        bookingManager.bookLesson(m4, lesson);

        String result = bookingManager.bookLesson(m5, lesson);
        assertTrue(result.startsWith("FAILED"));
    }

    // ── Test 4: Cancel a booking ─────────────────────────
    @Test
    void testCancelBooking() {
        bookingManager.bookLesson(member, lesson);
        String bookingId = bookingManager
                .getBookingsByMember("M01")
                .get(0)
                .getId();

        String result = bookingManager.cancelBooking(bookingId);
        assertTrue(result.startsWith("SUCCESS"));
    }

    // ── Test 5: Attend a lesson with valid rating ─────────
    @Test
    void testAttendLesson() {
        bookingManager.bookLesson(member, lesson);
        String bookingId = bookingManager
                .getBookingsByMember("M01")
                .get(0)
                .getId();

        String result = bookingManager.attendLesson(bookingId, "Great class!", 5);
        assertTrue(result.startsWith("SUCCESS"));
    }
}