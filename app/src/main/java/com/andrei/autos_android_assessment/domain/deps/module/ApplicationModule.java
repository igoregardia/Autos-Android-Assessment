package com.andrei.autos_android_assessment.domain.deps.module;

import android.app.Application;
import android.content.Context;

import com.andrei.autos_android_assessment.domain.data.DataManager;
import com.andrei.autos_android_assessment.domain.data.DataManagerImpl;
import com.andrei.autos_android_assessment.domain.data.prefrences.PreferencesHelper;
import com.andrei.autos_android_assessment.domain.data.prefrences.PreferencesImpl;
import com.andrei.autos_android_assessment.domain.deps.ApplicationContext;
import com.andrei.autos_android_assessment.domain.deps.PreferenceInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrei on 25/03/2018.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return "settings";
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(PreferencesImpl appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(DataManagerImpl dataManager) {
        return dataManager;
    }

}