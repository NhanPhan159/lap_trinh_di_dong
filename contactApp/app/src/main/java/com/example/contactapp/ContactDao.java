package com.example.contactapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAllContact();

    @Insert
    void insertAll(Contact... contact);

    @Delete
    void delete(Contact... contact);

    @Update
    void update(Contact contact);

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getContact(int id);

    @Query("SELECT * FROM Contact WHERE name LIKE '%'||:word||'%'")
    List<Contact> getContactContainWord(String word);
}
