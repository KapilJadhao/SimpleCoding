package com.mtp.simplecoding.FirebaseDataBase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mtp.simplecoding.R;

public class PaymentListActivity extends AppCompatActivity {

    private static final String TAG = FireBaseDataActivity.class.getSimpleName();

    String image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        ImageView back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        callFragment();
    }

    public  void  callFragment(){
        Bundle bundle=new Bundle();
        bundle.putString("image",image);
        bundle.putString("Application_id", "");
        PaymentListFragment paymentListFragment=new PaymentListFragment();
        paymentListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ActiveFragment, paymentListFragment,"DeviceInfo").commit();

    }


}
