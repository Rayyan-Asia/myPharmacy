package com.example.mypharmacy.ui.medRecord.appointment;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView appointmentTitle;
    private TextView appointmentDoctor;
    private TextView appointmentSymptoms;
    private TextView appointmentDiagnosis;
    private TextView appointmentDate;

    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        appointmentTitle = itemView.findViewById(R.id.text_view_title);
        appointmentDoctor = itemView.findViewById(R.id.text_view_doctor_name);
        appointmentSymptoms = itemView.findViewById(R.id.text_view_symptoms);
        appointmentDiagnosis = itemView.findViewById(R.id.text_view_diagnosis);
        appointmentDate = itemView.findViewById(R.id.text_view_date_of_appointment);
    }

    public void bind(Appointment appointment) {
        appointmentTitle.setText(appointment.getTitle());
        appointmentSymptoms.setText(appointment.getSymptoms());
        appointmentDiagnosis.setText(appointment.getDiagnosis());
        appointmentDate.setText(appointment.getDateOfAppointment().toString());

        // Execute AsyncTask to fetch and set the doctor's name
        new FetchDoctorNameTask().execute(appointment.getDoctorId());
    }

    // AsyncTask for fetching the doctor's name
    private class FetchDoctorNameTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            int doctorId = params[0];
            DoctorRepository doctorRepository = new DoctorRepositoryImpl(context);
            return doctorRepository.getDoctor(doctorId).getName();
        }

        @Override
        protected void onPostExecute(String doctorName) {
            appointmentDoctor.setText(doctorName);
        }
    }
}
