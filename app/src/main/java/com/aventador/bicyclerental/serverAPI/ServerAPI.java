package com.aventador.bicyclerental.serverAPI;

import com.aventador.bicyclerental.Bicycle;
import com.aventador.bicyclerental.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerAPI {

    @GET("getAllBicycleData.php")
    Single<List<Bicycle>> getAllBicycleData();

    @GET("getBicyclesByType.php")
    Single<List<Bicycle>> getBicyclesByType(@Query("bType") String bType);

    @GET("getBicyclesByName.php")
    Single<List<Bicycle>> getBicyclesByName(@Query("name") String name);

    @GET("loginUser.php")
    Single<User> loginUser(@Query("phoneNumber") String phoneNumber, @Query("password") String password);

    @GET("getUserWallet.php")
    Single<User> getUserWallet(@Query("specID") String specID);

    @GET("updateUserWallet.php")
    Single<String> updateUserWallet(@Query("specID") String specID, @Query("wallet") String wallet);

    @GET("updateUserData.php")
    Single<User> updateUserData(@Query("specID") String specID);




    @GET("startRentalBike.php")
    Single<String> startRental(@Query("specID") int specID, @Query("whoIsRental") String whoIsRental);

    @GET("startRentalBike.php")
    Single<String> stopRental(@Query("specID") int specID, @Query("whoIsRental") String whoIsRental);

    @GET("updateBikeStatus.php")
    Single<String> updateBikeStatus(@Query("specID") int specID, @Query("status") String status);

    @GET("updateUserBikeStatus.php")
    Single<String> updateUserBikeStatus(@Query("specID") String specID, @Query("bikeName") String bikeName, @Query("bikeNumber") int bikeNumber);

    @GET("getMyBikeByID.php")
    Single<List<Bicycle>> getMyBikePosition(@Query("specID") int specID);

    @GET("userAlreadyHaveBike.php")
    Single<List<User>> userAlreadyHaveBike(@Query("specID") String specID);
}
