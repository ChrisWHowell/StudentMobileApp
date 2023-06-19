package android.reserver.chrishowellstudentschedulerapp;

import java.util.Date;

public class Assessment {
    private int id;
    private int course_Id;
    private String title;
    private String startDate;
    private String endDate;
    private AssessmentType assessmentType;

    public enum AssessmentType {
        OBJECTIVE,
        PERFORMANCE
    }
    public Assessment(int id,int course_Id, String title, String startDate, String endDate, AssessmentType assessmentType) {
        this.id = id;
        this.course_Id = course_Id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assessmentType = assessmentType;
    }
    //constructor that can create Assessment without Assessment ID
    public Assessment(int course_Id, String title, String startDate, String endDate, AssessmentType assessmentType) {
        this.course_Id = course_Id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assessmentType = assessmentType;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", course_Id=" + course_Id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", assessmentType=" + assessmentType +
                '}';
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
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

    public void setAssessmentType(AssessmentType assessmentType) {this.assessmentType = assessmentType;}

    public AssessmentType getAssessmentType() {return assessmentType;}
}
