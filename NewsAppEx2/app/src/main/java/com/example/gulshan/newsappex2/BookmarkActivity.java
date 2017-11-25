package com.example.gulshan.newsappex2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BookmarkActivity extends AppCompatActivity {
    private DatabaseHandler db;

    private ListView lv;
    private dataAdapter data;
    private Contact dataModel;
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
        setContentView(R.layout.activity_bookmark);
        db=new DatabaseHandler(this);
        lv = (ListView) findViewById(R.id.lview);
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data=new dataAdapter(this, contacts);

        lv.setAdapter(data);
        ImageButton btnaboutback = (ImageButton)findViewById(R.id.btnaboutback);
        btnaboutback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(v.getContext(),ActivityThree.class);
                startActivity(i);
                finish();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);
                Intent i = new Intent(getApplication(),ShowActivity.class);
               // Log.d("gulshansahuadapter",url1 +"/n"+ tv1.getText()+"/n"+ tv2.getText()+"/n"+ dis);
                i.putExtra("Image_url",String.valueOf(dataModel.get_imgurl()));
                i.putExtra("Database_id",String.valueOf(dataModel.get_id()));
                i.putExtra("Title",String.valueOf(dataModel.get_title()));
                i.putExtra("Time",String.valueOf(dataModel.get_time()));
                i.putExtra("Discription",String.valueOf(dataModel.get_dis()));
                startActivity(i);
             //   Toast.makeText(getApplicationContext(), String.valueOf(dataModel.get_id())+"/n"+String.valueOf(dataModel.get_dis()), Toast.LENGTH_SHORT).show();
            }
        });
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                dataModel = contacts.get(position);
//
//                Toast.makeText(getApplicationContext(),String.valueOf(dataModel.get_title()), Toast.LENGTH_SHORT).show();
//            }
//        });
        lv.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataModel = contacts.get(i);
                dataModel.get_id();
                db.deleteContact(dataModel.get_id());
                Toast.makeText(getApplicationContext(),"Delete",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
