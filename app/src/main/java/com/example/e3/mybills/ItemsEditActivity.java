package com.example.e3.mybills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemsEditActivity extends AppCompatActivity {
    EditText code, name, cost, price;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_edit);
        Bundle data = getIntent().getExtras();
        code = (EditText) findViewById(R.id.Edit_itm_code);
        code.setText(String.valueOf(data.getInt("get_col_itm_code")));
        code.setFocusable(false);
        code.setEnabled(false);
        name = (EditText) findViewById(R.id.Edit_itm_name);
        name.setText(data.getString("get_col_itm_name"));
        cost = (EditText) findViewById(R.id.Edit_itm_cost);
        cost.setText(String.valueOf(data.getDouble("get_col_itm_Cost")));
        price = (EditText) findViewById(R.id.Edit_itm_price);
        price.setText(String.valueOf(data.getDouble("get_col_itm_price")));
        save = (Button) findViewById(R.id.Edit_Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValuesIfNull();
                if (checkData(code.getText().toString(), name.getText().toString(), cost.getText().toString(), price.getText().toString())) {
                    final int itm_code = Integer.parseInt(code.getText().toString());
                    final String itm_name = name.getText().toString();
                    final double itm_cost = Double.parseDouble(cost.getText().toString());
                    final double itm_price = Double.parseDouble(price.getText().toString());
                    DataManager update = new DataManager(ItemsEditActivity.this);
                    update.UpdateItem(itm_code, itm_name, itm_cost, itm_price);
                    Toast.makeText(getBaseContext(), R.string.Done_Edit, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        cancel = (Button) findViewById(R.id.Edit_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setDefaultValuesIfNull() {
        if (cost == null || cost.length() == 0) {
            cost.setText("0");
        }
        if (price == null || price.length() == 0) {
            price.setText("0");
        }
    }

    public boolean checkData(String itm_code, String itm_name, String itm_cost, String itm_price) {
        boolean result = true;
        if (itm_code == null || itm_code.length() == 0) {
            code.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_name == null || itm_name.length() == 0) {
            name.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_cost == null || itm_cost.length() == 0) {
            cost.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (itm_price == null || itm_price.length() == 0) {
            price.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        return result;
    }
}
