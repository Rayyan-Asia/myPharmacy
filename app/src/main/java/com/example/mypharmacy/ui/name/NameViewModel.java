package com.example.mypharmacy.ui.name;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mypharmacy.data.local.repositories.NameRepository;

public class NameViewModel extends AndroidViewModel {

    private final NameRepository nameRepository;

    private MutableLiveData<String> nameLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> countLiveData = new MutableLiveData<>();

    public NameViewModel(Application application, NameRepository nameRepository) {
        super(application);
        this.nameRepository = nameRepository;
        countLiveData.setValue(0);
    }

    public LiveData<String> getNameLiveData() {
        return nameLiveData;
    }

    public LiveData<Integer> getCountLiveData() {
        return countLiveData;
    }

    public void updateName(String name) {
        nameRepository.saveName(name);
        nameLiveData.setValue(name);
        countLiveData.setValue(countLiveData.getValue() + 1);
    }

}

