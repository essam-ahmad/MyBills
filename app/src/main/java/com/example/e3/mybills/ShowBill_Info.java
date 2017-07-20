package com.example.e3.mybills;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowBill_Info extends AppCompatActivity {
    TextView Number_Bill, Number_Customer, Name_Cust, Desc, Date_bill, Year_bill, Tybe, Disc, total, NetTotal;
    String BillSeq;
    File pdfFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetBillNumber();
        setTitle(getResources().getString(R.string.Show_Bill_Info));
        //region تعريف دوال Textview
        Number_Bill = (TextView) findViewById(R.id.bill_no);
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
        }
        if (id == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowBill_Info.this);
            builder.setTitle(getResources().getString(R.string.alert));
            builder.setIcon(android.R.drawable.ic_menu_delete);
            builder.setMessage(R.string.delete);
            builder.setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowBill_Info.this);
                    builder.setIcon(android.R.drawable.ic_menu_delete);
                    builder.setTitle(R.string.AlertDialog_Title_delete);
                    builder.setMessage(R.string.You_will_not_be_able_to_retrieve);
                    builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            new DataManager(ShowBill_Info.this).deleteOneBill_m(new DataManager(ShowBill_Info.this).getBill_m_ById(Integer.parseInt(BillSeq)).get_col_bill_seq());
                            new DataManager(ShowBill_Info.this).deleteOneBill_d(new DataManager(ShowBill_Info.this).getBill_m_ById(Integer.parseInt(BillSeq)).get_col_bill_seq());
                            finish();
                            Toast.makeText(ShowBill_Info.this, getResources().getString(R.string.delete) + "...", Toast.LENGTH_LONG).show();
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
        }
        if (id == R.id.update) {
            Intent No = new Intent(ShowBill_Info.this, BillAddEditActivity.class);
            Bundle b = new Bundle();
            b.putString("BillSeq", BillSeq);
            b.putString("action", "edit");
            No.putExtras(b);
            startActivity(No);
        }
        if (id == R.id.Print) {
            try {
                createpdf();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public void GetBillNumber() {
        Bundle BillNO = getIntent().getExtras();
        BillSeq = BillNO.getString("Number_bill");
    }

    public void GetDataFromDb() {
        bill_m bill_m = new DataManager(this).getBill_m_ById(Integer.parseInt(BillSeq));
        Number_Bill.setText(bill_m.get_col_bill_no());
        Number_Customer.setText(bill_m.get_col_c_code());
        if (!Number_Customer.getText().equals("")) {
            Name_Cust.setText(new DataManager(this).getCustomerById(String.valueOf(Number_Customer.getText())).get_col_c_name());
        }
        Desc.setText(bill_m.get_col_desc());
        Date_bill.setText(bill_m.get_col_bill_date());
        Year_bill.setText(bill_m.get_col_bill_yr());
        if (Integer.parseInt(bill_m.get_col_bill_type()) == 1) {
            Tybe.setText(R.string.cash);
        } else if (Integer.parseInt(bill_m.get_col_bill_type()) == 2) {
            Tybe.setText(R.string.credit);
        }
        Disc.setText(bill_m.get_col_disc_amt());
        total.setText(bill_m.get_col_bill_amt());
        if (Disc.getText().length() == 0) {
            Disc.setText("0");
            NetTotal.setText(total.getText().toString() + "");
        } else {
            double _nettotal = Double.parseDouble(total.getText().toString()) - Double.parseDouble(Disc.getText().toString());
            NetTotal.setText(_nettotal + "");
        }
    }

    public void GetAllBill_d() {
        final Bill_d_Adapter bill_d = new Bill_d_Adapter(this, new DataManager(this).getAllBill_d(BillSeq));
        final ListView list = (ListView) findViewById(R.id.All_bill_d);
        list.setAdapter(bill_d);
    }

    public void createpdf() throws FileNotFoundException, DocumentException {

        PdfCreator pdfCreator = new PdfCreator();
        pdfFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/mybill");
        pdfCreator.initializeFonts();

        //this.f = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/tahoma.ttf");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }
        copyAssets();
        pdfCreator.initializeFonts();
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        String file = new String(pdfFolder+ "/mybill" + timeStamp + ".pdf");
        OutputStream output = new FileOutputStream(file);
        //step1
        Document document = new Document(PageSize.A4);
        document.setMargins(10.0F, 10.0F, 40.0F, 200.0F);
        document.setMarginMirroring(false);
        PdfWriter writer = PdfWriter.getInstance(document, output);
        bill_m bill_m = new DataManager(this).getBill_m_ById(Integer.parseInt(BillSeq));
        bill_d bill_d[] = new DataManager(this).getAllBill_d(BillSeq);
        document.open();
        if (Integer.parseInt(bill_m.get_col_bill_type()) == 1) {
            document.add(pdfCreator.BillTybe("نقد"));
        } else if (Integer.parseInt(bill_m.get_col_bill_type()) == 2) {
            document.add(pdfCreator.BillTybe("اجل"));

        }
        document.add(pdfCreator.InfoOfCo());
        if (!bill_m.get_col_c_code().equals("")) {
            customers CustInfo = new DataManager(this).getCustomerById(bill_m.get_col_c_code());
            document.add(pdfCreator.BillInfo(bill_m.get_col_bill_no(), bill_m.get_col_bill_date(), CustInfo.get_col_c_name(), CustInfo.get_col_phone()));
        } else {
            document.add(pdfCreator.BillInfo(bill_m.get_col_bill_no(), bill_m.get_col_bill_date(), "", ""));

        }
        document.add(pdfCreator.HeaderTableOfItems());
        document.add(pdfCreator.itemsTable(bill_d));
        if (!bill_m.get_col_disc_amt().equals("")) {
            document.add(pdfCreator.TotalTable(bill_m.get_col_bill_amt(), bill_m.get_col_disc_amt()));
        } else {
            document.add(pdfCreator.TotalTable(bill_m.get_col_bill_amt(), "0"));

        }
        document.close();
        File path = new File(file);
        //Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", path);
        Intent intent = new Intent(ShowBill_Info.this,Pdf_View.class);
        Bundle bundle =new Bundle();
        bundle.putString("path", String.valueOf(path));
        intent.putExtras(bundle);
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/
        startActivity(intent);


    }
    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        //String filename="tahoma.ttf";
        if (files != null) for ( String filename: files) {
            InputStream in = null;
            OutputStream out = null;
            //filename="tahoma.tff";
            try {
                in = assetManager.open(filename);
                File outFile = new File(pdfFolder , filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}
