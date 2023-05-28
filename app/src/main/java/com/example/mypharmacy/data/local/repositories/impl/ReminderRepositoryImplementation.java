package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.ReminderDao;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.ReminderRepository;

import java.time.LocalDate;
import java.util.List;

public class ReminderRepositoryImplementation implements ReminderRepository {
    private ReminderDao reminderDao;
    private Context context;

    myPharmacyDatabase database;

    public ReminderRepositoryImplementation(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.reminderDao = database.getReminderDao();
    }

    @Override
    public void insertReminder(Reminder reminder) {
        reminderDao.insertReminder(reminder);
    }

    @Override
    public List<Reminder> getActiveReminders() {
        return reminderDao.getActiveReminders();
    }

    @Override
    public void deleteReminder(int id) {
        reminderDao.deleteReminder(id);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        reminderDao.updateReminder(reminder);
    }
}
