package android.reserver.chrishowellstudentschedulerapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCourse extends AppCompatActivity {

    private Button btn_Cancel,bt_Create_Course;
    private EditText et_Course_Name,et_Start_Date_C,et_End_Date_C,et_Ins_Name,et_Ins_Phone,et_Ins_Email;
    private Spinner sp_course_status;
    private int term_id,id,course_id;
    String optionalNote;
    DatePickerDialog.OnDateSetListener dpdOdsListenerStart,dpdOdsListenerEnd;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
       optionalNote = "None";

        et_Course_Name = findViewById(R.id.et_Course_Name);
        et_Start_Date_C = findViewById(R.id.et_Start_Date_C);
        et_End_Date_C = findViewById(R.id.et_End_Date_C);
        et_Ins_Name = findViewById(R.id.et_Ins_Name);
        et_Ins_Phone = findViewById(R.id.et_Ins_Phone);
        et_Ins_Email = findViewById(R.id.et_Ins_Email);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        bt_Create_Course = findViewById(R.id.bt_Create_Course);
        sp_course_status = findViewById(R.id.sp_course_status);
        String selectedItem = (String) sp_course_status.getSelectedItem();
        Course.StatusEnum status = Course.StatusEnum.valueOf(selectedItem);

        //////////Sets up the format for the Start and End Date //////////////////
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        et_End_Date_C.setText(sdf.format(new Date()));
        et_Start_Date_C.setText(sdf.format(new Date()));
        /////////////////////////

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        String previousActivity = intent.getStringExtra("previous_activity");
        DataBaseHelper dbh = new DataBaseHelper(AddCourse.this);

        if(previousActivity.equals("CourseView")){
            course_id = id;
            Course course = dbh.getCourse(course_id);
            term_id = course.getTerm_id();
            et_Course_Name.setText(course.getTitle());
            et_Start_Date_C.setText(course.getStartDate());
            et_End_Date_C.setText(course.getEndDate());
            et_Ins_Name.setText(course.getInstructorName());
            et_Ins_Phone.setText(course.getInstructorPhone());
            et_Ins_Email.setText(course.getInstructorEmail());
            optionalNote = course.getOptionalNote();
            String statusStr = course.getStatus().toString();
            int selectnum;

            if(statusStr.equals("In_Progress"))
            {
                selectnum=0;
            }else if(statusStr.equals("Completed"))
            {
                selectnum = 1;
            }
            else if(statusStr.equals("Dropped"))
            {
                selectnum = 2;
            }else{
                selectnum = 3;
            }
            sp_course_status.setSelection(selectnum);
        }else{
            term_id = id;
        }


        et_Start_Date_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String startDate = et_Start_Date_C.getText().toString();
                if (startDate.equals("")) startDate = "02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(startDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourse.this, dpdOdsListenerStart, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dpdOdsListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart(et_Start_Date_C,myCalendarStart);
            }

        };


        et_End_Date_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String endDate = et_End_Date_C.getText().toString();
                if (endDate.equals("")) endDate = "02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourse.this, dpdOdsListenerEnd, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dpdOdsListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart(et_End_Date_C,myCalendarEnd);
            }
        };


        //below code for executing the CANCEL button, this simply brings back to home page
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCancel = new Intent(AddCourse.this, TermView.class);
                intentCancel.putExtra("id",term_id);
                startActivity(intentCancel);
            }
        });

        bt_Create_Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course newCourse;
                if(previousActivity.equals("TermView") ) {

                    newCourse = new Course(term_id, et_Course_Name.getText().toString(), et_Start_Date_C.getText().toString(), et_End_Date_C.getText().toString(),
                            status, et_Ins_Name.getText().toString(), et_Ins_Phone.getText().toString(), et_Ins_Email.getText().toString());
                }
                else{
                    newCourse = new Course(course_id,term_id, et_Course_Name.getText().toString(), et_Start_Date_C.getText().toString(), et_End_Date_C.getText().toString(),
                            status, et_Ins_Name.getText().toString(), et_Ins_Phone.getText().toString(), et_Ins_Email.getText().toString(),optionalNote);
                }

                boolean bool = dbh.addCourse(AddCourse.this,newCourse,previousActivity);
                if(!bool)
                {
                    Toast.makeText(AddCourse.this,"Error adding Course to Database",Toast.LENGTH_SHORT).show();
                }

                Intent intentCreate = new Intent(AddCourse.this, TermView.class);
                intentCreate.putExtra("id",term_id);
                startActivity(intentCreate);
            }
        });
        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourse.this, TermView.class);
                intent.putExtra("id", term_id);
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
