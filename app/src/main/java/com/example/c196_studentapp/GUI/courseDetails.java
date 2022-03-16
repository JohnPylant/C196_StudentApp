package com.example.c196_studentapp.GUI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_studentapp.Database.Repository;
import com.example.c196_studentapp.Entity.assessmentEntity;
import com.example.c196_studentapp.Entity.courseEntity;
import com.example.c196_studentapp.Entity.termEntity;
import com.example.c196_studentapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class courseDetails extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog2;
    private Button startDateButton;
    private Button endDateButton;
    private TextView name;
    private TextView courseNotes;
    private TextView teacherName;
    private TextView teacherPhone;
    private TextView teacherEMail;
    private int courseID;
    private Spinner term;
    private Spinner status;

    String id = null;
    ArrayAdapter<String> adapter;
    Repository repository = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        startDateButton = findViewById(R.id.termStartDatePickerButton1);
        endDateButton = findViewById(R.id.termEndDatePickerButton1);
        name = findViewById(R.id.textClassNameField);
        courseNotes = findViewById(R.id.classNote);
        teacherName = findViewById(R.id.instructorName);
        teacherPhone = findViewById(R.id.instructorPhone);
        teacherEMail = findViewById(R.id.instructorEMail);
        term = findViewById(R.id.TermList);
        status = findViewById(R.id.Status);
        initDatePicker();
        initDatePicker2();
        List<String> spinnerArray = getAllTermNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        term.setAdapter(adapter);

        if(getIntent().getStringExtra("id") != null){
            this.id = getIntent().getStringExtra("id");
            termForm(id);
        }
        else if(getIntent().getStringExtra("termID") != null){
            try {
                termForm();
                String stringTermID = String.valueOf(getIntent().getStringExtra("termID"));
                List<termEntity> mAllTerms = repository.getAllTerms();
                int TermID = Integer.parseInt(stringTermID);
                int i=0;
                while(i<mAllTerms.size()){
                    if(mAllTerms.get(i).getTermID() == TermID){
                        term.setSelection(adapter.getPosition(mAllTerms.get(i).getTermName()));
                        int test = adapter.getPosition(mAllTerms.get(i).getTermName());
                        i = mAllTerms.size();
                    }
                    i=i+1;
                }


            }catch(Exception ignored){
            }
        }
        else{
            termForm();
        }
    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu_notifications, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.startDateNotification:
                // Notifications for the start of the course
                String courseName = name.getText().toString();
                String fromDateButton = startDateButton.getText().toString();
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR)-1900;
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH)-1;
                Date yesterday = new Date(year,month,day,23,59);
                Date myDate = null;
                try{
                    myDate =  simpleDateFormat.parse(fromDateButton);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(myDate.after(yesterday)) {
                    Long longNum = myDate.getTime();
                    Intent intent = new Intent(courseDetails.this, myReceiver.class);
                    intent.putExtra("key", "Course " + courseName + " begins on " + fromDateButton + ".");
                    PendingIntent sender = PendingIntent.getBroadcast(courseDetails.this, ++MainActivity.alertNums, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, longNum, sender);
                }
                return true;

            case R.id.endDateNotification:
                //Notifications for the end of the courses
                String endCourseName = name.getText().toString();
                String endDateFormat = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(endDateFormat, Locale.US);
                Calendar calendar2 = Calendar.getInstance();
                int year2 = calendar2.get(Calendar.YEAR)-1900;
                int month2 = calendar2.get(Calendar.MONTH);
                int day2 = calendar2.get(Calendar.DAY_OF_MONTH)-1;
                Date yesterday2 = new Date(year2,month2,day2,23,59);

                String fromDateButton2 = endDateButton.getText().toString();
                Date myDate2 = null;
                try{
                    myDate2 =  simpleDateFormat2.parse(fromDateButton2);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(myDate2.after(yesterday2)) {
                    Long longNum2 = myDate2.getTime();
                    Intent intent2 = new Intent(courseDetails.this, myReceiver.class);
                    intent2.putExtra("key", "Course " + endCourseName + " ends on " + fromDateButton2 + ".");
                    PendingIntent sender2 = PendingIntent.getBroadcast(courseDetails.this, ++MainActivity.alertNums, intent2, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, longNum2, sender2);
                }
                return true;
            case R.id.refresh:
                loadRecycler();
                return true;
        }
        return true;
    }


    private void termForm() {
        startDateButton.setText(dateHelper.getTodaysDate());
        endDateButton.setText(dateHelper.getTodaysDate());
    }

    private void termForm(String id) {
        name.setText(String.valueOf(getIntent().getStringExtra("name")));
        courseNotes.setText(String.valueOf(getIntent().getStringExtra("notes")));
        teacherName.setText(String.valueOf(getIntent().getStringExtra("instructorName")));
        teacherPhone.setText(String.valueOf(getIntent().getStringExtra("instructorPhone")));
        teacherEMail.setText(String.valueOf(getIntent().getStringExtra("instructorEMail")));
        startDateButton.setText(String.valueOf(getIntent().getStringExtra("start")));
        endDateButton.setText(String.valueOf(getIntent().getStringExtra("end")));
        String stringTermID = String.valueOf(getIntent().getStringExtra("term"));
        List<termEntity> mAllTerms = repository.getAllTerms();
        int TermID = Integer.parseInt(stringTermID);
        int i=0;
        while(i<mAllTerms.size()){
            if(mAllTerms.get(i).getTermID() == TermID){
                term.setSelection(adapter.getPosition(mAllTerms.get(i).getTermName()));
                int allTermNames = adapter.getPosition(mAllTerms.get(i).getTermName());
                i = mAllTerms.size();
            }
            i=i+1;
        }
        String courseStatus = String.valueOf(getIntent().getStringExtra("status"));
        String[] stringArray = getResources().getStringArray(R.array.Course_Status);
        status.setSelection(Arrays.asList(stringArray).indexOf(courseStatus));
        loadRecycler();


    }

    private void loadRecycler() {
        List<assessmentEntity> allAssessments = repository.getdAllAssesments();
        RecyclerView recyclerView = findViewById(R.id.courseAssessmentList);
        List<assessmentEntity> courseAssessments = new ArrayList<assessmentEntity>();
        try {
            int courseDetailID = Integer.parseInt(getIntent().getStringExtra("id"));
            int d = 0;
            while (d < allAssessments.size()) {
                if (allAssessments.get(d).getCourseID() == courseDetailID) {
                    assessmentEntity current = allAssessments.get(d);
                    courseAssessments.add(current);
                }
                d = d + 1;
            }
        }catch(Exception Ignore){

        }
        assessmentAdapter assessmentAdapter = new assessmentAdapter(this, courseAssessments);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<String> getAllTermNames() {
        ArrayList<String> allTermNames = new ArrayList<String>();
        List<termEntity> allTerms = repository.getAllTerms();
        int i = 0;
        while (i < allTerms.size()) {
            allTermNames.add(allTerms.get(i).getTermName());
            i = i + 1;
        }
        return allTermNames;
    }


    public void saveCourse(View view) {
        String courseStart = String.valueOf(startDateButton.getText());
        String courseEndDate = String.valueOf(endDateButton.getText());
        String courseName = String.valueOf(name.getText());
        String courseNotes = String.valueOf(this.courseNotes.getText());
        String courseTeacherName = String.valueOf(teacherName.getText());
        String courseTeacherPhone = String.valueOf(teacherPhone.getText());
        String courseTeacherEMail = String.valueOf(teacherEMail.getText());
        String courseTermString = term.getSelectedItem().toString();
        String courseStatus = status.getSelectedItem().toString();
        int courseTerm = 0;
        List<termEntity> mAllTerms = repository.getAllTerms();
        int i = 0;
        while(i < mAllTerms.size()){
            if(mAllTerms.get(i).getTermName().equals(courseTermString)){
                courseTerm = mAllTerms.get(i).getTermID();
                i=mAllTerms.size();
            }
            i=i+1;
        }

        if(id != null){
            int intCourseID = Integer.parseInt(id);
            courseEntity currentCourse = new courseEntity(intCourseID,courseName,courseStart,courseEndDate,courseStatus,courseTeacherName,courseTeacherPhone,courseTeacherEMail,courseNotes,courseTerm);
            repository.update(currentCourse);
        }
        else{
            List<courseEntity> mAllCourses = repository.getAllCourses();
            int intCourseID = mAllCourses.get(mAllCourses.size()-1).getCourseID()+1;
            courseEntity currentCourse = new courseEntity(intCourseID,courseName,courseStart,courseEndDate,courseStatus,courseTeacherName,courseTeacherPhone,courseTeacherEMail,courseNotes,courseTerm);
            repository.insert(currentCourse);
        }
        finish();

    }

    public void cancelCourse(View view) {
        finish();
    }

    public void deleteCourse(View view) {

        if(id !=null){
            int dCourseID = Integer.parseInt(id);
            List<courseEntity> mAllCourses = repository.getAllCourses();
            courseEntity current = null;
            int i = 0;
            while(i<mAllCourses.size()){
                if(dCourseID == mAllCourses.get(i).getCourseID()){
                    current = mAllCourses.get(i);
                    i = mAllCourses.size();
                }
                i=i+1;
            }
            List<assessmentEntity> dAssessments = repository.getdAllAssesments();
            List<assessmentEntity> courseAssessments = new ArrayList<>();
            int d = 0;
            while(d < dAssessments.size()){
                if(dAssessments.get(d).getCourseID() == dCourseID){
                    courseAssessments.add(dAssessments.get(d));
                    d = dAssessments.size();
                }
                d=d+1;
            }
            if(courseAssessments.size()!=0){
                AlertDialog.Builder builder = new AlertDialog.Builder(courseDetails.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("All associated assessments must be deleted.  Please delete course with all associated assessments");
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

    public void shareNote(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String note = String.valueOf(courseNotes.getText());
        String courseName = String.valueOf(name.getText());
        String shareText = courseName+": "+note;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, courseName+" notes");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, "Share Via:"));
    }

    public void addAssessment(View view) {
        Intent intent = new Intent(courseDetails.this, assessmentDetails.class);
        intent.putExtra("courseID",String.valueOf(getIntent().getStringExtra("id")));
        startActivity(intent);
    }

    public void deleteRecursive(){

        if(id !=null){
            int dCourseID = Integer.parseInt(id);
            List<courseEntity> mAllCourses = repository.getAllCourses();
            courseEntity current = null;
            int i = 0;
            while(i<mAllCourses.size()){
                if(dCourseID == mAllCourses.get(i).getCourseID()){
                    current = mAllCourses.get(i);
                    i = mAllCourses.size();
                }
                i=i+1;
            }
            List<assessmentEntity> dAssessments = repository.getdAllAssesments();
            int j = 0;
            while(j < dAssessments.size()){
                if(dAssessments.get(j).getCourseID() == dCourseID){
                    assessmentEntity currentAssessment = dAssessments.get(j);
                    repository.delete(currentAssessment);
                }
                j=j+1;
            }
            repository.delete(current);
        }
        Toast.makeText(courseDetails.this, "Deletion Complete", Toast.LENGTH_LONG).show();
        finish();
    }
}
