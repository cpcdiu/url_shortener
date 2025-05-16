package com.mojahid2021.shortly;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
//    @GET("users")
//    Call<List<User>> getUsers();

    @POST("api/shorten")
    Call<ShortenResponse> shortenUrl(@Body Map<String, String> shortenRequest);
}
