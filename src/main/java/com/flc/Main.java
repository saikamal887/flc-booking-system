package com.flc;

import com.flc.model.*;
import com.flc.service.*;
import com.flc.model.Day;
import java.util.*;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Timetable timetable = new Timetable();
    static BookingManager bookingManager = new BookingManager();
    static ReportGenerator reportGenerator = new ReportGenerator(timetable);

    static {
        bookingManager.seedSampleData(timetable);
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   FURZEFIELD LEISURE CENTRE              ║");
        System.out.println("║   Group Exercise Booking System          ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> bookLesson();
                case "2" -> changeOrCancelBooking();
                case "3" -> attendLesson();
                case "4" -> monthlyLessonReport();
                case "5" -> championReport();
                case "0" -> {
                    System.out.println("\nThank you for using FLC Booking System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ── Main Menu ────────────────────────────────────────
    private static void printMainMenu() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  MAIN MENU");
        System.out.println("══════════════════════════════════════════");
        System.out.println("  1. Book a lesson");
        System.out.println("  2. Change / Cancel a booking");
        System.out.println("  3. Attend a lesson");
        System.out.println("  4. Monthly lesson report");
        System.out.println("  5. Champion exercise type report");
        System.out.println("  0. Exit");
        System.out.println("══════════════════════════════════════════");
        System.out.print("Enter choice: ");
    }

    // ── Feature 1: Book a Lesson ─────────────────────────
    private static void bookLesson() {

        // Step 1: Select member
        System.out.println("\n── BOOK A LESSON ──────────────────────────");
        System.out.println("Available Members:");
        for (Member m : bookingManager.getAllMembers()) {
            System.out.println("  " + m);
        }
        System.out.print("Enter your Member ID: ");
        String memberId = scanner.nextLine().trim();
        Member member = bookingManager.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        System.out.println("Welcome, " + member.getName());

        // Step 2: Choose timetable view
        System.out.println("\nHow would you like to view the timetable?");
        System.out.println("  1. By Day (Saturday or Sunday)");
        System.out.println("  2. By Exercise Type");
        System.out.print("Enter choice: ");
        String viewChoice = scanner.nextLine().trim();

        List<Lesson> lessons;

        if (viewChoice.equals("1")) {
            System.out.println("  1. SATURDAY");
            System.out.println("  2. SUNDAY");
            System.out.print("Enter choice: ");
            String dayChoice = scanner.nextLine().trim();
            Day day = dayChoice.equals("1") ? Day.SATURDAY : Day.SUNDAY;
            lessons = timetable.getLessonsByDay(day);

        } else {
            System.out.println("Exercise Types:");
            ExerciseType[] types = ExerciseType.values();
            for (int i = 0; i < types.length; i++) {
                System.out.println("  " + (i + 1) + ". " + types[i]);
            }
            System.out.print("Enter choice: ");
            int typeIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (typeIndex < 0 || typeIndex >= types.length) {
                System.out.println("Invalid choice.");
                return;
            }
            lessons = timetable.getLessonsByExerciseType(types[typeIndex]);
        }

        // Step 3: Display lessons
        System.out.println("\nAvailable Lessons:");
        System.out.printf("  %-15s %-12s %-10s %-12s %-8s %-8s%n",
                "ID", "Exercise", "Day", "Timeslot", "Week", "Spaces");
        System.out.println("  ──────────────────────────────────────────────────────");
        for (Lesson l : lessons) {
            System.out.printf("  %-15s %-12s %-10s %-12s %-8d %-8d%n",
                    l.getId(),
                    l.getExerciseType(),
                    l.getDay(),
                    l.getTimeSlot(),
                    l.getWeekNumber(),
                    l.getAvailableSpaces());
        }

        // Step 4: Select lesson
        System.out.print("\nEnter Lesson ID to book: ");
        String lessonId = scanner.nextLine().trim();
        Lesson lesson = timetable.getLessonById(lessonId);
        if (lesson == null) {
            System.out.println("Lesson not found.");
            return;
        }

        String result = bookingManager.bookLesson(member, lesson);
        System.out.println(result);
    }

    // ── Feature 2: Change or Cancel a Booking ────────────
    private static void changeOrCancelBooking() {

        System.out.println("\n── CHANGE / CANCEL BOOKING ────────────────");
        System.out.print("Enter your Member ID: ");
        String memberId = scanner.nextLine().trim();
        Member member = bookingManager.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        // Show member's bookings
        List<Booking> bookings = bookingManager.getBookingsByMember(memberId);
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }

        System.out.println("\nYour Bookings:");
        for (Booking b : bookings) {
            System.out.println("  " + b);
        }

        System.out.print("\nEnter Booking ID: ");
        String bookingId = scanner.nextLine().trim().toUpperCase();

        System.out.println("\n  1. Change booking to a new lesson");
        System.out.println("  2. Cancel booking");
        System.out.print("Enter choice: ");
        String action = scanner.nextLine().trim();

        if (action.equals("1")) {
            // Change booking
            System.out.println("\nHow would you like to find the new lesson?");
            System.out.println("  1. By Day");
            System.out.println("  2. By Exercise Type");
            System.out.print("Enter choice: ");
            String viewChoice = scanner.nextLine().trim();

            List<Lesson> lessons;

            if (viewChoice.equals("1")) {
                System.out.println("  1. SATURDAY  2. SUNDAY");
                System.out.print("Enter choice: ");
                String dayChoice = scanner.nextLine().trim();
                Day day = dayChoice.equals("1") ? Day.SATURDAY : Day.SUNDAY;
                lessons = timetable.getLessonsByDay(day);
            } else {
                ExerciseType[] types = ExerciseType.values();
                for (int i = 0; i < types.length; i++) {
                    System.out.println("  " + (i + 1) + ". " + types[i]);
                }
                System.out.print("Enter choice: ");
                int typeIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (typeIndex < 0 || typeIndex >= types.length) {
                    System.out.println("Invalid choice.");
                    return;
                }
                lessons = timetable.getLessonsByExerciseType(types[typeIndex]);
            }

            System.out.println("\nAvailable Lessons:");
            System.out.printf("  %-15s %-12s %-10s %-12s %-8s %-8s%n",
                    "ID", "Exercise", "Day", "Timeslot", "Week", "Spaces");
            System.out.println("  ──────────────────────────────────────────────────────");
            for (Lesson l : lessons) {
                System.out.printf("  %-15s %-12s %-10s %-12s %-8d %-8d%n",
                        l.getId(),
                        l.getExerciseType(),
                        l.getDay(),
                        l.getTimeSlot(),
                        l.getWeekNumber(),
                        l.getAvailableSpaces());
            }

            System.out.print("\nEnter new Lesson ID: ");
            String newLessonId = scanner.nextLine().trim();
            Lesson newLesson = timetable.getLessonById(newLessonId);
            if (newLesson == null) {
                System.out.println("Lesson not found.");
                return;
            }

            System.out.println(bookingManager.changeBooking(bookingId, newLesson));

        } else if (action.equals("2")) {
            System.out.println(bookingManager.cancelBooking(bookingId));
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // ── Feature 3: Attend a Lesson ───────────────────────
    private static void attendLesson() {

        System.out.println("\n── ATTEND A LESSON ────────────────────────");
        System.out.print("Enter your Member ID: ");
        String memberId = scanner.nextLine().trim();
        Member member = bookingManager.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        List<Booking> bookings = bookingManager.getBookingsByMember(memberId);
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }

        System.out.println("\nYour Bookings:");
        for (Booking b : bookings) {
            if (b.getStatus() == BookingStatus.BOOKED
                    || b.getStatus() == BookingStatus.CHANGED) {
                System.out.println("  " + b);
            }
        }

        System.out.print("\nEnter Booking ID to attend: ");
        String bookingId = scanner.nextLine().trim().toUpperCase();

        System.out.print("Write your review: ");
        String review = scanner.nextLine().trim();

        System.out.print("Enter rating (1-5): ");
        int rating;
        try {
            rating = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating. Must be a number.");
            return;
        }

        System.out.println(bookingManager.attendLesson(bookingId, review, rating));
    }

    // ── Feature 4: Monthly Lesson Report ─────────────────
    private static void monthlyLessonReport() {

        System.out.println("\n── MONTHLY LESSON REPORT ───────────────────");
        System.out.println("Select month:");
        System.out.println("  1. Month 1 (Weeks 1-4)");
        System.out.println("  2. Month 2 (Weeks 5-8)");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            reportGenerator.printMonthlyLessonReport(1, 4);
        } else if (choice.equals("2")) {
            reportGenerator.printMonthlyLessonReport(5, 8);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // ── Feature 5: Champion Report ───────────────────────
    private static void championReport() {

        System.out.println("\n── CHAMPION EXERCISE TYPE REPORT ───────────");
        System.out.println("Select month:");
        System.out.println("  1. Month 1 (Weeks 1-4)");
        System.out.println("  2. Month 2 (Weeks 5-8)");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            reportGenerator.printChampionReport(1, 4);
        } else if (choice.equals("2")) {
            reportGenerator.printChampionReport(5, 8);
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
