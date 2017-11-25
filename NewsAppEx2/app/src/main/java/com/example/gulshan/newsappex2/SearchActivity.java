package com.example.gulshan.newsappex2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText searchedit;
    private DatabaseHandlerSearch db;
    ImageButton search;
    private ListView lv;
    private SearchAdapter data;
    private Contact dataModel;
    SharedPreferences sharedPreferences;
    boolean j=false,h;

   // ListView lview;
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
        setContentView(R.layout.activity_search);
        db=new DatabaseHandlerSearch(this);
        lv = (ListView) findViewById(R.id.search_lview);
         EditText editText = (EditText)findViewById(R.id.editsearchtxt);

         editText.getText().toString();

        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data=new SearchAdapter(this, contacts);


        lv.setAdapter(data);
      //  ImageButton btnaboutback = (ImageButton)findViewById(R.id.btnaboutback);
//        btnaboutback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(v.getContext(),ActivityThree.class);
//                startActivity(i);
//                finish();
//            }
//        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);
                Intent i = new Intent(getApplication(),ShowActivity.class);
                // Log.d("gulshansahuadapter",url1 +"/n"+ tv1.getText()+"/n"+ tv2.getText()+"/n"+ dis);
                i.putExtra("Image_url",String.valueOf(dataModel.get_imgurl()));
                i.putExtra("Title",String.valueOf(dataModel.get_title()));
                i.putExtra("Time",String.valueOf(dataModel.get_time()));
                i.putExtra("Discription",String.valueOf(dataModel.get_dis()));
                startActivity(i);
                //   Toast.makeText(getApplicationContext(), String.valueOf(dataModel.get_id())+"/n"+String.valueOf(dataModel.get_dis()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
