package com.example.mypharmacy.data.local.entities;

import com.example.mypharmacy.data.local.entities.Person;
import lombok.Data;

@Data
public class FamilyMemeber extends Person {
    public String role;
    public String email;
}
