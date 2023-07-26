package com.example.mypharmacy.ui.medRecord.drug;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.DrugAliasRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;

import java.util.List;

public class DrugAliasAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private Drug drug;

    public DrugAliasAsyncTask(Context context, Drug drug) {
        this.context = context;
        this.drug = drug;
    }

    @Override
    protected String doInBackground(Void... voids) {
        DrugAliasRepositoryImpl repo = new DrugAliasRepositoryImpl(context);
        DrugRepository drugRepo = new DrugRepositoryImpl(context);
        List<Integer> aliasIds = repo.getAliasDrugIds(drug.getId());
        List<Drug> drugs = drugRepo.getAllDrugs();
        StringBuilder stringBuilder = new StringBuilder();
        for (Drug d : drugs) {
            if (aliasIds.contains(d.getId())) {
                stringBuilder.append(d.getName()).append("\n");
            }
        }
        String message = stringBuilder.toString();
        if (message.isEmpty()) {
            message = "NO aliases were found";
        }
        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setTitle("Aliases");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
