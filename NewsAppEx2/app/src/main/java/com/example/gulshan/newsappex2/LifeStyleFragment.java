package com.example.gulshan.newsappex2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LifeStyleFragment extends Fragment {
    ListView lView;
    public  SharedPreferences pref;
    public   SharedPreferences.Editor editor;
    SwipeRefreshLayout swiperefresh;

    private static final String TAG = "LifeStyleFragment";
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifestylefragment_activity,container,false);

        lView = (ListView) view.findViewById(R.id.lview);
        Startdataloading();
        //  Starthttp();
        swiperefresh = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("rahulok", "post btn called ");
                        String url4 = "http://techzig.com/newsapp12/fetch__life.php";
                        pref=getContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
                        editor = pref.edit();

                        if(pref.getBoolean("lvalue", false)){
                            url4 = "http://techzig.com/newsapp12/fetch_hindi_life.php";
                        }
                        else {
                            url4 = "http://techzig.com/newsapp12/fetch__life.php";
                        }
                        OkHttpClient client = new OkHttpClient();
                        MediaType json = MediaType.parse("application/json;charset=utf-8");
                        JSONObject actualData = new JSONObject();
                        RequestBody body = RequestBody.create(json, actualData.toString());
                        Log.d("shubhuok", "request body ");
                        Log.d("rahuldata", actualData + "");
                        Request newreq = new Request.Builder()
                                .url(url4)
                                .post(body)
                                .build();
                        try {
                            okhttp3.Response response = client.newCall(newreq).execute();
                            Log.d("rahul", "response got");
                            final String ab = response.body().string();
                            Log.d("rahul", "finally : " + ab);

                            // sq.delete("event",null,null);

                        } catch (IOException e) {
                            Log.d("shubhuok", "Exception while doing request " + e);
                            e.printStackTrace();

                        }
                    }
                }).start();

                Startdataloading();
                swiperefresh.setRefreshing(false);
                Toast.makeText(getContext(),"Refreshed",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void Startdataloading() {
        Retrofit r = new Retrofit.Builder().baseUrl("http://techzig.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ResultApi api = r.create(ResultApi.class);
        Call<Results> call = api.getResults();
        call.enqueue(new Callback<Results>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                //Log.d("rahul","ok"+response.body().toString());
                Log.w("abcde", new Gson().toJson(response));

                Results ar = response.body();
                //  Toast.makeText(getContext(),A,Toast.LENGTH_SHORT).show();
                List<Result> list1 = ar.getResult();
                // Collections.reverse(list1);

                lView.setAdapter(new
                        ResultsAdapter(LifeStyleFragment.this, list1));

            }
            @Override
            public void onFailure(Call<Results> call, Throwable t) {
            }
        });


    }

}