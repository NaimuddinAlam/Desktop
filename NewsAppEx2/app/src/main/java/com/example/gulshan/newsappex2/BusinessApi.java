package com.example.gulshan.newsappex2;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gulshan on 13-11-2017.
 */

public interface BusinessApi {
    @GET("newsapp12/fetch_json_business.php")
    Call<Results> getResults();
}
