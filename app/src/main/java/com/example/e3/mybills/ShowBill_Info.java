package com.example.e3.mybills;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        }if (id == R.id.Print){
            try {
                //this.dataHeader.clear();

                createpdf();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

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

    public void createpdf() throws FileNotFoundException, DocumentException {

PdfCreator pdfCreator =new PdfCreator();
        File pdfFolder = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        pdfCreator.initializeFonts();

        //this.f = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/tahoma.ttf");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        pdfCreator.initializeFonts();
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        File file = new File(pdfFolder + timeStamp + ".pdf");
        OutputStream output = new FileOutputStream(file);
        //step1
        Document document = new Document(PageSize.A4);
        document.setMargins(10.0F, 10.0F, 40.0F, 200.0F);
        document.setMarginMirroring(false);
        PdfWriter writer = PdfWriter.getInstance(document, output);
     String nameCust=   new DataManager(this).getCustomerById(new DataManager(this).getBill_m_ById(Integer.parseInt(getBillNumber)).get_col_c_code()).get_col_c_name();
        document.open();
        document.add(pdfCreator.addres());
        document.add(pdfCreator.pdfPTableheader());
        document.add(pdfCreator.tabbleOfinfocost(nameCust));
        document.add(pdfCreator.createheaderOftble());
        //int k;
        //for (k = 0; k < 2; k++) {
        //  document.add(itemsTable());
        //}
        document.add(pdfCreator.itemsTable());
        document.add(pdfCreator.lowertable());
        document.close();
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        //Uri photoURI = FileProvider.getUriForFile(ShwoBillInfo.this, BuildConfig.APPLICATION_ID+ ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }

}
