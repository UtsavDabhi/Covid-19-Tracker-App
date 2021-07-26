package com.example.covid_19app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUitilites {

    private static Retrofit retrofit = null;

    public static ApiInterface getAPIInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
