package com.mtp.simplecoding.RetrofitExample;

import com.mtp.simplecoding.RetrofitExample.pojo.ProductInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "http://test.jarstech.com/";

    @GET("data_getDeal.jsp")
    Call<ProductInfo> getHeroes();
}
