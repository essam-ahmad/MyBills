package com.example.e3.mybills;

/**
 * Created by e3 on 01/07/2017.
 */

public class bill_d {
    private String _col_bill_seq, _col_bill_yr, _col_bill_no,
            _col_bill_d_seq, _col_itm_code, _col_itm_name,
            _col_itm_price, _col_itm_cost, _col_itm_qty;

    public enum rowStatus {
        newRow, updatedRow, deletedRow,unChanged
    }

    ;

    private rowStatus _rowStatus;

    public bill_d() {
    }

    public bill_d(String _col_itm_code, String _col_itm_price, String _col_itm_cost, String _col_itm_qty, String _col_itm_name, rowStatus _rowStatus) {
        set_col_itm_code(_col_itm_code);
        set_col_itm_price(_col_itm_price);
        set_col_itm_cost(_col_itm_cost);
        set_col_itm_qty(_col_itm_qty);
        set_col_itm_name(_col_itm_name);
        set_rowStatus(_rowStatus);
    }

    public bill_d(String _col_bill_seq, String _col_bill_yr, String _col_bill_no,
                  String _col_bill_d_seq, String _col_itm_code,
                  String _col_itm_price, String _col_itm_cost, String _col_itm_qty, String _col_itm_name, rowStatus _rowStatus) {
        set_col_bill_seq(_col_bill_seq);
        set_col_bill_yr(_col_bill_yr);
        set_col_bill_no(_col_bill_no);
        set_col_bill_d_seq(_col_bill_d_seq);
        set_col_itm_code(_col_itm_code);
        set_col_itm_price(_col_itm_price);
        set_col_itm_cost(_col_itm_cost);
        set_col_itm_qty(_col_itm_qty);
        set_col_itm_name(_col_itm_name);
        set_rowStatus(_rowStatus);
    }

    public void set_col_bill_seq(String _col_bill_seq) {
        this._col_bill_seq = _col_bill_seq;
    }

    public String get_col_bill_seq() {
        return _col_bill_seq;
    }

    public void set_col_bill_yr(String _col_bill_yr) {
        this._col_bill_yr = _col_bill_yr;
    }

    public String get_col_bill_yr() {
        return _col_bill_yr;
    }

    public void set_col_bill_no(String _col_bill_no) {
        this._col_bill_no = _col_bill_no;
    }

    public String get_col_bill_no() {
        return _col_bill_no;
    }

    public void set_col_bill_d_seq(String _col_bill_d_seq) {
        this._col_bill_d_seq = _col_bill_d_seq;
    }

    public String get_col_bill_d_seq() {
        return _col_bill_d_seq;
    }

    public void set_col_itm_code(String _col_itm_code) {
        this._col_itm_code = _col_itm_code;
    }

    public String get_col_itm_code() {
        return _col_itm_code;
    }

    public void set_col_itm_price(String _col_itm_price) {
        this._col_itm_price = _col_itm_price;
    }

    public String get_col_itm_price() {
        return _col_itm_price;
    }

    public void set_col_itm_cost(String _col_itm_cost) {
        this._col_itm_cost = _col_itm_cost;
    }

    public String get_col_itm_cost() {
        return _col_itm_cost;
    }

    public void set_col_itm_qty(String _col_itm_qty) {
        this._col_itm_qty = _col_itm_qty;
    }

    public String get_col_itm_qty() {
        return _col_itm_qty;
    }

    public void set_col_itm_name(String _col_itm_name) {
        this._col_itm_name = _col_itm_name;
    }

    public String get_col_itm_name() {
        return _col_itm_name;
    }

    public rowStatus get_rowStatus() {
        return _rowStatus;
    }

    public void set_rowStatus(rowStatus _rowStatus) {
        this._rowStatus = _rowStatus;
    }

}