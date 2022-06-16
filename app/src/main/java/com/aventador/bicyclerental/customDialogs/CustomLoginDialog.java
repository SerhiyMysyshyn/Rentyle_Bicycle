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

import androidx.annotation.NonNull;

import com.aventador.bicyclerental.R;

public class CustomLoginDialog extends Dialog {
    private EditText inputLastName, inputFirstName, inputPhoneNumber;
    private Button btnOK;

    private static boolean isLastnameEmpty = true;
    private static boolean isFirstnameEmpty = true;
    private static boolean isPhoneNumberEmpty = true;

    public String userName;
    public String userLastname;
    public String userPhoneNumber;
    public View.OnClickListener listener;
    private View.OnClickListener onEmptyInputFieldListener;

    public CustomLoginDialog(@NonNull Context context) {
        super(context);
    }

    public String getUserName() {
        return inputFirstName.getText().toString().trim();
    }

    public String getUserLastname() {
        return inputLastName.getText().toString().trim();
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

        this.inputLastName = findViewById(R.id.dialog_text_lastName);
        this.inputFirstName = findViewById(R.id.dialog_text_firstName);
        this.inputPhoneNumber = findViewById(R.id.dialog_text_phoneNumber);
        this.btnOK = findViewById(R.id.dialog_button_ok);

        onEmptyInputFieldListener = v -> btnOK.setClickable(false);

        isFieldsEmpty();

// Check Lastname field
        inputLastName.addTextChangedListener(new TextWatcher() {
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

// Check Firstname field
        inputFirstName.addTextChangedListener(new TextWatcher() {
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


    }

    private void isFieldsEmpty(){
        if (!getUserName().equals("")){
            isFirstnameEmpty = false;
        }else{
            isFirstnameEmpty = true;
        }

        if (!getUserLastname().equals("")){
            isLastnameEmpty = false;
        }else{
            isLastnameEmpty = true;
        }

        if (!getUserPhoneNumber().equals("")){
            isPhoneNumberEmpty = false;
        }else{
            isPhoneNumberEmpty = true;
        }

        if (isLastnameEmpty || isFirstnameEmpty || isPhoneNumberEmpty){
            btnOK.setOnClickListener(onEmptyInputFieldListener);
        }else{
            btnOK.setOnClickListener(listener);
        }
    }

}
