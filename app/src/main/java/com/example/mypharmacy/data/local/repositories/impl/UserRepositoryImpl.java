package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.UserDao;
import com.example.mypharmacy.data.local.entities.User;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private UserDao userDao;
    private final Context context;

    myPharmacyDatabase database;

    public UserRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.userDao = database.getUserDao();
    }

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public User getUser() {
        return userDao.getUser();
    }
}
