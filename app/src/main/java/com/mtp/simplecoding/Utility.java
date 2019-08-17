package com.mtp.simplecoding;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mtp.simplecoding.FirebaseDataBase.FireBaseDataActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
   public static Boolean paymentData=false;
   public static String mobileNUmber="0000000001";
   public static final String TAG = FireBaseDataActivity.class.getSimpleName();
   public static long mStartRX = 0;
   public static long mStartTX = 0;
   public static long mLastTime = 0;


   public static Long chknull(Long ll, Long def){
      return ll!=null?ll:def;
   }

   public static Double chknull(Double dd, Double def){
      return dd!=null?dd:def;
   }

   public static Float chknull(Float ff, Float def){return ff!=null?ff:def;}

   public static Integer chknull(Integer ii, Integer def){return ii!=null?ii:def;}

   public static Boolean chknull(Boolean bb, Boolean def){
      return bb!=null?bb:def;
   }

   public static String chknull(String str, String def) {
      return (str != null && str.trim().length() > 0 && !str.equalsIgnoreCase("null")) ? str : def;
   }


   public static ProgressDialog getCustomProgressDialog1(final Activity context) {

      final ProgressDialog dialog = new ProgressDialog(context);
      final View view = context.getLayoutInflater().inflate(R.layout.view_dialog_progress, null);
      final ProgressBar progress = (ProgressBar) view.findViewById(R.id.img_progress);
      final TextView time = (TextView) view.findViewById(R.id.txt_progress_time);
      final TextView msg = (TextView) view.findViewById(R.id.txt_progress_msg);
      //final TextView txt_net_speed =(TextView) view.findViewById(R.id.txt_net_speed);
      msg.setText("Please wait...");
      final CountDownTimer cdt = new CountDownTimer(40000, 1000) {
         @Override
         public void onTick(long millisUntilFinished) {
            if (time != null)
               time.setText("" + millisUntilFinished / 1000);
            long time1 =  millisUntilFinished / 1000;
            msg.setText("Please wait..."+"\n"+getNetworkSpeed(context,false));

         }

         @Override
         public void onFinish() {
            if (context != null && !context.isFinishing() && dialog != null && dialog.isShowing())
               dialog.dismiss();
         }
      };
      dialog.setOnShowListener(new DialogInterface.OnShowListener() {
         @Override
         public void onShow(DialogInterface dialog) {
            cdt.start();
         }
      });
      dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
         @Override
         public void onDismiss(DialogInterface dialog) {
            cdt.cancel();
         }
      });
      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCancelable(false);
      dialog.show();
      dialog.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
      //dialog.show();
      return dialog;
   }


   public static String getNetworkSpeed(Context context, boolean isPojo) {
      long usedTime = 0, totalSpeed = 0, rxBytes = 0, txBytes = 0;
      if (mStartRX != TrafficStats.UNSUPPORTED || mStartTX != TrafficStats.UNSUPPORTED) {
         if (isConnectedToInternet(context)) {
            rxBytes = TrafficStats.getTotalRxBytes() - mStartRX;
            txBytes = TrafficStats.getTotalTxBytes() - mStartTX;
            usedTime = System.currentTimeMillis() - mLastTime;
            totalSpeed = usedTime > 0 ? ((rxBytes + txBytes) * 1000) / usedTime : 0;
         }
         Log.e("totalSpeed", "" + totalSpeed);
         final String txTotalUnit = totalSpeed >= (1024 * 1024 * 1024) ? " gb/s" : totalSpeed >= (1024 * 1024) ? " mb/s" : totalSpeed > 1024 ? " kb/s" : " b/s";
         final long txTotalSpeed = totalSpeed >= (1024 * 1024 * 1024) ? totalSpeed / (1024 * 1024 * 1024) : totalSpeed >= (1024 * 1024) ? totalSpeed / (1024 * 1024) : totalSpeed > 1024 ? totalSpeed / 1024 : totalSpeed;
         Log.e("calTotalSpeed", "" + (txTotalSpeed + txTotalUnit));
         mStartRX = TrafficStats.getTotalRxBytes();
         mStartTX = TrafficStats.getTotalTxBytes();
         return txTotalSpeed + txTotalUnit;
      } else {
         Log.e("Service Error", "Unsupported Service! TrafficStats");
         return isPojo ? "Unsupported Device for TrafficStats!" : "";
      }
   }

   public static  boolean isConnectedToInternet(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
         return true;
      }else{
         Toast.makeText(context, "Please Connect To Internet", Toast.LENGTH_LONG).show();
         return false;
      }
   }


   public void setCompulsoryAsterisk(TextView textView) {
      String  txt_name=textView.getText().toString();
      String colored="*";
      SpannableStringBuilder strBuilder=new SpannableStringBuilder();
      strBuilder.append(txt_name);
      int start=strBuilder.length();
      strBuilder.append(colored);
      int end=strBuilder.length();
      strBuilder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      textView.setText(strBuilder);
   }

   public static void showToast(Activity activity, String message) {
      Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
   }


   //get current date and time
   public static String getCurrentDate(){
      //date and time
      Date d=new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      int h = d.getHours();
      int m=d.getMinutes();
      String Time=((h>12)?(h-12):h)+":"+m+" "+(h>=12?"PM" : "AM");
      String Date=sdf.format(d);
      return  Date;
   }


   //get differance bitween dates
   public static String dateDifferance(Date startDate,Date endDate){
      //millisecind
      long different=endDate.getTime()-startDate.getTime();
      System.out.println("start date"+startDate);
      System.out.println("end date"+endDate);
      System.out.println("differance"+different);
      //timer=different;
      long secondsInMilli=1000;
      long minutesInMilli=secondsInMilli*60;
      long hoursInMilli=minutesInMilli*60;
      long dayInMilli=hoursInMilli*24;
      long elapsedDays=different/dayInMilli;
      different=different%dayInMilli;
      long elapsedHours=different/hoursInMilli;
      different=different%minutesInMilli;

      long elapsedMinutes=different/minutesInMilli;
      different=different%hoursInMilli;

      long elapsedSeconds=different/secondsInMilli;
      //TextView closesin=findViewById(R.id.closesin);

      System.out.println("days"+elapsedDays+" hours"+elapsedHours+" minutes"+elapsedMinutes+" seconds"+elapsedSeconds);
      String daysss= String.valueOf(elapsedDays+1);
      /*if((elapsedDays+1)==1){
         tv_deals_days.setText("Today");
         closesin.setText("Closes");

      }else {
         tv_deals_days.setText(daysss+" days");
         closesin.setText("Closes in");
      }*/


    //  starttimer();
      return daysss;

   }



   //set spinner data
   public static void spinnerStatus(String[] s, final Spinner spinnersevices, final TextView setText, Context context){
      String[] ServicesW ;
      ServicesW=s;
      final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinnertextview, ServicesW) {
         @Override
         public boolean isEnabled(int position) {
            if (position == 0) {
               // Disable the first item from Spinner
               // First item will be use for hint
               return false;
            } else {

               return true;
            }
         }
         @Override
         public View getDropDownView(int position, View convertView,
                                     ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if (position == 0) {
               // Set the hint text color gray
               tv.setTextColor(Color.GRAY);
            } else {
               tv.setTextColor(Color.BLACK);
            }
            return view;
         }
      };

      spinnersevices.setAdapter(arrayAdapter);

      spinnersevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String result = (String) spinnersevices.getSelectedItem();
            setText.setText(result);

            /*Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/
            //spinnerValue_SLOTS=result;
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
      });


   }
}
