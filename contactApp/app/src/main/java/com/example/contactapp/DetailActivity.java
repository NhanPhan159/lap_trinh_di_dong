package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.contactapp.databinding.ActivityAddEditorBinding;
import com.example.contactapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    int id;
    Bitmap bmpImage;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    private ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();
        binding.rlContact.setBackgroundColor(Color.rgb(96,125,139));

        Intent intent = getIntent();
        if(intent!=null){
            id  = intent.getIntExtra("id", 0);
            Contact ct = contactDao.getContact(id);
            if(ct!=null){
                bmpImage = DataConverter.convertByteArray2Image(ct.getImage());
                binding.txtName.setText(ct.getName());
                binding.txtPhone.setText(ct.getMobile());
                binding.imvContact.setImageBitmap(bmpImage);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.ic_edit:
                intent = new Intent(DetailActivity.this, AddEditorActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 5);
                return true;
            case R.id.action_delete:
                Contact contactDel = contactDao.getContact(id);
                contactDao.delete((contactDel));
                intent = new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}