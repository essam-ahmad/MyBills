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
    EditText etBillNo, etYearNo, etBillDate, etDesc, etDisc;
    TextView tvCustomer_Name, tvCustomer_id, Total, NetTotal;
    RadioGroup mySelection;
    int selectedValue = 1;
    double price, Qty, _total, Disc;
    public ArrayList<bill_d> arrBill_d = new ArrayList<bill_d>();

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
            String Bill_no = etBillNo.getText().toString();
            String year = etYearNo.getText().toString();
            String Date = etBillDate.getText().toString();
            String Desc = etDesc.getText().toString();
            String Disc = etDisc.getText().toString();
            setDefaultValuesIfNull();
            if (checkData(Bill_no, year, Date, _total)) {
                DataManager dataManager = new DataManager(BillAddActivity.this);
                long Bill_seq = 1/*dataManager.addBill_m(year, Bill_no, String.valueOf(selectedValue), Date,
                        String.valueOf(tvCustomer_id.getText()), Disc, Desc, Total.getText().toString())*/;
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
                    etBillNo.setError(getResources().getString(R.string.The_number_already_exists));
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
        String Get_etYearNo = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        etBillNo = (EditText) findViewById(R.id.bill_no);
        etYearNo = (EditText) findViewById(R.id.year);
        etBillDate = (EditText) findViewById(R.id.date);
        etDesc = (EditText) findViewById(R.id.desc);
        etDisc = (EditText) findViewById(R.id.disc);
        Total = (TextView) findViewById(R.id.total);
        //region on click etDisc
        etDisc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _total = Double.parseDouble(Total.getText().toString());
                if (etDisc.getText().toString().trim().length() > 0) {
                    if (Double.parseDouble(etDisc.getText().toString()) > _total) {
                        etDisc.setError(getResources().getString(R.string.The_discount_is_large));
                        NetTotal = (TextView) findViewById(R.id.NetTotal);
                        NetTotal.setText(_total + "");
                    } else {
                        if (etDisc.getText().toString().trim().length() > 0) {
                            Disc = Double.parseDouble(String.valueOf(s));
                            NetTotal = (TextView) findViewById(R.id.NetTotal);
                            NetTotal.setText("0");
                            NetTotal.setText(_total - Disc + "");
                        }
                    }
                } else if (etDisc.getText().toString().isEmpty()) {
                    NetTotal = (TextView) findViewById(R.id.NetTotal);
                    NetTotal.setText("0");
                    NetTotal.setText(_total + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etBillNo.setText(new DataManager(this).Get_Bill_No() + 1 + "");
        etYearNo.setText(Get_etYearNo);
        etBillDate.setText(Get_Date);
        tvCustomer_Name = (TextView) findViewById(R.id.C_nameOfCust);
        tvCustomer_id = (TextView) findViewById(R.id.C_codeOfCust);
        Total.setText("0");
        tvCustomer_Name.setOnClickListener(new View.OnClickListener() {
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
        Total = (TextView) findViewById(R.id.total);
        Total.setText(String.valueOf(Double.parseDouble(Total.getText().toString()) + (price * Qty)));
    }

    @Override
    public void onActivityResult(int code, int result, final Intent data) {
        if ((code == 1) && (result == RESULT_OK)) {
            final Bill_d_Adapter bill_d = new Bill_d_Adapter(this, arrBill_d);
            String ItemCode = data.getExtras().getString("ItemCode");
            final String ItemQty = data.getExtras().getString("ItemQty");
            final String ItemPrice = data.getExtras().getString("ItemPrice");
            DataManager dataBase = new DataManager(BillAddActivity.this);
            items dummyItem = dataBase.getItemById(Integer.parseInt(ItemCode));
            arrBill_d.add(new bill_d(ItemCode, ItemPrice, String.valueOf(dummyItem.get_col_itm_cost()), ItemQty, dummyItem.get_col_itm_name(), com.example.e3.mybills.bill_d.rowStatus.newRow));
            getTotal(ItemPrice, ItemQty);
            final ListView list = (ListView) findViewById(R.id.listView_bill_d);
            list.setAdapter(bill_d);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(BillAddActivity.this);
                    yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                    yesOrNoBuilder.setMessage(arrBill_d.get(position).get_col_itm_name());
                    yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            arrBill_d.remove(position);
                            list.setAdapter(bill_d);
                            getTotal(ItemPrice, "-" + ItemQty);
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
        } else if (((code == 2) && (result == RESULT_OK))) {
            tvCustomer_Name.setText(data.getStringExtra("get_col_c_name"));
            tvCustomer_id.setText(data.getStringExtra("get_col_c_code"));
        }
    }

    public void setDefaultValuesIfNull() {
        if (etDisc == null || etDisc.length() == 0) {
            etDisc.setText("0");
        }
        if (tvCustomer_id == null || tvCustomer_id.length() == 0) {
            tvCustomer_id.setText("");
        }
    }

    public boolean checkData(String etBillNo_Bil, String etYearNos, String etBillDates, double resultToota) {
        boolean result = true;
        if (etBillNo_Bil == null || etBillNo_Bil.length() == 0) {
            etBillNo.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (etYearNos == null || etYearNos.length() == 0) {
            etYearNo.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (etBillDates == null || etBillDates.length() == 0) {
            etBillDate.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (arrBill_d.isEmpty()) {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.There_are_no_items), Toast.LENGTH_LONG).show();
            result = false;
        }
        if (Double.parseDouble(etDisc.getText().toString()) > resultToota) {
            etDisc.setError(getResources().getString(R.string.The_discount_is_large));
            Toast.makeText(getBaseContext(), R.string.The_discount_is_large, Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }
}
