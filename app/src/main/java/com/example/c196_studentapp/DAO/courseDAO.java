package com.example.c196_studentapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_studentapp.Entity.courseEntity;

import java.util.List;

@Dao
public interface courseDAO {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(courseEntity courses);

        @Update
        void update(courseEntity courses);

        @Delete
        void delete(courseEntity courses);

        @Query("DELETE FROM course_table")
        void deleteAllClasses();

        @Query("SELECT * FROM course_table ORDER BY courseID ASC")
        List<courseEntity> getAllCourses();

}
