package android.reserver.chrishowellstudentschedulerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button btn_add_Term;
    List<Term> termList = new ArrayList<>();
    public static int numAlert;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper db = new DataBaseHelper(MainActivity.this);
        termList = db.getAllTerms();

        btn_add_Term = findViewById(R.id.btn_add_Term);
        btn_add_Term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTerm.class);
                intent.putExtra("previous_activity", "MainActivity");
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.rv_Term_List);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecycleViewTermListAdapter(termList,MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        ImageView tb_menu_button = findViewById(R.id.tb_menu_button);
        tb_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.mainmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.menuAddterm) {
                            Intent intent = new Intent(MainActivity.this, AddTerm.class);
                            intent.putExtra("previous_activity", "MainActivity");
                            startActivity(intent);
                            return true;
                        } else {
                            return false;
                        }

                    }
                });
                popupMenu.show();
                }

            });
}
}