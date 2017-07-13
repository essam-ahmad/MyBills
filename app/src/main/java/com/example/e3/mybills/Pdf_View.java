package com.example.e3.mybills;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.IOException;

public class Pdf_View extends AppCompatActivity {
    PDFView pdfView;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf__view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.Print);
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        File file = new File(path);

        pdfView = (PDFView)findViewById(R.id.View_pdf);
        pdfView.fromFile(file).load();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }if (id == R.id.Share_bill) {
            String str = path;
            Intent intent = new Intent("android.intent.action.SEND");
            if (new File(str).exists())
            {
                intent.setType("application/pdf");
                intent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + str));
                startActivity(Intent.createChooser(intent,""));
            }
        }
        return false;
    }
}
