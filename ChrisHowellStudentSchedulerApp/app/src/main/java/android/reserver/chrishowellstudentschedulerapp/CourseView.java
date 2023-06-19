package android.reserver.chrishowellstudentschedulerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseView extends AppCompatActivity {

    private List<Assessment> assessmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        TextView tvTermIDnumvar,tvCourseID,tvCourseName,tv_StartDate,
                tv_EndDate,tv_CourseStatus,tv_InsName,tv_InsPhone,tvInsEmail,tv_Note;


        tvTermIDnumvar = findViewById(R.id.tvTermIDnumvar);
        tvCourseID = findViewById(R.id.tvCourseID);
        tvCourseName = findViewById(R.id.tvCourseName);
        tv_StartDate = findViewById(R.id.tv_StartDate);
        tv_EndDate = findViewById(R.id.tv_EndDate);
        tv_CourseStatus = findViewById(R.id.tv_CourseStatus);
        tv_InsName = findViewById(R.id.tv_InsName);
        tv_InsPhone = findViewById(R.id.tv_InsPhone);
        tvInsEmail = findViewById(R.id.tvInsEmail);
        tv_Note = findViewById(R.id.tv_Note);

        DataBaseHelper db = new DataBaseHelper(CourseView.this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);


        Course course = db.getCourse(id);
        int term_id  = course.getTerm_id();
        if (course != null) {

            tvTermIDnumvar.setText(String.valueOf(course.getTerm_id()));

            tvCourseID.setText(String.valueOf(course.getId()));
            tvCourseName.setText(course.getTitle());
            tv_StartDate.setText(course.getStartDate());
            tv_EndDate.setText(course.getEndDate());
            tv_CourseStatus.setText(course.getStatus().toString());
            tv_InsName.setText(course.getInstructorName());
            tv_InsPhone.setText(course.getInstructorPhone());
            tvInsEmail.setText(course.getInstructorEmail());

            tv_Note.setText(course.getOptionalNote());

        } else {
            tvTermIDnumvar.setText("error");
            tvCourseID.setText("error");
            tvCourseName.setText("error");
            tv_StartDate.setText("error");
            tv_EndDate.setText("error");
            tv_CourseStatus.setText("error");
            tv_InsName.setText("error");
            tv_InsPhone.setText("error");
            tvInsEmail.setText("error");
            tv_Note.setText("error");
        }
        recyclerView = findViewById(R.id.rv_assessmentlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        assessmentList = db.getAllAssessments(id);


        mAdapter = new RVAssessmentListAdapter(assessmentList,CourseView.this);
        recyclerView.setAdapter(mAdapter);

        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        ImageView tb_menu_button = findViewById(R.id.tb_menu_button);
        tb_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(CourseView.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.viewcourse_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_edit_course) {
                            Intent intent = new Intent(CourseView.this, AddCourse.class);
                            intent.putExtra("id", id);
                            intent.putExtra("previous_activity","CourseView");
                            startActivity(intent);
                            return true;
                        } else if(item.getItemId() == R.id.action_add_assessment){
                            Intent intent = new Intent(CourseView.this, AddEditAssessment.class);
                            intent.putExtra("id", id);
                            intent.putExtra("previous_activity", "CourseView");
                            startActivity(intent);
                            return true;

                        }else if(item.getItemId() == R.id.action_add_note){
                            Intent intent = new Intent(CourseView.this, AddEditNote.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            return true;

                        }else if(item.getItemId() == R.id.action_delete_course){
                            db.deleteCourse(course);
                            Intent intent = new Intent(CourseView.this, TermView.class);
                            intent.putExtra("id", term_id);
                            startActivity(intent);
                            return true;

                        }else if(item.getItemId()==R.id.action_share) {
                            Intent intentShare = new Intent();
                            intentShare.setAction(Intent.ACTION_SEND);
                            intentShare.putExtra(Intent.EXTRA_TEXT,tv_Note.getText().toString());
                            intentShare.putExtra(Intent.EXTRA_TITLE,course.getTitle()+" Course Notes");
                            intentShare.setType("text/plain");

                            Intent shareIntent = Intent.createChooser(intentShare,null);
                            startActivity(shareIntent);
                            return true;
                        }else if(item.getItemId()==R.id.action_Alarm_SD) {
                            String startDate = tv_StartDate.getText().toString();
                            Date myStartdate =null;
                            try{
                                myStartdate=sdf.parse(startDate);
                            }catch(ParseException e){
                                e.printStackTrace();
                            }
                            Long triggerStart =myStartdate.getTime();
                            Intent intentstart = new Intent(CourseView.this,MyReceiver.class);
                            intentstart.putExtra("key","This is notification of Start date of Course");
                            PendingIntent senderStart = PendingIntent.getBroadcast(CourseView.this, ++MainActivity.numAlert,intentstart,PendingIntent.FLAG_IMMUTABLE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerStart,senderStart);
                            return true;

                        }else if(item.getItemId()==R.id.action_Alarm_ED) {
                            String endDate = tv_EndDate.getText().toString();
                            Date myEnddate = null;
                            try{
                                myEnddate=sdf.parse(endDate);
                            }catch(ParseException e){
                                e.printStackTrace();
                            }
                            Long triggerEnd =myEnddate.getTime();
                            Intent intentEnd = new Intent(CourseView.this,MyReceiver.class);
                            intentEnd.putExtra("key","This is notification of End date of Course ");
                            PendingIntent senderEnd = PendingIntent.getBroadcast(CourseView.this, ++MainActivity.numAlert,intentEnd,PendingIntent.FLAG_IMMUTABLE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerEnd,senderEnd);
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



        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseView.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}