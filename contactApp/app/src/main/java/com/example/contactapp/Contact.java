package com.example.contactapp;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Contact {
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String mobile;

    @ColumnInfo
    private String email;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Contact(String name, String mobile, String email, byte[] image) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
