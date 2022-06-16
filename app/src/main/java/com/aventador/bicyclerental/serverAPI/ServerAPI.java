package com.aventador.bicyclerental.serverAPI;

import com.aventador.bicyclerental.Bicycle;

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
}
