package com.mtp.simplecoding.FirebaseDataBase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mtp.simplecoding.R;

public class PaymentListActivity extends AppCompatActivity {
     Button btn_add_payment;
    private static final String TAG = FireBaseDataActivity.class.getSimpleName();

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
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
