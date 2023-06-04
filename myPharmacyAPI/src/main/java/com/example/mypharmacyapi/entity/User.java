package com.example.mypharmacyapi.entity;

import java.util.UUID;

public class User {
    private String id = UUID.randomUUID().toString();
    private String email;
}
