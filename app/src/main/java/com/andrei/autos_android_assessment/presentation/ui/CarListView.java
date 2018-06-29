package com.andrei.autos_android_assessment.presentation.ui;

import com.andrei.autos_android_assessment.domain.data.model.Car;
import com.andrei.autos_android_assessment.presentation.ui.generics.MvpView;

import java.util.List;

/**
 * Created by Andrei on 25/03/2018.
 */

public interface CarListView extends MvpView {
    void onLoadCars(List<Car> carList);
    void onErrorLoadingCars(String message);

    void onLoadLayout(int layout);
    void onLayoutChanged(int layout);
}
