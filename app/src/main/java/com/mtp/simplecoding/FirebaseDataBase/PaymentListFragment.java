package com.mtp.simplecoding.FirebaseDataBase;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mtp.simplecoding.R;

import java.util.ArrayList;

public class PaymentListFragment extends Fragment implements PaymentListAdapter.ItemListener {
    RecyclerView rv_paymenyList;
    private PaymentListAdapter paymentListAdapter;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<paymentPojo> paymentPojosList=new ArrayList<>();

    Button btn_add_payment;
    private static final String TAG = FireBaseDataActivity.class.getSimpleName();

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().invalidateOptionsMenu();
        final View rootView=inflater.inflate(R.layout.payment_list_fragment, container, false);
        rv_paymenyList=rootView.findViewById(R.id.rv_excavation_list);

        /* search_list.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search_list.getText().toString().toLowerCase(Locale.getDefault());

                rv_shop_by_catagory_Adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });*/


        btn_add_payment=rootView.findViewById(R.id.btn_add_payment);
        btn_add_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPaymentDialog();
            }
        });

        firebaseData();
        paymentData();
        return rootView;
    }

    public  void  firebaseData(){
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("paymentList");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
    }

    public void paymentData() {


        String [] Amount={"100","200","1500","222"};
        String [] fromDate={"10-05-19","15-05-19","22-05","23-22-30"};
        String [] toDate={"10-05-19","15-05-19","22-05","11-11-11"};
        String [] reminder={"true","false","true","false"};
        String [] Status={"paid","paid","pending","paid"};
        String [] nameOfOwner={"akash","kapil","akshay","kishor"};
        String [] description={"akash","kapil","akshay","kishor"};
        for (int i = 0; i < nameOfOwner.length; i++) {
            paymentPojo paumentList = new paymentPojo(Amount[i],fromDate[i],toDate[i],reminder[i],Status[i],nameOfOwner[i],description[i]);
            paymentPojosList.add(paumentList);

        }

        setRecylerView();
    }


    public  void  setRecylerView(){
        paymentListAdapter = new PaymentListAdapter(paymentPojosList,getActivity(),this);
        linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_paymenyList.setLayoutManager(linearLayoutManager);
        rv_paymenyList.setAdapter(paymentListAdapter);
        rv_paymenyList.setNestedScrollingEnabled(false);

    }

    @Override
    public void onDetach(){
        /*setActionBar(getActivity(), title, true);*/
        super.onDetach();
    }

    @Override
    public void onItemClick(paymentPojo item) {

    }

    public void addRecord(paymentPojo paumentList){
        paymentPojosList.add(paumentList);
        paymentListAdapter.notifyDataSetChanged();

    }

    public  void  checkData(paymentPojo paumentList){
        ArrayList<paymentPojo> sendData=new ArrayList<>();
        sendData=paymentPojosList;
        sendData.add(paumentList);
        Log.e("parameter:", "" + new Gson().toJson(sendData));
        PaymentRequest paymentRequest=new PaymentRequest("7030537378",new Gson().toJson(sendData));

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        mFirebaseDatabase.child(userId).setValue(paymentRequest);
        addUserChangeListener();


        addRecord(paumentList);
    }


    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PaymentRequest user = dataSnapshot.getValue(PaymentRequest.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.mobileNumber + ", " + user.paymentPojo);




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }




    public void showAddPaymentDialog()
    {
        try {
            final Dialog dialog2 = new Dialog(getContext());
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.add_payment_dialog);
            dialog2.setCancelable(false);
            dialog2.setCanceledOnTouchOutside(false);

            final EditText ev_add_description = (EditText) dialog2.findViewById(R.id.ev_add_description);
            final EditText ev_add_amount = (EditText) dialog2.findViewById(R.id.ev_add_amount);
            final EditText ev_add_from_date = (EditText) dialog2.findViewById(R.id.ev_add_from_date);
            final EditText ev_add_to_date = (EditText) dialog2.findViewById(R.id.ev_add_to_date);
            final EditText ev_add_name_of_owner = (EditText) dialog2.findViewById(R.id.ev_add_name_of_owner);
            final EditText ev_add_status = (EditText) dialog2.findViewById(R.id.ev_add_status);

            Button btn_submit = (Button) dialog2.findViewById(R.id.btn_submit);
            btn_submit.setText("SUBMIT");

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    paymentPojo paumentList = new paymentPojo(ev_add_amount.getText().toString()
                            ,ev_add_from_date.getText().toString()
                            ,ev_add_to_date.getText().toString()
                            ,"false"
                            ,ev_add_status.getText().toString()
                            ,ev_add_name_of_owner.getText().toString()
                            ,ev_add_description.getText().toString());
                    checkData(paumentList);

                    dialog2.dismiss();

                }
            });
            dialog2.show();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void createUser(String name, String email,String mobile) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth

    }
}