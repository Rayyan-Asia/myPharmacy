package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.Data;

@Data
@Entity(tableName = "appointment_drug",
        foreignKeys = { @ForeignKey(entity = Appointment.class, parentColumns = "id", childColumns = "appointment_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Drug.class, parentColumns = "id", childColumns = "drug_id", onDelete = ForeignKey.CASCADE)})
public class AppointmentDrug {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "appointment_id")
    public int appointmentId;
    @ColumnInfo(name = "drug_id")
    public int drugId;
}
