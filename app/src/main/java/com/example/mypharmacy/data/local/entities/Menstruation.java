package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import lombok.Data;

import java.time.LocalDate;

@Data
@Entity(tableName = "menstruation")
@TypeConverters(Converters.class)
public class Menstruation {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "start_date")
    public LocalDate startDate;
    @ColumnInfo(name = "end_date")
    public LocalDate endDate;

}
