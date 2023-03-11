package com.example.mypharmacy.ui.name;

import com.example.mypharmacy.data.local.repositories.NameRepository;
import com.example.mypharmacy.data.local.repositories.impl.FirebaseNameRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class NameModule {

    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. LoginRetrofitService).
    // Function parameters are the dependencies of this type.
    @Provides
    public NameRepository provideNameRepository() {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return new FirebaseNameRepository();
    }
}

