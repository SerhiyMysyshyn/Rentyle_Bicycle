package com.aventador.bicyclerental.mainActivity;

import static com.aventador.bicyclerental.sharedPreferences.SharedData.RENTAL_COIN_RATE;
import static com.aventador.bicyclerental.sharedPreferences.SharedData.getMapMode;
import static com.aventador.bicyclerental.sharedPreferences.SharedData.getRentalCoinsCount;
import static com.aventador.bicyclerental.sharedPreferences.SharedData.saveMapMode;
import static com.aventador.bicyclerental.sharedPreferences.SharedData.saveRentalCoinsCount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aventador.bicyclerental.Bicycle;
import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.customDialogs.CustomAddCoinsDialog;
import com.aventador.bicyclerental.startRentalBikeFragment.StartRentalBikeFragment;
import com.aventador.bicyclerental.customDialogs.CustomLoginDialog;
import com.aventador.bicyclerental.customDialogs.CustomSortDialog;
import com.aventador.bicyclerental.selectBicycleFragment.SelectBicycleFragment;
import com.aventador.bicyclerental.sharedPreferences.SharedData;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MainActivityViewModel mainActivityViewModel;

    private FloatingActionButton selectBicycle, login, mapMode, myLocation, sort;
    private GoogleMap mMap;
    private SharedPreferences preferencesData;
    private String map1, map2, map3, map4;
    private LinearLayout progressBar;
    private TextView addRentalCoins, rentalCoinsCount;

    private String SELECTED_ITEM_FROM_LIST = "";

    private List<Bicycle> markersList = new ArrayList<>();

    private Marker myMarker;

    private double RC_COUNT = 0.0;

    private String selectedMarkerTitle;
    private String selectedMarkerName;
    private String selectedMarkerType;
    private int selectedMarkerBicycleNumber;
    private String selectedMarkerStatus;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location deviceLastLocation;
    private static final int DEFAULT_ZOOM = 10;
    private final LatLng defaultLocation = new LatLng(48.9226, 24.7103);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPermissionGranted();
        initStrings();
        initViewComponents();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mainActivityViewModel.setAllMarkers();

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortDialog();
            }
        });

        mapMode.setOnClickListener(view -> showMapModeDialog());

        myLocation.setOnClickListener(view -> isLocationAvailable());

        selectBicycle.setOnClickListener(v -> {
            SelectBicycleFragment selectBicycleFragment = new SelectBicycleFragment(MainActivity.this);
            selectBicycleFragment.setSelectedItem(SELECTED_ITEM_FROM_LIST);
            selectBicycleFragment.show(getSupportFragmentManager(), "TAG");
            selectBicycleFragment.setViewForDisablePossibility(selectBicycleFragment);
        });

        login.setOnClickListener(v -> showLoginDialog());

        addRentalCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });

        mainActivityViewModel.setCoins();
        mainActivityViewModel.coinsCount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                RC_COUNT = aDouble;
                rentalCoinsCount.setText(String.valueOf(RC_COUNT));
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        });

    }



