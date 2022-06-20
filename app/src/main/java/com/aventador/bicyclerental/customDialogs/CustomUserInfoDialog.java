package com.aventador.bicyclerental.customDialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aventador.bicyclerental.R;

public class CustomUserInfoDialog extends Dialog {
    private Button btnClose;
    private TextView btnLogout, info1, info2, info3, info4, info5;

    public String userName, userWallet, userNumber, userPassword, userBike;
    public View.OnClickListener logOutClickListener;
    public View.OnClickListener closeWindowClickListener;

    public CustomUserInfoDialog(@NonNull Context context) {
        super(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_user_info_dialog);

        this.btnClose = findViewById(R.id.dialog_button_close);
        this.btnLogout = findViewById(R.id.logOut);
        this.info1 = findViewById(R.id.text_user_name);
        this.info2 = findViewById(R.id.text_wallet);
        this.info3 = findViewById(R.id.text_phone);
        this.info4 = findViewById(R.id.text_password);
        this.info5 = findViewById(R.id.text_bike);

        info1.setText(getUserName());
        info2.setText(getUserWallet());
        info3.setText(getUserNumber());
        info4.setText(getUserPassword());
        info5.setText(getUserBike());

        btnClose.setOnClickListener(getCloseWindowClickListener());
        btnLogout.setOnClickListener(getLogOutClickListener());
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(String userWallet) {
        this.userWallet = userWallet;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserBike() {
        return userBike;
    }

    public void setUserBike(String userBike) {
        this.userBike = userBike;
    }

    public View.OnClickListener getLogOutClickListener() {
        return logOutClickListener;
    }

    public void setLogOutClickListener(View.OnClickListener logOutClickListener) {
        this.logOutClickListener = logOutClickListener;
    }

    public View.OnClickListener getCloseWindowClickListener() {
        return closeWindowClickListener;
    }

    public void setCloseWindowClickListener(View.OnClickListener closeWindowClickListener) {
        this.closeWindowClickListener = closeWindowClickListener;
    }
}
