package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity(tableName = "person")
@TypeConverters(Converters.class)
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    public String gender;
    public String address;
    public float weight;
    public float height;
    @ColumnInfo(name = "phone_number")
    public int phoneNumber;
    @ColumnInfo(name = "birth_date")
    public LocalDate birthDate;
    @ColumnInfo(name = "marital_status")
    public String maritalStatus;
    @ColumnInfo(name = "blood_type")
    public String bloodType;
    @ColumnInfo(name = "profile_pic_path")
    public String profilePicPath;
}
