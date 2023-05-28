package com.example.mypharmacy.data.local.daos;

import androidx.room.*;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.entities.Converters;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters(Converters.class)
public interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminder(Reminder reminder);

    // get active reminders
    @Query("SELECT * FROM reminder ")
    List<Reminder> getActiveReminders();

    @Query("DELETE FROM reminder WHERE id = :id")
    void deleteReminder(int id);

    @Update
    void updateReminder(Reminder reminder);


}
