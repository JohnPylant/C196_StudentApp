package com.example.c196_studentapp.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_studentapp.R;

public class mainScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

    }

    public void loadAllTerms(View view){

        startActivity(new Intent(mainScreen.this, terms.class));
    }

    public void loadAllCourses(View view){
        startActivity(new Intent(mainScreen.this, courses.class));
    }

    public void loadAllAssessments(View view){
        startActivity(new Intent(mainScreen.this, assessments.class));
    }


}