//##################################################################################################
//##################################################################################################
//##################################################################################################



    public void loadMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(Integer.parseInt(getMapMode()));
        LatLng IvanoFrankivsk = new LatLng(48.9226, 24.7103);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(IvanoFrankivsk));
        mMap.setMinZoomPreference(13);

        mainActivityViewModel.allBicycleList.observe(this, bicycles -> {
            markersList = bicycles;
            mMap.clear();
            if (bicycles != null){
                for (Bicycle bicycle:bicycles){
                    if (bicycle.getStatus().equals("parking")){
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(bicycle.getCoordX(), bicycle.getCoordY()))
                                .title(bicycle.getName() + " №" + bicycle.getSpecID())
                                .icon(mainActivityViewModel.setBicycleItemIcon(bicycle.getbType())));
                    }
                }
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }else{
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        });

        mMap.setOnMarkerClickListener(marker -> {
            progressBar.setVisibility(View.VISIBLE);

            selectedMarkerTitle = marker.getTitle();

            new Thread(() -> {
                for (Bicycle bicycle : markersList) {
                    if (selectedMarkerTitle.equals(bicycle.getName() + " №" + bicycle.getSpecID())) {
                        selectedMarkerName = bicycle.getName();
                        selectedMarkerType = bicycle.getbType();
                        selectedMarkerBicycleNumber = bicycle.getSpecID();
                        selectedMarkerStatus = bicycle.getStatus();
                    }
                }

                StartRentalBikeFragment startRentalBikeFragment = new StartRentalBikeFragment(MainActivity.this);
                startRentalBikeFragment.setViewForDisablePossibility(startRentalBikeFragment);
                startRentalBikeFragment.setCountMoney_DATA(RC_COUNT);
                startRentalBikeFragment.setName_DATA(selectedMarkerName);
                startRentalBikeFragment.setType_DATA(selectedMarkerType);
                startRentalBikeFragment.setSpecID_DATA(selectedMarkerBicycleNumber);
                startRentalBikeFragment.setStatus_DATA(selectedMarkerStatus);
                startRentalBikeFragment.show(getSupportFragmentManager(), "TAG2");

                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }).start();
            return false;
        });
    }



//##################################################################################################
//##################################################################################################
//##################################################################################################

    public void notifyActivityToChangeData(String name){
        SELECTED_ITEM_FROM_LIST = name;
        mainActivityViewModel.setMarkersByName(name);
    }

    public void notifyActivityToChangeData(double rentalPrice){
        mainActivityViewModel.writeOffMoney(rentalPrice);
    }

//##################################################################################################
//##################################################################################################
//##################################################################################################



    private void isLocationAvailable() {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);

        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        settingsBuilder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(settingsBuilder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    myLocation.setImageResource(R.drawable.ic_gps_notfixed);
                    getDeviceLocation();
                } catch (ApiException ex) {
                    myLocation.setImageResource(R.drawable.ic_gps_off);
                    switch (ex.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) ex;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 222);
                            } catch (IntentSender.SendIntentException e) {}
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
    }

    private void getDeviceLocation() {
        progressBar.setVisibility(View.VISIBLE);
        Task<Location> locationResult = null;
        try {
            if (isPermissionGranted()) {
                while (locationResult == null){
                    System.out.println("[ SYSTEM ] Try to get user location");
                    locationResult = fusedLocationProviderClient.getLastLocation();
                    locationResult.addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            deviceLastLocation = task.getResult();
                            System.out.println("[ SYSTEM ] Last saved location (" + deviceLastLocation + ")");
                            if (deviceLastLocation != null) {
                                myLocation.setImageResource(R.drawable.ic_gps_fixed);

                                myMarker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(deviceLastLocation.getLatitude(),
                                                deviceLastLocation.getLongitude()))
                                        .title("My position")
                                        .icon(mainActivityViewModel.bitmapDescriptorFromVector(R.drawable.ic_baseline_boy_24)));

                                mMap.moveCamera(CameraUpdateFactory.
                                        newLatLngZoom(new LatLng(deviceLastLocation.getLatitude(),
                                                deviceLastLocation.getLongitude()), DEFAULT_ZOOM));
                                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                            }else {
                                myLocation.setImageResource(R.drawable.ic_gps_notfixed);
                            }
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                        }
                    });
                }
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }



