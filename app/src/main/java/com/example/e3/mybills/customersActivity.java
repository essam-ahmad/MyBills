package com.example.e3.mybills;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class customersActivity extends AppCompatActivity {
    ListView list;
    DataManager ch = new DataManager(this);
    int FromBill;
    Bundle b;
    public static Activity fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        setTitle(getResources().getString(R.string.customers));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fa=this;
        list = (ListView) findViewById(R.id.listView_item);
        listItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listItem();
    }

    public void listItem() {
        b=getIntent().getExtras();
        FromBill=b.getInt("ImFromBillAdd");
        if (FromBill==1){
        DataManager ch = new DataManager(this);
        //list = (ListView) findViewById(R.id.listView_item);
        customers_Adapter lazy = new customers_Adapter(customersActivity.this, ch.getAllCustomers(),1);
        list.setAdapter(lazy);
    }else {
            DataManager ch = new DataManager(this);
           // list = (ListView) findViewById(R.id.listView_item);
            customers_Adapter lazy = new customers_Adapter(customersActivity.this, ch.getAllCustomers(),2);
            list.setAdapter(lazy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i = new Intent(customersActivity.this, CustomersAddEditActivity.class);
            Bundle b = new Bundle();
            b.putString("action", "add");
            i.putExtras(b);
            startActivity(i);
        }
        if (id == R.id.search) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) item.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            if (FromBill==1){
            android.support.v7.widget.SearchView.OnQueryTextListener textChangeListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    ListView list = (ListView) findViewById(R.id.listView_item);
                    customers_Adapter lazy = new customers_Adapter(customersActivity.this, ch.SearchCustomer(newText),1);
                    list.setAdapter(lazy);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(textChangeListener);
        }else {
                android.support.v7.widget.SearchView.OnQueryTextListener textChangeListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        ListView list = (ListView) findViewById(R.id.listView_item);
                        customers_Adapter lazy = new customers_Adapter(customersActivity.this, ch.SearchCustomer(newText),2);
                        list.setAdapter(lazy);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }
                };
                searchView.setOnQueryTextListener(textChangeListener);
            }
        } if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}