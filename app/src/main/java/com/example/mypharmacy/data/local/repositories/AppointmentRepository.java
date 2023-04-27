package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository {
    long insertAppointment(Appointment appointment);

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    List<Appointment> getAppointmentsByPersonId(int personId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByDate(LocalDate date);

    void deleteAppointment(int appointmentId);

    void updateAppointment(Appointment appointment);
}
