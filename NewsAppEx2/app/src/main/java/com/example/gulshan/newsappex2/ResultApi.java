package com.example.gulshan.newsappex2;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gulshan on 09-11-2017.
 */

public interface ResultApi {
    @GET("newsapp12/fetch_json_life.php")
    Call<Results> getResults();

}
