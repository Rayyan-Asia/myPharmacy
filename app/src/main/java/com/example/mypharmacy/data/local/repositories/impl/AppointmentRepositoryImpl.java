package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.AppointmentDao;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;

import java.time.LocalDate;
import java.util.List;

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private AppointmentDao appointmentDao;
    private Context context;

    myPharmacyDatabase database;

    public AppointmentRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.appointmentDao = database.getAppointmentDao();
    }
    @Override
    public long insertAppointment(Appointment appointment) {
        return appointmentDao.insertAppointment(appointment);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentDao.getAppointmentById(appointmentId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        return appointmentDao.getAppointmentsByDoctorId(doctorId) ;
    }

    @Override
    public List<Appointment> getAppointmentsByPersonId(int personId) {
        return appointmentDao.getAppointmentsByPersonId(personId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentDao.getAppointmentsByDate(date);
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        appointmentDao.deleteAppointment(appointmentId);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentDao.updateAppointment(appointment);
    }
}
