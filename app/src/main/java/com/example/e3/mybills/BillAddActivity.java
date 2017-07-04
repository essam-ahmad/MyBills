package com.example.e3.mybills;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class BillAddActivity extends TabActivity {
    TabHost tabHost;
    //region bill_m
    EditText Number,Year,date,desc,disc;
    TextView Customer_Name,Customer_id,Tootal;
    RadioGroup mySelection;
    int selectedValue;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        ActiveTab();
        bill_m();
        bill_d();
    }
  public void ActiveTab(){
      tabHost = (TabHost)findViewById(android.R.id.tabhost);
      tabHost.setup();

      TabHost.TabSpec Bill_m = tabHost.newTabSpec("ABA UM");
      TabHost.TabSpec Bill_d = tabHost.newTabSpec("ABA DOIS");
      Bill_m.setIndicator(getResources().getString(R.string.Main_Bill));
      Bill_m.setContent(R.id.tab1);

      Bill_d.setIndicator(getResources().getString(R.string.Add_Items_for_Bill));
      Bill_d.setContent(R.id.tab2);

      tabHost.addTab(Bill_m);
      tabHost.addTab(Bill_d);
  }
    public void bill_m(){
        mySelection = (RadioGroup)findViewById(R.id.radiogruop);
        mySelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioButton7:
                        selectedValue = 1;
                        Toast.makeText(getBaseContext(), String.valueOf(selectedValue), Toast.LENGTH_LONG).show();

                        break;
                    case R.id.radioButton8:
                        selectedValue = 2;
                        Toast.makeText(getBaseContext(), String.valueOf(selectedValue), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        String Get_Date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        String Get_Year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        Number =(EditText)findViewById(R.id.Number_Bill);
        Year =(EditText)findViewById(R.id.year);
        date =(EditText)findViewById(R.id.date);
        desc =(EditText)findViewById(R.id.desc);
        disc =(EditText)findViewById(R.id.disc);

        Year.setText(Get_Year);
        date.setText(Get_Date);




    }
    public void bill_d(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Add_item_to_bill_d);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BillAddActivity.this,ItemsActivity.class);

               i.putExtra("ImFromBillAdd",1);

                startActivityForResult(i,1);


            }
        });

         }
}
