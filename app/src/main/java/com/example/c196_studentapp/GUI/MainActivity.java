package com.example.c196_studentapp.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.c196_studentapp.Database.Repository;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.Entity.termEntity;
import com.example.c196_studentapp.R;

public class MainActivity extends AppCompatActivity {
    public static int alertNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = new Repository(getApplication());
        termEntity termEntity = new termEntity(1,"Winter", "10/27/2021", "03/15/2022");
        repository.insert(termEntity);
        termEntity termEntity2 = new termEntity(2,"Spring", "03/20/2022", "06/15/2022");
        repository.insert(termEntity2);
        courseEntity courseEntity = new courseEntity(1,"Cyber Fundamentals","10/27/2021","03/15/2022","In-Progress","Dr. Strange","256-800-8331","Drstrange@pylantu.edu","",1);
        repository.insert(courseEntity);
        courseEntity courseEntity2 = new courseEntity(2,"English 101","03/20/2022","06/15/2022","In-Progress","Dr. House","256-800-8332","Drhouse@pylantu.edu","",1);
        repository.insert(courseEntity2);
        assessmentEntity assessmentEntity = new assessmentEntity(1,"Security+","Objective","03/15/2022",1);
        repository.insert(assessmentEntity);
        assessmentEntity assessmentEntity2 = new assessmentEntity(2,"English Paper","Performance","06/15/2022",1);
        repository.insert(assessmentEntity2);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(MainActivity.this, mainScreen.class));
            }
        },1500 );

    }
    }
