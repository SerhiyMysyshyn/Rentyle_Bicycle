package com.aventador.bicyclerental.mainActivity;

import static com.aventador.bicyclerental.sharedPreferences.SharedData.getMapMode;
import static com.aventador.bicyclerental.sharedPreferences.SharedData.saveMapMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.settingFragment.SelectBicycleFragment;
import com.aventador.bicyclerental.sharedPreferences.SharedData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FloatingActionButton selectBicycle, login, mapMode, myLocation;
    private GoogleMap mMap;
    private SharedPreferences preferencesData;
    private String map1, map2, map3, map4;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    private static final int DEFAULT_ZOOM = 10;
    private final LatLng defaultLocation = new LatLng(48.9226, 24.7103);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPermissionGranted();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        myLocation = findViewById(R.id.getMyPositionButton);
        selectBicycle = findViewById(R.id.openSettings);
        mapMode = findViewById(R.id.selectMapModeButton);

        preferencesData = SharedData.getSharedPreferences(MainActivity.this);

        map1 = getApplicationContext().getResources().getString(R.string.map_main);
        map2 = getApplicationContext().getResources().getString(R.string.map_hybrid);
        map3 = getApplicationContext().getResources().getString(R.string.map_satellite);
        map4 = getApplicationContext().getResources().getString(R.string.map_terrain);

        mapMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMapModeDialog();
            }
        });

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        selectBicycle.setOnClickListener(v -> {
            SelectBicycleFragment selectBicycleFragment = new SelectBicycleFragment();
            selectBicycleFragment.show(getSupportFragmentManager(), "TAG");
        });
    }

    private void getDeviceLocation() {
        try {
            if (isPermissionGranted()) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())).title("My position"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }



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






    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(Integer.parseInt(getMapMode()));
        LatLng IvanoFrankivsk = new LatLng(48.9226, 24.7103);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(IvanoFrankivsk));
        mMap.setMinZoomPreference(12);
    }

    public void loadMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
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
}