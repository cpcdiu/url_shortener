package com.mojahid2021.shortly;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://s-hort.vercel.app")
                    .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
                    .build();
        }
        return retrofit;
    }
}
