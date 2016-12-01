package edu.orangecoastcollege.cs273.kfrederick5tmorrissey1ischenck.occstudentsuccesscenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Course> mCourses;
    private List<DayTime> mDayTimes;
    private List<Tutor> mTutors;
    private List<TutorTimeRelation> mRelations;
    private Spinner subjectSpinner;
    private Spinner classSpinner;
    private Spinner daySpinner;
    private Spinner hourSpinner;
    private Spinner minuteSpinner;
    private Button searchButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new DBHelper(this);
        mCourses = db.getAllCourses();
        mDayTimes = db.getAllDayTimes();
        mTutors = db.getAllTutors();
        mRelations = db.getAllRelations();

        subjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);
        minuteSpinner = (Spinner) findViewById(R.id.minuteSpinner);

        ArrayAdapter<String> subjectSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getAllSubjectNames());
        subjectSpinner.setAdapter(subjectSpinnerAdapter);
        subjectSpinner.setOnItemSelectedListener(subjectSpinnerListener);

        ArrayAdapter<String> classSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getAllClassNumbers());
        classSpinner.setAdapter(classSpinnerAdapter);
        classSpinner.setOnItemSelectedListener(classSpinnerListener);

        ArrayAdapter<String> daySpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getAllDays());
        daySpinner.setAdapter(daySpinnerAdapter);

        ArrayAdapter<String> hourSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getAllHours());
        hourSpinner.setAdapter(hourSpinnerAdapter);

        ArrayAdapter<String> minuteSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getAllMinutes());
        minuteSpinner.setAdapter(minuteSpinnerAdapter);

        searchButton = (Button) findViewById(R.id.searchButton);
        clearButton = (Button) findViewById(R.id.clearButton);
    }

    public AdapterView.OnItemSelectedListener subjectSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedSubject = parent.getItemAtPosition(position).toString();
            if (!selectedSubject.equals("[Select subject]")) {
                classSpinner.setEnabled(true);
                updateSpinner(selectedSubject);
            }
            else {
                classSpinner.setEnabled(false);
                classSpinner.setSelection(0);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public AdapterView.OnItemSelectedListener classSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //TODO
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void updateSpinner(String input) {
        ArrayList<String> modifiedCourseList = new ArrayList<>();
        modifiedCourseList.add("[Select class]");
        for (Course course : mCourses)
            if (course.getDepartment().equals(input))
                modifiedCourseList.add(course.getNumber());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                modifiedCourseList);
        classSpinner.setAdapter(adapter);
    }

    private String[] getAllSubjectNames() {
        ArrayList<String> subjectNames = new ArrayList<>();
        subjectNames.add("[Select subject]");
        int size = mCourses.size();

        for (int i = 0; i < size; ++i) {
            String subject = mCourses.get(i).getDepartment();
            if (!subjectNames.contains(subject))
                subjectNames.add(subject);
        }
        return subjectNames.toArray(new String[0]);
    }

    private String[] getAllClassNumbers() {
        String[] classNumbers = new String[mCourses.size() + 1];
        classNumbers[0] = "[Select class]";
        int size = classNumbers.length;

        for (int i = 1; i < size; ++i)
            classNumbers[i] = mCourses.get(i - 1).getNumber();

        return classNumbers;
    }

    private String[] getAllDays() {
        String[] days = {"[Select day]", "Monday", "Tuesday",
                        "Wednesday", "Thursday", "Friday", "Saturday"};
        return days;
    }

    private String[] getAllHours() {
        String[] hours = {"[Select hours]", "9", "10", "11", "12", "1", "2", "3", "4", "5", "6", "7"};
        return hours;
    }

    private String[] getAllMinutes() {
        String[] minutes = {"[Select minutes]", ":00", ":30"};
        return minutes;
    }

    public void search(View v) {
        String subject = subjectSpinner.getSelectedItem().toString();
        String classNumber = classSpinner.getSelectedItem().toString();
        String day = daySpinner.getSelectedItem().toString();
        float time;

        // Format time from 12 hour to 24 hour format
        String hourString = hourSpinner.getSelectedItem().toString();
        if (!hourString.equals("[Select hours]")) {
            time = Float.parseFloat(hourSpinner.getSelectedItem().toString());
            time += (minuteSpinner.getSelectedItem().toString().equals(":30")) ? .50f : 0.0;

            if (!(hourString.equals("9") || hourString.equals("10")
                    || hourString.equals("11") || hourString.equals("12")))
                time += 12.0f;
        }
        // User did not select a time and search will not filter by time
        else
            time = 25.0f;

        ArrayList<Tutor> tutorResults = new ArrayList<>();
        for (TutorTimeRelation relation : mRelations) {
            Course course = relation.getCourse();
            DayTime startTime = relation.getStartTime();
            DayTime endTime = relation.getEndTime();

            if (course.getDepartment().equals(subject) && course.getNumber().equals(classNumber)
                    && startTime.getDay().equals(day)) {
                // Check to see if a time was selected and search for tutors available at that time
                if (time < 25.0f) {
                    if (startTime.getTime() <= time && endTime.getTime() >= time)
                        tutorResults.add(relation.getTutor());
                }
                else // No time was selected and all qualified tutors available at the specified
                     // day will be selected
                    tutorResults.add(relation.getTutor());
            }
        }

        Intent listIntent = new Intent(this, TutorListActivity.class);
        listIntent.putExtra("Tutor Results", tutorResults);
        startActivity(listIntent);
    }

    public void clearSearch(View v) {

    }
}
