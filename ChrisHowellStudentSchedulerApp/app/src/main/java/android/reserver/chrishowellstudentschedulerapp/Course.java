package android.reserver.chrishowellstudentschedulerapp;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private int term_id;
    private String title;
    private String startDate;
    private String endDate;
    private StatusEnum status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String optionalNote;
    private List<Assessment> assessmentList = new ArrayList<>();

    public enum StatusEnum {
        In_Progress,
        Completed,
        Dropped,
        Plan_To_Take
    }
    public Course(int id,int term_id, String title, String startDate, String endDate,
                  StatusEnum status, String instructorName, String instructorPhone,
                  String instructorEmail,String optionalNote) {
        this.id = id;
        this.term_id = term_id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.optionalNote = optionalNote;

    }
    public Course( int term_id,String title, String startDate, String endDate,
                  StatusEnum status, String instructorName, String instructorPhone,
                  String instructorEmail) {
        this.term_id = term_id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;

    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", term_id=" + term_id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status=" + status +
                ", instructorName='" + instructorName + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", optionalNote='" + optionalNote + '\'' +
                ", assessmentList=" + assessmentList +
                '}';
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setCourse_id(int course_id) {
        this.term_id = course_id;
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

    public StatusEnum getStatus() {return status;}

    public void setStatus(StatusEnum status) {this.status = status;}

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {this.instructorPhone = instructorPhone;}

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {this.instructorEmail = instructorEmail;}

    public String getOptionalNote() {
        return optionalNote;
    }

    public void setOptionalNote(String optionalNote) {
        this.optionalNote = optionalNote;
    }

    public List<Assessment> getAssessmentList() {return assessmentList;}

    public void setAssessmentList(List<Assessment> assessmentList) {this.assessmentList = assessmentList;}
}
