package com.example.e3.mybills;

/**
 * Created by e3 on 10/06/2017.

   tbn_items
           col_itm_code INTEGER PRIMARY KEY
           col_itm_name text
           col_itm_cost numeric
           col_itm_price NUMERIC
           col_ad_date text*/

public class items {
    private int _col_itm_code;
    private String _col_itm_name;
    private double _col_itm_cost;
    private double _col_itm_price;
    private String _col_ad_date;

    public items() {
    }

    public items(int pCol_itm_code ,String pCol_itm_name, Double pCol_itm_cost
            ,Double pCol_itm_price , String pCol_ad_date) {
        set_col_itm_code(pCol_itm_code);
        set_col_itm_name(pCol_itm_name);
        set_col_itm_cost(pCol_itm_cost);
        set_col_itm_price(pCol_itm_price);
        set_col_ad_date(pCol_ad_date);
    }

    public int get_col_itm_code() {
        return _col_itm_code;
    }

    public void set_col_itm_code(int _col_itm_code) {
        this._col_itm_code = _col_itm_code;
    }

    public String get_col_itm_name() {
        return _col_itm_name;
    }

    public void set_col_itm_name(String _col_itm_name) {
        this._col_itm_name = _col_itm_name;
    }

    public double get_col_itm_cost() {
        return _col_itm_cost;
    }

    public void set_col_itm_cost(double _col_itm_cost) {
        this._col_itm_cost = _col_itm_cost;
    }

    public double get_col_itm_price() {
        return _col_itm_price;
    }

    public void set_col_itm_price(double _col_itm_price) {
        this._col_itm_price = _col_itm_price;
    }

    public String get_col_ad_date() {
        return _col_ad_date;
    }

    public void set_col_ad_date(String _col_ad_date) {
        this._col_ad_date = _col_ad_date;
    }
}

