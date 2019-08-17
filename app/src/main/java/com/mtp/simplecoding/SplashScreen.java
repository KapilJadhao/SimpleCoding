package com.mtp.simplecoding;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.mtp.simplecoding.FirebaseDataBase.PaymentListActivity;
import com.mtp.simplecoding.kotlin.Activity.DashBoaedView;
import com.mtp.simplecoding.kotlin.Activity.PaymentActvity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


       /* Window window = SplashScreen.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SplashScreen.this,R.color.Orange));
        }*/

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


                  //  SharePrefUtil.setValue(getApplicationContext(),"mobile_number","0000000001");
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
