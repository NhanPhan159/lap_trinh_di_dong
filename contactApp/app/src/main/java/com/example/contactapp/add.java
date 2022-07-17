package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contactapp.databinding.ActivityAddBinding;
import com.example.contactapp.databinding.ActivityMainBinding;

public class add extends AppCompatActivity {
    private TextView remove ;
    private TextView save ;
    private String fname ;
    private String name ;
    private String lname ;
    private String phone  ;
    private String mail ;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    private ActivityAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();


        binding.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        binding.saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = binding.fisrtName.getText().toString();
                lname = binding.lastName.getText().toString();
                name = fname+" "+lname;
                phone = binding.phone.getText().toString();
                mail = binding.email.getText().toString();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        contactDao.insertAll(new Contact(name,phone,mail));
                    }
                });
                Intent intent = new Intent(add.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}