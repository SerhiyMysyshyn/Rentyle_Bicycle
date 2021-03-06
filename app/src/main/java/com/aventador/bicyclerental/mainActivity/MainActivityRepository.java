package com.aventador.bicyclerental.mainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.aventador.bicyclerental.Bicycle;
import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.serverAPI.ServerAPI;
import com.aventador.bicyclerental.serverAPI.ServerRetrofit;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MainActivityRepository {

    public BitmapDescriptor getBitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public BitmapDescriptor setBicycleItemIcon(Context context ,String type){
        int resId = 0;
        switch (type){
            case "City":
                resId = R.drawable.ic_baseline_city_24;
                break;
            case "Electro":
                resId = R.drawable.ic_baseline_electric_bike_24;
                break;
            case "Kids":
                resId = R.drawable.ic_baseline_kids_24;
                break;
        }
        return getBitmapDescriptorFromVector(context, resId);
    }

}
