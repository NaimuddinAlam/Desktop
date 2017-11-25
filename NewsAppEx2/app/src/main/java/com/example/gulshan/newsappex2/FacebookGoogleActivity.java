package com.example.gulshan.newsappex2;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 15-11-2017.
 */

public class FacebookGoogleActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvFGName, tvFGEmailID;
    CircleImageView civImages;
    Button btnFGLogOut;
    String name, email, photoUrl;
    Uri personPhotoUri;
    Toolbar fgToolBar;
    int f;
    GoogleApiClient mGAC;
    SharedPreferences sharedPreferences;
    boolean j=false,h;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
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
        setContentView(R.layout.facebookgoogle_activity);
        civImages = findViewById(R.id.civImages);
        tvFGName = findViewById(R.id.tvFGName);
//        tvFGEmailID=findViewById(R.id.tvFGEmailId);
        btnFGLogOut = findViewById(R.id.btnFGLogOut);
        fgToolBar = findViewById(R.id.fgToolBar);
        setSupportActionBar(fgToolBar);
        getSupportActionBar().setTitle("LoggedIn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        name = i.getStringExtra("Name");
//        email=i.getStringExtra("Email");
        f = i.getIntExtra("int", f);
        photoUrl = i.getStringExtra("Photo");
        personPhotoUri = Uri.parse(photoUrl);
        Log.d("name", "" + personPhotoUri);
        tvFGName.setText(name);
//        tvFGEmailID.setText(email);
        if (f == 10) {
            if (photoUrl!=null) {
                Glide.with(getApplicationContext()).load(personPhotoUri)
                        .thumbnail(0.6f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(civImages);
            } else {
                Log.d("rahul11", "" + personPhotoUri);
                Log.d("rahul", "" + personPhotoUri);
                civImages.setImageResource(R.drawable.user);
            }
        } else if(f==20) {
            if (photoUrl.isEmpty()) {
                civImages.setImageResource(R.drawable.user);
            } else {
                Log.d("rahul11", "" + personPhotoUri);
                Log.d("rahul", "" + personPhotoUri);
                Glide.with(getApplicationContext()).load(personPhotoUri)
                        .thumbnail(0.6f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(civImages);
            }
        }
        btnFGLogOut.setOnClickListener(this);
        fgToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFGLogOut:
                dialogBox();
                break;
        }
    }

    private void dialogBox() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("LogOut");
        adb.setMessage("Do you want to LogOut?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Auth.GoogleSignInApi.signOut(mGAC).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                dialog.dismiss();
                LoginManager.getInstance().logOut();
                finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGAC = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGAC.connect();
    }
}
