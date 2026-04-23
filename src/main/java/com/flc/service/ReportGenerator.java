package com.flc.service;

import com.flc.model.*;
import java.util.*;

public class ReportGenerator {
    private final Timetable timetable;

    public ReportGenerator(Timetable timetable) {
        this.timetable = timetable;
    }

    // ── Report 1: Monthly Lesson Report ─────────────────
    public void printMonthlyLessonReport(int weekStart, int weekEnd) {

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║         MONTHLY LESSON REPORT                   ║");
        System.out.println("║         Weeks " + weekStart + " to " + weekEnd + "                              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.printf("%-15s %-10s %-12s %-10s %-15s%n",
                "Lesson ID", "Exercise", "Day", "Timeslot", "Attended | Avg Rating");
        System.out.println("─────────────────────────────────────────────────────────────────");

        for (int w = weekStart; w <= weekEnd; w++) {
            List<Lesson> weekLessons = timetable.getLessonsByWeek(w);
            System.out.println("  Week " + w + ":");
            for (Lesson l : weekLessons) {
                int attended = l.getAttendedCount();
                double avgRating = l.getAverageRating();
                String rating = attended == 0 ? "N/A" : String.format("%.1f", avgRating);
                System.out.printf("  %-15s %-12s %-10s %-12s %d attended | Rating: %s%n",
                        l.getId(),
                        l.getExerciseType(),
                        l.getDay(),
                        l.getTimeSlot(),
                        attended,
                        rating);
            }
            System.out.println();
        }
    }

    // ── Report 2: Champion Exercise Type Report ──────────
    public void printChampionReport(int weekStart, int weekEnd) {

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║         CHAMPION EXERCISE TYPE REPORT            ║");
        System.out.println("║         Weeks " + weekStart + " to " + weekEnd + "                              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        ExerciseType champion = null;
        double highestIncome = 0;

        System.out.printf("%-15s %-15s%n", "Exercise Type", "Total Income");
        System.out.println("──────────────────────────────");

        for (ExerciseType type : ExerciseType.values()) {
            double totalIncome = 0;

            for (int w = weekStart; w <= weekEnd; w++) {
                List<Lesson> weekLessons = timetable.getLessonsByWeek(w);
                for (Lesson l : weekLessons) {
                    if (l.getExerciseType() == type) {
                        totalIncome += l.getTotalIncome();
                    }
                }
            }

            System.out.printf("%-15s £%-14.2f%n", type, totalIncome);

            if (totalIncome > highestIncome) {
                highestIncome = totalIncome;
                champion = type;
            }
        }

        System.out.println("──────────────────────────────");
        if (champion != null) {
            System.out.println("\n🏆 CHAMPION: " + champion
                    + " with £" + String.format("%.2f", highestIncome));
        } else {
            System.out.println("\nNo attended lessons found for this period.");
        }
        System.out.println();
    }
}
