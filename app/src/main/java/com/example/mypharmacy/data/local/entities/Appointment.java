package com.example.mypharmacy.data.local.entities;

import java.time.LocalDate;

public class Appointment {
    public int id;
    public int doctorId;
    public int personId;
    public int prescriptionId;
    public String symptoms;
    public String diagnosis;
    public LocalDate dateOfAppointment;

}
