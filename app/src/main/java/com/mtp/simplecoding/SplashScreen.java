package com.mtp.simplecoding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mtp.simplecoding.FirebaseDataBase.PaymentListActivity;
import com.mtp.simplecoding.kotlin.Activity.DashBoaedView;
import com.mtp.simplecoding.kotlin.Activity.PaymentActvity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThred = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);


                   /* if(user.equals("NA")){
                        Intent intent = new Intent(getApplicationContext(), PlacesEmpty.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), PlacesEmpty.class);
                        startActivity(intent);

                        finish();

                    }*/


                    Intent intent = new Intent(getApplicationContext(), DashBoaedView.class);
                    startActivity(intent);

                    finish();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThred.start();
    }
}
