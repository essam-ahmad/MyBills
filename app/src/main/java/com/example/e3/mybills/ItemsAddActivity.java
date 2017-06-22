package com.example.e3.mybills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemsAddActivity extends AppCompatActivity {
    EditText addItemCode, addItemName,addItemCost, addItemprice;
Button Save,Cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_add);
        addItemCode   =(EditText)findViewById(R.id.itm_code);
        addItemName  =(EditText)findViewById(R.id.itm_name);
        addItemCost  =(EditText)findViewById(R.id.itm_cost);
        addItemprice =(EditText)findViewById(R.id.itm_price);
        Save = (Button)findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ItemCode=addItemCode.getText().toString();
                String ItemName= addItemName.getText().toString();
                String ItemCost=addItemCost.getText().toString();
                String ItemPrice = addItemprice.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                if (checkData(ItemCode,ItemName,ItemCost,ItemPrice)){
                    DataManager dataManager = new DataManager(ItemsAddActivity.this);
                    dataManager.addItem(Integer.parseInt(ItemCode),ItemName,Double.parseDouble(ItemCost),Double.parseDouble(ItemPrice),date);
                    Toast.makeText(getBaseContext(), "تم اضافة صنف جديد", Toast.LENGTH_LONG).show();

                    finish();


                }else {


                }

            }
        });

        Cancel = (Button)findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public boolean checkData(String itm_code, String itm_name, String itm_cost,String itm_price) {
        if ((itm_code != null && itm_name != null && itm_cost != null && itm_price !=null) &&
                (itm_code.length() != 0 && itm_name.length() != 0 && itm_cost.length() != 0 && itm_price.length()!=0)) {
            return true;
        } else {
            Toast.makeText(getBaseContext(), "الرجاء املىء الحقول", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
