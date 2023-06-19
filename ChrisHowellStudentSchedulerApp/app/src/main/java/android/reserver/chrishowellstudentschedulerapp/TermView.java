package android.reserver.chrishowellstudentschedulerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TermView extends AppCompatActivity {

    private int id;
    List<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);

        TextView tvTVED = findViewById(R.id.tvTVED);
        TextView tvTVName = findViewById(R.id.tvTVName);
        TextView tvTVSD = findViewById(R.id.tvTVSD);
        TextView tvTVID = findViewById(R.id.tvTVID);


        DataBaseHelper db = new DataBaseHelper(TermView.this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        courseList = db.getAllCourses(id);

        Term term = db.getTerm(id);
        if (term != null) {
            tvTVID.setText(String.valueOf(term.getId()));
            tvTVName.setText(term.getTitle());
            tvTVSD.setText(term.getStartDate());
            tvTVED.setText(term.getEndDate());
        } else {
            tvTVID.setText("error");
            tvTVName.setText("error");
            tvTVSD.setText("error");
            tvTVED.setText("error");
        }
        recyclerView = findViewById(R.id.rvTVCourseList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RVCourseListAdapter(courseList,TermView.this);
        recyclerView.setAdapter(mAdapter);

        ImageView tb_menu_button = findViewById(R.id.tb_menu_button);
        tb_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(TermView.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.termview_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_add_course) {
                            Intent intent = new Intent(TermView.this, AddCourse.class);
                            intent.putExtra("id", id);
                            intent.putExtra("previous_activity","TermView");
                            startActivity(intent);
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_delete_term) {
                            boolean bool = db.deleteTerm(term);
                            if (bool) {
                                Intent intent = new Intent(TermView.this, MainActivity.class);
                                startActivity(intent);
                                return true;
                            }

                            else{
                                Toast.makeText(getApplicationContext(), "You can not delete a term if it has a Course attached to it", Toast.LENGTH_SHORT).show();
                                return false;
                            }}
                        else if(item.getItemId() == R.id.action_edit_term){
                            System.out.println("send to the edit ffffffffffffffff");
                            Intent intent = new Intent(TermView.this, AddTerm.class);
                            intent.putExtra("id", id);
                            intent.putExtra("previous_activity", "TermView");
                            startActivity(intent);
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
                Intent intent = new Intent(TermView.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}