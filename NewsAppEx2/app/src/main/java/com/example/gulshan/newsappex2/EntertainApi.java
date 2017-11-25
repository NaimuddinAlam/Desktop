package com.example.gulshan.newsappex2;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gulshan on 10-11-2017.
 */

public interface EntertainApi {
    @GET("newsapp12/fetch_json_bolly.php")

    Call<Results> getResults();
}
