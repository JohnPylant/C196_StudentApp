package com.example.c196_studentapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_studentapp.Entity.assessmentEntity;

import java.util.List;

@Dao
public interface assessmentDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        public void insert(assessmentEntity assessment);

        @Update
        public void update(assessmentEntity assessment);

        @Delete
        public void delete(assessmentEntity assessment);

        @Query("DELETE FROM assessment_table")
        public void deleteAllAssessments();

        @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
        List<assessmentEntity> getAllAssessments();



}
