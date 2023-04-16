package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.Data;

@Data
@Entity(tableName = "document_test",
        foreignKeys = {
                @ForeignKey(entity = Document.class, parentColumns = "id", childColumns = "document_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = LabTest.class, parentColumns = "id", childColumns = "lab_test_id", onDelete = ForeignKey.CASCADE)
        })
public class DocumentTest {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "document_id")
    public int documentId;
    @ColumnInfo(name = "lab_test_id")
    public int labTestId;
}
