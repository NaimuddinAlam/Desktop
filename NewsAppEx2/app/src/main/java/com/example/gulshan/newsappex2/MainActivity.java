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
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    ImageView ivTab;
    TextView search;
    PopupMenu popupMenu;
    TabLayout tabLayout;
    SharedPreferences sharedPreferences;
    boolean h,j=false;
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



        int status= ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(status== PackageManager.PERMISSION_GRANTED){
            onStart();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
        }



        search = (TextView)findViewById(R.id.editsearchtxt);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(v.getContext(),SearchActivity.class);
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

        tabLayout.getTabAt(0).setText(getResources().getString(R.string.ma_sport));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.ma_enter));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.ma_gad));
        tabLayout.getTabAt(3).setText(getResources().getString(R.string.ma_auto));
        tabLayout.getTabAt(4).setText(getResources().getString(R.string.ma_poli));
        tabLayout.getTabAt(5).setText(getResources().getString(R.string.ma_crime));
        tabLayout.getTabAt(6).setText(getResources().getString(R.string.ma_edu));
        tabLayout.getTabAt(7).setText(getResources().getString(R.string.ma_jokes));
        tabLayout.getTabAt(8).setText(getResources().getString(R.string.ma_crick));
        tabLayout.getTabAt(9).setText(getResources().getString(R.string.ma_top));
        tabLayout.getTabAt(10).setText(getResources().getString(R.string.ma_life));
        tabLayout.getTabAt(11).setText(getResources().getString(R.string.ma_helth));
        tabLayout.getTabAt(12).setText(getResources().getString(R.string.ma_business));

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_arrow:

                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(MainActivity.this, ActivityOne.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(MainActivity.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(MainActivity.this, ActivityThree.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SportsFragment());
        adapter.addFragment(new EntertainmentFragment());
        adapter.addFragment(new GedgetsFragment());
        adapter.addFragment(new AutoFragment());
        adapter.addFragment(new PoliticsFragment());
        adapter.addFragment(new CrimeFragment());
        adapter.addFragment(new EducationFragment());
        adapter.addFragment(new FunFragment());
        adapter.addFragment(new CricketFragment());
        adapter.addFragment(new PopulerFragment());
        adapter.addFragment(new LifeStyleFragment());
        adapter.addFragment(new HealthFragment());
        adapter.addFragment(new BusinessFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        popupMenu = new PopupMenu(MainActivity.this, ivTab);
        getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.sports:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.entertainment:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.gadgets:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.auto:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.politics:
                        mViewPager.setCurrentItem(4);
                        break;
                    case R.id.crime:
                        mViewPager.setCurrentItem(5);
                        break;
                    case R.id.education:
                        mViewPager.setCurrentItem(6);
                        break;
                    case R.id.fun:
                        mViewPager.setCurrentItem(7);
                        break;
                    case R.id.cricket:
                        mViewPager.setCurrentItem(8);
                        break;
                    case R.id.top:
                        mViewPager.setCurrentItem(9);
                        break;
                    case R.id.lifeStyle:
                        mViewPager.setCurrentItem(10);
                        break;
                    case R.id.health:
                        mViewPager.setCurrentItem(11);
                        break;
                    case R.id.business:
                        mViewPager.setCurrentItem(12);
                        break;
                }

                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onStart();
        } else {
            Toast.makeText(MainActivity.this, "User is Not Intrested to Read...",
                    Toast.LENGTH_LONG).show();
        }
    }
}
