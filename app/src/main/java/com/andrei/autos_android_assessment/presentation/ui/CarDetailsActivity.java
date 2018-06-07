package com.andrei.autos_android_assessment.presentation.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrei.autos_android_assessment.R;
import com.andrei.autos_android_assessment.domain.data.model.Car;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Andrei on 24/03/2018.
 */

public class CarDetailsActivity extends AppCompatActivity {

    public static final String CAR_EXTRAS = "car_extras";

    private Car mCar;
    private ImageView mCarImg;
    private TextView mCarMake;
    private TextView mCarModel;
    private TextView mCarPrice;
    private TextView mCarYear;
    private TextView mCarKm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        mCarImg = findViewById(R.id.car_img);
        mCarMake = findViewById(R.id.car_make);
        mCarModel = findViewById(R.id.car_model);
        mCarPrice = findViewById(R.id.car_price);
        mCarYear = findViewById(R.id.car_year);
        mCarKm = findViewById(R.id.car_km);

        setupToolbar();
        setupDataFromBundle();
        setupCar();
    }

    private void setupDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCar = bundle.getParcelable(CAR_EXTRAS);
        }
    }

    private void setupCar() {
        if (mCar != null) {
            loadCarImage(mCar.getPictureUrl(), mCarImg);
            mCarMake.setText(mCar.getMake());
            mCarModel.setText(mCar.getModel());
            mCarPrice.setText(mCar.getPrice());
            mCarYear.setText(mCar.getYear());
            mCarKm.setText(mCar.getKm());
        }
    }

    void loadCarImage(String url, ImageView imageView) {
//        if (url.isEmpty()) {
//            imageView.setImageResource(R.drawable.ic_car_placeholder_error);
//        } else {
//            Picasso.get()
//                    .load(url)
//                    .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(imageView, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            Picasso.get()
//                                    .load(url)
//                                    .placeholder(R.drawable.ic_car_placeholder)
//                                    .error(R.drawable.ic_car_placeholder_error)
//                                    .into(imageView);
//                        }
//                    });
//        }
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions()
                        .fallback(R.drawable.ic_car_placeholder_error)
                        .error(R.drawable.ic_car_placeholder_error)
                )
//                .transition(new GenericTransitionOptions<>())
                .transition(withCrossFade(200))
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
