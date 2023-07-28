package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.os.AsyncTask;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;

import java.util.List;

public class RetrieveAppointmentsByPersonIdTask extends AsyncTask<Integer, Void, List<Appointment>> {

    private Context context;
    private OnAppointmentsRetrievedListener listener;

    public RetrieveAppointmentsByPersonIdTask(Context context, OnAppointmentsRetrievedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Appointment> doInBackground(Integer... params) {
        int personId = params[0];
        AppointmentRepositoryImpl appointmentRepository = new AppointmentRepositoryImpl(context);
        return appointmentRepository.getAppointmentsByPersonId(personId);
    }

    @Override
    protected void onPostExecute(List<Appointment> appointments) {
        if (listener != null) {
            listener.onAppointmentsRetrieved(appointments);
        }
    }

    public interface OnAppointmentsRetrievedListener {
        void onAppointmentsRetrieved(List<Appointment> appointments);
    }
}
