package com.example.gulshan.newsappex2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class Setting extends Activity {

    ListView listView;
    Button about,update,help;
    Switch notification,Quickread;
    SharedPreferences  sharedPreferences;
    boolean  j=false,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences=getSharedPreferences("LANGUAGE",MODE_PRIVATE);
        h=sharedPreferences.getBoolean("j",j);
        if(h==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppThemes);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.start, R.anim.end);
        setContentView(R.layout.activity_setting);

        ImageButton btnaboutback = (ImageButton)findViewById(R.id.btnaboutback);
        btnaboutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(v.getContext(),ActivityThree.class);
                startActivity(i);
                finish();
            }
        });


        about = (Button)findViewById(R.id.btnaboutset);
        update=(Button)findViewById(R.id.btnsetupdate);
        help=(Button)findViewById(R.id.btnhelpset);
        about.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i = new Intent(v.getContext(),AboutUs.class);
                                         startActivity(i);
                                     }
                                 }
        );
        update.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Toast.makeText(v.getContext(),"Updates",Toast.LENGTH_SHORT).show();
                                     }
                                 }
        );
        help.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                        Intent i = new Intent(v.getContext(),help.class);
                                         startActivity(i);

                                     }
                                 }
        );


    }
}
