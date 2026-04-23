package com.flc.model;

public class Booking {
    private final String id;
    private final Member member;
    private Lesson lesson;
    private BookingStatus status;
    private String review;
    private int rating;

    public Booking(String id, Member member, Lesson lesson) {
        this.id = id;
        this.member = member;
        this.lesson = lesson;
        this.status = BookingStatus.BOOKED;
        this.review = "";
        this.rating = 0;
    }

    public void attend(String review, int rating) {
        this.review = review;
        this.rating = rating;
        this.status = BookingStatus.ATTENDED;
    }

    public void changeTo(Lesson newLesson) {
        this.lesson = newLesson;
        this.status = BookingStatus.CHANGED;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public String getId()            { return id; }
    public Member getMember()        { return member; }
    public Lesson getLesson()        { return lesson; }
    public BookingStatus getStatus() { return status; }
    public String getReview()        { return review; }
    public int getRating()           { return rating; }

    @Override
    public String toString() {
        return String.format("[%s] %s → %s (%s)",
                id, member.getName(), lesson.getId(), status);
    }
}
