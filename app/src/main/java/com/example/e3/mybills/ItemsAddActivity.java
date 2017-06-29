package com.example.e3.mybills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemsAddActivity extends AppCompatActivity {
    EditText addItemCode, addItemName, addItemCost, addItemPrice;
    Button Save, Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_add);
        addItemCode = (EditText) findViewById(R.id.itm_code);
        addItemName = (EditText) findViewById(R.id.itm_name);
        addItemCost = (EditText) findViewById(R.id.itm_cost);
        addItemPrice = (EditText) findViewById(R.id.itm_price);
        Save = (Button) findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValuesIfNull();
                String ItemCode = addItemCode.getText().toString();
                String ItemName = addItemName.getText().toString();
                String ItemCost = addItemCost.getText().toString();
                String ItemPrice = addItemPrice.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                if (checkData(ItemCode, ItemName, ItemCost, ItemPrice)) {
                    DataManager dataManager = new DataManager(ItemsAddActivity.this);
                    boolean result = dataManager.addItem(Integer.parseInt(ItemCode), ItemName, Double.parseDouble(ItemCost), Double.parseDouble(ItemPrice), date);
                    if (result) {
                        Toast.makeText(getBaseContext(), R.string.Done_Adding, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        addItemCode.setError(getResources().getString(R.string.The_number_already_exists));
                        Toast.makeText(getBaseContext(), R.string.The_number_already_exists, Toast.LENGTH_LONG).show();
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

    public void setDefaultValuesIfNull() {
        if (addItemCost == null || addItemCost.length() == 0) {
            addItemCost.setText("0");
        }
        if (addItemPrice == null || addItemPrice.length() == 0) {
            addItemPrice.setText("0");
        }
    }

    public boolean checkData(String itm_code, String itm_name, String itm_cost, String itm_price) {
        boolean result = true;
        if (itm_code == null || itm_code.length() == 0) {
            addItemCode.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_name == null || itm_name.length() == 0) {
            addItemName.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_cost == null || itm_cost.length() == 0) {
            addItemCost.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_price == null || itm_price.length() == 0) {
            addItemPrice.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        return result;
    }
}