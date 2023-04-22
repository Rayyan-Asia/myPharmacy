package com.example.mypharmacy.data.local.daos;

import androidx.room.*;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Converters;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters(Converters.class)
public interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAppointment(Appointment appointment);

    @Query("SELECT * FROM appointment WHERE id = :appointmentId")
    Appointment getAppointmentById(int appointmentId);

    @Query("SELECT * FROM appointment WHERE doctor_id = :doctorId")
    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    @Query("SELECT * FROM appointment WHERE person_id = :personId")
    List<Appointment> getAppointmentsByPersonId(int personId);

    @Query("SELECT * FROM appointment WHERE date_of_appointment = :date")
    List<Appointment> getAppointmentsByDate(LocalDate date);

    @Query("SELECT * FROM appointment")
    List<Appointment> getAllAppointments();

    @Query("DELETE FROM appointment WHERE id = :appointmentId")
    void deleteAppointment(int appointmentId);

    @Update
    void updateAppointment(Appointment appointment);
}
