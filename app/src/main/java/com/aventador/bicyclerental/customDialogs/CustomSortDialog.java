package com.aventador.bicyclerental.customDialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.aventador.bicyclerental.R;

public class CustomSortDialog extends Dialog {
    private Button btn_showAll, btn_showCity, btn_showElectro, btn_showKids;

    public View.OnClickListener showAllMarkersListener;
    public View.OnClickListener showCityMarkersListener;
    public View.OnClickListener showElectroMarkersListener;
    public View.OnClickListener showKidsMarkersListener;

    public CustomSortDialog(@NonNull Context context) {
        super(context);
    }

    public View.OnClickListener getShowAllMarkersListener() {
        return showAllMarkersListener;
    }

    public void setShowAllMarkersListener(View.OnClickListener showAllMarkersListener) {
        this.showAllMarkersListener = showAllMarkersListener;
    }

    public View.OnClickListener getShowCityMarkersListener() {
        return showCityMarkersListener;
    }

    public void setShowCityMarkersListener(View.OnClickListener showCityMarkersListener) {
        this.showCityMarkersListener = showCityMarkersListener;
    }

    public View.OnClickListener getShowElectroMarkersListener() {
        return showElectroMarkersListener;
    }

    public void setShowElectroMarkersListener(View.OnClickListener showElectroMarkersListener) {
        this.showElectroMarkersListener = showElectroMarkersListener;
    }

    public View.OnClickListener getShowKidsMarkersListener() {
        return showKidsMarkersListener;
    }

    public void setShowKidsMarkersListener(View.OnClickListener showKidsMarkersListener) {
        this.showKidsMarkersListener = showKidsMarkersListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_sort_dialog);

        this.btn_showAll = findViewById(R.id.button);
        this.btn_showCity = findViewById(R.id.button2);
        this.btn_showElectro = findViewById(R.id.button3);
        this.btn_showKids = findViewById(R.id.button4);


        btn_showAll.setOnClickListener(getShowAllMarkersListener());
        btn_showCity.setOnClickListener(getShowCityMarkersListener());
        btn_showElectro.setOnClickListener(getShowElectroMarkersListener());
        btn_showKids.setOnClickListener(getShowKidsMarkersListener());
    }

}
