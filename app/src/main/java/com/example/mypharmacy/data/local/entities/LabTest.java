package com.example.mypharmacy.data.local.entities;

import androidx.room.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity(tableName = "lab_test",
        foreignKeys = {
                @ForeignKey(entity = Doctor.class, parentColumns = "id", childColumns = "doctor_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Person.class, parentColumns = "id", childColumns = "person_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Prescription.class, parentColumns = "id", childColumns = "person_id", onDelete = ForeignKey.CASCADE)
        })
@TypeConverters(Converters.class)
public class LabTest {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "doctor_id")
    public int doctorId;
    @ColumnInfo(name = "person_id")
    public int personId;
    @ColumnInfo(name = "date_of_test")
    public LocalDate dateOfTest;

}
