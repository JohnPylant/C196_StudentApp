package com.example.c196_studentapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_studentapp.DAO.assessmentDAO;
import com.example.c196_studentapp.DAO.courseDAO;
import com.example.c196_studentapp.DAO.termDAO;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.Entity.termEntity;

@Database(entities = {assessmentEntity.class, courseEntity.class, termEntity.class}, version = 3, exportSchema = false)
public abstract class DBBuilder extends RoomDatabase {
    public abstract assessmentDAO assessmentDAO();
    public abstract courseDAO courseDAO();
    public abstract termDAO termDAO();


    private static volatile DBBuilder INSTANCE;
    //Context tells you where you are in the program (Android syntax)
    static DBBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DBBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DBBuilder.class, "schoolDatabase.db")
                            .fallbackToDestructiveMigration()
                            //If wanting to do Main Thread Queries, put here.
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}
