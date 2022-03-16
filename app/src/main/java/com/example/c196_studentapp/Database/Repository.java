package com.example.c196_studentapp.Database;

import android.app.Application;

import com.example.c196_studentapp.DAO.assessmentDAO;
import com.example.c196_studentapp.DAO.courseDAO;
import com.example.c196_studentapp.DAO.termDAO;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.Entity.termEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private courseDAO dCourseDAO;
    private assessmentDAO dAssessmentDAO;
    private termDAO dTermDAO;

    private List<assessmentEntity> dAllAssesments;
    private List<courseEntity> dAllCourses;
    private List<termEntity> dAllTerms;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DBBuilder db = DBBuilder.getDatabase(application);
        dAssessmentDAO = db.assessmentDAO();
        dCourseDAO = db.courseDAO();
        dTermDAO = db.termDAO();
    }

    //Gets all courses from the database
    public List<courseEntity> getAllCourses(){
        databaseExecutor.execute(()-> {
            dAllCourses = dCourseDAO.getAllCourses();
        });

        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return dAllCourses;
    }

    //Gets all assessments from the database
    public List<assessmentEntity> getdAllAssesments(){
        databaseExecutor.execute(()-> {
            dAllAssesments = dAssessmentDAO.getAllAssessments();
        });

        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return dAllAssesments;
    }

    //Gets all terms from the database
    public List<termEntity> getAllTerms(){
        databaseExecutor.execute(()-> {
            dAllTerms = dTermDAO.getAllTerms();
        });

        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return dAllTerms;
    }

    //Inserts courses into the database
    public void insert(courseEntity courseEntity){
        databaseExecutor.execute(()-> {
            dCourseDAO.insert(courseEntity);
        });

        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Inserts assessments into the database

    public void insert(assessmentEntity assessment){
        databaseExecutor.execute(()-> {
            dAssessmentDAO.insert(assessment);
        });

        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Inserts terms into the database
    public void insert(termEntity term){
        databaseExecutor.execute(()-> {
            dTermDAO.insert(term);
        });

        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Deletes courses from the database
    public void delete(courseEntity courseEntity){
        databaseExecutor.execute(()->{
            dCourseDAO.delete(courseEntity);
        });
        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Deletes assessments from the database
    public void delete(assessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            dAssessmentDAO.delete(assessmentEntity);
        });
        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Deletes terms from the database
    public void delete(termEntity termEntity){
        databaseExecutor.execute(()->{
            dTermDAO.delete(termEntity);
        });
        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Updates courses and saves them in the database
    public void update(courseEntity courseEntity){
        databaseExecutor.execute(()->{
            dCourseDAO.update(courseEntity);
        });
        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Updates assessments and saves them in the database
    public void update(assessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            dAssessmentDAO.update(assessmentEntity);
        });
        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Updates courses and saves them in the database
    public void update(termEntity termEntity){
        databaseExecutor.execute(()->{
            dTermDAO.update(termEntity);
        });
        try{
            //Delay so everything can catch up.
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
