package com.aventador.bicyclerental.startRentalBikeFragment;

import static com.aventador.bicyclerental.sharedPreferences.SharedData.getRentalCoinsCount;
import static com.aventador.bicyclerental.sharedPreferences.SharedData.saveRentalCoinsCount;

import android.content.Context;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class StartRentalBikeFragmentRepository {
    private final double CITY_COEFFICIENT = 1.0;
    private final double ELECTRO_COEFFICIENT = 2.5;
    private final double KIDS_COEFFICIENT = 0.5;

    private double generatePriceList(String bName, String bType, int rTime){
        double resultPrice = 0.0;
        switch (bType){
            case "City":
                switch (bName){
                    case "Orbea Capre":
                        resultPrice = rTime * CITY_COEFFICIENT + 5;
                        break;
                    case "Retro Black":
                        resultPrice = rTime * CITY_COEFFICIENT + 2.5;
                        break;
                }
                break;
            case "Electro":
                switch (bName){
                    case "Haibike Sduro":
                        resultPrice = rTime * ELECTRO_COEFFICIENT + 25;
                        break;
                    case "Orbea Keram":
                        resultPrice = rTime * ELECTRO_COEFFICIENT + 20;
                        break;
                }
                break;
            case "Kids":
                switch (bName){
                    case "Ghost Powerkid":
                        resultPrice = rTime * KIDS_COEFFICIENT + 1;
                        break;
                    case "Ghost Kato":
                        resultPrice = rTime * KIDS_COEFFICIENT + 1;
                        break;
                }
                break;
        }
        return resultPrice;
    }

    Single<Double> generateRentalPrice(String bName, String bType, int rTime){
        Single<Double> resultPrice = Single.fromCallable(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                    return generatePriceList(bName, bType, rTime);
                }
        }).subscribeOn(Schedulers.io());
        return resultPrice;
    }


    Single<Boolean> checkIfUserCanRental(double availableMoney, String bName, String bType, int rTime){
        Single<Boolean> result = Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (availableMoney >= generatePriceList(bName, bType, rTime)){
                    return true;
                }else {
                    return false;
                }
            }
        }).subscribeOn(Schedulers.io());
        return result;
    }
}
