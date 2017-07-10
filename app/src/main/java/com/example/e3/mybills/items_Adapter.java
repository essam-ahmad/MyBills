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

public class items_Adapter extends BaseAdapter {
    private Activity activity;
    private items[] data;
    private static LayoutInflater inflater = null;
    int FromBill;

    public items_Adapter(Activity a, items list[],int clas) {
        activity = a;
        data = list;
        FromBill=clas;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.length;
    }

    @Override
    public items getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return data[position].get_col_itm_code();
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.item_layout, null);
        TextView code = (TextView) vi.findViewById(R.id.col_itm_code);
        TextView name = (TextView) vi.findViewById(R.id.col_itm_name);
        TextView cost = (TextView) vi.findViewById(R.id.col_itm_cost);
        TextView price = (TextView) vi.findViewById(R.id.col_itm_price);
        TextView ad_date = (TextView) vi.findViewById(R.id.col_ad_date);
        code.setText(String.valueOf(data[position].get_col_itm_code()));
        name.setText(data[position].get_col_itm_name());
        cost.setText(String.valueOf(data[position].get_col_itm_cost()));
        price.setText(String.valueOf(data[position].get_col_itm_price()));
        ad_date.setText(data[position].get_col_ad_date());
        if (position % 2 == 0) {
            //noinspection ResourceAsColor
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorFirstRow));
            code.setTextColor(Color.BLACK);
            name.setTextColor(Color.BLACK);
            cost.setTextColor(Color.BLACK);
            price.setTextColor(Color.BLACK);
            ad_date.setTextColor(Color.BLACK);
        } else {
            //noinspection ResourceAsColor
            vi.setBackgroundColor(activity.getResources().getColor(R.color.colorSecondRow));
            code.setTextColor(Color.BLACK);
            name.setTextColor(Color.BLACK);
            cost.setTextColor(Color.BLACK);
            price.setTextColor(Color.BLACK);
            ad_date.setTextColor(Color.BLACK);
        }
        final View finalVi = vi;

        if (FromBill==1){
            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(finalVi.getContext(), ItemsAddEditActivity.class);
                    Bundle b = new Bundle();
                    b.putString("action","editqty");
                    b.putInt("get_col_itm_code", data[position].get_col_itm_code());
                    b.putString("get_col_itm_name", data[position].get_col_itm_name());
                    b.putDouble("get_col_itm_Cost", data[position].get_col_itm_cost());
                    b.putDouble("get_col_itm_price", data[position].get_col_itm_price());
                    i.putExtras(b);
                    i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    finalVi.getContext().startActivity(i);
                    activity.finish();
                }
            });
        }else {

        vi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
                builder.setTitle(v.getResources().getString(R.string.alert));
                builder.setMessage(data[position].get_col_itm_code() + ":" + data[position].get_col_itm_name());
                builder.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(finalVi.getContext(), ItemsAddEditActivity.class);
                        Bundle b = new Bundle();
                        b.putString("action","edit");
                        b.putInt("get_col_itm_code", data[position].get_col_itm_code());
                        b.putString("get_col_itm_name", data[position].get_col_itm_name());
                        b.putDouble("get_col_itm_Cost", data[position].get_col_itm_cost());
                        b.putDouble("get_col_itm_price", data[position].get_col_itm_price());
                        i.putExtras(b);
                        finalVi.getContext().startActivity(i);
                    }
                });
                builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder yesOrNoBuilder = new AlertDialog.Builder(finalVi.getContext());
                        yesOrNoBuilder.setTitle(R.string.AlertDialog_Title_delete);
                        yesOrNoBuilder.setMessage(data[position].get_col_itm_name());
                        yesOrNoBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                DataManager dataBase = new DataManager(finalVi.getContext());
                                bill_d result = dataBase.Get_Bill_d_ById(null, String.valueOf(data[position].get_col_itm_code()));
                                if (result.get_col_bill_seq() == null) {
                                dataBase.deleteOneItem(data[position].get_col_itm_code());
                                ArrayList<items> arrayList = new ArrayList<>(Arrays.asList(data));
                                arrayList.remove(data[position]);
                                data = arrayList.toArray(new items[]{});
                                notifyDataSetChanged();
                                    Toast.makeText(finalVi.getContext(), "تم الحذف ", Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(finalVi.getContext(), "لايمكن حذف الصنف لارتباطة بفاتورة", Toast.LENGTH_LONG).show();

                                }
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
        });}
        return vi;
    }
}