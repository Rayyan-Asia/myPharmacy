package com.example.mypharmacy.data.local.entities;

import androidx.room.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity(tableName = "appointment",
        foreignKeys = {
                @ForeignKey(entity = Doctor.class, parentColumns = "id", childColumns = "doctor_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Person.class, parentColumns = "id", childColumns = "person_id", onDelete = ForeignKey.CASCADE),
        })
@TypeConverters(Converters.class)
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "doctor_id")
    public int doctorId;
    @ColumnInfo(name = "person_id")
    public int personId;
    public String symptoms;
    public String diagnosis;
    @ColumnInfo(name = "date_of_appointment")
    public LocalDate dateOfAppointment;

}
