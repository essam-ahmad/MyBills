package com.example.e3.mybills;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by e3 on 13/06/2017.
 */
public class item_Adapter {
    Context context;
    private Activity activity;
    private items[] data;
    static int currentSelection = 0;
    private static LayoutInflater inflater=null;

    public item_Adapter(Activity a, items list[]) {
        activity = a;
        data=list;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length;
    }

    /*public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

      /*public long getPosition(int position) {
        return position;
    }*/

/*
    tbn_items
    col_itm_code INTEGER PRIMARY KEY
    col_itm_name text
    col_itm_cost numeric
    col_itm_price NUMERIC
    col_ad_date text*/

//    public View getView(final int position, final View convertView, ViewGroup parent) {
//        View vi=convertView;
//        if(convertView==null)
//            vi = inflater.inflate(R.layout.item_layout, null);
//            TextView code=(TextView)vi.findViewById(R.id.nameChannle);;
//            TextView name = (TextView) vi.findViewById(R.id.discChannle);
//            TextView cost = (TextView)vi.findViewById(R.id.linkChannle);
//            TextView price = (TextView)vi.findViewById(R.id.number);
//            TextView ad_date = (TextView)vi.findViewById(R.id.number);
//
//        code.setText(data[position].get_col_itm_code());
//        name.setText(data[position].get_col_itm_name());
//        cost.setText(String.valueOf(data[position].get_col_itm_cost()));
//        price.setText(String.valueOf(data[position].get_col_itm_price()));
//        ad_date.setText(data[position].get_col_ad_date());
//        final View finalVi = vi;
//        vi.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
//                builder.setTitle(v.getResources().getString(R.string.alert));
//                builder.setMessage(data[position].getId()+":"+data[position].getnameitem());
//                builder.setNegativeButton("تعديل", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent i = new Intent(finalVi.getContext(),EditItem.class);
//                        Bundle b = new Bundle();
//                        b.putString("getname",data[position].getnameitem());
//                        b.putString("getdata",data[position].getdate());
//                        b.putString("getprice",data[position].getprice());
//                        b.putInt("getid",data[position].getId());
//                        i.putExtras(b);
//                        finalVi.getContext().startActivity(i);
//                    }
//                });
//
//                builder.setNeutralButton("حذف", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
//                        builder.setTitle("هل أنت متأكد من حذف الصنف:"+data[position].getnameitem());
//                        builder.setMessage("الوصف:"+data[position].getdate()+"  ");
//                        builder.setNegativeButton("نعم",new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                DataBase dataBase=new DataBase(finalVi.getContext());
//                                dataBase.deleteOneItem(data[position].getId());
//
//
//                            }
//
//                        });
//                        builder.setNeutralButton("لا", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        builder.show();
//
//                    }
//                });
//                builder.show();
//                return true; }
//        });
//        return vi;
//    }
}

