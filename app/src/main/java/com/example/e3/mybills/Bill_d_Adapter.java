package com.example.e3.mybills;

import android.app.Activity;
import android.content.Context;
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

public class Bill_d_Adapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<bill_d> data;
    private static LayoutInflater inflater = null;

    public Bill_d_Adapter(Activity a, bill_d list[]) {
        activity = a;
        data = new ArrayList<>(Arrays.asList(list));
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Bill_d_Adapter(Activity a, ArrayList<bill_d> list) {
        activity = a;
        data = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.bill_d_layout, null);
        TextView col_itm_code = (TextView) vi.findViewById(R.id.col_bill_d_code);
        TextView col_itm_name = (TextView) vi.findViewById(R.id.col_bill_d_name);
        TextView col_itm_qty = (TextView) vi.findViewById(R.id.col_bill_d_qty);
        TextView col_itm_cost = (TextView) vi.findViewById(R.id.col_bill_d_cost);
        TextView col_itm_price = (TextView) vi.findViewById(R.id.col_bill_d_price);
        TextView col_ad_Total = (TextView) vi.findViewById(R.id.col_bill_d_Total);
        col_itm_code.setText(data.get(position).get_col_itm_code());
        col_itm_name.setText(data.get(position).get_col_itm_name());
        col_itm_qty.setText(data.get(position).get_col_itm_qty());
        col_itm_cost.setText(data.get(position).get_col_itm_cost());
        col_itm_price.setText(data.get(position).get_col_itm_price());
        double price = Double.parseDouble(data.get(position).get_col_itm_price());
        double qty = Double.parseDouble(data.get(position).get_col_itm_qty());
        double result = price * qty;
        col_ad_Total.setText(result + "");
        if (position % 2 == 0) {
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorFirstRow));
            col_itm_code.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            col_itm_name.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            col_itm_qty.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            col_itm_cost.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            col_itm_price.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            col_ad_Total.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
        } else {
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorSecondRow));
            col_itm_code.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            col_itm_name.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            col_itm_qty.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            col_itm_cost.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            col_itm_price.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            col_ad_Total.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
        }
        return vi;
    }
}