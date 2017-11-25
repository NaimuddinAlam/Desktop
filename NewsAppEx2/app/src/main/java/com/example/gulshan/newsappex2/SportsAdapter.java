package com.example.gulshan.newsappex2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gulshan on 11-11-2017.
 */

public class SportsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    LinearLayout show;
    ProgressDialog progressdialog;
    SportsFragment lActivity;
    List<Result> list;
    String url1 = null, dis = null;
    private DatabaseHandlerSearch db;
    private String s_title, s_dis, s_imgurl, s_time;
    TextView tv1, tv2;
    ImageView iview;

    public SportsAdapter(SportsFragment lActivity, List<Result> list) {
        this.lActivity = lActivity;

        this.list = list;
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
        View v = inflater.inflate(R.layout.lifestyledemo, null);
        final Result ar = list.get(i);
        tv1 = (TextView) v.findViewById(R.id.title);
        tv2 = (TextView) v.findViewById(R.id.time);
        tv1.setText(ar.getTxt());
        db = new DatabaseHandlerSearch(viewGroup.getContext());
        tv2.setText("Time" + currentDateTimeString);
        dis = ar.getTxt();
        db.addContacts(new Contact(ar.getTxt(), ar.getImg(), currentDateTimeString, ar.getTxt()));
        progressdialog = new ProgressDialog(viewGroup.getContext());
        progressdialog.setIndeterminate(true);
        progressdialog.show();
        iview = (ImageView) v.findViewById(R.id.image);
        String url = ar.getImg();
        Picasso.with(viewGroup.getContext()).
                load(url).fit().into(iview);
        progressdialog.dismiss();
        show = (LinearLayout) v.findViewById(R.id.llshow);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lActivity.getActivity(), ShowActivity.class);
                Log.d("gulshansahuadapter", url1 + "/n" + tv1.getText() + "/n" + tv2.getText() + "/n" + dis);
                i.putExtra("Image_url", ar.getImg());
                i.putExtra("Title", ar.getTxt());
                i.putExtra("Time", currentDateTimeString);
                i.putExtra("Discription", ar.getTxt());
                lActivity.startActivity(i);
            }

        });

        return v;
    }


}



