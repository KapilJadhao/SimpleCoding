package com.mtp.simplecoding.FirebaseDataBase;
import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class PaymentRequest {
    public String mobileNumber="";
    public String paymentPojo="";

    public PaymentRequest(String mobileNumber, String paymentPojo) {
        this.mobileNumber = mobileNumber;
        this.paymentPojo = paymentPojo;
    }

    public PaymentRequest() {
    }
}
