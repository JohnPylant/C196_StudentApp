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
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.R;

import java.util.List;

public class courses extends AppCompatActivity {

    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        loadRecycler();

    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    private void loadRecycler() {
        repository = new Repository(getApplication());
        List<courseEntity> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.allClassList);
        courseAdapter courseAdapt = new courseAdapter(this, allCourses);
        recyclerView.setAdapter(courseAdapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        repository = new Repository(getApplication());
        List<courseEntity> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.allClassList);
        courseAdapter classAdapt = new courseAdapter(this, allCourses);
        recyclerView.setAdapter(classAdapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }



    public void addClass(View view) {
        startActivity(new Intent(courses.this, courseDetails.class));

    }
}
