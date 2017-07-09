package com.example.e3.mybills;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.e3.mybills.R.string.add_Customer;
import static com.example.e3.mybills.R.string.press_to_add_customer;

/**
 * Created by EBRHEEM on 7/9/2017.
 */

public class Edit_Bill extends AppCompatActivity {
    TabHost tabHost;
    //region bill_m
    EditText Number, Year, date, desc, disc;
    TextView Customer_Name, Customer_id, Total, Customer_Edit;
    RadioGroup mySelection;
    int selectedValue;
    //String idCust;
    double price,Qty,Result,Disc;
    String getBillNumber;
    public int i = 0;
    public ArrayList<bill_d> arrBill_d = new ArrayList<bill_d>();
    ListView list;
    Bill_d_Adabter bill_d;
    ArrayList<bill_d> item = new ArrayList<bill_d>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActiveTab();
        GetBillNumber();
        bill_m();
        bill_d();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetBillNumber();
        bill_m();
        bill_d();
    }

    public void ActiveTab() {
        setTitle(R.string.Edit_Bill);
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem iteme) {
        int id = iteme.getItemId();
    if (id == android.R.id.home) {
        finish();
    } if (id == R.id.Save_bill){
            String Bill_no = Number.getText().toString();
            String year = Year.getText().toString();
            String Date = date.getText().toString();
            String Desc = desc.getText().toString();
            String Disc = disc.getText().toString();
            if (checkData(Bill_no, year, Date)) {
                DataManager dataManager = new DataManager(Edit_Bill.this);
              dataManager.UpdateBill_m(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq(),year, Bill_no, String.valueOf(selectedValue), Date,
                        String.valueOf(Customer_id.getText()), Disc, Desc, "100");
                if (item.isEmpty()) {
                    Toast.makeText(getBaseContext(), "تعديل البيل دي", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    if (Integer.parseInt(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq()) != -1) {

                        for (int m = 0; m < arrBill_d.size(); m++) {

                            dataManager.addBill_d(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq(), year,String.valueOf(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_no()) ,
                                    arrBill_d.get(m).get_col_bill_d_seq(),
                                    arrBill_d.get(m).get_col_itm_code(),
                                    arrBill_d.get(m).get_col_itm_price(),
                                    arrBill_d.get(m).get_col_itm_cost(),
                                    arrBill_d.get(m).get_col_itm_qty());
                        }
                        Toast.makeText(getBaseContext(), R.string.Done_Adding, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

    }

    }
        return false; }

    public void bill_m() {
        selectedValue= Integer.parseInt(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_type());
        mySelection = (RadioGroup) findViewById(R.id.radiogruop);
        if (selectedValue == 1){
            mySelection.check(R.id.radioButton7);
        }
        else {
            mySelection.check(R.id.radioButton8);
        }
        mySelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton7:
                        selectedValue = 1;

                        break;
                    case R.id.radioButton8:
                        selectedValue = 2;
                        break;
                }
            }
        });

        Number = (EditText) findViewById(R.id.Number_Bill);
        Number.setFocusable(false);
        Number.setEnabled(false);
        Year = (EditText) findViewById(R.id.year);
        date = (EditText) findViewById(R.id.date);
        desc = (EditText) findViewById(R.id.desc);
        disc = (EditText) findViewById(R.id.disc);

        Number.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_no());
        Year.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_yr());
        date.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_date());
        desc.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_desc());
        disc.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_disc_amt());
        Customer_Name = (TextView) findViewById(R.id.C_nameOfCust);
        Customer_id = (TextView) findViewById(R.id.C_codeOfCust);
        Customer_Edit = (TextView) findViewById(R.id.text_of_add_Cost);
        Customer_id.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_c_code());
        if (!Customer_id.getText().equals("")){
            Customer_Edit.setText(R.string.edit_Customer);
            Customer_Name.setText(new DataManager(this).getCustomerById(Customer_id.getText().toString()).get_col_c_name());
            Customer_Name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(Edit_Bill.this);
                    yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                    yesOrNoBuilder.setMessage(Customer_Name.getText());
                    yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Customer_Edit.setText(add_Customer);
                            Customer_Name.setText(press_to_add_customer);
                            Customer_id.setText("");
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
            });}
        Customer_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit_Bill.this, customersActivity.class);
                i.putExtra("ImFromBillAdd", 1);
                startActivityForResult(i, 2);

            }
        });

    }
    public void bill_d() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Add_item_to_bill_d);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Edit_Bill.this, ItemsActivity.class);
                i.putExtra("ImFromBillAdd", 1);
                startActivityForResult(i, 1);
            }
        });
          bill_d = new Bill_d_Adabter(this, new DataManager(this).getAllBill_d(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq()));
         list = (ListView) findViewById(R.id.listView_bill_d);
        list.setAdapter(bill_d);
    }
    public void GetBillNumber(){
        Bundle b = getIntent().getExtras();
        getBillNumber= b.getString("ID_BIL");
    }
    @Override
    public void onActivityResult(int code, int result, final Intent data) {
        if ((code == 1) && (result == RESULT_OK)) {
            String ItemCode = data.getExtras().getString("ItemCode");
            String ItemQty = data.getExtras().getString("ItemQty");
            String ItemPrice = data.getExtras().getString("ItemPrice");
            DataManager dataBase = new DataManager(Edit_Bill.this);
            items dummyItem = dataBase.getItemById(Integer.parseInt(ItemCode));
            item.add(new bill_d(ItemCode, ItemPrice, String.valueOf(dummyItem.get_col_itm_cost()), ItemQty, dummyItem.get_col_itm_name()));
            bill_d = new Bill_d_Adabter(this, item);

           final ListView list = (ListView) findViewById(R.id.listView_bill_d_Edit);
            list.setAdapter(bill_d);
            //region on click list view
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(Edit_Bill.this);
                    yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                    yesOrNoBuilder.setMessage(item.get(position).get_col_itm_name());
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
            price= Double.parseDouble(ItemPrice);
            Qty= Double.parseDouble(ItemQty);
            Total=(TextView)findViewById(R.id.tootal);
            Total.setText("0");
           Total.setText(Result+"");
            bill_d e = new bill_d();
            e.set_col_bill_d_seq(String.valueOf(i + 1));
            e.set_col_itm_code(ItemCode);
            e.set_col_itm_price(ItemPrice);
            e.set_col_itm_qty(ItemQty);
            e.set_col_itm_cost(String.valueOf(dummyItem.get_col_itm_cost()));
            arrBill_d.add(e);
            i++;
        } else if (((code == 2) && (result == RESULT_OK))) {
            Customer_Name.setText(data.getStringExtra("get_col_c_name"));
            Customer_id.setText(data.getStringExtra("get_col_c_code"));
            Customer_Edit.setText(R.string.edit_Customer);

        }
    }
    public boolean checkData(String Number_Bil, String Years, String dates) {
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

        return result;
    }
}
