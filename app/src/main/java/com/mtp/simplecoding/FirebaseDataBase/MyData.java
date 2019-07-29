package com.mtp.simplecoding.FirebaseDataBase;
import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class MyData {
    String UtharDene="";
    String UtharGhene="";
    String Shoppping="";

    public MyData() {
    }

    public MyData(String utharDene, String utharGhene, String shoppping) {
        UtharDene = utharDene;
        UtharGhene = utharGhene;
        Shoppping = shoppping;
    }
}
