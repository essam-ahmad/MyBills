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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by EBRHEEM on 7/6/2017.
 */

public class Bill_Adapter extends BaseAdapter {
    private Activity activity;
    private bill_m[] data;
    private static LayoutInflater inflater = null;
    public Bill_Adapter(Activity a, bill_m[] list) {
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
        TextView number_bill = (TextView) vi.findViewById(R.id.bill_no);
        TextView desc = (TextView) vi.findViewById(R.id.desc);
        TextView customer_code = (TextView) vi.findViewById(R.id.customer_code);
        TextView customer_name = (TextView) vi.findViewById(R.id.customer_name);
        TextView bill_type = (TextView) vi.findViewById(R.id.bill_tybe);
        TextView disc = (TextView) vi.findViewById(R.id.disc);
        TextView Total = (TextView) vi.findViewById(R.id.Total);
        TextView Date = (TextView) vi.findViewById(R.id.Date);
        TextView NetTotal = (TextView) vi.findViewById(R.id.NetTotal);
        number_bill.setText(data[position].get_col_bill_no());
        desc.setText(data[position].get_col_desc());
        if (data[position].get_col_c_code().equals("")){
            customer_code.setText("_");
            customer_name.setText("_");
        }else  if (!data[position].get_col_c_code().equals("")){
            customer_code.setText(data[position].get_col_c_code());
            customer_name.setText(new DataManager(activity).getCustomerById(data[position].get_col_c_code()).get_col_c_name());
        }
        if (Integer.parseInt(data[position].get_col_bill_type()) == 1) {
            bill_type.setText(R.string.cash);
        } else if (Integer.parseInt(data[position].get_col_bill_type())==2) {
            bill_type.setText(R.string.credit);
        }
        disc.setText(data[position].get_col_disc_amt());
        Total.setText(data[position].get_col_bill_amt());
        Date.setText(data[position].get_col_bill_date());
        if (disc.getText().length()==0){
            disc.setText("0");
            NetTotal.setText(Total.getText().toString()+"");
        }else {double _nettotal = Double.parseDouble(Total.getText().toString())-Double.parseDouble(disc.getText().toString());
            NetTotal.setText(_nettotal+"");}
        if (position % 2 == 0) {
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorFirstRow));
            number_bill.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            desc.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            customer_code.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            customer_name.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            bill_type.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            disc.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            Total.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            Date.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
            NetTotal.setTextColor(activity.getResources().getColor(R.color.ColorFirstRowText));
        } else {
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorSecondRow));
            number_bill.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            desc.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            customer_code.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            customer_name.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            bill_type.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            disc.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            Total.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            Date.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
            NetTotal.setTextColor(activity.getResources().getColor(R.color.colorSecondRowText));
        }
        final View finalVi = vi;
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(finalVi.getContext(),ShowBill_Info.class);
                Bundle bundle=new Bundle();
                bundle.putString("Number_bill",data[position].get_col_bill_seq());
                intent.putExtras(bundle);
                finalVi.getContext().startActivity(intent);
            }
        });
       /* vi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
                builder.setTitle(finalVi.getResources().getString(R.string.alert));
                builder.setMessage(R.string.delete);
                builder.setNeutralButton(finalVi.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
                        builder.setTitle(R.string.AlertDialog_Title_delete);
                        builder.setMessage(R.string.You_will_not_be_able_to_retrieve);
                        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                new DataManager(finalVi.getContext()).deleteOneBill_m(new DataManager(finalVi.getContext()).getBill_m_ById(Integer.parseInt(data[position].get_col_bill_seq())).get_col_bill_seq());
                                new DataManager(finalVi.getContext()).deleteOneBill_d(new DataManager(finalVi.getContext()).getBill_m_ById(Integer.parseInt(data[position].get_col_bill_seq())).get_col_bill_seq());
                                ArrayList<bill_m> arrayList = new ArrayList<>(Arrays.asList(data));
                                arrayList.remove(data[position]);
                                data = arrayList.toArray(new bill_m[]{});
                                notifyDataSetChanged();
                                Toast.makeText(finalVi.getContext(),finalVi.getResources().getString(R.string.delete)+"...", Toast.LENGTH_LONG).show();
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
        });*/
        return vi;
    }
}