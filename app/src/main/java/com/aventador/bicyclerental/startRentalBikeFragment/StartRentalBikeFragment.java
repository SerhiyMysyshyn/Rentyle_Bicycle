package com.aventador.bicyclerental.startRentalBikeFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aventador.bicyclerental.R;
import com.aventador.bicyclerental.mainActivity.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class StartRentalBikeFragment extends BottomSheetDialogFragment {
    private StartRentalBikeFragmentViewModel startRentalBikeFragmentViewModel;
    private MainActivity mainActivity;
    private StartRentalBikeFragment startRentalBikeFragment;

    public double countMoney_DATA;
    public String name_DATA;
    public String type_DATA;
    public int specID_DATA;
    public String status_DATA;

    private View.OnClickListener goClickListener;

    private ImageView bicycleImage;
    private TextView name ,type, bikeNumber, status, price;
    private Button startBtn , time30minBtn, time1hourBtn, time3hourBtn, time6hourBtn, time12hourBtn, time24hourBtn;

    private int SELECT_TIME = 0;

    public StartRentalBikeFragment(MainActivity activity) {
        this.mainActivity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_rental_bike_dialog_view, container, false);

        startRentalBikeFragmentViewModel = new ViewModelProvider(this).get(StartRentalBikeFragmentViewModel.class);

        initViewComponents(view);

        bicycleImage.setImageDrawable(setMainImageByName(getName_DATA()));

        name.setText(getName_DATA());

        type.setText(setBicycleType(getType_DATA()));
        bikeNumber.setText(String.valueOf(getSpecID_DATA()));
        status.setText(setBicycleStatus(getStatus_DATA()));


        startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
        startRentalBikeFragmentViewModel.rentalPrice.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (SELECT_TIME != 0){
                    if (aDouble >= 0){
                        price.setText(String.valueOf(aDouble));
                    }else {
                        price.setText("");
                    }
                }
            }
        });

        startRentalBikeFragmentViewModel.canRental.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                System.out.println(">>>>> " + aBoolean);
                if (aBoolean){
                    goClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainActivity.notifyActivityToChangeData(Double.parseDouble(price.getText().toString()));
                            Toast.makeText(getContext(), "Щасливої дороги!", Toast.LENGTH_LONG).show();
                            startRentalBikeFragment.dismiss();
                        }
                    };
                    startBtn.setOnClickListener(goClickListener);
                }else {
                    goClickListener = v -> {
                        Toast.makeText(getContext(), "На вашому рахунку не вистачає грошей!", Toast.LENGTH_LONG).show();
                    };
                    startBtn.setOnClickListener(goClickListener);
                }
            }
        });

//--------------------------------------------------------------------------------------------------

        time30minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time30minBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 30;
                startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
                startRentalBikeFragmentViewModel.checkIfUserCanRental(getCountMoney_DATA(), getName_DATA(), getType_DATA(), SELECT_TIME);
            }
        });

        time1hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time1hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 60;
                startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
                startRentalBikeFragmentViewModel.checkIfUserCanRental(getCountMoney_DATA(), getName_DATA(), getType_DATA(), SELECT_TIME);
            }
        });

        time3hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time3hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 180;
                startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
                startRentalBikeFragmentViewModel.checkIfUserCanRental(getCountMoney_DATA(), getName_DATA(), getType_DATA(), SELECT_TIME);
            }
        });

        time6hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time6hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 360;
                startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
                startRentalBikeFragmentViewModel.checkIfUserCanRental(getCountMoney_DATA(), getName_DATA(), getType_DATA(), SELECT_TIME);
            }
        });

        time12hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time12hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 720;
                startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
                startRentalBikeFragmentViewModel.checkIfUserCanRental(getCountMoney_DATA(), getName_DATA(), getType_DATA(), SELECT_TIME);
            }
        });

        time24hourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableTimeButtons();
                time24hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background_selected));
                SELECT_TIME = 1440;
                startRentalBikeFragmentViewModel.setRentalPrice(getName_DATA(), getType_DATA(), SELECT_TIME);
                startRentalBikeFragmentViewModel.checkIfUserCanRental(getCountMoney_DATA(), getName_DATA(), getType_DATA(), SELECT_TIME);
            }
        });

