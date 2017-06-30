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
 * Created by e3 on 30/06/2017.
 */
public class customers_Adapter extends BaseAdapter {
    private Activity activity;
    private customers[] data;
    private static LayoutInflater inflater = null;

    public customers_Adapter(Activity a, customers[] list) {
        activity = a;
        data = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length;
    }

    @Override
    public customers getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(data[position].get_col_c_code());
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.customer_layout, null);
        TextView code = (TextView) vi.findViewById(R.id.col_c_code);
        TextView name = (TextView) vi.findViewById(R.id.col_c_name);
        TextView phone = (TextView) vi.findViewById(R.id.col_phone);
        TextView address = (TextView) vi.findViewById(R.id.col_address);
        TextView ad_date = (TextView) vi.findViewById(R.id.col_ad_date);
        code.setText(data[position].get_col_c_code());
        name.setText(data[position].get_col_c_name());
        phone.setText(data[position].get_col_phone());
        address.setText(data[position].get_col_address());
        ad_date.setText(data[position].get_col_ad_date());
        if (position % 2 == 0) {
            //noinspection ResourceAsColor
            vi.setBackgroundColor(R.color.colorFirstRow);
            code.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            phone.setTextColor(Color.WHITE);
            address.setTextColor(Color.WHITE);
            ad_date.setTextColor(Color.WHITE);
        } else {
            //noinspection ResourceAsColor
            vi.setBackgroundColor(R.color.colorSecondRow);
            code.setTextColor(Color.BLACK);
            name.setTextColor(Color.BLACK);
            phone.setTextColor(Color.BLACK);
            address.setTextColor(Color.BLACK);
            ad_date.setTextColor(Color.BLACK);
        }
        final View finalVi = vi;
        vi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
                builder.setTitle(v.getResources().getString(R.string.alert));
                builder.setMessage(data[position].get_col_c_code() + ":" + data[position].get_col_c_name());
                builder.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(finalVi.getContext(), CustomersAddEditActivity.class);
                        Bundle b = new Bundle();
                        b.putString("action", "edit");
                        b.putString("get_col_c_code", data[position].get_col_c_code());
                        b.putString("get_col_c_name", data[position].get_col_c_name());
                        b.putString("get_col_phone", data[position].get_col_phone());
                        b.putString("get_col_address", data[position].get_col_address());
                        i.putExtras(b);
                        finalVi.getContext().startActivity(i);
                    }
                });
                builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(finalVi.getContext());
                        yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                        yesOrNoBuilder.setMessage(data[position].get_col_c_name());
                        yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                DataManager dataBase = new DataManager(finalVi.getContext());
                                dataBase.deleteOneCustomer(data[position].get_col_c_code());
                                ArrayList<customers> arrayList = new ArrayList<>(Arrays.asList(data));
                                arrayList.remove(data[position]);
                                data = arrayList.toArray(new customers[]{});
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
        });
        return vi;
    }
}