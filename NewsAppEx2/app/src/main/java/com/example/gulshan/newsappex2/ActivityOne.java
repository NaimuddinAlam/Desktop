package com.example.gulshan.newsappex2;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ActivityOne extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ActivityOne";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    ImageView ivTab;
    TextView search;
    PopupMenu popupMenu;
    TabLayout tabLayout;
    SharedPreferences sharedPreferences;
    boolean j=false,h;
    @TargetApi(Build.VERSION_CODES.FROYO)
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
        setContentView(R.layout.activity_main);
        search = (TextView) findViewById(R.id.editsearchtxt);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.gulshan.newsappex2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //popup menu for change of fragments directly without scrolling
        ivTab = findViewById(R.id.ivTab);
        ivTab.setOnClickListener(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);

        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_assignment);

        tabLayout.getTabAt(0).setText(getResources().getString(R.string.ao_news));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.ao_music));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.ao_comedy));
        tabLayout.getTabAt(3).setText(getResources().getString(R.string.ao_rel));
        tabLayout.getTabAt(4).setText(getResources().getString(R.string.ao_film));
        tabLayout.getTabAt(5).setText(getResources().getString(R.string.ao_tech));
        tabLayout.getTabAt(6).setText(getResources().getString(R.string.ao_sport));
        tabLayout.getTabAt(7).setText(getResources().getString(R.string.ao_life));
        tabLayout.getTabAt(8).setText(getResources().getString(R.string.ao_shiksha));

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_arrow:
                        Intent intent1 = new Intent(ActivityOne.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;

                    case R.id.ic_android:

                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(ActivityOne.this, ActivityTwo.class);
                        startActivity(intent2);
                        finish();
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(ActivityOne.this, ActivityThree.class);
                        startActivity(intent3);
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new NewsVideoFragment());
        adapter.addFragment(new Music_Dance_Video_Fragment());
        adapter.addFragment(new Comedy_Video_Fragment());
        adapter.addFragment(new Religion_Video_Fragment());
        adapter.addFragment(new Film_Tv_Video_Fragment());
        adapter.addFragment(new Technology_Video_Fragment());
        adapter.addFragment(new Sports_Video_Fragment());
        adapter.addFragment(new Lifestyle_Video_Fragment());
        adapter.addFragment(new Shiksha_Video_Fragment());

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        popupMenu = new PopupMenu(ActivityOne.this, ivTab);
        getMenuInflater().inflate(R.menu.popupv, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.vnews:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.vDance:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.vComedy:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.vReligion:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.vFilm:
                        mViewPager.setCurrentItem(4);
                        break;
                    case R.id.vtechnology:
                        mViewPager.setCurrentItem(5);
                        break;
                    case R.id.vSports:
                        mViewPager.setCurrentItem(6);
                        break;
                    case R.id.vLifestyle:
                        mViewPager.setCurrentItem(7);
                        break;
                    case R.id.vshiksha:
                        mViewPager.setCurrentItem(8);
                        break;

                }

                return false;
            }
        });
        popupMenu.show();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
    }
