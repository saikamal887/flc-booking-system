package com.flc.service;

import com.flc.model.*;
import com.flc.model.Day;
import com.flc.model.Booking;
import com.flc.model.TimeSlot;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
     private final List<Lesson> lessons = new ArrayList<>();

    public Timetable() {
        initializeTimetable();
    }

    private void initializeTimetable() {

        // ── WEEK 1 ──────────────────────────────────────────
        lessons.add(new Lesson("W1-SAT-AM", ExerciseType.YOGA,       Day.SATURDAY, TimeSlot.MORNING,   1));
        lessons.add(new Lesson("W1-SAT-PM", ExerciseType.ZUMBA,      Day.SATURDAY, TimeSlot.AFTERNOON, 1));
        lessons.add(new Lesson("W1-SAT-EV", ExerciseType.BOX_FIT,    Day.SATURDAY, TimeSlot.EVENING,   1));
        lessons.add(new Lesson("W1-SUN-AM", ExerciseType.AQUACISE,   Day.SUNDAY,   TimeSlot.MORNING,   1));
        lessons.add(new Lesson("W1-SUN-PM", ExerciseType.BODY_BLITZ, Day.SUNDAY,   TimeSlot.AFTERNOON, 1));
        lessons.add(new Lesson("W1-SUN-EV", ExerciseType.YOGA,       Day.SUNDAY,   TimeSlot.EVENING,   1));

        // ── WEEK 2 ──────────────────────────────────────────
        lessons.add(new Lesson("W2-SAT-AM", ExerciseType.ZUMBA,      Day.SATURDAY, TimeSlot.MORNING,   2));
        lessons.add(new Lesson("W2-SAT-PM", ExerciseType.AQUACISE,   Day.SATURDAY, TimeSlot.AFTERNOON, 2));
        lessons.add(new Lesson("W2-SAT-EV", ExerciseType.BODY_BLITZ, Day.SATURDAY, TimeSlot.EVENING,   2));
        lessons.add(new Lesson("W2-SUN-AM", ExerciseType.BOX_FIT,    Day.SUNDAY,   TimeSlot.MORNING,   2));
        lessons.add(new Lesson("W2-SUN-PM", ExerciseType.YOGA,       Day.SUNDAY,   TimeSlot.AFTERNOON, 2));
        lessons.add(new Lesson("W2-SUN-EV", ExerciseType.ZUMBA,      Day.SUNDAY,   TimeSlot.EVENING,   2));

        // ── WEEK 3 ──────────────────────────────────────────
        lessons.add(new Lesson("W3-SAT-AM", ExerciseType.BOX_FIT,    Day.SATURDAY, TimeSlot.MORNING,   3));
        lessons.add(new Lesson("W3-SAT-PM", ExerciseType.YOGA,       Day.SATURDAY, TimeSlot.AFTERNOON, 3));
        lessons.add(new Lesson("W3-SAT-EV", ExerciseType.AQUACISE,   Day.SATURDAY, TimeSlot.EVENING,   3));
        lessons.add(new Lesson("W3-SUN-AM", ExerciseType.ZUMBA,      Day.SUNDAY,   TimeSlot.MORNING,   3));
        lessons.add(new Lesson("W3-SUN-PM", ExerciseType.BODY_BLITZ, Day.SUNDAY,   TimeSlot.AFTERNOON, 3));
        lessons.add(new Lesson("W3-SUN-EV", ExerciseType.BOX_FIT,    Day.SUNDAY,   TimeSlot.EVENING,   3));

        // ── WEEK 4 ──────────────────────────────────────────
        lessons.add(new Lesson("W4-SAT-AM", ExerciseType.BODY_BLITZ, Day.SATURDAY, TimeSlot.MORNING,   4));
        lessons.add(new Lesson("W4-SAT-PM", ExerciseType.BOX_FIT,    Day.SATURDAY, TimeSlot.AFTERNOON, 4));
        lessons.add(new Lesson("W4-SAT-EV", ExerciseType.ZUMBA,      Day.SATURDAY, TimeSlot.EVENING,   4));
        lessons.add(new Lesson("W4-SUN-AM", ExerciseType.YOGA,       Day.SUNDAY,   TimeSlot.MORNING,   4));
        lessons.add(new Lesson("W4-SUN-PM", ExerciseType.AQUACISE,   Day.SUNDAY,   TimeSlot.AFTERNOON, 4));
        lessons.add(new Lesson("W4-SUN-EV", ExerciseType.BODY_BLITZ, Day.SUNDAY,   TimeSlot.EVENING,   4));

        // ── WEEK 5 ──────────────────────────────────────────
        lessons.add(new Lesson("W5-SAT-AM", ExerciseType.AQUACISE,   Day.SATURDAY, TimeSlot.MORNING,   5));
        lessons.add(new Lesson("W5-SAT-PM", ExerciseType.BODY_BLITZ, Day.SATURDAY, TimeSlot.AFTERNOON, 5));
        lessons.add(new Lesson("W5-SAT-EV", ExerciseType.YOGA,       Day.SATURDAY, TimeSlot.EVENING,   5));
        lessons.add(new Lesson("W5-SUN-AM", ExerciseType.ZUMBA,      Day.SUNDAY,   TimeSlot.MORNING,   5));
        lessons.add(new Lesson("W5-SUN-PM", ExerciseType.BOX_FIT,    Day.SUNDAY,   TimeSlot.AFTERNOON, 5));
        lessons.add(new Lesson("W5-SUN-EV", ExerciseType.AQUACISE,   Day.SUNDAY,   TimeSlot.EVENING,   5));

        // ── WEEK 6 ──────────────────────────────────────────
        lessons.add(new Lesson("W6-SAT-AM", ExerciseType.YOGA,       Day.SATURDAY, TimeSlot.MORNING,   6));
        lessons.add(new Lesson("W6-SAT-PM", ExerciseType.BOX_FIT,    Day.SATURDAY, TimeSlot.AFTERNOON, 6));
        lessons.add(new Lesson("W6-SAT-EV", ExerciseType.BODY_BLITZ, Day.SATURDAY, TimeSlot.EVENING,   6));
        lessons.add(new Lesson("W6-SUN-AM", ExerciseType.ZUMBA,      Day.SUNDAY,   TimeSlot.MORNING,   6));
        lessons.add(new Lesson("W6-SUN-PM", ExerciseType.AQUACISE,   Day.SUNDAY,   TimeSlot.AFTERNOON, 6));
        lessons.add(new Lesson("W6-SUN-EV", ExerciseType.YOGA,       Day.SUNDAY,   TimeSlot.EVENING,   6));

        // ── WEEK 7 ──────────────────────────────────────────
        lessons.add(new Lesson("W7-SAT-AM", ExerciseType.ZUMBA,      Day.SATURDAY, TimeSlot.MORNING,   7));
        lessons.add(new Lesson("W7-SAT-PM", ExerciseType.BODY_BLITZ, Day.SATURDAY, TimeSlot.AFTERNOON, 7));
        lessons.add(new Lesson("W7-SAT-EV", ExerciseType.AQUACISE,   Day.SATURDAY, TimeSlot.EVENING,   7));
        lessons.add(new Lesson("W7-SUN-AM", ExerciseType.BOX_FIT,    Day.SUNDAY,   TimeSlot.MORNING,   7));
        lessons.add(new Lesson("W7-SUN-PM", ExerciseType.YOGA,       Day.SUNDAY,   TimeSlot.AFTERNOON, 7));
        lessons.add(new Lesson("W7-SUN-EV", ExerciseType.ZUMBA,      Day.SUNDAY,   TimeSlot.EVENING,   7));

        // ── WEEK 8 ──────────────────────────────────────────
        lessons.add(new Lesson("W8-SAT-AM", ExerciseType.BOX_FIT,    Day.SATURDAY, TimeSlot.MORNING,   8));
        lessons.add(new Lesson("W8-SAT-PM", ExerciseType.AQUACISE,   Day.SATURDAY, TimeSlot.AFTERNOON, 8));
        lessons.add(new Lesson("W8-SAT-EV", ExerciseType.ZUMBA,      Day.SATURDAY, TimeSlot.EVENING,   8));
        lessons.add(new Lesson("W8-SUN-AM", ExerciseType.BODY_BLITZ, Day.SUNDAY,   TimeSlot.MORNING,   8));
        lessons.add(new Lesson("W8-SUN-PM", ExerciseType.YOGA,       Day.SUNDAY,   TimeSlot.AFTERNOON, 8));
        lessons.add(new Lesson("W8-SUN-EV", ExerciseType.BOX_FIT,    Day.SUNDAY,   TimeSlot.EVENING,   8));
    }

    // Get all lessons
    public List<Lesson> getAllLessons() {
        return lessons;
    }

    // View timetable by day
    public List<Lesson> getLessonsByDay(Day day) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getDay() == day) result.add(l);
        }
        return result;
    }

    // View timetable by exercise type
    public List<Lesson> getLessonsByExerciseType(ExerciseType type) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getExerciseType() == type) result.add(l);
        }
        return result;
    }

    // Get lesson by ID
    public Lesson getLessonById(String id) {
        for (Lesson l : lessons) {
            if (l.getId().equalsIgnoreCase(id)) return l;
        }
        return null;
    }

    // Get lessons by week number
    public List<Lesson> getLessonsByWeek(int weekNumber) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getWeekNumber() == weekNumber) result.add(l);
        }
        return result;
    }
}
