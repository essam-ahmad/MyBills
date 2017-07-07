package com.example.e3.mybills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomersAddEditActivity extends AppCompatActivity {
    EditText _etCode, _etName, _etPhone, _etAddress;
    Button Save, Cancel;
    String _action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        _etCode = (EditText) findViewById(R.id.code);
        _etName = (EditText) findViewById(R.id.name);
        _etPhone = (EditText) findViewById(R.id.phone);
        _etAddress = (EditText) findViewById(R.id.address);
        Bundle data = getIntent().getExtras();
        _action = data.getString("action");
        if (_action.equals("add")) {
            _etCode.setText(new DataManager(this).Get_Cust_No()+1+"");
            this.setTitle( R.string.add_Customer);
        } else if (_action.equals("edit")) {
            this.setTitle( R.string.edit_Customer);
            _etCode.setText(data.getString("get_col_c_code"));
            _etCode.setFocusable(false);
            _etCode.setEnabled(false);
            _etName.setText(data.getString("get_col_c_name"));
            _etPhone.setText(data.getString("get_col_phone"));
            _etAddress.setText(data.getString("get_col_address"));
        }
        Save = (Button) findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValuesIfNull();
                String Code = _etCode.getText().toString();
                String Name = _etName.getText().toString();
                String phone = _etPhone.getText().toString();
                String Address = _etAddress.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                if (checkData(Code, Name)) {
                    DataManager dataManager = new DataManager(CustomersAddEditActivity.this);
                    if (_action.equals("add")) {
                        boolean result = dataManager.addCustomer(Code, Name, date, phone, Address);
                        if (result) {
                            Toast.makeText(getBaseContext(), R.string.Done_Adding, Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            _etCode.setError(getResources().getString(R.string.The_number_already_exists));
                            Toast.makeText(getBaseContext(), R.string.The_number_already_exists, Toast.LENGTH_LONG).show();
                        }
                    } else if (_action.equals("edit")) {
                        int result = dataManager.UpdateCustomer(Code, Name, phone, Address);
                        if (result != 0) {
                            Toast.makeText(getBaseContext(), R.string.Done_Edit, Toast.LENGTH_LONG).show();
                        }
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
    } @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return false;
    }

    public void setDefaultValuesIfNull() {
        if (_etPhone == null || _etPhone.length() == 0) {
            _etPhone.setText("");
        }
        if (_etAddress == null || _etAddress.length() == 0) {
            _etAddress.setText("");
        }
    }

    public boolean checkData(String code, String name) {
        boolean result = true;
        if (code == null || code.length() == 0) {
            _etCode.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        if (name == null || name.length() == 0) {
            _etName.setError(getResources().getString(R.string.Please_fill));
            result = false;
        }
        return result;
    }
}