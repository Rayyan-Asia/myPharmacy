package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(tableName = "drug")
@TypeConverters(Converters.class)
public class Drug {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public String manufacturer;
    public String category; //category of the drug (e.g. antibiotic, analgesic, antihypertensive)
    public String type; //type of the drug (e.g. tablet, capsule, syrup, injection, etc.)
    @ColumnInfo(name = "expiry_date")
    public LocalDate expiryDate;

    // toString method to display the name of the drug in the spinner
    @Override
    public String toString() {
        return name;
    }
}
