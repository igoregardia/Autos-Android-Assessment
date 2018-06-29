package com.andrei.autos_android_assessment.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andrei.autos_android_assessment.R;
import com.andrei.autos_android_assessment.domain.data.DataManager;
import com.andrei.autos_android_assessment.domain.data.model.Car;
import com.andrei.autos_android_assessment.domain.data.prefrences.PreferencesHelper;
import com.andrei.autos_android_assessment.presentation.presenters.CarListPresenter;
import com.andrei.autos_android_assessment.presentation.ui.callbacks.OnItemClickListener;
import com.andrei.autos_android_assessment.presentation.ui.generics.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Andrei on 24/03/2018.
 */

public class CarListActivity extends MvpActivity<CarListPresenter> implements CarListView, OnItemClickListener {

    @Inject
    DataManager mDataManager;

    private List<Car> mCars;
    private RecyclerView mCarList;
    private int mLayout;

    public static final String CARS_CACHE = "cars_cache";
    public static final String LAYOUT_CACHE = "layout_cache";


    private DividerItemDecoration mItemDecor;
    private CarListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        getActivityComp().inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_car_list);
        setSupportActionBar(toolbar);

        mCarList = findViewById(R.id.car_list_rw);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(CARS_CACHE)) {
            mPresenter.loadCars();
            mPresenter.loadLayout();
        } else {
            mCars = savedInstanceState.getParcelableArrayList(CARS_CACHE);
            mLayout = savedInstanceState.getInt(LAYOUT_CACHE);
            setupList();
        }
    }

    @Override
    public void onLoadCars(List<Car> cars) {
        mCars = cars;
        setupList();
    }

    @Override
    public void onErrorLoadingCars(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadLayout(int layout) {
        mLayout = layout;
    }

    @Override
    public void onLayoutChanged(int layout) {
        mLayout = layout;
        setupListLayout();
    }

    private void setupList() {
        mListAdapter = new CarListAdapter(this.mCars, this);

        mItemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mItemDecor.setDrawable(getResources().getDrawable(R.drawable.list_item_divider));

        setupListLayout();
    }

    private void setupListLayout() {
        mListAdapter.setLayout(mLayout);
        if (mLayout == PreferencesHelper.CAR_LIST_LAYOUT) {
            mCarList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mCarList.addItemDecoration(mItemDecor);
        } else {
            mCarList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mCarList.removeItemDecoration(mItemDecor);
        }
        mCarList.setAdapter(mListAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCars != null) {
            outState.putParcelableArrayList(CARS_CACHE, (ArrayList<Car>) mCars);
            outState.putInt(LAYOUT_CACHE, mLayout);
        }
    }

    @NonNull
    @Override
    public CarListPresenter createPresenter() {
        return new CarListPresenter(mDataManager);
    }

    @Override
    public void onItemClick(Car car) {
        Intent intent = new Intent(this, CarDetailsActivity.class);
        intent.putExtra(CarDetailsActivity.CAR_EXTRAS, car);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                if (mLayout == PreferencesHelper.CAR_LIST_LAYOUT)
                    mPresenter.changeLayout(PreferencesHelper.CAR_GRID_LAYOUT);
                else
                    mPresenter.changeLayout(PreferencesHelper.CAR_LIST_LAYOUT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
