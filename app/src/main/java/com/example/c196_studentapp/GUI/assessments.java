package com.example.c196_studentapp.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_studentapp.Database.Repository;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.R;

import java.util.List;

public class assessments extends AppCompatActivity {

    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        loadRecycler();

    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    private void loadRecycler() {
        repository = new Repository(getApplication());
        List<assessmentEntity> allCourses = repository.getdAllAssesments();
        RecyclerView recyclerView = findViewById(R.id.allAssessments);
        assessmentAdapter assessAdapter = new assessmentAdapter(this, allCourses);
        recyclerView.setAdapter(assessAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    //Inflate the menu; this adds items to the action bar if it is present.
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        List<assessmentEntity> allCourses = repository.getdAllAssesments();
        RecyclerView recyclerView = findViewById(R.id.allAssessments);
        assessmentAdapter assessAdapter = new assessmentAdapter(this, allCourses);
        recyclerView.setAdapter(assessAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }


    public void addAssessment(View view) {
        startActivity(new Intent(assessments.this, assessmentDetails.class));
    }
}
