package com.example.c196_studentapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName ="course_table")
public class courseEntity {
    @PrimaryKey(autoGenerate = true)

    private int courseID;
    private String courseName;
    private String startDate;
    private String endDate;
    private String courseStatus;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String courseNotes;
    private int termID;

    public courseEntity(int courseID, String courseName, String startDate, String endDate, String courseStatus, String instructorName, String instructorPhone, String instructorEmail, String courseNotes, int termID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public int getCourseID() {

        return courseID;
    }

    public void setCourseID(int courseID) {

        this.courseID = courseID;
    }

    public String getCourseName() {

        return courseName;
    }

    public void setCourseName(String courseName) {

        this.courseName = courseName;
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

    public String getCourseStatus() {

        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {

        this.courseStatus = courseStatus;
    }

    public String getInstructorName() {

        return instructorName;
    }

    public void setInstructorName(String instructorName) {

        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {

        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {

        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getCourseNotes() {

        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {

        this.courseNotes = courseNotes;
    }

    public int getTermID() {

        return termID;
    }

    public void setTermID(int termID) {

        this.termID = termID;
    }

}

