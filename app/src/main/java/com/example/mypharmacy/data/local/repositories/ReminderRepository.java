package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Reminder;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRepository {
    void insertReminder(Reminder reminder);

    List<Reminder> getActiveReminders();

    void deleteReminder(int id);

    void updateReminder(Reminder reminder);
}
