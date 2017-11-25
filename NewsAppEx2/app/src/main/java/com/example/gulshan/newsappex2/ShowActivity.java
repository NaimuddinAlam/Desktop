package com.example.gulshan.newsappex2;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShowActivity extends AppCompatActivity {
    TextView time,title,dis;
    ImageView image1;
    ListView lview;
    public DatabaseHandler db;
    private String s_title,s_dis,s_imgurl,s_time;


//    SQLiteDatabase dBase;
ImageButton bookmark;
    ToggleButton like;
    ImageButton wshare,fshare,share;
    String url=null;
    SharedPreferences sharedPreferences;
    boolean j=false,h;

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
        setContentView(R.layout.activity_show);
        time = (TextView) findViewById(R.id.txttime);
        title = (TextView) findViewById(R.id.txttitle);
        dis = (TextView) findViewById(R.id.txtdis);
        image1 = (ImageView) findViewById(R.id.image1);
        db=new DatabaseHandler(this);


//             dBase=openOrCreateDatabase("newsmall",
//                Context.MODE_PRIVATE,null);
//        dBase.execSQL("create table if not exists student(ID  INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,IMAGE_URL TEXT,TIME TEXT,DIS TEXT)");


        like = (ToggleButton) findViewById(R.id.likeibtn);
        wshare = (ImageButton) findViewById(R.id.wssharebtn);
        fshare = (ImageButton) findViewById(R.id.fbsharebtn);
        share = (ImageButton) findViewById(R.id.othersharebtn);

        ImageButton imgback = (ImageButton) findViewById(R.id.llimagebtnback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        time.setText(getIntent().getExtras().getString("Time"));
        title.setText(getIntent().getExtras().getString("Title"));
        dis.setText(getIntent().getExtras().getString("Discription"));
        url = getIntent().getExtras().getString("Image_url");
//   Picasso.with(getParent()).load(url).fit().into(image1);
        Log.d("gulshansahushowActivity", url + "/n" + title.getText() + "/n" + title.getText() + "/n" + dis);

        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);

            }
        });

        wshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = viewToBitmap(image1, image1.getWidth(), image1.getHeight());
                Intent sharingIntent = new Intent((Intent.ACTION_SEND));
                sharingIntent.setPackage("com.whatsapp");
                sharingIntent.setType("image/*");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "imageDemo.jpg");
                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());

                } catch (IOException e)

                {
                    Toast.makeText(v.getContext(), "Whatsapp Not Install", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/imageDemo.jpg"));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("Title"));
                //  startActivity(Intent.createChooser(sharingIntent,"Share Image"));
                startActivity(sharingIntent);



            }
        });


        fshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = viewToBitmap(image1, image1.getWidth(), image1.getHeight());
                Intent sharingIntent = new Intent((Intent.ACTION_SEND));
                //sharingIntent.setPackage("com.facebook.orca");
               // sharingIntent.setPackage("com.facebook.lite");
               // sharingIntent.setPackage("com.facebook.katana");
               // sharingIntent.setPackage("com.facebook.android");




                List<ResolveInfo> matches = v.getContext().getApplicationContext().getPackageManager().queryIntentActivities(sharingIntent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().contains("facebook")) {
                        sharingIntent.setPackage(info.activityInfo.packageName);
                    }
                }

                sharingIntent.setType("image/*");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "imageDemo.jpg");
                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());

                } catch (IOException e)

                {
                    Toast.makeText(v.getContext(), "Facebook Not Install", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/imageDemo.jpg"));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("Title"));
                startActivity(Intent.createChooser(sharingIntent, "Share Image"));

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = viewToBitmap(image1, image1.getWidth(), image1.getHeight());
                Intent sharingIntent = new Intent((Intent.ACTION_SEND));
                sharingIntent.setType("image/*");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "imageDemo.jpg");
                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/imageDemo.jpg"));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("Title"));
                startActivity(Intent.createChooser(sharingIntent, "Share Image"));
            }
        });


   bookmark = (ImageButton)findViewById(R.id.btnsave);


     bookmark.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
                    //    if(getIntent().getExtras().getString("Title")=){}else {}
//           ContentValues cv=new ContentValues();
//             cv.put("TITLE",getIntent().getExtras().getString("Title"));
//             cv.put("IMAGE_URL",url);
//             cv.put("TIME",getIntent().getExtras().getString("Time") );
//             cv.put("DIS", getIntent().getExtras().getString("Title"));
//             long status=dBase.insert("student",null,cv);
//             if(status==-1) {
//                 Toast.makeText(ShowActivity.this,
//                         "Error:Data is not inserted..",
//                         Toast.LENGTH_LONG).show();
//             }else {
//                 Toast.makeText(ShowActivity.this,
//                         "Success:Data is Inserted..",
//                         Toast.LENGTH_LONG).show();
//
//             }

             addContact();
         }
     });

    }

    // function to get values from the Edittext
    private void getValues(){
        s_title = getIntent().getExtras().getString("Title");
        s_imgurl = url;
        s_time=getIntent().getExtras().getString("Time") ;
        s_dis=getIntent().getExtras().getString("Title");

    }


    //Insert data to the database
    private void addContact(){
        getValues();

        db.addContacts(new Contact(s_title,s_imgurl,s_time,s_dis));
        Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_LONG).show();
    }



public static Bitmap viewToBitmap(View view,int width,int height) {
    Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    view.draw(canvas);
    return bitmap;
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
