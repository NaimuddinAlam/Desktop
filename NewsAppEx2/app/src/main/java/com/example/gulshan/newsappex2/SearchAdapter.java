package com.example.gulshan.newsappex2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gulshan on 18-11-2017.
 */

public class SearchAdapter extends ArrayAdapter<Contact> {

        Context context;
        ArrayList<Contact> mcontact;


public SearchAdapter(Context context, ArrayList<Contact> contact){
        super(context, R.layout.lifestyledemo, contact);
        this.context=context;
        this.mcontact=contact;
        }

public  class  Holder{


    TextView title;
    TextView time;
    ImageView iview;
    //  TextView phoneV;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Contact data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        dataAdapter.Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new dataAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lifestyledemo, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.iview = (ImageView) convertView.findViewById(R.id.image);
            //  viewHolder.phoneV = (TextView) convertView.findViewById(R.id.txtView3);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (dataAdapter.Holder) convertView.getTag();
        }

        viewHolder.title.setText("First Name: "+data.get_title());

        String url=data.get_imgurl();
        Picasso.with(getContext()). load(url).fit().into(viewHolder.iview);

        //viewHolder.iview.setText("Second Name: "+data.getSName());

        viewHolder.time.setText("First Name: "+data.get_time());
//        viewHolder.nameSV.setText("Second Name: "+data.getSName());
//        viewHolder.phoneV.setText("Phone no: "+data.getPhoneNumber());

        // Return the completed view to render on screen
        return convertView;
    }

}

