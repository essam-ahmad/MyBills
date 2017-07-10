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

@SuppressWarnings("deprecation")
public class BillAddActivity extends AppCompatActivity {
    TabHost tabHost;
    EditText Number, Year, date, desc, disc;
    TextView Customer_Name, Customer_id, Total, NetTotal;
    RadioGroup mySelection;
    int selectedValue = 1;
    double price, Qty, _total, Disc;
    public int i = 0;
    public ArrayList<bill_d> arrBill_d = new ArrayList<bill_d>();
    ArrayList<bill_d> item = new ArrayList<bill_d>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        setTitle(getResources().getString(R.string.Add_New_Bil));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActiveTab();
        bill_m();
        bill_d();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_button_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Save_bill) {
            String Bill_no = Number.getText().toString();
            String year = Year.getText().toString();
            String Date = date.getText().toString();
            String Desc = desc.getText().toString();
            String Disc = disc.getText().toString();
            setDefaultValuesIfNull();
            if (checkData(Bill_no, year, Date, _total)) {
                DataManager dataManager = new DataManager(BillAddActivity.this);
                long Bill_seq = dataManager.addBill_m(year, Bill_no, String.valueOf(selectedValue), Date,
                        String.valueOf(Customer_id.getText()), Disc, Desc, Total.getText().toString());
                if (Bill_seq != -1) {
                    for (int m = 0; m < arrBill_d.size(); m++) {
                        dataManager.addBill_d(String.valueOf(Bill_seq), year, Bill_no,
                                arrBill_d.get(m).get_col_bill_d_seq(),
                                arrBill_d.get(m).get_col_itm_code(),
                                arrBill_d.get(m).get_col_itm_price(),
                                arrBill_d.get(m).get_col_itm_cost(),
                                arrBill_d.get(m).get_col_itm_qty());
                    }
                    Toast.makeText(getBaseContext(), R.string.Done_Adding, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Number.setError(getResources().getString(R.string.The_number_already_exists));
                    Toast.makeText(getBaseContext(), R.string.The_number_already_exists, Toast.LENGTH_LONG).show();
                }
            }
        }
        if (id == android.R.id.home) {
            finish();
        }
        return false;
    }

    public void ActiveTab() {
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

    public void bill_m() {
        mySelection = (RadioGroup) findViewById(R.id.radiogruop);
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
        String Get_Date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        String Get_Year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        Number = (EditText) findViewById(R.id.Number_Bill);
        Year = (EditText) findViewById(R.id.year);
        date = (EditText) findViewById(R.id.date);
        desc = (EditText) findViewById(R.id.desc);
        disc = (EditText) findViewById(R.id.disc);
        Total = (TextView) findViewById(R.id.tootal);
        //region on click disc
        disc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _total = Double.parseDouble(Total.getText().toString());
                if (disc.getText().toString().trim().length() > 0) {
                    if (Double.parseDouble(disc.getText().toString()) > _total) {
                        disc.setError(getResources().getString(R.string.The_discount_is_large));
                        NetTotal = (TextView) findViewById(R.id.NetTotal);
                        NetTotal.setText(_total + "");
                    } else {
                        if (disc.getText().toString().trim().length() > 0) {
                            Disc = Double.parseDouble(String.valueOf(s));
                            NetTotal = (TextView) findViewById(R.id.NetTotal);
                            NetTotal.setText("0");
                            NetTotal.setText(_total - Disc + "");
                        }
                    }
                } else if (disc.getText().toString().isEmpty()) {
                    NetTotal = (TextView) findViewById(R.id.NetTotal);
                    NetTotal.setText("0");
                    NetTotal.setText(_total + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        Number.setText(new DataManager(this).Get_Bill_No() + 1 + "");
        Year.setText(Get_Year);
        date.setText(Get_Date);
        Customer_Name = (TextView) findViewById(R.id.C_nameOfCust);
        Customer_id = (TextView) findViewById(R.id.C_codeOfCust);
        Total.setText("0");
        Customer_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillAddActivity.this, customersActivity.class);
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
                Intent i = new Intent(BillAddActivity.this, ItemsActivity.class);
                i.putExtra("ImFromBillAdd", 1);
                startActivityForResult(i, 1);
            }
        });
    }

    public void getTotal(String ItemPrice, String ItemQty) {
        price = Double.parseDouble(ItemPrice);
        Qty = Double.parseDouble(ItemQty);
        Total = (TextView) findViewById(R.id.tootal);
        Total.setText(String.valueOf(Double.parseDouble(Total.getText().toString()) + (price * Qty)));
    }

    @Override
    public void onActivityResult(int code, int result, final Intent data) {
        if ((code == 1) && (result == RESULT_OK)) {
            String ItemCode = data.getExtras().getString("ItemCode");
            String ItemQty = data.getExtras().getString("ItemQty");
            String ItemPrice = data.getExtras().getString("ItemPrice");
            DataManager dataBase = new DataManager(BillAddActivity.this);
            items dummyItem = dataBase.getItemById(Integer.parseInt(ItemCode));
            item.add(new bill_d(ItemCode, ItemPrice, String.valueOf(dummyItem.get_col_itm_cost()), ItemQty, dummyItem.get_col_itm_name()));
            final Bill_d_Adapter bill_d = new Bill_d_Adapter(this, item);
            final ListView list = (ListView) findViewById(R.id.listView_bill_d);
            list.setAdapter(bill_d);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(BillAddActivity.this);
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
            getTotal(ItemPrice, ItemQty);
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
        }
    }

    public void setDefaultValuesIfNull() {
        if (disc == null || disc.length() == 0) {
            disc.setText("0");
        }
        if (Customer_id == null || Customer_id.length() == 0) {
            Customer_id.setText("");
        }
    }

    public boolean checkData(String Number_Bil, String Years, String dates, double resultToota) {
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
        if (item.isEmpty()) {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.There_are_no_items), Toast.LENGTH_LONG).show();
            result = false;
        }
        if (Double.parseDouble(disc.getText().toString()) > resultToota) {
            disc.setError(getResources().getString(R.string.The_discount_is_large));
            Toast.makeText(getBaseContext(), R.string.The_discount_is_large, Toast.LENGTH_LONG).show();

            result = false;
        }
        return result;
    }
}
