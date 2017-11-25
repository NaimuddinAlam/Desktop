package com.example.gulshan.newsappex2;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;


public class ResultsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    LinearLayout show;
    LifeStyleFragment lActivity;
    List<Result> list;
    String url1=null,dis=null;
    TextView tv1,tv2;
    public ResultsAdapter(LifeStyleFragment lActivity, List<Result> list){
        this.lActivity=lActivity;

        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        inflater = LayoutInflater.from(viewGroup.getContext());
        View v  = inflater.inflate(R.layout.lifestyledemo,null);
        final  Result ar=list.get(i);
        tv1=(TextView)v.findViewById(R.id.title);
        tv2=(TextView)v.findViewById(R.id.time);
        tv1.setText(ar.getTxt());
        tv2.setText("Time"+currentDateTimeString);
        dis = ar.getTxt();

//     final   HashMap<String, String> meMap=new HashMap<String, String>();
//        meMap.put("title",ar.getTxt());
//        meMap.put("time",currentDateTimeString);
//        meMap.put("image",ar.getImg());
//        meMap.put("dis",ar.getTxt());
//
//
//        Set keys = meMap.keySet();
//        Log.i(TAG, "HashMapValue: " + new Gson().toJson(meMap));



        final ImageView iview=
                (ImageView)v.findViewById(R.id.image);
        String url=ar.getImg();
        Picasso.with(viewGroup.getContext()).
                load(url).fit().into(iview);
       show = (LinearLayout) v.findViewById(R.id.llshow);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lActivity.getActivity(),ShowActivity.class);
                Log.d("gulshansahuadapter",url1 +"/n"+ tv1.getText()+"/n"+ tv2.getText()+"/n"+ dis);
                i.putExtra("Image_url",ar.getImg());
                i.putExtra("Title",ar.getTxt());
                i.putExtra("Time",currentDateTimeString);
                i.putExtra("Discription",ar.getTxt());
                lActivity.startActivity(i);
            }

        });



        return v;
    }}
