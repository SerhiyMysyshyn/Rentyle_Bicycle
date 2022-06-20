package com.aventador.bicyclerental.startRentalBikeFragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class StartRentalBikeFragmentViewModel extends AndroidViewModel {
    private final StartRentalBikeFragmentRepository selectBicycleFragmentRepository = new StartRentalBikeFragmentRepository();
    @SuppressLint("StaticFieldLeak")
    public Context context;


    public StartRentalBikeFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    MutableLiveData<Double> rentalPrice = new MutableLiveData<>();
    MutableLiveData<Boolean> canRental = new MutableLiveData<>();


    public void setRentalPrice(String bName, String bType, int rTime){
        selectBicycleFragmentRepository.generateRentalPrice(bName, bType, rTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Double>() {
                    @Override
                    public void onSuccess(Double aDouble) {
                        rentalPrice.setValue(aDouble);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rentalPrice.setValue(-1.0);
                    }
                });
    }

    public void checkIfUserCanRental(double availableMoney, String bName, String bType, int rTime){
        selectBicycleFragmentRepository.checkIfUserCanRental(availableMoney, bName, bType, rTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        canRental.setValue(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });



    }
}
