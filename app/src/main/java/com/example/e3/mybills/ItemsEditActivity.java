package com.example.e3.mybills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemsEditActivity extends AppCompatActivity {
EditText code ,name,cost,price;
Button save,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_edit);

        code = (EditText)findViewById(R.id.Edit_itm_code);
        code.setFocusable(false);
        code.setEnabled(false);
        //code.setCursorVisible(false);
       // code.setKeyListener(null);
        name = (EditText)findViewById(R.id.Edit_itm_name);
        cost = (EditText)findViewById(R.id.Edit_itm_cost);
        price = (EditText)findViewById(R.id.Edit_itm_price);

        Bundle data =getIntent().getExtras();
        int item_code=data.getInt("get_col_itm_code");
        String item_name = data.getString("get_col_itm_name");
        double item_cost = data.getDouble("get_col_itm_Cost");
        double item_price = data.getDouble("get_col_itm_price");
        final String item_date = data.getString("get_col_ad_date");

        code.setText(String.valueOf(item_code));
        name.setText(String.valueOf(item_name));
        cost.setText(String.valueOf( item_cost));
        price.setText(String.valueOf( item_price));


        save=(Button)findViewById(R.id.Edit_Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkData(code.getText().toString(),name.getText().toString(),cost.getText().toString(),price.getText().toString())){
                    final int itm_code=Integer.parseInt(code.getText().toString());
                    final String itm_name =name.getText().toString();
                    final double itm_cost = Double.parseDouble(cost.getText().toString());
                    final double itm_price = Double.parseDouble(price.getText().toString());

                    DataManager update = new DataManager(ItemsEditActivity.this);
                update.UpdateItem(itm_code,itm_name,itm_cost,itm_price,item_date);
                Toast.makeText(getBaseContext(), R.string.Done_Edit, Toast.LENGTH_LONG).show();
finish();
                }

            }
        });


        cancel = (Button)findViewById(R.id.Edit_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
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
            code.setError(getResources().getString(R.string.Pleaze_fill));
            name.setError(getResources().getString(R.string.Pleaze_fill));
            cost.setError(getResources().getString(R.string.Pleaze_fill));
            price.setError(getResources().getString(R.string.Pleaze_fill));
            Toast.makeText(getBaseContext(), R.string.Pleaze_fill, Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
