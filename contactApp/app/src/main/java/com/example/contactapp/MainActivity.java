package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Contact> contacts;
    private ContactsAdapter contactsAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditorActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        contacts = new ArrayList<>();
        contacts.addAll(contactDao.getAllContact());
        contactsAdapter = new ContactsAdapter(contacts);
        binding.rvContacts.setAdapter(contactsAdapter);
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1 || resultCode == 2 || resultCode == 3) {
            contacts = new ArrayList<>();
            contacts.addAll(contactDao.getAllContact());
            contactsAdapter = new ContactsAdapter(contacts);
            binding.rvContacts.setAdapter(contactsAdapter);
            binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Danh Bạ");
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.ic_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập tên vào nà");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contacts = new ArrayList<>();
                contacts.addAll(contactDao.getContactContainWord(newText));
                contactsAdapter = new ContactsAdapter(contacts);
                binding.rvContacts.setAdapter(contactsAdapter);
                binding.rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



}