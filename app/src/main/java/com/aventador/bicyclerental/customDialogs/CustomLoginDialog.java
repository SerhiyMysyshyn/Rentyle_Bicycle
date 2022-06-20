package com.aventador.bicyclerental.customDialogs;

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

public class CustomLoginDialog extends Dialog {
    private EditText inputPassword, inputPhoneNumber;
    private Button btnOK;
    private TextView btnREG;


    private static boolean isPhoneNumberEmpty = true;
    private static boolean isPasswordEmpty = true;

    public String userPhoneNumber;
    public String userPassword;
    public View.OnClickListener listener;
    private View.OnClickListener onEmptyInputFieldListener;

    public CustomLoginDialog(@NonNull Context context) {
        super(context);
    }

    public String getUserPassword() {
        return inputPassword.getText().toString().trim();
    }

    public String getUserPhoneNumber() {
        return inputPhoneNumber.getText().toString().trim();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_login_dialog);

        this.inputPassword = findViewById(R.id.dialog_text_password);
        this.inputPhoneNumber = findViewById(R.id.dialog_text_phone);
        this.btnOK = findViewById(R.id.dialog_button_ok);
        this.btnREG = findViewById(R.id.textView25);

        onEmptyInputFieldListener = v -> btnOK.setClickable(false);

        isFieldsEmpty();


// Check Password field
        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isFieldsEmpty();
            }
        });

// Check Phone field
        inputPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isFieldsEmpty();
            }
        });

        btnREG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void isFieldsEmpty(){
        if (!getUserPassword().equals("")){
            isPasswordEmpty = false;
        }else{
            isPasswordEmpty = true;
        }

        if (!getUserPhoneNumber().equals("")){
            isPhoneNumberEmpty = false;
        }else{
            isPhoneNumberEmpty = true;
        }

        if (isPasswordEmpty || isPhoneNumberEmpty){
            btnOK.setOnClickListener(onEmptyInputFieldListener);
        }else{
            btnOK.setOnClickListener(listener);
        }
    }

}
