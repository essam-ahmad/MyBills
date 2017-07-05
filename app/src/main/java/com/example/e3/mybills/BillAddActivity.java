package com.example.e3.mybills;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ArrayList<BILL_DCalss> item = new ArrayList<BILL_DCalss>();
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
      FloatingActionButton save = (FloatingActionButton)findViewById(R.id.toolbar_save);
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String Number_bill = Number.getText().toString();
        String year = Year.getText().toString();
        String Date = date.getText().toString();
        String Desc = desc.getText().toString();
        setDefaultValuesIfNull();
        checkData(Number_bill,year,Date,Desc);
    }
});
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
        Customer_Name = (TextView)findViewById(R.id.C_nameOfCust);
        Customer_id = (TextView)findViewById(R.id.C_codeOfCust);

        Customer_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillAddActivity.this,customersActivity.class);
                i.putExtra("ImFromBillAdd",1);
                startActivityForResult(i,2);

            }
        });



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
    @Override
    public void onActivityResult(int code , int esult , final Intent data){
        if ((code ==1)&&(esult == RESULT_OK)){


            String  ItemCode = data.getExtras().getString("ItemCode");
            String  Itemqty =data.getExtras().getString("ItemCost");
            String  ItemPrice = data.getExtras().getString("ItemPrice");

            DataManager dataBase=new DataManager(BillAddActivity.this);
            item.add(new BILL_DCalss(dataBase.getItemById(Integer.parseInt(ItemCode)).get_col_itm_name(),ItemPrice,ItemCode,Itemqty));
            final Bill_d_Adabter bill_d= new Bill_d_Adabter(this,item);
            final ListView list = (ListView) findViewById(R.id.listView_bill_d);
            list.setAdapter(bill_d);
            //region on click list view
      list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(BillAddActivity.this);
        yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
        yesOrNoBuilder.setMessage(item.get(position).name);
        yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                item.remove(position);
                list.setAdapter(bill_d);
            }
        });
        yesOrNoBuilder.setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        yesOrNoBuilder.show();
        return false;
    }
});
            //endregion
            
           /* bill_d e =new bill_d();
            e.set_col_bill_seq(Integer.parseInt(id));
            e.set_col_bill_d_seq(String.valueOf(i));
            e.set_col_itm_price(Integer.parseInt(pricee));
            e.set_col_itm_qty(Integer.parseInt(itemqtye));
            arrBill_d.add(e);
            i++;*/


        }else if (((code==2)&&(esult==RESULT_OK))){

            Customer_Name.setText(data.getStringExtra("get_col_c_name"));
            Customer_id.setText(getResources().getString(R.string.customer_code)+":             " +data.getStringExtra("get_col_c_code"));

        }
}
    public void setDefaultValuesIfNull() {
        if (disc == null || disc.length() == 0) {
            disc.setText("0");
        } if (Customer_id == null || Customer_id.length() == 0) {
            Customer_id.setText("null");
        }
    }
    public boolean checkData(String Number_Bil, String Years , String dates ,String describ ) {
        boolean result = true;
        if (Number_Bil == null || Number_Bil.length() == 0) {
            Number.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (Years == null || Years.length() == 0) {
            Year.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (dates == null || dates.length() == 0) {
            date.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (describ == null || describ.length() == 0) {
            desc.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        return result;
    }
}
