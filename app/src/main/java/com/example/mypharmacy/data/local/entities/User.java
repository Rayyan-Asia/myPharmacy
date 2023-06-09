package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import androidx.annotation.NonNull;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(tableName = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @PrimaryKey
    @NonNull
    public String id = UUID.randomUUID().toString();
    public String email;
    @ColumnInfo(name = "person_id")
    public int personId;
}
