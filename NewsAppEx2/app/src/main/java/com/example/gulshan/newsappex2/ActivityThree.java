package com.example.gulshan.newsappex2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaCas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.service.textservice.SpellCheckerService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;

import java.net.ContentHandler;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Url;

public class ActivityThree extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Button logout, Setting, Feed;
    static boolean a = false,j=false,h;
    int c;
    //    ImageButton fblogin, googlelogin;
    private static String APP_ID = "366720170431579";
    ImageView ivGoogleImage, ivFacebookImage;
    GoogleApiClient mGAC;
    LinearLayout llGoogleView, llSignInLayout, llLayout, llMain;

    TextView tvGoogleName, tvEmailId, tvFacebookName;
    private static final int RC_SIGN_IN = 007;
    SignInButton googleSignIn;
    LoginButton facebookLogin;
    ProgressDialog mProgressDialog;
    CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Profile profile;
    CircleImageView civImage;
    Button book;
    SwitchCompat scNightMode;
    public  SharedPreferences pref;
    public   SharedPreferences.Editor editor;

    GoogleSignInAccount acct;
    String personName, personEmailId, facebookName, facebookMiddleName, facebookLastName, photo, photoName;
    Uri personPhotoUrl;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       pref=getSharedPreferences("LANGUAGE",MODE_PRIVATE);
        h=pref.getBoolean("j",j);
        if(h==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppThemes);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_three);
        llGoogleView = findViewById(R.id.llGoogleView);
        llMain = findViewById(R.id.llMain);
        scNightMode=findViewById(R.id.scNightMode);
        googleSignIn = findViewById(R.id.googleSignIn);
        tvGoogleName = findViewById(R.id.tvGoogleName);
        tvEmailId = findViewById(R.id.tvEmailId);
        civImage = findViewById(R.id.civImage);
        book= (Button)findViewById(R.id.at_bookmark);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),BookmarkActivity.class);
                startActivity(i);
            }
        });
     //   pref=getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        editor = pref.edit();

        if(h==true){
            scNightMode.setChecked(true);
        }else {
            scNightMode.setChecked(false);
        }

        scNightMode.setOnCheckedChangeListener(this);
        facebookLogin = findViewById(R.id.facebookLogin);
        facebookLogin.setText("Log In");

        if (a == true) {
            nextActivity(Profile.getCurrentProfile());
            googleSignIn.setVisibility(View.GONE);
            updateUiFacebook(true);
        }
        googleSignIn.setVisibility(View.VISIBLE);
        facebookLogin.setVisibility(View.VISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGAC = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        facebookLogin.setReadPermissions("email");

        googleSignIn.setSize(SignInButton.SIZE_STANDARD);
        googleSignIn.setScopes(gso.getScopeArray());
        googleSignIn.setOnClickListener(this);
        civImage.setOnClickListener(this);
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                nextActivity(currentProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                if (accessToken == null && profile == null) {
                    LoginManager.getInstance().logOut();
                    updateUiFacebook(false);
                } else {
                    profile = Profile.getCurrentProfile();
                    Log.d("face", "" + profile);
                    a = true;
                    Log.d("session", "" + a);
                    nextActivity(profile);
                }
            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
                updateUI(false);
            }

            @Override
            public void onError(FacebookException error) {
                updateUiFacebook(false);
                Toast.makeText(getApplicationContext(), "Something Went wrong", Toast.LENGTH_LONG).show();
            }
        });
        facebookLogin.unregisterCallback(callbackManager);

        Setting = findViewById(R.id.btnsetting);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityThree.this, Setting.class);
                startActivity(i);
            }
        });

        Feed = (Button) findViewById(R.id.btnfeed);
        Feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(ActivityThree.this,Feedback.class);
                //startActivity(i);

                feedback();
            }
        });

        setLocale(pref.getString("local","es"));
     //   Toast.makeText(this,pref.getString("local","es"),Toast.LENGTH_SHORT).show();

        final Spinner sp1 = (Spinner) findViewById(R.id.langspin);
           sp1.setSelection(pref.getInt("spin",1));

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                if (position == 2) {
                  // setLocale("hi");
                    editor.putString("lname", "hindi");
                    editor.putBoolean("lvalue", true);
                    editor.putInt("spin", 2);
                    editor.putString("local", "hi");
                    editor.commit();
                   // Toast.makeText(getApplicationContext(),pref.getString("lname",""),Toast.LENGTH_SHORT).show();
                }
                if (position == 1) {
                    editor.putString("lname", "english");
                    editor.putBoolean("lvalue", false);
                    editor.putInt("spin", 1);
                    editor.putString("local", "es");
                    editor.commit();
                 //   Toast.makeText(getApplicationContext(),pref.getString("lname",""),Toast.LENGTH_SHORT).show();
                  //  setLocale("es");
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(ActivityThree.this, MainActivity.class);
                        startActivity(intent0);
                        finish();
                        break;
                    case R.id.ic_android:
                        Intent intent1 = new Intent(ActivityThree.this, ActivityOne.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.ic_books:
                        Intent intent2 = new Intent(ActivityThree.this, ActivityTwo.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.ic_center_focus:
                        break;
                }
                return false;
            }
        });
    }

    public void setLocale(String localeCode) {
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
    private void feedback() {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_feedback);
        dialog.setTitle("Feedback");
        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
     final   EditText editText = dialog.findViewById(R.id.editText);
        TextView tvSend = dialog.findViewById(R.id.tvSend);
        TextView tvLater = dialog.findViewById(R.id.tvLater);
        ratingBar.getRating();

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(editText.getText().toString().matches("")){

                    Toast.makeText(getApplicationContext(),"Please write something in the comment box", Toast.LENGTH_LONG).show();

                    }
                else {
                    String message = editText.getText().toString();
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"gksahu100@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "NEWS MALL APP FEEDBACK");
                    email.putExtra(Intent.EXTRA_TEXT, message);
                    email.setType(message+"/rfc822");

                    try {
                        startActivity(Intent.createChooser(email, "Send mail..."));
                        finish();
                        Log.d("abc", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }



                    //startActivity(Intent.createChooser(email, "Choose an Email client :"));
                }

         }
        });
        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGAC);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGAC).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handlingSignInResult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = googleSignInResult.getSignInAccount();
            personName = acct.getDisplayName();
            personEmailId = acct.getEmail();
            personPhotoUrl = acct.getPhotoUrl();
            photo = String.valueOf(personPhotoUrl);
            tvGoogleName.setText(personName);
            tvEmailId.setText(personEmailId);


            if (personPhotoUrl != null) {
                c = 10;
                Glide.with(getApplicationContext()).load(personPhotoUrl)
                        .thumbnail(0.6f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(civImage);
            } else {
                c=30;
                civImage.setImageResource(R.drawable.user);
            }
            updateUI(true);
        }
        //Signout showunauthenticatedUI
        else {
            if (a == true) {
                updateUiFacebook(true);
            } else {
                googleSignInResult = null;
                updateUI(false);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("Signinfailed", "onConnectionFailed:" + connectionResult);
        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.googleSignIn:
                signIn();
                break;
//        //    case R.id.btnSignOut:
//                signOut();
//                break;
            case R.id.civImage:
                Intent i = new Intent(getApplicationContext(), FacebookGoogleActivity.class);
                i.putExtra("Name", personName);
//               i.putExtra("Email",personEmailId);
                i.putExtra("Photo", photo);
                i.putExtra("int", c);
                startActivity(i);
                overridePendingTransition(R.anim.start, R.anim.end);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handlingSignInResult(result);
        }
    }

    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGAC);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            GoogleSignInResult result = opr.get();
            handlingSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handlingSignInResult(googleSignInResult);
                }
            });
        }
        if (a == true) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                updateUiFacebook(true);
            }
        }
    }

    public void nextActivity(Profile profile) {
        if (profile != null) {
            a = true;
            Log.e("name", profile.getFirstName());
            facebookName = profile.getFirstName();
            facebookMiddleName = profile.getMiddleName();
            facebookLastName = profile.getLastName();
            personName = facebookName + " " + facebookMiddleName + " " + facebookLastName;
            tvEmailId.setText(personName);
            tvGoogleName.setText("Welcome..!");
            c = 20;
//          tvEmailId.setText(facebookName + " " + facebookMiddleName + " " + facebookLastName);
            personPhotoUrl = profile.getProfilePictureUri(200, 200);
            photo = String.valueOf(personPhotoUrl);
            if (personPhotoUrl != null) {
                Glide.with(getApplicationContext()).load(personPhotoUrl)
                        .thumbnail(0.6f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(civImage);
            } else civImage.setImageResource(R.drawable.facebook);
            updateUiFacebook(true);
        } else
            updateUiFacebook(false);
    }

    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            nextActivity(profile);
            updateUiFacebook(true);
        }
    }

    protected void onPause() {
        super.onPause();
        //Facebook login
        accessTokenTracker.isTracking();
        profileTracker.isTracking();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        mProgressDialog.dismiss();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            llGoogleView.setVisibility(View.VISIBLE);
            //rlFacebookView.setVisibility(View.GONE);
            facebookLogin.setVisibility(View.GONE);
            googleSignIn.setVisibility(View.GONE);
            civImage.setVisibility(View.VISIBLE);
            tvGoogleName.setVisibility(View.VISIBLE);
            tvEmailId.setVisibility(View.VISIBLE);
            //llSignInLayout.setVisibility(View.VISIBLE);
//            btnSignOut.setVisibility(View.VISIBLE);
            // btnRevokeAccess.setVisibility(View.VISIBLE);
        } else {
            llGoogleView.setVisibility(View.VISIBLE);
            //   rlFacebookView.setVisibility(View.GONE);
            //          btnSignOut.setVisibility(View.GONE);
            googleSignIn.setVisibility(View.VISIBLE);
            tvGoogleName.setVisibility(View.GONE);
            tvEmailId.setVisibility(View.GONE);
            civImage.setVisibility(View.GONE);
            facebookLogin.setVisibility(View.VISIBLE);
            //btnRevokeAccess.setVisibility(View.GONE);
        }
    }

    public void updateUiFacebook(boolean b) {
        if (b) {
//            rlFacebookView.setVisibility(View.VISIBLE);
            facebookLogin.setVisibility(View.GONE);
            llGoogleView.setVisibility(View.VISIBLE);
            tvGoogleName.setVisibility(View.VISIBLE);
            civImage.setVisibility(View.VISIBLE);
            googleSignIn.setVisibility(View.GONE);
            //facebookLogin.setVisibility(View.VISIBLE);
            tvEmailId.setVisibility(View.VISIBLE);
            //        btnSignOut.setVisibility(View.GONE);
        } else {
            llGoogleView.setVisibility(View.VISIBLE);
            tvGoogleName.setVisibility(View.GONE);
            tvEmailId.setVisibility(View.GONE);
            civImage.setVisibility(View.GONE);
            googleSignIn.setVisibility(View.VISIBLE);
            facebookLogin.setVisibility(View.VISIBLE);
//            rlFacebookView.setVisibility(View.GONE);
            //      btnSignOut.setVisibility(View.GONE);
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();

            a = false;
        }
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean g) {
        if (g==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppThemes);
            recreate();
            j=true;
            h=true;
            editor.putBoolean("j",j);
            editor.commit();
            scNightMode.setChecked(true);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme);
            recreate();
            j=false;
            h=false;
            editor.putBoolean("j",j);
            editor.commit();
            scNightMode.setChecked(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
