package com.aventador.bicyclerental.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.aventador.bicyclerental.mainActivity.MainActivity;
import com.aventador.bicyclerental.R;

import java.util.Timer;
import java.util.TimerTask;

/*
*
*  FOR EXAMPLE ONLY
*
* */

public class SplashScreen extends AppCompatActivity {
    private Timer timer;
    private TimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        timer = new Timer();
        mTimerTask = new MyTimerTask();
        timer.schedule(mTimerTask, 3000);


    }
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
