package com.example.e3.mybills;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.support.v7.app.ActionBar;
@SuppressWarnings("deprecation")
public class BillAddActivity extends TabActivity {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        ActiveTab();
         this.setTitle("vfdvbdvdvdvd");

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
    public void bill_d(){

      }
    public void bill_m(){

         }
}