//--------------------------------------------------------------------------------------------------
        return view;
    }

    public void initViewComponents(View view){
        startBtn = view.findViewById(R.id.start_button);

        bicycleImage = view.findViewById(R.id.imageView3);

        name = view.findViewById(R.id.textView9);
        type = view.findViewById(R.id.textView15);
        bikeNumber = view.findViewById(R.id.textView13);
        status = view.findViewById(R.id.textView11);
        price = view.findViewById(R.id.textView21);

        time30minBtn = view.findViewById(R.id.button);
        time1hourBtn = view.findViewById(R.id.button2);
        time3hourBtn = view.findViewById(R.id.button3);
        time6hourBtn = view.findViewById(R.id.button4);
        time12hourBtn = view.findViewById(R.id.button5);
        time24hourBtn = view.findViewById(R.id.button6);
    }



    public String setBicycleType(String type){
        String UA_type = "";
        switch (type){
            case "City":
                UA_type = "Міський";
                break;
            case "Electro":
                UA_type = "Електро-байк";
                break;
            case "Kids":
                UA_type = "Дитячий";
                break;
        }
        return UA_type;
    }

    public String setBicycleStatus(String status){
        String UA_status = "";
        switch (status){
            case "parking":
                UA_status = "Вільний";
                break;
            case "inUse":
                UA_status = "Орендований";
                break;
            case "inRepair":
                UA_status = "В ремонті";
                break;
        }
        return UA_status;
    }

    public Drawable setMainImageByName(String name){
        Drawable mainDrawable = null;
        switch (name){
            case "Orbea Capre":
                mainDrawable = getContext().getDrawable(R.drawable.c_orbea_capre);
                break;
            case "Retro Black":
                mainDrawable = getContext().getDrawable(R.drawable.c_retro_black);
                break;
            case "Haibike Sduro":
                mainDrawable = getContext().getDrawable(R.drawable.e_haibike_sduro);
                break;
            case "Orbea Keram":
                mainDrawable = getContext().getDrawable(R.drawable.e_orbea_keram);
                break;
            case "Ghost Powerkid":
                mainDrawable = getContext().getDrawable(R.drawable.k_ghost_powerkid);
                break;
            case "Ghost Kato":
                mainDrawable = getContext().getDrawable(R.drawable.k_ghost_kato);
                break;
        }
        return mainDrawable;
    }

    public void disableTimeButtons(){
        time30minBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time1hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time3hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time6hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time12hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
        time24hourBtn.setBackground(getContext().getResources().getDrawable(R.drawable.custom_black_button_background));
    }

    public void setViewForDisablePossibility(StartRentalBikeFragment startRentalBikeFragment){
        this.startRentalBikeFragment = startRentalBikeFragment;
    }

    public double getCountMoney_DATA() {
        return countMoney_DATA;
    }

    public void setCountMoney_DATA(double countMoney_DATA) {
        this.countMoney_DATA = countMoney_DATA;
    }

    public String getName_DATA() {
        return name_DATA;
    }

    public void setName_DATA(String name_DATA) {
        this.name_DATA = name_DATA;
    }

    public String getType_DATA() {
        return type_DATA;
    }

    public void setType_DATA(String type_DATA) {
        this.type_DATA = type_DATA;
    }

    public int getSpecID_DATA() {
        return specID_DATA;
    }

    public void setSpecID_DATA(int specID_DATA) {
        this.specID_DATA = specID_DATA;
    }

    public String getStatus_DATA() {
        return status_DATA;
    }

    public void setStatus_DATA(String status_DATA) {
        this.status_DATA = status_DATA;
    }
}
