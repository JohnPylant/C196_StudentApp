package com.example.c196_studentapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_studentapp.Entity.termEntity;

import java.util.List;

@Dao
public interface termDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(termEntity term);

    @Update
    void update(termEntity term);

    @Delete
    void delete(termEntity term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termID ASC")
    List<termEntity> getAllTerms();
}
