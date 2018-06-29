package com.andrei.autos_android_assessment.domain.deps.component;

import android.content.Context;

import com.andrei.autos_android_assessment.domain.data.DataManager;
import com.andrei.autos_android_assessment.domain.deps.ApplicationContext;
import com.andrei.autos_android_assessment.domain.deps.module.ApplicationModule;
import com.andrei.autos_android_assessment.domain.deps.module.NetworkModule;
import com.andrei.autos_android_assessment.presentation.application.AndroidAssessmentApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andrei on 25/03/2018.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(AndroidAssessmentApplication app);

    @ApplicationContext
    Context context();

    DataManager getDataManager();
}