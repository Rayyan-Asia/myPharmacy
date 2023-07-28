package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.os.AsyncTask;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;

public class RetrievePersonTask extends AsyncTask<Integer, Void, Person> {
    private Context context;
    private PersonRepository personRepository;
    private OnPersonRetrievedListener listener;

    public RetrievePersonTask(Context context, OnPersonRetrievedListener listener) {
        this.context = context;
        this.personRepository = new PersonRepositoryImpl(context);
        this.listener = listener;
    }

    @Override
    protected Person doInBackground(Integer... params) {
        int personId = params[0];
        return personRepository.getPersonWithId(personId);
    }

    @Override
    protected void onPostExecute(Person person) {
        if (listener != null) {
            listener.onPersonRetrieved(person);
        }
    }

    public interface OnPersonRetrievedListener {
        void onPersonRetrieved(Person person);
    }
}