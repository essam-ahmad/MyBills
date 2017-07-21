package com.example.e3.mybills;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import java.util.Locale;

public class BillActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DataManager ch = new DataManager(this);
    ListView list;
    Bill_Adapter lazy;
    FirebaseAnalytics mFirebaseAnalytics;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle(getResources().getString(R.string.app_name));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        list = (ListView) findViewById(R.id.listView_item);
        //FirebaseCrash.report(new Exception("My first Android non-fatal error"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        lazy = new Bill_Adapter(BillActivity.this, ch.getAllBill_m(null));
        list.setAdapter(lazy);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.pressBack), Toast.LENGTH_SHORT).show();
        }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar items clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(BillActivity.this,BillAddEditActivity.class);
            Bundle b = new Bundle();
            b.putString("action","add");
            intent.putExtras(b);
            startActivity(intent);
            return true;
        }if (id == R.id.search) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) item.getActionView();
            searchView.setInputType(2);
            searchView.setQueryHint(getResources().getString(R.string.InsertBillNO));
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            android.support.v7.widget.SearchView.OnQueryTextListener textChangeListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    DataManager ch = new DataManager(BillActivity.this);
                    ListView list = (ListView) findViewById(R.id.listView_item);
                    if (newText.equals("")){
                        Bill_Adapter lazy = new Bill_Adapter(BillActivity.this, ch.getAllBill_m(null));
                        list.setAdapter(lazy);
                    }else {
                        Bill_Adapter lazy = new Bill_Adapter(BillActivity.this, ch.SearchBill_m(newText));
                        list.setAdapter(lazy);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(textChangeListener);
        }
            return super.onOptionsItemSelected(item);
        }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language,true);
    }

    public void changeLang(String lang,Boolean onCreateActivity) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        DisplayMetrics dm = res.getDisplayMetrics();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        /*android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(myLocale);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());*/
        if (!onCreateActivity){
            this.recreate();
        }
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
        editor.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view items clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_items) {
            Intent i = new Intent(BillActivity.this,ItemsActivity.class);
            Bundle bundle= new Bundle();
            bundle.putInt("ImFromBillAdd",2);
            i.putExtras(bundle);
            startActivity(i);
        } else if (id == R.id.nav_customers) {
            Intent i = new Intent(BillActivity.this,customersActivity.class);
            Bundle bundle= new Bundle();
            bundle.putInt("ImFromBillAdd",2);
            i.putExtras(bundle);
            startActivity(i);
        }else if (id == R.id.Lang){
            final CharSequence[] items = { "English", "العربية"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_language_black_24dp);
            builder.setTitle(R.string.Change_Language);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int pos) {
                    if (pos == 0) {
                        changeLang("en",false);
                    } else if (pos == 1) {
                        changeLang("ar",false);

                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else if(id==R.id.Support) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_support_devices_black_24dp);
            builder.setTitle(R.string.Support);
            builder.setMessage(R.string.GoToMarket);
            builder.setPositiveButton(R.string.Download, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5432474436276944851" )));
               }
           });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }else if (id==R.id.Info){
            Intent intent = new Intent(BillActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

