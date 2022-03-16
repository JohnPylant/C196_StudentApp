package com.example.c196_studentapp.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class assessmentEntity {
    @PrimaryKey(autoGenerate = true)

    private int assessmentID;
    private String assessmentName;
    private String assessmentType;
    private String endDate;

    public assessmentEntity(int assessmentID, String assessmentName, String assessmentType, String endDate, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    public int getAssessmentID() {

        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {

        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {

        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {

        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {

        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {

        this.assessmentType = assessmentType;
    }

    public int getCourseID() {

        return courseID;
    }

    public void setCourseID(int courseID) {

        this.courseID = courseID;
    }

    private int courseID;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {

        this.endDate = endDate;
    }
}
