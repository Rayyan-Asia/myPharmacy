package com.example.mypharmacy.data.local.entities;

import androidx.room.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(tableName = "prescription",
foreignKeys = @ForeignKey(entity = Doctor.class,
        parentColumns = "id",
        childColumns = "doctor_id",
        onDelete = ForeignKey.CASCADE))
@TypeConverters(Converters.class)
public class Prescription {
   @PrimaryKey
   public int id;
   public String name;
   public String description;
   public String dosage;
   public String frequency;
   @ColumnInfo(name = "start_date")
   public LocalDate startDate;
   @ColumnInfo(name = "end_date")
   public LocalDate endDate;
   @ColumnInfo(name = "doctor_id")
   public int doctorId;
}

