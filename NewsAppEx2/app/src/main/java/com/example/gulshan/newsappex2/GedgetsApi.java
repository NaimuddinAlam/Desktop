package com.example.gulshan.newsappex2;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gulshan on 11-11-2017.
 */

public interface GedgetsApi {
    @GET("newsapp12/fetch_json_techno.php")
    Call<Results> getResults();
}
