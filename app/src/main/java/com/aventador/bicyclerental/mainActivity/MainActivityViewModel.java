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
import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.User;
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

    private String WHO_IS_RENTAL;
    private int BIKE_SPEC_ID;
    private String BIKE_NAME;
    private int RENTAL_TIME;
    private double RC_COUNT;
    private double RENTAL_PRICE;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }



    MutableLiveData<List<Bicycle>> allBicycleList = new MutableLiveData<>();
    MutableLiveData<Double> coinsCount = new MutableLiveData<>();
    MutableLiveData<User> loginUserData = new MutableLiveData<>();


    MutableLiveData<Boolean> writeOffMoneySuccess = new MutableLiveData<>();
    MutableLiveData<Boolean> updateBikeStatusSuccess = new MutableLiveData<>();
    MutableLiveData<Boolean> updateUserBikeStatus = new MutableLiveData<>();
    MutableLiveData<List<Bicycle>> getMyBicyclePositionData = new MutableLiveData<>();

    MutableLiveData<List<User>> isAlreadyRentalBike = new MutableLiveData<>();

    MutableLiveData<Boolean> updateBikeStatusSTOP = new MutableLiveData<>();
    MutableLiveData<Boolean> updateUserBikeStatusSTOP = new MutableLiveData<>();
    MutableLiveData<Boolean> stopRental  = new MutableLiveData<>();

    MutableLiveData<User> updatedUserDataForStopProcess = new MutableLiveData<>();


    public void loginUser(String password, String phoneNumber){
        serverApi.loginUser(phoneNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        loginUserData.setValue(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (String.valueOf(e).equals("java.util.NoSuchElementException")) {
                            Toast.makeText(context, context.getResources().getString(R.string.error_login_or_password), Toast.LENGTH_LONG).show();
                        }
                    }
                });
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

    public void setCoins(double wallet){
        coinsCount.setValue(wallet);
    }

    public void updateCoinsCount(String userID, double alreadyHaveCount, double inputCoins){
        double newBalance = alreadyHaveCount + inputCoins;
        serverApi.updateUserWallet(userID, String.valueOf(newBalance))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        serverApi.getUserWallet(userID)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        coinsCount.setValue(user.getWallet());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public void writeOffMoney(String userID, double rc_count, double rentalPrice){
        double newBalance =  rc_count - rentalPrice;
        serverApi.updateUserWallet(userID, String.valueOf(newBalance))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        serverApi.getUserWallet(userID)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        System.out.println(">>> 1");
                                        coinsCount.setValue(user.getWallet());
                                        writeOffMoneySuccess.setValue(true);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateBikeStatus(){
        serverApi.updateBikeStatus(BIKE_SPEC_ID, "inUse")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.equals("Status changed successfully!")){
                            updateBikeStatusSuccess.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateUserBikeStatus(){
        serverApi.updateUserBikeStatus(WHO_IS_RENTAL, BIKE_NAME, BIKE_SPEC_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.equals("Status changed successfully!")){
                            updateUserBikeStatus.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    public void startRentalBike(String whoIsRental, int bikeSpecID, String bikeName, int rentalTime, double rc_count, double rentalPrice){
        serverApi.startRental(bikeSpecID, whoIsRental)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.equals("Rental success!")){
                            WHO_IS_RENTAL = whoIsRental;
                            BIKE_SPEC_ID = bikeSpecID;
                            BIKE_NAME = bikeName;
                            RENTAL_TIME = rentalTime;
                            RC_COUNT = rc_count;
                            RENTAL_PRICE = rentalPrice;
                            writeOffMoney(whoIsRental, rc_count, rentalPrice);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void getMyBicyclePosition(){
        serverApi.getMyBikePosition(BIKE_SPEC_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Bicycle>>() {
                    @Override
                    public void onSuccess(List<Bicycle> list) {
                        getMyBicyclePositionData.setValue(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void isAlreadyRentalBike(String userID){
        serverApi.userAlreadyHaveBike(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<User>>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        if (!list.isEmpty()){
                            BIKE_SPEC_ID = list.get(0).getBikeNumber();
                            isAlreadyRentalBike.setValue(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


//-- STOP RENTAL METHODS ---------------------------------------------------------------------------
    public void stopRentalBike(int bikeNumber){
        serverApi.updateBikeStatus(bikeNumber, "parking")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.equals("Status changed successfully!")){
                            updateBikeStatusSTOP.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void stopUserBikeStatus(String userSpecID){
        serverApi.updateUserBikeStatus(userSpecID, "", 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.equals("Status changed successfully!")){
                            updateUserBikeStatusSTOP.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void stopAllElseRentalProcess(int bikeID){
        System.out.println("# 3 #################### " + bikeID);
        serverApi.stopRental(bikeID, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s.equals("Rental success!")){
                            stopRental.setValue(true);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateUserInformationBeforeRunning(String data){
        serverApi.updateUserData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        updatedUserDataForStopProcess.setValue(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
