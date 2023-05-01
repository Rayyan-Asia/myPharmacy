package com.example.mypharmacy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;
import com.example.mypharmacy.data.local.repositories.impl.MenstruationRepositoryImpl;
import com.example.mypharmacy.ui.menstrualCal.MenstrualListAdapter;

import java.util.List;


public class MenstruationsList extends Fragment {
    private MutableLiveData<List<Menstruation>> menstruations = new MutableLiveData<>();
    public LiveData<List<Menstruation>> getData() {
        return menstruations;
    }
    private ListView menstruationListView;
    private TextView title;


    public MenstruationsList() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getMensrtuationsList();
        this.getData().observe(getViewLifecycleOwner(), new Observer<List<Menstruation>>() {
            @Override
            public void onChanged(List<Menstruation> menstruations) {
                initWidgets(view);
                if(menstruations.size()==0){
                    title.setText("We have no entries of previous cycles.");
                }
                else {
                    MenstrualListAdapter listAdapter = new MenstrualListAdapter(getContext(), R.layout.menstrual_list_item, menstruations);
                    menstruationListView.setAdapter(listAdapter);
                }
            }
        });
    }


    private void getMensrtuationsList() {
        MenstruationRepository menstruationRepository = new MenstruationRepositoryImpl(this.getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Menstruation> menstruationTemp = menstruationRepository.getMenstruations();
                menstruations.postValue(menstruationTemp);
            }
        }).start();
    }

    private void initWidgets(View view) {
        menstruationListView = view.findViewById(R.id.menstruations_listview);
        title = view.findViewById(R.id.menstruations_list_title);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menstruations_list, container, false);
    }
}