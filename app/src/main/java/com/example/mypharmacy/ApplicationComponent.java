package com.example.mypharmacy;

import com.example.mypharmacy.data.local.repositories.NameRepository;
import com.example.mypharmacy.ui.intro.IntroActivity;
import com.example.mypharmacy.ui.intro.IntroModule;
import com.example.mypharmacy.ui.name.NameActivity;
import com.example.mypharmacy.ui.name.NameModule;
import dagger.Component;

@Component(modules = IntroModule.class)
public interface ApplicationComponent {
    void injectIntroActivity(IntroActivity introActivity);

}
