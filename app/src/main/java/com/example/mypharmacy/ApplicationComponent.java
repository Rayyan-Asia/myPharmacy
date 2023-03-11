package com.example.mypharmacy;

import com.example.mypharmacy.data.local.repositories.NameRepository;
import com.example.mypharmacy.ui.name.NameActivity;
import com.example.mypharmacy.ui.name.NameModule;
import dagger.Component;

@Component(modules = NameModule.class)
public interface ApplicationComponent {
    void inject(NameActivity nameActivity);

}
