package com.andrei.autos_android_assessment.domain.deps.component;

import com.andrei.autos_android_assessment.domain.deps.ActivityScope;
import com.andrei.autos_android_assessment.domain.deps.module.ActivityModule;
import com.andrei.autos_android_assessment.domain.deps.module.NetworkModule;
import com.andrei.autos_android_assessment.presentation.ui.CarListActivity;

import dagger.Component;

/**
 * Created by Andrei on 25/03/2018.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, NetworkModule.class})
public interface ActivityComponent {
    void inject(CarListActivity activity);
}

