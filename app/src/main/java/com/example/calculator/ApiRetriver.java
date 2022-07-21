package com.example.calculator;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiRetriver {
    private static final String URL="https://api.exchangerate.host/";
    private static interfaceForAPI ApiInterface=null;
    public static interfaceForAPI getInterface(){
        if(ApiInterface==null){
            Retrofit retrofit=new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            ApiInterface=retrofit.create(interfaceForAPI.class);
        }
        return ApiInterface;
    }
    public interface interfaceForAPI{
        @GET("convert?from=INR")
        Call<Currency> getKey(@Query("to") String symbol);
    }
}
