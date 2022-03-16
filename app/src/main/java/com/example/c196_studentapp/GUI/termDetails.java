package com.example.c196_studentapp.GUI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_studentapp.Database.Repository;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.Entity.termEntity;
import com.example.c196_studentapp.R;

import java.util.ArrayList;
import java.util.List;

public class termDetails extends AppCompatActivity {


    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog2;
    private Button startDateButton;
    private Button endDateButton;
    Repository repository;
    private TextView TermNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_details2);
        repository = new Repository(getApplication());
        startDateButton = findViewById(R.id.termStartDatePickerButton1);
        endDateButton = findViewById(R.id.termEndDatePickerButton1);
        TermNameField = findViewById(R.id.editTermName1);
        initDatePicker();
        initDatePicker2();
        if(getIntent().getStringExtra("id") != null){
            termForms(getIntent().getStringExtra("id"));
        }
        else{
            termForms();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        loadRecycler();
        return true;
    }
    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    private void termForms(String id) {
        String test = String.valueOf(getIntent().getStringExtra("termName"));
        TermNameField.setText(test);
        startDateButton.setText(String.valueOf(getIntent().getStringExtra("startTermDate")));
        endDateButton.setText(String.valueOf(getIntent().getStringExtra("endTermDate")));
        loadRecycler();

    }

    private void loadRecycler() {
        if(getIntent().getStringExtra("id")!=null) {
            List<courseEntity> allCourses = repository.getAllCourses();
            RecyclerView recyclerView = findViewById(R.id.termCourseList);
            int termIDs = Integer.parseInt(getIntent().getStringExtra("id"));
            List<courseEntity> listCourses = new ArrayList<courseEntity>();
            int i = 0;
            while (i < allCourses.size()) {
                if (allCourses.get(i).getTermID() == termIDs) {
                    listCourses.add(allCourses.get(i));
                }
                i = i + 1;
            }
            courseAdapter courseAdapter = new courseAdapter(this, listCourses);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    private void termForms() {
        startDateButton.setText(dateHelper.getTodaysDate());
        endDateButton.setText(dateHelper.getTodaysDate());
    }


    public void dateStartPicker(View view) {

        datePickerDialog.show();
    }

    public void dateEndPicker(View view) {

        datePickerDialog2.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dateHelper.makeDateString(dayOfMonth, month, year);
                startDateButton.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog =  new DatePickerDialog(this,style, dateSetListener,year,month,day);
    }
    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dateHelper.makeDateString(dayOfMonth, month, year);
                endDateButton.setText(date);
            }
        };
        Calendar calendar2 = Calendar.getInstance();
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH);
        int day = calendar2.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog2 =  new DatePickerDialog(this,style, dateSetListener,year,month,day);
    }

    public void saveTerm(View view) {
        int termID;
        String termName = String.valueOf(TermNameField.getText());
        String startDate = String.valueOf(startDateButton.getText());
        String endDate = String.valueOf(endDateButton.getText());
        if(getIntent().getStringExtra("id") != null){
            termID = Integer.parseInt(getIntent().getStringExtra("id"));
            termEntity current = new termEntity(termID,termName,startDate,endDate);
            repository.update(current);
        }
        else{
            List<termEntity> allTerms = repository.getAllTerms();
            termID = allTerms.get(allTerms.size()-1).getTermID()+1;
            termEntity current = new termEntity(termID,termName,startDate,endDate);
            repository.insert(current);
        }
        termEntity current = new termEntity(termID,termName,startDate,endDate);



        finish();
    }

    public void cancelTerm(View view) {
        finish();
    }

    public void deleteTerm(View view) {
        if(getIntent().getStringExtra("id")!=null){
            int termID = Integer.parseInt(getIntent().getStringExtra("id"));
            List<courseEntity> allCourses = repository.getAllCourses();
            List<courseEntity> termCourses = new ArrayList<>();
            int d =0;
            while(d<allCourses.size()) {
                if (allCourses.get(d).getTermID() == termID) {
                    termCourses.add(allCourses.get(d));
                    d = allCourses.size();
                }
                d = d + 1;
            }
            if(termCourses.size()!=0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(termDetails.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("All associated courses and assessments must be deleted. Please delete term with all associated courses and assessments");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteRecursive();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                deleteRecursive();
            }
        }
        else{
            finish();
        }
    }

    public void addClass(View view) {
        Intent intent = new Intent(termDetails.this, courseDetails.class);
        intent.putExtra("termID", String.valueOf(getIntent().getStringExtra("id")));
        startActivity(intent);

    }

    public void deleteRecursive(){

        int termID = Integer.parseInt(getIntent().getStringExtra("id"));
        termEntity currentTerm = null;
        List<termEntity> allTerms = repository.getAllTerms();
        int i = 0;
        while(i < allTerms.size()){
            if(allTerms.get(i).getTermID() == termID){
                currentTerm = allTerms.get(i);
                i = allTerms.size();
            }
            i=i+1;
        }
        List<courseEntity> allCourses = repository.getAllCourses();
        int d =0;
        while(d < allCourses.size()){
            if(allCourses.get(d).getTermID() == termID){
                courseEntity currentClass = allCourses.get(d);
                int courseID = currentClass.getCourseID();
                int p = 0;
                List<assessmentEntity> allAssessments = repository.getdAllAssesments();
                while(p < allAssessments.size()){
                    if(allAssessments.get(p).getCourseID() == courseID){
                        assessmentEntity currentAssessment = allAssessments.get(p);
                        repository.delete(currentAssessment);
                    }
                    p = p+1;
                }
                repository.delete(currentClass);
            }
            d = d+1;
        }
        repository.delete(currentTerm);


        Toast.makeText(termDetails.this, "Deletion Complete", Toast.LENGTH_LONG).show();
        finish();
    }
}
