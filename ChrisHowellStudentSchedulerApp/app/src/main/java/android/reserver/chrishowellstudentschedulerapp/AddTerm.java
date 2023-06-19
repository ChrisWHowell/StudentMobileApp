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
import android.widget.Toast;

import java.util.Calendar;

public class AddTerm extends AppCompatActivity {

    private Button btn_Cancel,btn_Create_Term;
    private EditText et_Term_Name,et_Start_Date,et_End_Date;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        et_Term_Name = findViewById(R.id.et_Term_Name);
        et_Start_Date = findViewById(R.id.et_Start_Date_C);
        et_End_Date = findViewById(R.id.et_End_Date_C);
        btn_Cancel = findViewById(R.id.btn_Cancel);

        Intent intent = getIntent();
         id = intent.getIntExtra("id",-1);
        String previousActivity = intent.getStringExtra("previous_activity");
        DataBaseHelper dbh = new DataBaseHelper(AddTerm.this);

        //if previous activity is Termview then Term has already been created so it loads that data into textfields
        if(previousActivity.equals("TermView")){
            id = intent.getIntExtra("id",-1);
            Term term = dbh.getTerm(id);
            et_Term_Name.setText(term.getTitle());
            et_Start_Date.setText(term.getStartDate());
            et_End_Date.setText(term.getEndDate());
        }


        et_Start_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTerm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                et_Start_Date.setText(date);}
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        et_End_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTerm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                et_End_Date.setText(date);}
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        //below code for executing the cancel button, this simply brings back to home page
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCancel = new Intent(AddTerm.this, MainActivity.class);
                startActivity(intentCancel);
            }
        });
        btn_Create_Term = findViewById(R.id.bt_Create_Term);

        btn_Create_Term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Term newTerm;
                if(previousActivity.equals("TermView")){
                    newTerm = new Term(id,et_Term_Name.getText().toString(),et_Start_Date.getText().toString(),et_End_Date.getText().toString());
                }else{
                    newTerm = new Term(et_Term_Name.getText().toString(),et_Start_Date.getText().toString(),et_End_Date.getText().toString());
                }

                boolean bool = dbh.addTerm(AddTerm.this,newTerm,previousActivity);
                if(!bool)
                {
                    Toast.makeText(AddTerm.this,"Error adding term to list",Toast.LENGTH_SHORT).show();
                }

                Intent intentCreate = new Intent(AddTerm.this, MainActivity.class);
                startActivity(intentCreate);
            }
        });
        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTerm.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}