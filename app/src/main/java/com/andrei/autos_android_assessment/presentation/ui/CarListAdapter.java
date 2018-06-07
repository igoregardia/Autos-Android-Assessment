package com.andrei.autos_android_assessment.presentation.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrei.autos_android_assessment.R;
import com.andrei.autos_android_assessment.domain.data.model.Car;
import com.andrei.autos_android_assessment.domain.data.prefrences.PreferencesHelper;
import com.andrei.autos_android_assessment.presentation.ui.callbacks.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Andrei on 25/03/2018.
 */

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarHolder> {

    private List<Car> mCarList;
    private OnItemClickListener mListener;
    private int mLayout;


    CarListAdapter(List<Car> mCarList, OnItemClickListener listener) {
        this.mCarList = mCarList;
        this.mListener = listener;
    }

    public void setLayout(int layout) {
        this.mLayout = layout;
    }

    @Override
    public CarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(mLayout == PreferencesHelper.CAR_LIST_LAYOUT ? R.layout.car_list_item : R.layout.car_grid_item, parent, false));
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
            loadCarImage(car.getPictureUrl(), mThumbnail);
            mMake.setText(car.getMake());
            mModel.setText(car.getModel());
            mPrice.setText(car.getPrice());

            itemView.setOnClickListener(v -> listener.onItemClick(car));
        }

        void loadCarImage(String url, ImageView imageView) {
//            if (url.isEmpty()) {
//                imageView.setImageResource(R.drawable.ic_car_placeholder_error);
//            } else {
//                Glide.with(imageView)
//                        .load(url)
////                        .error(R.drawable.ic_car_placeholder_error)
//                        .into(imageView);
//            }

//            DrawableTransitionOptions

            Glide.with(imageView)
                    .load(url)
                    .apply(new RequestOptions()
                            .fallback(R.drawable.ic_car_placeholder_error)
                            .error(R.drawable.ic_car_placeholder_error)
                    )
//                    .transition(new GenericTransitionOptions<>())
                    .transition(withCrossFade(200))
                    .thumbnail(0.1f)
                    .into(imageView);
        }
    }
}
