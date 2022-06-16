package com.aventador.bicyclerental.mainActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aventador.bicyclerental.Bicycle;
import com.aventador.bicyclerental.serverAPI.ServerAPI;
import com.aventador.bicyclerental.serverAPI.ServerRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {
    private final MainActivityRepository mainActivityRepository = new MainActivityRepository();
    public static ServerAPI serverApi = ServerRetrofit.getRetrofit().create(ServerAPI.class);
    @SuppressLint("StaticFieldLeak")
    public Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }



    MutableLiveData<List<Bicycle>> allBicycleList = new MutableLiveData<>();



    public void loginUser(String lastName, String firstName, String phoneNumber){
        mainActivityRepository.loginUser(context, lastName, firstName, phoneNumber);
    }

    public void setAllMarkers(){
        serverApi.getAllBicycleData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Bicycle>>() {
                    @Override
                    public void onSuccess(List<Bicycle> bicycles) {
                        allBicycleList.setValue(bicycles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        allBicycleList.setValue(null);
                    }
                });
    }

    public void setCityMarkers(){
        serverApi.getBicyclesByType("City")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Bicycle>>() {
                    @Override
                    public void onSuccess(List<Bicycle> bicycles) {
                        allBicycleList.setValue(bicycles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        allBicycleList.setValue(null);
                    }
                });
    }

    public void setElectroMarkers(){
        serverApi.getBicyclesByType("Electro")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Bicycle>>() {
                    @Override
                    public void onSuccess(List<Bicycle> bicycles) {
                        allBicycleList.setValue(bicycles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        allBicycleList.setValue(null);
                    }
                });
    }

    public void setKidsMarkers(){
        serverApi.getBicyclesByType("Kids")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Bicycle>>() {
                    @Override
                    public void onSuccess(List<Bicycle> bicycles) {
                        allBicycleList.setValue(bicycles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        allBicycleList.setValue(null);
                    }
                });
    }

    public BitmapDescriptor bitmapDescriptorFromVector(int vectorResId){
        return mainActivityRepository.getBitmapDescriptorFromVector(context, vectorResId);
    }

    public BitmapDescriptor setBicycleItemIcon(String type){
        return mainActivityRepository.setBicycleItemIcon(context, type);
    }

    public void setMarkersByName(String name){
        serverApi.getBicyclesByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Bicycle>>() {
                    @Override
                    public void onSuccess(List<Bicycle> bicycles) {
                        allBicycleList.setValue(bicycles);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
