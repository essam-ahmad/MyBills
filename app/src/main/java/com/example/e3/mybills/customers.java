package com.example.e3.mybills;

/**
 * Created by e3 on 12/06/2017.
 */
    /*
    tbn_customers
        col_c_code integer PRIMARY KEY AUTOINCREMENT
        col_c_name TEXT NOT NULL
        col_ad_date TEXT
        col_phone TEXT
        col_address TEXT
    */
public class customers {
    private int     _col_c_code;
    private String  _col_c_name;
    private String  _col_ad_date;
    private String  _col_phone;
    private String  _col_address;

    public customers() {
    }

    public customers(int pCol_c_code ,String pCol_c_name, String pCol_ad_date
            ,String pCol_phone , String pCol_address) {
        set_col_c_code(pCol_c_code);
        set_col_c_name(pCol_c_name);
        set_col_ad_date(pCol_ad_date);
        set_col_phone(pCol_phone);
        set_col_address(pCol_address);
    }

    public int get_col_c_code() {
        return _col_c_code;
    }

    public void set_col_c_code(int _col_c_code) {
        this._col_c_code = _col_c_code;
    }

    public String get_col_c_name() {
        return _col_c_name;
    }

    public void set_col_c_name(String _col_c_name) {
        this._col_c_name = _col_c_name;
    }

    public String get_col_ad_date() {
        return _col_ad_date;
    }

    public void set_col_ad_date(String _col_ad_date) {
        this._col_ad_date = _col_ad_date;
    }

    public String get_col_phone() {
        return _col_phone;
    }

    public void set_col_phone(String _col_phone) {
        this._col_phone = _col_phone;
    }

    public String get_col_address() {
        return _col_address;
    }

    public void set_col_address(String _col_address) {
        this._col_address = _col_address;
    }
}
