package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.Data;

@Data
@Entity(tableName = "prescription_drug",
        foreignKeys = {
                @ForeignKey(entity = Prescription.class, parentColumns = "id", childColumns = "prescription_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Drug.class, parentColumns = "id", childColumns = "drug_id", onDelete = ForeignKey.CASCADE)
        })
public class PrescriptionDrug {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "drug_id")
    public int drugId;
    @ColumnInfo(name = "prescription_id")
    public int prescriptionId;
}
