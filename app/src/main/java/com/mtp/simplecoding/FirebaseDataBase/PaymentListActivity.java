package com.mtp.simplecoding.FirebaseDataBase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mtp.simplecoding.R;

public class PaymentListActivity extends AppCompatActivity {

    private static final String TAG = FireBaseDataActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);




        callFragment();
    }

    public  void  callFragment(){
        Bundle bundle=new Bundle();
        bundle.putString("Application_number", "");
        bundle.putString("Application_id", "");
        PaymentListFragment paymentListFragment=new PaymentListFragment();
        paymentListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ActiveFragment, paymentListFragment,"DeviceInfo").commit();

    }


}
