package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Data;

@Data
@Entity(tableName = "doctor")
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String specialty;
    public int phone;
    public String email;
    @ColumnInfo(name = "clinical_address")
    public String clinicalAddress;


}
