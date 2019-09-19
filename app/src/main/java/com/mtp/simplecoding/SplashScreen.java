package com.mtp.simplecoding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mtp.simplecoding.FirebaseDataBase.PaymentListActivity;
import com.mtp.simplecoding.kotlin.Activity.DashBoaedView;
import com.mtp.simplecoding.kotlin.Activity.PaymentActvity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.mtp.simplecoding.Utility.appPreference;
import static com.mtp.simplecoding.Utility.userPreference;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //registerReceiver(new kapil(),new IntentFilter("android.intent.action.TIME_TICK"));

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


    public class kapil extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            appPreference = new SharedPreferenceHelper(context,String.valueOf(PrefName.APP_PREFERENCE));
            userPreference = new SharedPreferenceHelper(context,String.valueOf(PrefName.USER_PREFERENCE));

            if(intent.getAction()!=null && intent.getAction().equalsIgnoreCase("android.intent.action.TIME_TICK")){
                //int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                Log.i("TIME Level",""+"");
                //Utility.batteryStat=level+"%";

                try{
                    String storedTime=userPreference.getString(PreferenceValueKey.LocationUpdateTime,"");
                    Calendar cc=Calendar.getInstance();
                    SimpleDateFormat sdf=new SimpleDateFormat(appPreference.getString(PreferenceValueKey.appDateFormat, "dd-MMM-yyyy HH:mm"));
                    if((storedTime.length()==0 || !cc.getTime().before(sdf.parse(storedTime)))){
                        Log.e("time", ""+storedTime);
                        cc.add(Calendar.MINUTE,2);
                        userPreference.putString(PreferenceValueKey.LocationUpdateTime,sdf.format(cc.getTime()));
                        Log.e("update time in activity",""+userPreference.getString(PreferenceValueKey.LocationUpdateTime,""));

                        /*Bundle extras = intent.getExtras();
                        Intent i = new Intent("broadCastName");
                        // Data you need to pass to activity
                        i.putExtra("message", extras.getString("MESSAGE_KEY"));

                        context.sendBroadcast(i);*/

                    }
                }catch(Exception e){e.printStackTrace();}
            }


        }
    }
}
