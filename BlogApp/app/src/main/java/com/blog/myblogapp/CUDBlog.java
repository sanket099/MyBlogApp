package com.blog.myblogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CUDBlog extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "title";
    public static final String EXTRA_DESCRIPTION =
            "desc";

    private EditText etTitle;
    private EditText etDesc;
    private FloatingActionButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_u_d_blog);

        etTitle  = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);

        save = findViewById(R.id.fab_save);

        if(getIntent().getStringExtra("ready_title") != null ){
            etTitle.setText(getIntent().getStringExtra("ready_title"));
            etDesc.setText(getIntent().getStringExtra("ready_desc"));
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });





    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String description = etDesc.getText().toString().trim();


        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);



        //editing
       long id =getIntent().getLongExtra("id",-1);
        if(id != -1){
            data.putExtra("id",id);

        }
       // System.out.println(data + "  " +id);
        setResult(RESULT_OK, data);





        finish();//close
    }
}