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
import com.example.c196_studentapp.Entity.termEntity;
import com.example.c196_studentapp.R;

import java.util.List;

public class terms extends AppCompatActivity {

    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        loadRecycler();

    }

    private void loadRecycler() {
        repository = new Repository(getApplication());
        List<termEntity> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.allTermList);
        termAdapter termAdapter = new termAdapter(this, allTerms);
        recyclerView.setAdapter(termAdapter);
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
        List<termEntity> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.allTermList);
        termAdapter TermAdapter = new termAdapter(this, allTerms);
        recyclerView.setAdapter(TermAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    public void addTerm(View view) {

        startActivity(new Intent(terms.this, termDetails.class));
    }
}
