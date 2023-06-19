package android.reserver.chrishowellstudentschedulerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        EditText editTextTextMultiLine;
        Button btn_cancel,btn_Save_Note;
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_Save_Note = findViewById(R.id.btn_Save_Note);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);

        DataBaseHelper db = new DataBaseHelper(AddEditNote.this);
        String note = db.getNote(id);
        editTextTextMultiLine.setText(note);

        btn_Save_Note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
                boolean bool = db.addNote(id,editTextTextMultiLine.getText().toString());
                if(!(bool)){
                    Toast.makeText(AddEditNote.this, "Adding data to the database failed", Toast.LENGTH_LONG).show();
                }
                Intent intentSave = new Intent(AddEditNote.this, CourseView.class);
                intentSave.putExtra("id", id);
                startActivity(intentSave);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCancel = new Intent(AddEditNote.this, CourseView.class);
                intentCancel.putExtra("id", id);
                startActivity(intentCancel);
            }
        });

        ImageView back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditNote.this, CourseView.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }
}
