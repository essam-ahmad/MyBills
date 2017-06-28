package com.example.e3.mybills;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by e3 on 13/06/2017.
 */
public class items_Adapter extends BaseAdapter {
    //Context context;
    private Activity activity;
    private items[] data;
    //static int currentSelection = 0;
    private static LayoutInflater inflater = null;

    public items_Adapter(Activity a, items list[]) {
        activity = a;
        data = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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



   /* tbn_items
    col_itm_code INTEGER PRIMARY KEY
    col_itm_name text
    col_itm_cost numeric
    col_itm_price NUMERIC
    col_ad_date text*/

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.item_layout, null);
        //        TextView tv = (TextView) vi;

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
            vi.setBackgroundColor(Color.rgb(85,98,112));
            code.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            cost.setTextColor(Color.WHITE);
            price.setTextColor(Color.WHITE);
            ad_date.setTextColor(Color.WHITE);

        } else {
            vi.setBackgroundColor(Color.rgb(252,251,227
            ));
        }
        final View finalVi = vi;
        vi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
                builder.setTitle(v.getResources().getString(R.string.alert));
                builder.setMessage(data[position].get_col_itm_code() + ":" + data[position].get_col_itm_name());
                builder.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(finalVi.getContext(), ItemsEditActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("get_col_itm_code", data[position].get_col_itm_code());
                        b.putString("get_col_itm_name", data[position].get_col_itm_name());
                        b.putString("get_col_ad_date", data[position].get_col_ad_date());
                        b.putDouble("get_col_itm_Cost", data[position].get_col_itm_cost());
                        b.putDouble("get_col_itm_price", data[position].get_col_itm_price());
                        i.putExtras(b);
                        finalVi.getContext().startActivity(i);
                    }
                });

                builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(finalVi.getContext());
                        builder.setTitle(R.string.AlertDialog_Title_delete);
                        builder.setMessage(data[position].get_col_itm_name());
                        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                DataManager dataBase = new DataManager(finalVi.getContext());
                                dataBase.deleteOneItem(data[position].get_col_itm_code());
                                //finalVi.refreshDrawableState();


                            }

                        });
                        builder.setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();

                    }
                });
                builder.show();
                return true;
            }
        });
        return vi;
    }
}

