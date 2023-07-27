package com.example.mypharmacy.ui.medReminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.repositories.DrugConflictRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.DrugConflictRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;

import java.util.List;

public class DrugConflictAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private Reminder reminder;

    public DrugConflictAsyncTask(Context context, Reminder reminder) {
        this.context = context;
        this.reminder = reminder;
    }

    @Override
    protected String doInBackground(Void... voids) {
        DrugConflictRepository drugConflictRepo = new DrugConflictRepositoryImpl(context);
        DrugRepository drugRepo = new DrugRepositoryImpl(context);

        List<Integer> conflictingDrugIds = drugConflictRepo.getConflictingDrugIds(reminder.getDrugId());
        List<Drug> drugs = drugRepo.getAllDrugs();

        StringBuilder stringBuilder = new StringBuilder();
        for (Drug d : drugs) {
            if (conflictingDrugIds.contains(d.getId())) {
                stringBuilder.append(d.getName()).append("\n");
            }
        }

        String message = stringBuilder.toString();
        if (message.isEmpty()) {
            message = "No conflicts were found";
        }

        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setTitle("Conflicting Drugs");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
