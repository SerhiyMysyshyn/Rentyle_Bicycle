package com.aventador.bicyclerental.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.settingFragment.SettingsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView close;
    private FloatingActionButton settings, login;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isStoragePermissionGranted();

        SettingsFragment settingsFragment = new SettingsFragment();

        settings = findViewById(R.id.openSettings);
        close = findViewById(R.id.closeBtn);

        hideUserInfoFragment(settingsFragment);

        settings.setOnClickListener(v -> {
            showUserInfoFragment(settingsFragment);
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideUserInfoFragment(settingsFragment);
            }
        });
    }


    public void showUserInfoFragment(SettingsFragment settingsFragment){
        close.setVisibility(View.VISIBLE);
        settings.hide();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(R.id.fragmentContainerView, settingsFragment)
                .commit();
    };

    public void hideUserInfoFragment(SettingsFragment settingsFragment){
        close.setVisibility(View.GONE);
        settings.show();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .remove(settingsFragment)
                .commit();
    };









    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng IvanoFrankivsk = new LatLng(48.89, 24.72);
        //mMap.addMarker(new MarkerOptions().position(IvanoFrankivsk).title("Marker in IF"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(IvanoFrankivsk));
        mMap.setMinZoomPreference(12);
    }

    public void loadMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }





    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean accessFineLocationGranted = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (accessFineLocationGranted) {
                loadMap();
                return true;
            } else {
                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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