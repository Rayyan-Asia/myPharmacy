package com.example.mypharmacy.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.Data;
@Data
@Entity(tableName = "drug_alias",
        foreignKeys = {
                @ForeignKey(entity = Drug.class,
                        parentColumns = "id",
                        childColumns = "drug_id_1",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Drug.class,
                        parentColumns = "id",
                        childColumns = "drug_id_2",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index("drug_id_1"),
                @Index("drug_id_2")
        }
)
public class DrugAlias {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "drug_id_1")
    public int drugId1; // First drug ID

    @ColumnInfo(name = "drug_id_2")
    public int drugId2; // Second drug ID

}
