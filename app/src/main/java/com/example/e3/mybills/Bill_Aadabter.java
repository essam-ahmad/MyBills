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

import static android.app.Activity.RESULT_OK;

/**
 * Created by EBRHEEM on 7/6/2017.
 */

public class Bill_Aadabter extends BaseAdapter {
    private Activity activity;
    private bill_m[] data;
    private static LayoutInflater inflater = null;
    public Bill_Aadabter(Activity a, bill_m[] list) {
        activity = a;
        data = list;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length;
    }

    @Override
    public bill_m getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
    return position;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.bill_layout, null);
        TextView number_bill = (TextView) vi.findViewById(R.id.number_bill);
        TextView desc = (TextView) vi.findViewById(R.id.desc);
        TextView customer_code = (TextView) vi.findViewById(R.id.customer_code);
        TextView name_cust = (TextView) vi.findViewById(R.id.name_cust);
        TextView bill_tybe = (TextView) vi.findViewById(R.id.bill_tybe);
        TextView disc = (TextView) vi.findViewById(R.id.disc);
        TextView Tootal = (TextView) vi.findViewById(R.id.Tootal);
        TextView Date = (TextView) vi.findViewById(R.id.Date);
        number_bill.setText(data[position].get_col_bill_no());
        desc.setText(data[position].get_col_desc());
        if (data[position].get_col_c_code().equals("")){
            customer_code.setText("_");
            name_cust.setText("_");
        }else  if (!data[position].get_col_c_code().equals("")){
            customer_code.setText(data[position].get_col_c_code());
            name_cust.setText(new DataManager(activity).getCustomerById(data[position].get_col_c_code()).get_col_c_name());
        }
        if (Integer.parseInt(data[position].get_col_bill_type()) == 1) {
            bill_tybe.setText(R.string.cash);
        } else if (Integer.parseInt(data[position].get_col_bill_type())==2) {
            bill_tybe.setText(R.string.forward);

        }
        disc.setText(data[position].get_col_disc_amt());
        Tootal.setText(data[position].get_col_bill_amt());
        Date.setText(data[position].get_col_bill_date());

        if (position % 2 == 0) {
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorFirstRow));

            number_bill.setTextColor(Color.BLACK);
            desc.setTextColor(Color.BLACK);
            customer_code.setTextColor(Color.BLACK);
            name_cust.setTextColor(Color.BLACK);
            bill_tybe.setTextColor(Color.BLACK);
            disc.setTextColor(Color.BLACK);
            Tootal.setTextColor(Color.BLACK);
            Date.setTextColor(Color.BLACK);

        } else {
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorSecondRow));

            number_bill.setTextColor(Color.BLACK);
            desc.setTextColor(Color.BLACK);
            customer_code.setTextColor(Color.BLACK);
            name_cust.setTextColor(Color.BLACK);
            bill_tybe.setTextColor(Color.BLACK);
            disc.setTextColor(Color.BLACK);
            Tootal.setTextColor(Color.BLACK);
            Date.setTextColor(Color.BLACK);
        }
        final View finalVi = vi;
        return vi;
    }
}