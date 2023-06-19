package android.reserver.chrishowellstudentschedulerapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class AssessmentView extends AppCompatActivity {

    private TextView tv_assessID,tv_assessment_type,tv_assessment_title,tv_assessment_SD,tv_assessment_ED,tv_Course_ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);

        DataBaseHelper db = new DataBaseHelper(AssessmentView.this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        Assessment assessment = db.getAssessment(id);

        tv_Course_ID = findViewById(R.id.tv_Course_ID);
        tv_assessID = findViewById(R.id.tv_assessID);
        tv_assessment_type = findViewById(R.id.tv_assessment_type);
        tv_assessment_title = findViewById(R.id.tv_assessment_title);
        tv_assessment_SD = findViewById(R.id.tv_assessment_SD);
        tv_assessment_ED = findViewById(R.id.tv_assessment_ED);



        int course_id = assessment.getCourse_Id();

        if (assessment != null) {
            tv_Course_ID.setText(String.valueOf(course_id));
            tv_assessID.setText(String.valueOf(assessment.getId()));
            tv_assessment_type.setText(String.valueOf(assessment.getAssessmentType()));
            tv_assessment_title.setText(assessment.getTitle());
            tv_assessment_SD.setText(assessment.getStartDate());
            tv_assessment_ED.setText(assessment.getEndDate());
        } else {
            tv_Course_ID.setText("error");
            tv_assessID.setText("error");
            tv_assessment_type.setText("error");
            tv_assessment_title.setText("error");
            tv_assessment_SD.setText("error");
            tv_assessment_ED.setText("error");
        }
        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentView.this, CourseView.class);
                intent.putExtra("id", course_id);
                startActivity(intent);
            }
        });

        //Code Below is an example of setting an element of a user interface programatically
        ///////////////////////////////////////////////////////////
        ConstraintLayout parentLayout = findViewById(R.id.viewassessment);


        Button setAlarmButton = new Button(this);
        setAlarmButton.setText("Set Alarm");
        setAlarmButton.setId(View.generateViewId());

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        params.topToBottom = tv_assessment_ED.getId();
        params.leftToLeft = parentLayout.getId();
        params.rightToRight = parentLayout.getId();
        params.topMargin = 32;
        params.bottomMargin = 16;
        parentLayout.addView(setAlarmButton, params);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String startDate = tv_assessment_SD.getText().toString();
               String endDate = tv_assessment_ED.getText().toString();
               String format = "MM/dd/yy";
               SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.US);
               Date myStartdate =null;
               Date myEnddate = null;
               try{
                   myStartdate=sdf.parse(startDate);
               }catch(ParseException e){
                   e.printStackTrace();
               }
                try{
                    myEnddate=sdf.parse(endDate);
                }catch(ParseException e){
                    e.printStackTrace();
                }
                Long triggerStart =myStartdate.getTime();
                Long triggerEnd =myEnddate.getTime();

                Intent intentstart = new Intent(AssessmentView.this,MyReceiver.class);
                intentstart.putExtra("key","This is notification of Start date of Assessment");

                Intent intentEnd = new Intent(AssessmentView.this,MyReceiver.class);
                intentEnd.putExtra("key","This is notification of End date of Assessment");

                PendingIntent senderStart = PendingIntent.getBroadcast(AssessmentView.this, ++MainActivity.numAlert,intentstart,PendingIntent.FLAG_IMMUTABLE);
                PendingIntent senderEnd = PendingIntent.getBroadcast(AssessmentView.this, ++MainActivity.numAlert,intentEnd,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,triggerStart,senderStart);
                alarmManager.set(AlarmManager.RTC_WAKEUP,triggerEnd,senderEnd);
            }
        });
        ////////////////////////

        ImageView tb_menu_button = findViewById(R.id.tb_menu_button);
        tb_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(AssessmentView.this, view);

                popupMenu.getMenuInflater().inflate(R.menu.viewassessment_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.action_edit_assessment){
                            Intent intent_Edit = new Intent(AssessmentView.this,AddEditAssessment.class);
                            intent_Edit.putExtra("id",id);
                            intent_Edit.putExtra("previous_activity","AssessmentView");
                            startActivity(intent_Edit);
                            return true;
                        }
                        else if(item.getItemId() ==R.id.action_delete_assessment){
                            Intent intentDelete = new Intent(AssessmentView.this,CourseView.class);
                            db.deleteAssessment(assessment);
                            intentDelete.putExtra("id",course_id);
                            startActivity(intentDelete);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

}
