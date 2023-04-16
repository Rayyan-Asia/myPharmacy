package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointment_prescription",
        foreignKeys = {
                @ForeignKey(entity = Appointment.class,
                        parentColumns = "id",
                        childColumns = "appointment_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Prescription.class,
                        parentColumns = "id",
                        childColumns = "prescription_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class AppointmentPrescription{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "appointment_id")
    public int appointmentId;
    @ColumnInfo(name = "prescription_id")
    public int prescriptionId;
}

