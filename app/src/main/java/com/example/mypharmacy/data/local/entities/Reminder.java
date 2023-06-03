package com.example.mypharmacy.data.local.entities;

import androidx.room.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity(tableName = "reminder", foreignKeys = {@ForeignKey(entity = Drug.class, parentColumns = "id", childColumns = "drug_id", onDelete = androidx.room.ForeignKey.CASCADE)})
@TypeConverters(Converters.class)
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String dosage;
    public List<Timestamp> times;
    @ColumnInfo(name = "drug_id")
    public int drugId;
}
