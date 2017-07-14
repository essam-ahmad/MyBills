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
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static com.example.e3.mybills.R.string.add_Customer;
import static com.example.e3.mybills.R.string.press_to_add_customer;
import static com.example.e3.mybills.bill_d.rowStatus;

public class BillAddEditActivity extends AppCompatActivity {
    TabHost tabHost;
    EditText etBillNo, etYearNo, etBillDate, etDesc, etDisc;
    TextView tvCustomer_Name, tvCustomer_id, tvTotal, tvNetTotal, tvCustomer_Edit;
    RadioGroup rgBillType;
    public ArrayList<bill_d> arrBill_d = new ArrayList<>();
    public ArrayList<bill_d> arrBill_dForDB = new ArrayList<>();
    String _action;

    // Var For update
    String BillSeq;
    bill_m oldBill_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        setTitle(getResources().getString(R.string.Add_New_Bil));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle data = getIntent().getExtras();
        _action = data.getString("action");
        if (_action.equals("add")) {
            BillSeq = "";
        } else if (_action.equals("edit")) {
            getBillSeq();
        }
        ActiveTab();
        bill_m();
        bill_d();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!tvCustomer_id.getText().equals("")) {
            tvCustomer_Edit.setText(R.string.edit_Customer);
            tvCustomer_Name.setText(new DataManager(this).getCustomerById(tvCustomer_id.getText().toString()).get_col_c_name());
            tvCustomer_Name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(BillAddEditActivity.this);
                    yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                    yesOrNoBuilder.setMessage(tvCustomer_Name.getText());
                    yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            tvCustomer_Edit.setText(add_Customer);
                            tvCustomer_Name.setText(press_to_add_customer);
                            tvCustomer_id.setText("");
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
        }
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
            setDefaultValuesIfNull();
            String BillType = "1";
            int BillTypeId = rgBillType.getCheckedRadioButtonId();
            if (BillTypeId == R.id.radioButton7) {
                BillType = "1";
            } else if (BillTypeId == R.id.radioButton8) {
                BillType = "2";
            }
            bill_m bill_m = new bill_m(BillSeq, etYearNo.getText().toString(), etBillNo.getText().toString(), BillType
                    , etBillDate.getText().toString(), String.valueOf(tvCustomer_id.getText())
                    , etDisc.getText().toString(), etDesc.getText().toString(), tvTotal.getText().toString());
            bill_d bill_dList[] = new bill_d[arrBill_dForDB.size()];
            if (DataIsOk()) {
                DataManager dataManager = new DataManager(BillAddEditActivity.this);
                for (int i = 0; i < arrBill_dForDB.size(); i++) {
                    bill_d bill_d = new bill_d(String.valueOf(i)
                            , arrBill_dForDB.get(i).get_col_bill_yr()
                            , arrBill_dForDB.get(i).get_col_bill_no()
                            , ""
                            , arrBill_dForDB.get(i).get_col_itm_code()
                            , arrBill_dForDB.get(i).get_col_itm_price()
                            , arrBill_dForDB.get(i).get_col_itm_cost()
                            , arrBill_dForDB.get(i).get_col_itm_qty()
                            , ""
                            , arrBill_dForDB.get(i).get_rowStatus());
                    bill_dList[i] = bill_d;
                }
                long Bill_seq = dataManager.addBill_m(bill_m, bill_dList);
                if (Bill_seq != -1) {
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

    @Override
    public void onActivityResult(int code, int result, final Intent data) {
        if ((code == 1) && (result == RESULT_OK)) {
            final Bill_d_Adapter bill_d = new Bill_d_Adapter(this, arrBill_d);
            String ItemCode = data.getExtras().getString("ItemCode");
            String ItemQty = data.getExtras().getString("ItemQty");
            String ItemPrice = data.getExtras().getString("ItemPrice");
            DataManager dataBase = new DataManager(BillAddEditActivity.this);
            items dummyElement = dataBase.getItemById(Integer.parseInt(ItemCode));
            arrBill_d.add(new bill_d(ItemCode, ItemPrice, String.valueOf(dummyElement.get_col_itm_cost()), ItemQty
                    , dummyElement.get_col_itm_name(), rowStatus.newRow));
            arrBill_dForDB.add(new bill_d(ItemCode, ItemPrice, String.valueOf(dummyElement.get_col_itm_cost()), ItemQty
                    , dummyElement.get_col_itm_name(), rowStatus.newRow));
            tvTotal.setText(String.valueOf(Double.parseDouble(tvTotal.getText().toString())
                    + (Double.parseDouble(ItemPrice) * Double.parseDouble(ItemQty))));
            setNetTotal(etDisc.getText().toString());
            final ListView list = (ListView) findViewById(R.id.listView_bill_d);
            list.setAdapter(bill_d);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(BillAddEditActivity.this);
                    yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                    yesOrNoBuilder.setMessage(arrBill_d.get(position).get_col_itm_name());
                    yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            bill_d dummyElement = arrBill_d.get(position);
                            arrBill_d.remove(position);
                            arrBill_dForDB.set(position, dummyElement).set_rowStatus(rowStatus.deletedRow);
                            list.setAdapter(bill_d);
                            tvTotal.setText(String.valueOf(Double.parseDouble(tvTotal.getText().toString())
                                    - (Double.parseDouble(dummyElement.get_col_itm_price())
                                    * Double.parseDouble(dummyElement.get_col_itm_qty()))));
                            setNetTotal(etDisc.getText().toString());
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
            if (_action.equals("edit")) {
                //for editing
                tvCustomer_Edit.setText(R.string.edit_Customer);
            }
        }
    }

    public void ActiveTab() {
        if (_action.equals("edit")) {
            setTitle(R.string.Edit_Bill);
        }
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

    public void getBillSeq() {
        Bundle b = getIntent().getExtras();
        BillSeq = b.getString("BillSeq");
    }

    public void bill_m() {
        etYearNo = (EditText) findViewById(R.id.year);
        etBillNo = (EditText) findViewById(R.id.bill_no);
        etBillDate = (EditText) findViewById(R.id.date);
        etDesc = (EditText) findViewById(R.id.desc);
        etDisc = (EditText) findViewById(R.id.disc);
        tvCustomer_Name = (TextView) findViewById(R.id.C_nameOfCust);
        tvCustomer_id = (TextView) findViewById(R.id.C_codeOfCust);
        tvCustomer_Edit = (TextView) findViewById(R.id.text_of_add_Cost);
        tvTotal = (TextView) findViewById(R.id.total);
        tvNetTotal = (TextView) findViewById(R.id.NetTotal);
        rgBillType = (RadioGroup) findViewById(R.id.radiogruop);
        if (_action.equals("add")) {
            etBillNo.setText(new DataManager(this).Get_Bill_No() + 1 + "");
            etYearNo.setText(new SimpleDateFormat("yyyy", Locale.US).format(new Date()));
            etBillDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));
            tvTotal.setText("0");
            tvNetTotal.setText("0");
        } else if (_action.equals("edit")) {
            oldBill_m = new DataManager(this).getBill_m_ById(Integer.parseInt(BillSeq));
            int selectedValue = Integer.parseInt(oldBill_m.get_col_bill_type());
            if (selectedValue == 1) {
                rgBillType.check(R.id.radioButton7);
            } else {
                rgBillType.check(R.id.radioButton8);
            }
            etYearNo.setText(oldBill_m.get_col_bill_yr());
            etBillNo.setFocusable(true);
            etBillNo.setEnabled(false);
            etBillNo.setText(oldBill_m.get_col_bill_no());
            etBillDate.setText(oldBill_m.get_col_bill_date());
            etDesc.setText(oldBill_m.get_col_desc());
            etDisc.setText(oldBill_m.get_col_disc_amt());
            tvTotal.setText(oldBill_m.get_col_bill_amt());
            tvCustomer_id.setText(oldBill_m.get_col_c_code());
            tvCustomer_Name.setText(new DataManager(this).getCustomerById(oldBill_m.get_col_c_code()).get_col_c_name());
            tvNetTotal.setText(String.valueOf(Double.parseDouble(oldBill_m.get_col_bill_amt())
                    - Double.parseDouble(oldBill_m.get_col_bill_amt())));

        }

        //region uncomment below code if we want to do something when user change bill type when _action ="add"
/*
        rgBillType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton7:
                        _BillType = "1";
                        break;
                    case R.id.radioButton8:
                        _BillType = "2";
                        break;
                }
            }
        });
*/
        //endregion

        etDisc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setNetTotal(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tvCustomer_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillAddEditActivity.this, customersActivity.class);
                i.putExtra("ImFromBillAdd", 1);
                startActivityForResult(i, 2);
            }
        });
    }

    public void bill_d() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Add_item_to_bill_d);
        DataManager dataBase = new DataManager(BillAddEditActivity.this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BillAddEditActivity.this, ItemsActivity.class);
                i.putExtra("ImFromBillAdd", 1);
                startActivityForResult(i, 1);
            }
        });
        if (_action.equals("edit")) {
            bill_d bill_dArray[] = dataBase.getAllBill_d(BillSeq);
            Bill_d_Adapter bill_d = new Bill_d_Adapter(this, bill_dArray);
            ListView Bill_dList = (ListView) findViewById(R.id.listView_bill_d);
            Bill_dList.setAdapter(bill_d);
            arrBill_d = new ArrayList<>(Arrays.asList(bill_dArray));
        }
    }

    private void setNetTotal(String s) {
        if (etDisc.getText().toString().trim().length() > 0) {
            if (Double.parseDouble(etDisc.getText().toString()) > Double.parseDouble(tvTotal.getText().toString())) {
                etDisc.setError(getResources().getString(R.string.The_discount_is_large));
                tvNetTotal.setText(String.valueOf(Double.parseDouble(tvTotal.getText().toString())));
            } else {
                if (etDisc.getText().toString().trim().length() > 0) {
                    etDisc.setError(null);
                    tvNetTotal.setText(String.valueOf(Double.parseDouble(tvTotal.getText().toString())
                            - Double.parseDouble(s)));
                }
            }
        } else if (etDisc.getText().toString().isEmpty()) {
            tvNetTotal.setText(String.valueOf(Double.parseDouble(tvTotal.getText().toString())));
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

    public boolean DataIsOk() {
        boolean result = true;
        if (etBillNo.getText().toString() == null || etBillNo.getText().toString().length() == 0) {
            etBillNo.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (etYearNo.getText().toString() == null || etYearNo.getText().toString().length() == 0) {
            etYearNo.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (etBillDate.getText().toString() == null || etBillDate.getText().toString().length() == 0) {
            etBillDate.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (arrBill_d.isEmpty()) {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.There_are_no_items), Toast.LENGTH_LONG).show();
            result = false;
        }
        if (Double.parseDouble(etDisc.getText().toString()) > Double.parseDouble(tvTotal.getText().toString())) {
            etDisc.setError(getResources().getString(R.string.The_discount_is_large));
            Toast.makeText(getBaseContext(), R.string.The_discount_is_large, Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }
}