//##################################################################################################
//##################################################################################################
//##################################################################################################

    public void showMapModeDialog(){
        final String[] array = {map1, map2, map3, map4};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setItems(array, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    saveMapMode(String.valueOf(GoogleMap.MAP_TYPE_NORMAL));
                    break;
                case 1:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    saveMapMode(String.valueOf(GoogleMap.MAP_TYPE_HYBRID));
                    break;
                case 2:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    saveMapMode(String.valueOf(GoogleMap.MAP_TYPE_SATELLITE));
                    break;
                case 3:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    saveMapMode(String.valueOf(GoogleMap.MAP_TYPE_TERRAIN));
                    break;
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showLoginDialog() {
        CustomLoginDialog dialog = new CustomLoginDialog(MainActivity.this);
        View.OnClickListener button_ok_listener = v -> {
            dialog.cancel();
            mainActivityViewModel.loginUser(dialog.getUserLastname(), dialog.getUserName(), dialog.getUserPhoneNumber());
            System.out.println("[ SYSTEM ]: " + dialog.getUserLastname() + " / " + dialog.getUserName() + " / " + dialog.getUserPhoneNumber());
        };
        dialog.setListener(button_ok_listener);
        dialog.show();
    }

    private void showSortDialog() {
        SELECTED_ITEM_FROM_LIST = "";
        CustomSortDialog sortDialog = new CustomSortDialog(MainActivity.this);
        View.OnClickListener button_show_all_listener = v -> {
            sortDialog.cancel();
            mainActivityViewModel.setAllMarkers();
        };

        View.OnClickListener button_show_city_listener = v -> {
            sortDialog.cancel();
            mainActivityViewModel.setCityMarkers();
        };

        View.OnClickListener button_show_electro_listener = v -> {
            sortDialog.cancel();
            mainActivityViewModel.setElectroMarkers();
        };

        View.OnClickListener button_show_kids_listener = v -> {
            sortDialog.cancel();
            mainActivityViewModel.setKidsMarkers();
        };

        sortDialog.setShowAllMarkersListener(button_show_all_listener);
        sortDialog.setShowCityMarkersListener(button_show_city_listener);
        sortDialog.setShowElectroMarkersListener(button_show_electro_listener);
        sortDialog.setShowKidsMarkersListener(button_show_kids_listener);
        sortDialog.show();
    }

    public void showPayDialog(){
        progressBar.setVisibility(View.VISIBLE);
        CustomAddCoinsDialog customAddCoinsDialog = new CustomAddCoinsDialog(MainActivity.this);
        View.OnClickListener button_pay_listener = v -> {
            if (!String.valueOf(customAddCoinsDialog.getInputAmount()).equals("")){
                customAddCoinsDialog.cancel();
                mainActivityViewModel.updateCoinsCount(RC_COUNT, customAddCoinsDialog.getInputAmount() * RENTAL_COIN_RATE);
            }
        };
        View.OnClickListener button_exit_listener = v -> {
            customAddCoinsDialog.cancel();
            progressBar.setVisibility(View.GONE);
        };
        customAddCoinsDialog.setButtonPayListener(button_pay_listener);
        customAddCoinsDialog.setButtonExitListener(button_exit_listener);
        customAddCoinsDialog.show();
    }



//##################################################################################################
//##################################################################################################
//##################################################################################################



    public void initStrings(){
        map1 = getApplicationContext().getResources().getString(R.string.map_main);
        map2 = getApplicationContext().getResources().getString(R.string.map_hybrid);
        map3 = getApplicationContext().getResources().getString(R.string.map_satellite);
        map4 = getApplicationContext().getResources().getString(R.string.map_terrain);
    }

    public void initViewComponents(){
        myLocation = findViewById(R.id.getMyPositionButton);
        selectBicycle = findViewById(R.id.openSettings);
        mapMode = findViewById(R.id.selectMapModeButton);
        login = findViewById(R.id.floatingActionButton);
        sort = findViewById(R.id.floatingActionButton2);

        addRentalCoins = findViewById(R.id.textView19);
        rentalCoinsCount = findViewById(R.id.textView18);

        progressBar = findViewById(R.id.progressBar3);

        preferencesData = SharedData.getSharedPreferences(MainActivity.this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean accessFineLocationGranted = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            boolean accessCoarseLocationGranted = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (accessFineLocationGranted && accessCoarseLocationGranted) {
                loadMap();
                return true;
            } else {
                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                loadMap();
            }else{
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(">>> "  + requestCode + " / " + resultCode);

        if (requestCode == 222 && resultCode == -1) {
            myLocation.setImageResource(R.drawable.ic_gps_fixed);
            getDeviceLocation();
        } else {
            Toast.makeText(getApplicationContext(), "To continue you need to turn on GPS!", Toast.LENGTH_LONG).show();
        }
    }
}