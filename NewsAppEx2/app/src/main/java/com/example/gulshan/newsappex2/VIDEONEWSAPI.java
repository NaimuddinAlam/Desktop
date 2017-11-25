package com.example.gulshan.newsappex2;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gulshan on 17-11-2017.
 */

public interface VIDEONEWSAPI {
    @GET("newsapp12/youtube_json_news.php")
    Call<Results> getResults();
}
