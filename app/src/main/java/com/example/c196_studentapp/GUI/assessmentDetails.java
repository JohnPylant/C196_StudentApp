package com.example.c196_studentapp.GUI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_studentapp.Database.Repository;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class assessmentDetails extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TextView assessmentName;
    private Spinner course;
    private Spinner type;
    String id = null;
    ArrayAdapter<String> adapter;
    Repository repository = new Repository(getApplication());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerbutton);
        dateButton.setText(getTodaysDate());
        Spinner course = findViewById(R.id.assessmentCourseList);
        List<String> spinnerArray = getAllCourseNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(adapter);
        if(getIntent().getStringExtra("id") != null){
            this.id = getIntent().getStringExtra("id");
            fillFields(id);
        }
        else if(getIntent().getStringExtra("courseID") != null){
            try{
                int assessmentCourseID = Integer.parseInt(getIntent().getStringExtra("courseID"));
                String assessmentCourse = null;
                int i = 0;
                List<courseEntity> mAllCourses = repository.getAllCourses();
                while(i<mAllCourses.size()){
                    if(mAllCourses.get(i).getCourseID() == assessmentCourseID){
                        assessmentCourse = mAllCourses.get(i).getCourseName();
                        i = mAllCourses.size();
                        course.setSelection(adapter.getPosition(assessmentCourse));
                    }
                    i=i+1;
                }

            }catch(Exception ignore){

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String assessmentName = this.assessmentName.getText().toString();
        String dateFromButton = dateButton.getText().toString();
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Date myDate = null;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR)-1900;
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH)-1;
        Date yesterday = new Date(year,month,day,23,59);
        try{
            myDate =  simpleDateFormat.parse(dateFromButton);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(myDate.after(yesterday)) {
            Long longNum = myDate.getTime();
            Intent intent = new Intent(assessmentDetails.this, myReceiver.class);
            intent.putExtra("key", "Assessment " + assessmentName + " is due " + dateFromButton + ".");
            PendingIntent sender = PendingIntent.getBroadcast(assessmentDetails.this, ++MainActivity.alertNums, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, longNum, sender);
        }
        return true;
    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month+"/"+day+"/"+year;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog =  new DatePickerDialog(this,style, dateSetListener,year,month,day);

    }

    private String makeDateString(int day, int month, int year){

        return month + "/"+day+"/"+year;
    }


    public void datePicker(View view) {

        datePickerDialog.show();
    }

    private void fillFields(String id) {
        assessmentName = findViewById(R.id.editAssessmentName);
        course = findViewById(R.id.assessmentCourseList);
        type = findViewById(R.id.assessmentType);
        String assessmentName = String.valueOf(getIntent().getStringExtra("name"));
        String assessmentType = String.valueOf(getIntent().getStringExtra("type"));
        int assessmentCourseID = Integer.parseInt(getIntent().getStringExtra("course"));
        String assessmentEnd = String.valueOf((getIntent().getStringExtra("end")));

        String assessmentCourse = null;
        int i = 0;
        List<courseEntity> mAllCourses = repository.getAllCourses();
        while(i<mAllCourses.size()){
            if(mAllCourses.get(i).getCourseID() == assessmentCourseID){
                assessmentCourse = mAllCourses.get(i).getCourseName();
                i = mAllCourses.size();
            }
            i=i+1;
        }

        this.assessmentName.setText(assessmentName);
        course.setSelection(adapter.getPosition(assessmentCourse));
        String[] stringArray = getResources().getStringArray(R.array.Assessment_Types);
        type.setSelection(Arrays.asList(stringArray).indexOf(assessmentType));
        dateButton.setText(assessmentEnd);
    }

    private ArrayList<String> getAllCourseNames() {
        ArrayList<String> courseNames = new ArrayList<String>();
        List<courseEntity> allCourses = repository.getAllCourses();
        int i = 0;
        while(i < allCourses.size()){
            courseNames.add(allCourses.get(i).getCourseName());
            i=i+1;
        }
        return courseNames;
    }

    public void saveAssessment(View view) {
        if(id != null){
            id = getIntent().getStringExtra("id");
            int currentID = Integer.parseInt(id);
            assessmentName = findViewById(R.id.editAssessmentName);
            course = findViewById(R.id.assessmentCourseList);
            type = findViewById(R.id.assessmentType);
            dateButton = findViewById(R.id.datePickerbutton);

            String currentName = String.valueOf(assessmentName.getText());
            String currentEndDate = String.valueOf(dateButton.getText());
            String currentType = type.getSelectedItem().toString();
            String CCourseString = course.getSelectedItem().toString();
            List<courseEntity> allCourses = repository.getAllCourses();
            int currentCourseID = 0;
            int i = 0;
            while(i < allCourses.size()){
                if(allCourses.get(i).getCourseName().equals(CCourseString)){
                    currentCourseID = allCourses.get(i).getCourseID();
                    i = allCourses.size();
                }
                i=i+1;
            }
            assessmentEntity currentAssessment = new assessmentEntity(currentID, currentName, currentType, currentEndDate, currentCourseID);
            repository.update(currentAssessment);
            finish();

        }
        else{
            List<assessmentEntity> mAllAssessments = repository.getdAllAssesments();
            int last = mAllAssessments.size();
            int currentID = 1;
            if(last > 0) {
                int lastID = mAllAssessments.get(last - 1).getAssessmentID();
                currentID = lastID + 1;
            }
            assessmentName = findViewById(R.id.editAssessmentName);
            course = findViewById(R.id.assessmentCourseList);
            type = findViewById(R.id.assessmentType);
            dateButton = findViewById(R.id.datePickerbutton);

            String currentName = String.valueOf(assessmentName.getText());
            String currentEndDate = String.valueOf(dateButton.getText());
            String currentType = type.getSelectedItem().toString();
            String CCourseString = course.getSelectedItem().toString();
            List<courseEntity> allCourses = repository.getAllCourses();
            int currentCourseID = 0;
            int i = 0;
            while(i < allCourses.size()){
                if(allCourses.get(i).getCourseName().equals(CCourseString)){
                    currentCourseID = allCourses.get(i).getCourseID();
                    i = allCourses.size();
                }
                i=i+1;
            }
            assessmentEntity currentAssessment = new assessmentEntity(currentID, currentName, currentType, currentEndDate, currentCourseID);
            repository.insert(currentAssessment);
            finish();
        }

    }

    public void cancelAssessment(View view) {

        finish();
    }

    public void deleteAssessment(View view) {
        if(id != null){
            List<assessmentEntity> mAllAssessments = repository.getdAllAssesments();
            assessmentEntity current = null;
            int i=0;
            while(i < mAllAssessments.size()){
                if(id.equalsIgnoreCase(String.valueOf(mAllAssessments.get(i).getAssessmentID()))){
                    current = mAllAssessments.get(i);
                    i=mAllAssessments.size();
                }
                i = i+1;
            }
            repository.delete(current);
            Toast.makeText(assessmentDetails.this, "Deletion Complete", Toast.LENGTH_LONG).show();
            finish();
        }
        finish();

    }
}
