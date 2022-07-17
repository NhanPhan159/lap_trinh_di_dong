package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.contactapp.databinding.ActivityAddEditorBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddEditorActivity extends AppCompatActivity {

    private ActivityAddEditorBinding binding;
    private AppDatabase appDatabase;
    private ContactDao contactDao;
    private boolean checkInDb = false;
    private int id;
    Bitmap bmpImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddEditorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        bmpImage = null;
        binding.imvContact.setBackgroundColor(Color.rgb(96,125,139));

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();

        Intent intent = getIntent();
        if(intent!=null){
            id  = intent.getIntExtra("id", 0);
            Contact ct = contactDao.getContact(id);

            if(ct!=null){
                bmpImage = DataConverter.convertByteArray2Image(ct.getImage());
                binding.txtName.setText(ct.getName());
                binding.txtEmail.setText((ct.getEmail()));
                binding.txtPhone.setText((ct.getMobile()));
                binding.btnAdd.setVisibility(View.INVISIBLE);
                binding.imvContact.setImageBitmap(bmpImage);
                checkInDb = true;
            }
            else {
                checkInDb = false;
            }

        }

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bmpImage==null){
                    bmpImage = ((BitmapDrawable)(getResources().getDrawable(R.mipmap.ic_person))).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmpImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteImage = stream.toByteArray();
                    Contact a = new Contact(binding.txtName.getText().toString(),
                            binding.txtPhone.getText().toString(),
                            binding.txtEmail.getText().toString(),
                            byteImage);
                    contactDao.insertAll(a);
                }
                else {
                    Contact a = new Contact(binding.txtName.getText().toString(),
                            binding.txtPhone.getText().toString(),
                            binding.txtEmail.getText().toString(),
                            DataConverter.convertImage2ByteArray(bmpImage));
                    contactDao.insertAll(a);
                }

                Intent intent = new Intent(AddEditorActivity.this,MainActivity.class);
                setResult(1, intent);
                finish();
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_save:
                Contact contactUpdate = contactDao.getContact(id);
                contactUpdate.setName(binding.txtName.getText().toString());
                contactUpdate.setMobile(binding.txtPhone.getText().toString());
                contactUpdate.setEmail(binding.txtEmail.getText().toString());
                contactUpdate.setImage(DataConverter.convertImage2ByteArray(bmpImage));
                contactDao.update(contactUpdate);

                intent = new Intent(AddEditorActivity.this,DetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                return true;

            case R.id.action_delete:
                Contact contactDel = contactDao.getContact(id);
                contactDao.delete((contactDel));
                intent = new Intent(AddEditorActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                if (id!=0){
                    intent = new Intent(AddEditorActivity.this,DetailActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    return true;
                }
                else {
                    this.finish();
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(id!=0){
            getSupportActionBar().setTitle("Edit");
        }
        else getSupportActionBar().setTitle("Add");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        if(!checkInDb){
            MenuItem btnSave = menu.findItem(R.id.action_save);
            btnSave.setVisible(false);

            MenuItem btnDel = menu.findItem(R.id.action_delete);
            btnDel.setVisible(false);

            this.invalidateOptionsMenu();
        }

        return true;
    }

    public void takePhoto(View view) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select image"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            try{
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bmpImage = BitmapFactory.decodeStream(inputStream);
                binding.imvContact.setImageBitmap(bmpImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
}