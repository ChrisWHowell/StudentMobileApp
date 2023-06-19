package android.reserver.chrishowellstudentschedulerapp;

import java.util.ArrayList;
import java.util.List;

public class Term {
    private int id;
    private String title;
    private String startDate;
    private String endDate;

    List<Course> courseList = new ArrayList<>();

    public Term() {}

    //Constructor to be created by database that has int id value
    public Term(int id ,String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Contstuctor to create Term without the need to specify term id number
    public Term(String title, String startDate, String endDate) {

        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public List getCourseList(){return courseList; }
}
