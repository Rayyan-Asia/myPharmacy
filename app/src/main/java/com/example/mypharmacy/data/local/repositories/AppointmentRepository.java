package com.example.mypharmacy.data.local.repositories;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mypharmacy.data.local.entities.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository {
    void insertAppointment(Appointment appointment);

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    List<Appointment> getAppointmentsByPersonId(int personId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByDate(LocalDate date);

    void deleteAppointment(int appointmentId);

    void updateAppointment(Appointment appointment);
}
