package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

@TypeConverters(Converters.class)
@Entity(tableName = "family_member",
        foreignKeys = {
                @ForeignKey(entity = Family.class,
                        parentColumns = "id",
                        childColumns = "family_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Person.class,
                        parentColumns = "id",
                        childColumns = "person_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class FamilyMember {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "family_id")
    public int familyId;

    @ColumnInfo(name = "person_id")
    public int personId;
}
