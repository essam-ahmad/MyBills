package com.example.e3.mybills;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
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

import java.util.Locale;

public class BillActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataManager ch = new DataManager(this);
        ListView list = (ListView) findViewById(R.id.listView_item);
        Bill_Adapter lazy = new Bill_Adapter(BillActivity.this, ch.getAllBill_m());
        list.setAdapter(lazy);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        }

        return super.onOptionsItemSelected(item);
    }
    public void setLocale(String lang) {
        finish();
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Start_screen.class);
        startActivity(refresh);
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
            builder.setTitle(R.string.Change_Language);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int pos) {
                    if (pos == 0) {

                        setLocale("en");
                    } else if (pos == 1) {

                        setLocale("ar");
                    }

                }
            });

            AlertDialog alert = builder.create();
            alert.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
