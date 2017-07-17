package com.example.e3.mybills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowCustBills extends AppCompatActivity {
    Bundle C_code;
    DataManager ch = new DataManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cust_bills);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        C_code = getIntent().getExtras();
        String Customer_No = C_code.getString("get_col_c_code");
        bill_m result = ch.getBill_m_ById(null, Customer_No);
        if (result.get_col_bill_seq() == null) {
            setTitle(getResources().getString(R.string.Customer_Bill)+":"+ch.getCustomerById(Customer_No).get_col_c_name());
            setContentView(R.layout.there_is_no_bills);
            Toast.makeText(getBaseContext(),R.string.There_Is_No_Bills,Toast.LENGTH_LONG).show();
        } else {
            setTitle(getResources().getString(R.string.Customer_Bill)+":"+ch.getCustomerById(Customer_No).get_col_c_name());
            ListView list = (ListView) findViewById(R.id.listView_item);
            Bill_Adapter lazy = new Bill_Adapter(ShowCustBills.this, ch.getAllBill_m(Customer_No));
            list.setAdapter(lazy);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
    if (id == android.R.id.home) {
        finish();
    }
        return true;
}
}
