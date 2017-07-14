package com.example.e3.mybills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemsAddEditActivity extends AppCompatActivity {
    EditText _etItemCode, _etItemName, _etItemCost, _etItemPrice;
    Button Save, Cancel;
    String _action;
    android.support.design.widget.TextInputLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        _etItemCode = (EditText) findViewById(R.id.itm_code);
        _etItemName = (EditText) findViewById(R.id.itm_name);
        _etItemCost = (EditText) findViewById(R.id.itm_cost);
        _etItemPrice = (EditText) findViewById(R.id.itm_price);
        Bundle data = getIntent().getExtras();
        _action = data.getString("action");
        if (_action.equals("add")) {
            _etItemCode.setText(new DataManager(this).Get_Item_No()+1+"");
            this.setTitle( R.string.add_Items);
        } else if (_action.equals("edit")) {
            this.setTitle( R.string.Edit_Items);
            _etItemCode.setText(String.valueOf(data.getInt("get_col_itm_code")));
            _etItemCode.setFocusable(false);
            _etItemCode.setEnabled(false);
            _etItemName.setText(data.getString("get_col_itm_name"));
            _etItemCost.setText(String.valueOf(data.getDouble("get_col_itm_Cost")));
            _etItemPrice.setText(String.valueOf(data.getDouble("get_col_itm_price")));
        }else if(_action.equals("editqty")){
            this.setTitle(R.string.Edit_qty);
            _etItemCode.setText(String.valueOf(data.getInt("get_col_itm_code")));
            _etItemCode.setFocusable(false);
            _etItemCode.setEnabled(false);
            _etItemName.setText(data.getString("get_col_itm_name"));
            _etItemName.setFocusable(false);
            _etItemName.setEnabled(false);
            layout=(android.support.design.widget.TextInputLayout)findViewById(R.id.itm_cost_input_layout);
            layout.setHint(getResources().getString(R.string.qty));

            _etItemCost.setInputType(2);
            _etItemPrice.setText(String.valueOf(data.getDouble("get_col_itm_price")));
        }
        Save = (Button) findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValuesIfNull();
                String ItemCode = _etItemCode.getText().toString();
                String ItemName = _etItemName.getText().toString();
                String ItemCost = _etItemCost.getText().toString();
                String ItemPrice = _etItemPrice.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                if (checkData(ItemCode, ItemName, ItemCost, ItemPrice)) {
                    DataManager dataManager = new DataManager(ItemsAddEditActivity.this);
                    if (_action.equals("add")) {
                        boolean result = dataManager.addItem(Integer.parseInt(ItemCode), ItemName, Double.parseDouble(ItemCost), Double.parseDouble(ItemPrice), date);
                        if (result) {
                            Toast.makeText(getBaseContext(), R.string.Done_Adding, Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            _etItemCode.setError(getResources().getString(R.string.The_number_already_exists));
                            Toast.makeText(getBaseContext(), R.string.The_number_already_exists, Toast.LENGTH_LONG).show();
                        }
                    } else if (_action.equals("edit")) {
                        int result = dataManager.UpdateItem(Integer.parseInt(ItemCode), ItemName, Double.parseDouble(ItemCost), Double.parseDouble(ItemPrice));
                        if (result != 0) {
                            Toast.makeText(getBaseContext(), R.string.Done_Edit, Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }else if(_action.equals("editqty") && Integer.parseInt(ItemCost) > 0){
                        Intent i = new Intent();
                        i.putExtra("ItemCode",ItemCode);
                        i.putExtra("ItemName",ItemName);
                        i.putExtra("ItemQty",ItemCost);
                        i.putExtra("ItemPrice",ItemPrice);
                        setResult(RESULT_OK,i);
                        finish();
                    }
                }
            }
        });
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return false;
    }

    public void setDefaultValuesIfNull() {
        if(_action.equals("editqty")){
            if (_etItemCost == null || _etItemCost.length() == 0){
                _etItemCost.setText(null);
            }


        }else {
        if (_etItemCost == null || _etItemCost.length() == 0) {
            _etItemCost.setText("0");
        }
        if (_etItemPrice == null || _etItemPrice.length() == 0) {
            _etItemPrice.setText("0");
        }
    }}

    public boolean checkData(String itm_code, String itm_name, String itm_cost, String itm_price) {
        boolean result = true;
        if (itm_code == null || itm_code.length() == 0) {
            _etItemCode.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_name == null || itm_name.length() == 0) {
            _etItemName.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_cost == null || itm_cost.length() == 0) {
            _etItemCost.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_price == null || itm_price.length() == 0) {
            _etItemPrice.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        return result;
    }
}