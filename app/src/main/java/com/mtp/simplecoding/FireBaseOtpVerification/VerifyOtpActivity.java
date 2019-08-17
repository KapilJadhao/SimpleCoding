package com.mtp.simplecoding.FireBaseOtpVerification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mtp.simplecoding.R;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mtp.simplecoding.SharePrefUtil;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import static com.mtp.simplecoding.Utility.isConnectedToInternet;
public class VerifyOtpActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = VerifyOtpActivity.class.getSimpleName();
    RelativeLayout rl_number,rl_otp;

    EditText MobileNumber, OTPEditview;
    Button Submit, OTPButton;
    String result, mobnumb;
    /*private BroadcastReceiver receiver;*/
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    boolean mVerificationInProgress = false;
    String mVerificationId, netConnectivity;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    TextView enter_number,tv_enter,enter_otp,tv_resend;
    TextInputLayout tl_number;
    Typeface custom_of_alegreyasans_regular;
    private EditText otp1,otp2,otp3,otp4,otp5,otp6;
    private String otpnm;
    RelativeLayout rl_name_email;
    EditText et_name_121,et_email_121;

    LinearLayout linearLayoutProgressBar;
    Button back_btn_otp;
    private static final String PREF_UNIQUE_ID="PREF_UNIQUE_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView toolbat_title=findViewById(R.id.toolbar_title);

        enter_number=findViewById(R.id.enter_number);
        tl_number=findViewById(R.id.tl_number);
        tv_enter=findViewById(R.id.tv_enter);
        tv_resend=findViewById(R.id.tv_resend);
        enter_otp=findViewById(R.id.enter_otp);

        SpannableString content =new SpannableString("ENTER OTP");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        tv_enter.setText(content);
        SpannableString content1 =new SpannableString("Resend OTP");
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        tv_resend.setText(content1);
        rl_name_email=findViewById(R.id.rl_name_email);
        et_name_121=findViewById(R.id.et_name_121);
        et_email_121=findViewById(R.id.et_email_121);

        rl_number=findViewById(R.id.rl_number);
        rl_otp=findViewById(R.id.rl_otp);
        MobileNumber = (EditText) findViewById(R.id.mobileNumber);

        Submit = (Button) findViewById(R.id.submit);

        OTPEditview = (EditText) findViewById(R.id.ed_otp);

        OTPButton = (Button) findViewById(R.id.otp_button);


        //back button otp
        back_btn_otp=findViewById(R.id.back_btn_otp);
        back_btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        otp1=findViewById(R.id.otpnum1);
        otp2=findViewById(R.id.otpnum2);
        otp3=findViewById(R.id.otpnum3);
        otp4=findViewById(R.id.otpnum4);
        otp5=findViewById(R.id.otpnum5);
        otp6=findViewById(R.id.otpnum6);

        otp1.addTextChangedListener( this);
        otp2.addTextChangedListener( this);
        otp3.addTextChangedListener( this);
        otp4.addTextChangedListener( this);
        otp5.addTextChangedListener( this);
        otp6.addTextChangedListener( this);


        mobnumb = MobileNumber.getText().toString();
        tv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_number.setVisibility(View.GONE);
                rl_otp.setVisibility(View.VISIBLE);

                /*Intent intent=new Intent(getApplicationContext(),CurrentOrders.class);
                intent.putExtra("Mobile_Number","123456");
                startActivity(intent);*/
            }
        });

        enter_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* rl_number.setVisibility(View.GONE);
                rl_otp.setVisibility(View.VISIBLE);*/


            }
        });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_otp.setVisibility(View.GONE);
                rl_number.setVisibility(View.VISIBLE);

            }
        });

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                /* customToast("verification done");
                db7.insertLogin(MobileNumber.getText().toString());*/
                /*onBackPressed();*/

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                /*Toast.makeText(MainActivity.this, "verification fail", Toast.LENGTH_LONG).show();*/
                customToast("verification fail");
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    // Invalid request
                    // [START_EXCLUDE]
                    /* Toast.makeText(MainActivity.this, "invalid mob no", Toast.LENGTH_LONG).show();*/
                    customToast("invalid mobile number");

                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    /*Toast.makeText(MainActivity.this, "quota over", Toast.LENGTH_LONG).show();*/
                    customToast("quota over");

                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);
                /*Toast.makeText(MainActivity.this, "Verification code sent to mobile", Toast.LENGTH_LONG).show();*/
                customToast("Verification code sent to mobile");

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                rl_number.setVisibility(View.GONE);
                rl_otp.setVisibility(View.VISIBLE);


            }
        };


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isConnectedToInternet(VerifyOtpActivity.this)) {

                    if (MobileNumber.getText().toString().equals("")) {

                        Toast.makeText(VerifyOtpActivity.this, "Enter Number First", Toast.LENGTH_LONG).show();

                        /*customsnackbaar();*/
                    } else {
                        /* timerin();*/


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + MobileNumber.getText().toString(),        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                VerifyOtpActivity.this,               // Activity (for callback binding)
                                mCallbacks);        // OnVerificationStateChangedCallbacks


                    }

                }


            }
        });



        OTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp1.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter OTP First", Toast.LENGTH_LONG).show();
                }else if(otp2.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter OTP First", Toast.LENGTH_LONG).show();
                }else if(otp3.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter OTP First", Toast.LENGTH_LONG).show();
                }else if(otp4.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter OTP First", Toast.LENGTH_LONG).show();
                }else if(otp5.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter OTP First", Toast.LENGTH_LONG).show();
                }else if(otp6.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter OTP First", Toast.LENGTH_LONG).show();
                }else if(et_name_121.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyOtpActivity.this, "Enter your name", Toast.LENGTH_LONG).show();
                }

                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpnm);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        /* LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, new IntentFilter("OTP"));*/




    }

    @Override
    protected void onPause() {
        super.onPause();
        /*LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);*/
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            //  db.doLogin(MobileNumber.getText().toString());
                            /*Toast.makeText(MainActivity.this, "Verification done", Toast.LENGTH_LONG).show();*/
                            customToast("Verification done");
                            onVerificationDone(MobileNumber.getText().toString());
                            FirebaseUser user = task.getResult().getUser();

                                   /*Intent intent=new Intent(getApplicationContext(),Myjarbill.class);
                            intent.putExtra("activity",activity);
                            intent.putExtra("Mobile_Number",MobileNumber.getText().toString());
                            startActivity(intent);*/

                            /* onBackPressed();*/


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                customToast("Verification failed invalid otp");
                                /* Toast.makeText(MainActivity.this, "Verification failed code invalid", Toast.LENGTH_LONG).show();*/
                            }
                        }
                    }
                });
    }



    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
        super.onBackPressed();
    }


    public  void  customToast( String toast_text){
        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.custom_container));
        TextView text=(TextView)layout.findViewById(R.id.text);
        ImageView imageView=layout.findViewById(R.id.img);

        text.setText(toast_text);
        text.setTypeface(custom_of_alegreyasans_regular);
        if(toast_text.equalsIgnoreCase("verification done")||toast_text.equalsIgnoreCase("Verification code sent to mobile")){
           // imageView.setImageDrawable(getResources().getDrawable(R.drawable.interfac));
        }else {
           // imageView.setImageDrawable(getResources().getDrawable(R.drawable.warningimg));
            layout.setBackgroundColor(getResources().getColor(R.color.red));
        }
        Toast toast=new Toast((getApplicationContext()));
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setMargin(0,0);
        toast.setView(layout);
        toast.show();
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(s.length()==1){
            if(otp1.length()==1){
                otp2.requestFocus();
                otpnm=otp1.getText().toString();
            }

            if(otp2.length()==1){
                otp3.requestFocus();
                otpnm+=otp2.getText().toString();
            }

            if(otp3.length()==1){
                otp4.requestFocus();
                otpnm+=otp3.getText().toString();
            }

            if(otp4.length()==1){
                otp5.requestFocus();
                otpnm+=otp4.getText().toString();
            }

            if(otp5.length()==1){
                otp6.requestFocus();
                otpnm+=otp5.getText().toString();
            }

            if(otp6.length()==1){
                otpnm+=otp6.getText().toString();
                rl_name_email.setVisibility(View.VISIBLE);
                Log.d(TAG,otpnm);
            }
        }

        else if(s.length()==0){
            if(otp6.length()==0){
                otp5.requestFocus();
            }

            if(otp5.length()==0){
                otp4.requestFocus();
            }

            if(otp4.length()==0){
                otp3.requestFocus();
            }

            if(otp3.length()==0){
                otp2.requestFocus();
            }

            if(otp2.length()==0){
                otp1.requestFocus();
            }
        }

    }

    public void onVerificationDone(String mobileNumber){
        SharePrefUtil.setValue(getApplicationContext(),"mobile_number",mobileNumber);

    }



   /* //create new data
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

    }*/


}
