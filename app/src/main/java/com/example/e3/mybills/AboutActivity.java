package com.example.e3.mybills;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


public class AboutActivity extends AppCompatActivity {
DataManager DB = new DataManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.Info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView versionApp = (TextView)findViewById(R.id.VerApp);
        TextView versionDB = (TextView)findViewById(R.id.VerDb);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionApp.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       String version= String.valueOf(DB.version);
        versionDB.setText(version);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
          finish();    }

        return super.onOptionsItemSelected(item);
    }
}
