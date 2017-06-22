package com.example.e3.mybills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ItemsActivity extends AppCompatActivity {
    Button searchbottn ;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        list = (ListView) findViewById(R.id.listView_item);
        //ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.items_header, list, false);
        //list.addHeaderView(headerView);
        listItem();
        searchb();
    }

    @Override
    protected void onResume() {
        super.onResume();
       listItem();
        searchb();

    }
    public void listItem() {

        DataManager ch = new DataManager(this);
        list = (ListView) findViewById(R.id.listView_item);
        items_Adapter lazy = new items_Adapter(ItemsActivity.this, ch.getAllItems());
        list.setAdapter(lazy);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_add) {
            //Toast.makeText(ItemsActivity.this, "تم حذف الصنف", Toast.LENGTH_LONG).show();

          Intent i = new Intent(ItemsActivity.this,ItemsAddActivity.class);
          startActivity(i);

    }
    return true;
    }
    public void searchb(){
        searchbottn = (Button)findViewById(R.id.search_item);
        searchbottn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();

            }
        });
    }
    public void search(){
        EditText searcedit = (EditText) findViewById(R.id.searchView2);
        DataManager ch = new DataManager(this);
        ListView list = (ListView) findViewById(R.id.listView_item);
        items_Adapter lazy = new items_Adapter(ItemsActivity.this, ch.SearchItem(searcedit.getText().toString()));
        list.setAdapter(lazy);


    }
}

