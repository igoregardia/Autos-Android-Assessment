package com.andrei.autos_android_assessment.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrei.autos_android_assessment.R;
import com.andrei.autos_android_assessment.domain.data.model.Car;
import com.andrei.autos_android_assessment.presentation.ui.callbacks.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 24/03/2018.
 */

public class CarListActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView mCarList;

    // TODO: 24/03/2018 remove this list
    List<Car> testList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_car_list);
        setSupportActionBar(toolbar);

        mCarList = findViewById(R.id.car_list_rw);


        // TODO: 24/03/2018 remove this
        setupTestList();

        CarListAdapter adapter = new CarListAdapter(testList, this);
        mCarList.setAdapter(adapter);
        mCarList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(getResources().getDrawable(R.drawable.list_item_divider));
        mCarList.addItemDecoration(itemDecor);


    }

    // TODO: 24/03/2018 remove this
    private void setupTestList() {
        testList = new ArrayList<>();

        testList.add(new Car("Mercedes-Benz", "A 180", "13600.00", "2017", "17000", ""));
        testList.add(new Car("Audi", "A1 1.6 TDI Sportback S tronic", "13600.00", "2017", "17000", ""));
        testList.add(new Car("Opel", "Corsa 1.3 CDTI Enjoy Start/Stop", "245000", "2017", "17000", ""));
        testList.add(new Car("BMW", "530 Serie 5 (F10/F11) xDrive 249CV Touring Msport", "13600.00", "2017", "17000", ""));
    }

    @Override
    public void onItemClick(Car car) {
        Intent intent = new Intent(this, CarDetailsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }


    private class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarHolder> {

        List<Car> mCarList;
        OnItemClickListener mListener;

        CarListAdapter(List<Car> mCarList, OnItemClickListener listener) {
            this.mCarList = mCarList;
            this.mListener = listener;
        }

        @Override
        public CarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CarHolder(
                    LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.car_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(CarHolder holder, int position) {
            holder.bind(mCarList.get(position), mListener);
        }

        @Override
        public int getItemCount() {
            if (mCarList != null) {
                return mCarList.size();
            }
            return 0;
        }

// TODO: 24/03/2018 remove this
//        void swapCars(List<String> newList) {
//            mCarList = newList;
//            notifyDataSetChanged();
//        }

        class CarHolder extends RecyclerView.ViewHolder {
            private ImageView mThumbnail;
            private TextView mMake;
            private TextView mModel;
            private TextView mPrice;

            CarHolder(View itemView) {
                super(itemView);
                mThumbnail = itemView.findViewById(R.id.car_img);
                mMake = itemView.findViewById(R.id.car_make);
                mModel = itemView.findViewById(R.id.car_model);
                mPrice = itemView.findViewById(R.id.car_price);
            }

            void bind(final Car car, final OnItemClickListener listener) {
                // TODO: 24/03/2018 load image from the internet
                mThumbnail.setImageDrawable(ContextCompat.getDrawable(mThumbnail.getContext(), R.drawable.delete_this_img));
//                mThumbnail.invalidate();
                mMake.setText(car.getMake());
                mModel.setText(car.getModel());
                mPrice.setText(car.getPrice());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(car);
                    }
                });
            }
        }
    }
}
