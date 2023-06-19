package android.reserver.chrishowellstudentschedulerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditAssessment extends AppCompatActivity {

    EditText et_assessment_title, et_assessment_SD, et_assessment_ED;
    Button btn_cance_edit_assessl, btn_saveAssessment;
    Spinner sp_assess_type;
    Assessment assessment;
    int id, courseID, assessmentID;
    DatePickerDialog.OnDateSetListener dpdOdsListenerStart,dpdOdsListenerEnd;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        et_assessment_title = findViewById(R.id.et_assessment_title);
        et_assessment_SD = findViewById(R.id.et_assessment_SD);
        et_assessment_ED = findViewById(R.id.et_assessment_ED);
        sp_assess_type = findViewById(R.id.sp_assess_type);


        //////////Sets up the format for the Start and End Date //////////////////
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        et_assessment_ED.setText(sdf.format(new Date()));
        et_assessment_SD.setText(sdf.format(new Date()));
        /////////////////////////

        DataBaseHelper dbh = new DataBaseHelper(AddEditAssessment.this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        String previousActivity = intent.getStringExtra("previous_activity");
        if (previousActivity.equals("AssessmentView")) {
            System.out.println("I am from assessmentview and my id is " + id);
            assessmentID = id;
            Assessment assessment = dbh.getAssessment(id);
            courseID = assessment.getCourse_Id();
            System.out.println("I am from courseID and my id is " + courseID);
            et_assessment_title.setText(assessment.getTitle());
            et_assessment_SD.setText(assessment.getStartDate());
            et_assessment_ED.setText(assessment.getEndDate());
            String type = assessment.getAssessmentType().toString();
            int selectnum;
            if (type.equals("OBJECTIVE")) {
                selectnum = 0;
            } else {
                selectnum = 1;
            }
            sp_assess_type.setSelection(selectnum);
        } else {
            courseID = id;
        }

        et_assessment_SD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String startDate = et_assessment_SD.getText().toString();
                if (startDate.equals("")) startDate = "02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(startDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddEditAssessment.this, dpdOdsListenerStart, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dpdOdsListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart(et_assessment_SD,myCalendarStart);
            }

        };


        et_assessment_ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String endDate = et_assessment_ED.getText().toString();
                if (endDate.equals("")) endDate = "02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddEditAssessment.this, dpdOdsListenerEnd, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dpdOdsListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart(et_assessment_ED,myCalendarEnd);
            }
        };
        btn_saveAssessment = findViewById(R.id.btn_saveAssessment);
        btn_saveAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = sp_assess_type.getSelectedItem().toString();

                Assessment.AssessmentType typeenum = Assessment.AssessmentType.valueOf(selectedItem);

                if (previousActivity.equals("CourseView")) {
                    assessment = new Assessment(courseID, et_assessment_title.getText().toString(),
                            et_assessment_SD.getText().toString(), et_assessment_ED.getText().toString(), typeenum);
                } else {
                    assessment = new Assessment(assessmentID, courseID, et_assessment_title.getText().toString(),
                            et_assessment_SD.getText().toString(), et_assessment_ED.getText().toString(), typeenum);
                    id = assessmentID;
                }

                boolean bool = dbh.addAssessment(AddEditAssessment.this, assessment, previousActivity);
                if (!bool) {
                    Toast.makeText(AddEditAssessment.this, "Error adding Assessment to Database", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(AddEditAssessment.this, CourseView.class);
                intent.putExtra("id", courseID);
                startActivity(intent);
            }
        });

        //Cancels the edit and sends them back to CourseView via back button
        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditAssessment.this, CourseView.class);
                intent.putExtra("id", courseID);
                startActivity(intent);
            }
        });
        //Cancels the edit and sends them back to CourseView via cancel button
        btn_cance_edit_assessl =

                findViewById(R.id.btn_cance_edit_assessl);
        btn_cance_edit_assessl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditAssessment.this, CourseView.class);
                intent.putExtra("id", courseID);
                startActivity(intent);
            }
        });
    }

    public void updateLabelStart(EditText et,Calendar myCalendar) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et.setText(sdf.format(myCalendar.getTime()));
    }
}
