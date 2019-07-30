package com.mtp.simplecoding;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mtp.simplecoding.FirebaseDataBase.FireBaseDataActivity;
import com.mtp.simplecoding.FirebaseDataBase.PaymentListActivity;
import com.mtp.simplecoding.FirebasePush.app.Config;
import com.mtp.simplecoding.RetrofitExample.Api;
import com.mtp.simplecoding.RetrofitExample.Hero;
import com.mtp.simplecoding.RetrofitExample.pojo.ProductInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mtp.simplecoding.FirebasePush.util.Constants.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        Api api = retrofit.create(Api.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<ProductInfo>call = api.getHeroes();

        //then finallly we are making the call using enqueue()
        //it takes callback interface as an argument
        //and callback is having two methods onRespnose() and onFailure
        //if the request is successfull we will get the correct response and onResponse will be executed
        //if there is some error we will get inside the onFailure() method
        call.enqueue(new Callback<ProductInfo>() {
            @Override
            public void onResponse(Call<ProductInfo> call, Response<ProductInfo> response) {

                //In this point we got our hero list
                //thats damn easy right ;)
                if(response.code()==200){
                    ProductInfo heroList = response.body();
                   Toast.makeText(getApplicationContext(), "onResponce"+heroList.getProductDealTransactionDetails().size(), Toast.LENGTH_SHORT).show();
                   for(int i=0;i<heroList.getProductDealTransactionDetails().size();i++){
                       System.out.println("Product Name:"+heroList.getProductDealTransactionDetails().get(i).getProductSku());
                   }

                    Intent intent=new Intent(getApplicationContext(), PaymentListActivity.class);
                    startActivity(intent);
                   // System.out.println("onResponce"+heroList.size());
                }


                //now we can do whatever we want with this list

            }

            @Override
            public void onFailure(Call<ProductInfo>call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage()+"onFalure", Toast.LENGTH_SHORT).show();
                System.out.println("onFalure"+t.getMessage());
            }
        });



        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            CharSequence name="News Channel";
            String description="Default Notification News Channel";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        //push notificaton topics for pune and nagpur and global
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        FirebaseMessaging.getInstance().subscribeToTopic("All");

    }
}
