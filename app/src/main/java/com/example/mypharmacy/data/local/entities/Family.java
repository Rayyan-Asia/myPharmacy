package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "family")
public class Family {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    @ColumnInfo(name = "profile_pic_path")
    public String profilePicPath;

}
