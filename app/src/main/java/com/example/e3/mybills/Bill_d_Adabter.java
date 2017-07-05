package com.example.e3.mybills;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by EBRHEEM on 7/5/2017.
 */

public class Bill_d_Adabter extends BaseAdapter {
    private Activity activity;
    private ArrayList<BILL_DCalss> data;
    private static LayoutInflater inflater=null;

    public Bill_d_Adabter(Activity a, ArrayList<BILL_DCalss> list) {
        activity = a;

        data=list;


        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.bill_d_layout, null);
        final View finalVi = vi;
        TextView col_itm_code=(TextView)vi.findViewById(R.id.col_bill_d_code);
        TextView col_itm_name = (TextView) vi.findViewById(R.id.col_bill_d_name);
        TextView col_itm_qty = (TextView)vi.findViewById(R.id.col_bill_d_qty);
        TextView col_itm_cost = (TextView)vi.findViewById(R.id.col_bill_d_cost);
        TextView col_itm_price = (TextView)vi.findViewById(R.id.col_bill_d_price);
        TextView col_ad_Total = (TextView)vi.findViewById(R.id.col_bill_d_Total);
        col_itm_code.setText(data.get(position).number);
        col_itm_name.setText(data.get(position).name);
        col_itm_qty.setText(data.get(position).qty);
        DataManager dataBase=new DataManager(finalVi.getContext());
        col_itm_cost.setText(dataBase.getItemById(Integer.parseInt(data.get(position).number)).get_col_itm_cost()+"");
        col_itm_price.setText(data.get(position).price);
        double price = Double.parseDouble(data.get(position).price);
        double qty = Double.parseDouble(data.get(position).qty);
        double result = price*qty;
        col_ad_Total.setText(result+"");
        if (position % 2 == 0) {
            //noinspection ResourceAsColor
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorFirstRow));
            col_itm_code.setTextColor(Color.BLACK);
            col_itm_name.setTextColor(Color.BLACK);
            col_itm_qty.setTextColor(Color.BLACK);
            col_itm_cost.setTextColor(Color.BLACK);
            col_itm_price.setTextColor(Color.BLACK);
            col_ad_Total.setTextColor(Color.BLACK);
        } else {
            //noinspection ResourceAsColor
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorSecondRow));
            col_itm_code.setTextColor(Color.BLACK);
            col_itm_name.setTextColor(Color.BLACK);
            col_itm_qty.setTextColor(Color.BLACK);
            col_itm_cost.setTextColor(Color.BLACK);
            col_itm_price.setTextColor(Color.BLACK);
            col_ad_Total.setTextColor(Color.BLACK);

         /*vi.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
        builder.setTitle(v.getResources().getString(R.string.alert));
        builder.setMessage(data.get(position).name+ ":" + data.get(position).number);
        builder.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(finalVi.getContext());
                yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                yesOrNoBuilder.setMessage(data.get(position).name);
                yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        data.remove(data.get(position));
                        notifyDataSetChanged();
                    }
                });
                yesOrNoBuilder.setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                yesOrNoBuilder.show();
            }
        });
        builder.show();
        return true;
    }
});*/






    }

        return vi;


}}