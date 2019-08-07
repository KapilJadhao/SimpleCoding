package com.mtp.simplecoding.FirebaseDataBase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mtp.simplecoding.R;
import com.mtp.simplecoding.Utility;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.mtp.simplecoding.RefranceName.paymetRef;
import static com.mtp.simplecoding.Utility.chknull;
import static com.mtp.simplecoding.Utility.getCurrentDate;
import static com.mtp.simplecoding.Utility.mobileNUmber;
import static com.mtp.simplecoding.Utility.paymentData;
import static com.mtp.simplecoding.Utility.spinnerStatus;


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



        //check data availability
        getKeydataValue(paymetRef,mobileNUmber);

       // paymentData();
        return rootView;
    }

    public  void  firebaseData(){
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(paymetRef);

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
    public void onItemClick(final paymentPojo item, int i) {
        Log.e(TAG, "Position "+i+" Click On:"+paymentPojosList.get(i).getDescription());

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("Are you sure you want to remove data");
        builder.setMessage(" !");
        final SpannableStringBuilder update=new SpannableStringBuilder("YES");
        final SpannableStringBuilder skip=new SpannableStringBuilder("NO");
        final StyleSpan bss=new StyleSpan(Typeface.BOLD);
        final StyleSpan iss=new StyleSpan(Typeface.ITALIC);
        update.setSpan(bss,0,3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        skip.setSpan(bss,0,2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setPositiveButton(update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddData(item,"REMOVE",0);
            }
        });

        builder.setNegativeButton(skip, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog= builder.create();
        alertDialog.show();


    }



    public void showAddPaymentDialog()
    {
        try {
            final Dialog dialog2 = new Dialog(getContext());
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.add_payment_dialog);
            dialog2.setCancelable(true);
            dialog2.setCanceledOnTouchOutside(false);
            final Calendar myCalendar = Calendar.getInstance();

            final EditText ev_add_description = (EditText) dialog2.findViewById(R.id.ev_add_description);
            final EditText ev_add_amount = (EditText) dialog2.findViewById(R.id.ev_add_amount);
            final TextView ev_add_from_date = (TextView) dialog2.findViewById(R.id.ev_add_from_date);
            final TextView ev_add_to_date = (TextView) dialog2.findViewById(R.id.ev_add_to_date);
            final EditText ev_add_name_of_owner = (EditText) dialog2.findViewById(R.id.ev_add_name_of_owner);

            String [] Status={"Please Select Status","Paid","Pending"};
            String [] ExpenceType={"Please Select Expense Type","Room","Shopping","Vehicle","Other"};
            Spinner sp_status=dialog2.findViewById(R.id.sp_status);
            final TextView sp_text_status=dialog2.findViewById(R.id.sp_text_status);
            spinnerStatus(Status,sp_status,sp_text_status,getContext());

            Spinner sp_expense_type=dialog2.findViewById(R.id.sp_expense_type);
            final TextView sp_text_expense_type=dialog2.findViewById(R.id.sp_text_expense_type);
            spinnerStatus(ExpenceType,sp_expense_type,sp_text_expense_type,getContext());

            ev_add_from_date.setText(getCurrentDate());
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(ev_add_to_date,myCalendar);
                }

            };




            ev_add_to_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });


            Button btn_submit = (Button) dialog2.findViewById(R.id.btn_submit);
            btn_submit.setText("SUBMIT");

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    paymentPojo paumentList = new paymentPojo(ev_add_amount.getText().toString()
                            ,ev_add_from_date.getText().toString()
                            ,ev_add_to_date.getText().toString()
                            ,"false"
                            ,sp_text_status.getText().toString()
                            ,ev_add_name_of_owner.getText().toString()
                            ,ev_add_description.getText().toString());


                    AddData(paumentList,"ADD",0);

                    dialog2.dismiss();

                }
            });
            dialog2.show();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void updateLabel(TextView editText,Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void createUser(String name, String email,String mobile) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth

    }


    //create new data
    public  void  AddData(paymentPojo paumentList,String process,int i){
        ArrayList<paymentPojo> sendData=new ArrayList<>();
        sendData=paymentPojosList;
        if(process.equalsIgnoreCase("ADD")){
            sendData.add(paumentList);
        }else if(process.equalsIgnoreCase("REMOVE")){
            sendData.remove(paumentList);
        }

        Log.e("parameter:", "" + new Gson().toJson(sendData));
        PaymentRequest paymentRequest=new PaymentRequest(mobileNUmber,new Gson().toJson(sendData));

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        mFirebaseDatabase.child(userId).setValue(paymentRequest);

        //check data availability
        getKeydataValue(paymetRef,mobileNUmber);

    }


    //update data of user
    private void updateUser(paymentPojo paumentList) {
        // updating the user via child nodes
        ArrayList<paymentPojo> sendData=new ArrayList<>();
        sendData=paymentPojosList;
        sendData.add(paumentList);
        mFirebaseDatabase.child(userId).child("paymentPojo").setValue(new Gson().toJson(sendData));
        mFirebaseDatabase.child(userId).child("mobileNumber").setValue(mobileNUmber);

        //check data availability
        getKeydataValue(paymetRef,mobileNUmber);

    }

    //check and set data of user
    public void getKeydataValue(String refrance,String mobileNumber){

        final ProgressDialog progressDialog = Utility.getCustomProgressDialog1(getActivity());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(refrance);
        reference.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                paymentData=true;
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    paymentPojosList.clear();
                    Log.e(TAG, "User Registration key"+datas);
                    userId=datas.getKey().toString();
                    Log.e(TAG, "User key"+userId);
                    PaymentRequest user = datas.getValue(PaymentRequest.class);
                    // Check for null
                    if (user == null) {
                        Log.e(TAG, "User data is null!");
                        return;
                    }

                    Log.e(TAG, "User data is changed!" + user.mobileNumber + ", " + user.paymentPojo);

                    try {
                        JSONArray paymentPojoArray = new JSONArray(user.paymentPojo);
                        for (int i=0;i<paymentPojoArray.length();i++){
                            JSONObject  jsonObject1 = paymentPojoArray.getJSONObject(i);
                            paymentPojosList.add(new Gson().fromJson(jsonObject1.toString(), paymentPojo.class));
                        }

                        setRecylerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                 Log.e(TAG, "User cancel");
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
               // System.out.println("User cancel");
            }
        });



    }



}