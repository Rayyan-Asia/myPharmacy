package com.example.mypharmacy.ui.intro;

import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.NameRepository;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.FirebaseNameRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class IntroModule {

    private myPharmacyDatabase database;
    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. LoginRetrofitService).
    // Function parameters are the dependencies of this type.
    @Provides
    public PersonRepository providePersonRepository() {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return new PersonRepositoryImpl(database.personDao());
    }
}

