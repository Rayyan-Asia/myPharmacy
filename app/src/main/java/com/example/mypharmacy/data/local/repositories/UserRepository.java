package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.User;

public interface UserRepository {
    void insertUser(User user);
    User getUser();
}
