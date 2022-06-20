package com.aventador.bicyclerental.customDialogs;

import static com.aventador.bicyclerental.sharedPreferences.SharedData.RENTAL_COIN_RATE;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aventador.bicyclerental.R;

public class CustomAddCoinsDialog extends Dialog {
    private Button btn_pay, btn_exit;
    private EditText inputGRN, cardNumberEditText, cardDateEditText, cardCVVEditText;
    private TextView fromGRNtoRC;

    public double inputAmount;
    public View.OnClickListener buttonExitListener;
    public View.OnClickListener buttonPayListener;

    public CustomAddCoinsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_add_coins_dialog);

        this.btn_pay = findViewById(R.id.button_pay);
        this.btn_exit = findViewById(R.id.button7);
        this.inputGRN = findViewById(R.id.textView15);
        this.fromGRNtoRC = findViewById(R.id.grnTOrc);
        this.cardNumberEditText = findViewById(R.id.editTextCardNumber);
        this.cardDateEditText = findViewById(R.id.editTextCardDate);
        this.cardCVVEditText = findViewById(R.id.editTextCardCVV);


        btn_exit.setOnClickListener(getButtonExitListener());

        inputGRN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    fromGRNtoRC.setText("0");
                }else{
                    double result = getInputAmount() * RENTAL_COIN_RATE;
                    fromGRNtoRC.setText(String.valueOf(result));
                    btn_pay.setOnClickListener(getButtonPayListener());
                }
            }
        });
    }


    public double getInputAmount() {
        return Double.parseDouble(inputGRN.getText().toString().trim());
    }

    public void setInputAmount(double inputAmount) {
        this.inputAmount = inputAmount;
    }

    public View.OnClickListener getButtonPayListener() {
        return buttonPayListener;
    }

    public void setButtonPayListener(View.OnClickListener buttonPayListener) {
        this.buttonPayListener = buttonPayListener;
    }

    public View.OnClickListener getButtonExitListener() {
        return buttonExitListener;
    }

    public void setButtonExitListener(View.OnClickListener buttonExitListener) {
        this.buttonExitListener = buttonExitListener;
    }
}
