package com.example.e3.mybills;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowBill_Info extends AppCompatActivity {
    TextView Number_Bill,Number_Customer,Name_Cust,Desc,Date_bill,Year_bill,Tybe,Disc, total,NetTotal;
    String getBillNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill__info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetBillNumber();
        //region تعريف دوال Textview
        Number_Bill = (TextView) findViewById(R.id.Number_Bill);
        Number_Customer = (TextView) findViewById(R.id.Number_Customer);
        Name_Cust = (TextView) findViewById(R.id.Name_Cust);
        Desc = (TextView) findViewById(R.id.Desc);
        Date_bill = (TextView) findViewById(R.id.Date_bill);
        Year_bill = (TextView) findViewById(R.id.Year_bill);
        Tybe = (TextView) findViewById(R.id.Tybe);
        Disc = (TextView) findViewById(R.id.Disc);
        total = (TextView) findViewById(R.id.Tootal_);
        NetTotal = (TextView) findViewById(R.id.NetTotal);

        //endregion
        GetDataFromDb();
        GetAllBill_d();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetBillNumber();
        GetDataFromDb();
        GetAllBill_d();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
     if (id == android.R.id.home) {
        finish();
     }if (id == R.id.delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowBill_Info.this);
            builder.setTitle(getResources().getString(R.string.alert));
            builder.setMessage(R.string.delete);
            builder.setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowBill_Info.this);
                    builder.setTitle(R.string.AlertDialog_Title_delete);
                    builder.setMessage(R.string.You_will_not_be_able_to_retrieve);
                    builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            new DataManager(ShowBill_Info.this).deleteOneBill_m(new DataManager(ShowBill_Info.this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq());
                            new DataManager(ShowBill_Info.this).deleteOneBill_d(new DataManager(ShowBill_Info.this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq());
                            finish();
                            Toast.makeText(ShowBill_Info.this, getResources().getString(R.string.delete)+"...", Toast.LENGTH_LONG).show();


                        }

                    });
                    builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }
            });
            builder.show();


        }if (id == R.id.update){
            Intent No = new Intent(ShowBill_Info.this,Edit_Bill.class);
            Bundle b = new Bundle();
            b.putString("ID_BIL",getBillNumber);
            No.putExtras(b);
            startActivity(No);
        }
        return false;
    }
    public void GetBillNumber(){
       Bundle BillNO = getIntent().getExtras();
      getBillNumber= BillNO.getString("Number_bill");
    }
    public void GetDataFromDb(){
        Number_Bill.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_no());
        Number_Customer.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_c_code());
        if (!Number_Customer.getText().equals("")){
            Name_Cust.setText(new DataManager(this).getCustomerById(String.valueOf(Number_Customer.getText())).get_col_c_name());}
        Desc.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_desc());
        Date_bill.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_date());
        Year_bill.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_yr());
        if (Integer.parseInt(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_type()) == 1) {
            Tybe.setText(R.string.cash);
        } else if (Integer.parseInt(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_type())==2) {
            Tybe.setText(R.string.credit);

        }
        Disc.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_disc_amt());
        total.setText(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_amt());
        if (Disc.getText().length()==0){
            Disc.setText("0");
            NetTotal.setText(total.getText().toString()+"");

        }else {double _nettotal = Double.parseDouble(total.getText().toString())-Double.parseDouble(Disc.getText().toString());
            NetTotal.setText(_nettotal+"");}
    }
    public void GetAllBill_d(){
         final Bill_d_Adapter bill_d = new Bill_d_Adapter(this, new DataManager(this).getAllBill_d(getBillNumber));
         final ListView list = (ListView) findViewById(R.id.All_bill_d);
        list.setAdapter(bill_d);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowBill_Info.this);
                builder.setTitle(getResources().getString(R.string.alert));
                builder.setMessage(R.string.delete);
                builder.setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowBill_Info.this);
                        builder.setTitle(getResources().getString(R.string.AlertDialog_Title_delete )+ new DataManager(ShowBill_Info.this).Get_Bill_d_ById(Integer.parseInt(getBillNumber)).get_col_itm_name());
                        builder.setMessage(R.string.You_will_not_be_able_to_retrieve);
                        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                               // new DataManager(ShowBill_Info.this).deleteOneBill_d(new DataManager(ShowBill_Info.this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_bill_seq());
                                Toast.makeText(ShowBill_Info.this, getResources().getString(R.string.delete)+"...", Toast.LENGTH_LONG).show();
                                list.setAdapter(bill_d);


                            }

                        });
                        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();

                    }
                });
                builder.show();
                return false;
            }
        });

    }
}
