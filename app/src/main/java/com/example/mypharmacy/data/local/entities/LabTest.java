package com.example.mypharmacy.data.local.entities;

import androidx.room.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity(tableName = "lab_test",
        foreignKeys = {
                @ForeignKey(entity = Person.class, parentColumns = "id", childColumns = "person_id", onDelete = ForeignKey.CASCADE),
        })
@TypeConverters(Converters.class)
public class LabTest {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "person_id")
    public int personId;
    @ColumnInfo(name = "date_of_test")
    public LocalDate dateOfTest;
    @ColumnInfo(name = "test_name")
    public String testName;
    @ColumnInfo(name = "file_path")
    public String path;
}
