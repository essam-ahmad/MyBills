package com.example.e3.mybills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Start_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
        Thread background = new Thread() {
            public void run() {

                try {

                    sleep(1 * 1000);
                    Intent i = new Intent(getBaseContext(), BillActivity.class);
                    Bundle b = new Bundle();
                    b.putString("action", "Bill");
                    i.putExtras(b);
                    startActivity(i);
                    finish();

                } catch (Exception e) {

                }
            }
        };
        background.start();
    }}
