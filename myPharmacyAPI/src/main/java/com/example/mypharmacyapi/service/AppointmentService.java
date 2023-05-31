package com.example.mypharmacyapi.service;

import com.example.mypharmacyapi.dto.AppointmentDto;
import com.example.mypharmacyapi.dto.AppointmentInsertDto;

public interface AppointmentService {
    AppointmentDto insertAppointment(AppointmentInsertDto appointment);
    AppointmentDto getAppointment(long id);
}
