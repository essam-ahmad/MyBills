package com.example.e3.mybills;
/**
 * Created by e3 on 10/06/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager extends SQLiteOpenHelper {
    //region create the database ,tables and columns
    private static final int version = 1;
    private static final String Tag = "DataManager";
    private static final String databaseName = "MyBills.db";

    private static final String tbn_customers = "customers";
    private static final String tbn_items = "items";
    private static final String tbN_bill_m = "bill_m";
    private static final String tbN_bill_d = "bill_d";

    private static final String col_c_code = "c_code";
    private static final String col_c_name = "c_name";
    private static final String col_phone = "phone";
    private static final String col_address = "address";
    private static final String col_ad_date = "ad_date";
    private static final String col_itm_code = "itm_code";
    private static final String col_itm_name = "itm_name";
    private static final String col_itm_price = "itm_price";
    private static final String col_itm_cost = "itm_cost";
    private static final String col_bill_seq = "bill_seq";
    private static final String col_bill_yr = "bill_yr";
    private static final String col_bill_no = "bill_no";
    private static final String col_bill_type = "bill_type";
    private static final String col_bill_date = "bill_date";
    private static final String col_disc_amt = "disc_amt";
    private static final String col_desc = "desc";
    private static final String col_bill_amt = "bill_amt";
    private static final String col_bill_d_seq = "bill_d_seq";
    private static final String col_itm_qty = "itm_qty";

    public DataManager(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version, DatabaseErrorHandler errorHandler) {
        super(context, databaseName, factory, version, errorHandler);
    }

    public DataManager(Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_statement = new String();
        sql_statement = "create table IF NOT EXISTS " + tbn_customers +
                "( " + col_c_code + " integer PRIMARY KEY AUTOINCREMENT" +
                ", " + col_c_name + " TEXT NOT NULL" +
                ", " + col_ad_date + " TEXT" +
                ", " + col_phone + " TEXT" +
                ", " + col_address + " TEXT )";
        db.execSQL(sql_statement);

        sql_statement = "CREATE TABLE IF NOT EXISTS " + tbn_items +//رقم الصنف
                "( " + col_itm_code + " INTEGER PRIMARY KEY " +
                ", " + col_itm_name + " TEXT NOT NULL" +
                ", " + col_itm_cost + " numeric default 0 " +
                ", " + col_itm_price + " NUMERIC DEFAULT 0" +
                ", " + col_ad_date + " TEXT )";
        db.execSQL(sql_statement);

        sql_statement = "CREATE TABLE IF NOT EXISTS " + tbN_bill_m +
                "( " + col_bill_seq + " integer PRIMARY KEY AUTOINCREMENT" +
                ", " + col_bill_yr + " integer NOT NULL " +
                ", " + col_bill_no + " INTEGER NOT NULL " +
                ", " + col_bill_type + " INTEGER NOT NULL DEFAULT 1" +
                ", " + col_bill_date + " TEXT" +
                ", " + col_c_code + " INTEGER" +
                ", " + col_disc_amt + " NUMERIC" +
                ", " + col_desc + " TEXT" +
                ", " + col_bill_amt + " NUMERIC" +
                ", FOREIGN KEY(" + col_c_code + ") REFERENCES " + tbn_customers + "(" + col_c_code + ")" +
                ",UNIQUE (" + col_bill_yr + ", " + col_bill_no + "))";
        db.execSQL(sql_statement);

        sql_statement = "CREATE TABLE IF NOT EXISTS " + tbN_bill_d +
                "(" + col_bill_seq + " integer " +
                "," + col_bill_yr + " integer NOT NULL " +
                "," + col_bill_no + " INTEGER NOT NULL " +
                "," + col_bill_d_seq + " TEXT " +
                "," + col_itm_code + " INTEGER " +
                "," + col_itm_price + " numeric " +
                "," + col_itm_cost + " numeric " +
                "," + col_itm_qty + " numeric, " +
                " PRIMARY KEY(" + col_bill_seq + "," + col_bill_d_seq + ") " +
                ",FOREIGN KEY (" + col_bill_seq + ") REFERENCES " + tbN_bill_m + "(" + col_bill_seq + ") " +
                ",FOREIGN KEY (" + col_itm_code + ") REFERENCES " + tbn_items + "(" + col_itm_code + "))";
        db.execSQL(sql_statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Tag, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which destroys existing data.");
        db.execSQL("Drop table if EXISTS " + tbN_bill_d);
        db.execSQL("Drop table if EXISTS " + tbN_bill_m);
        db.execSQL("Drop table if EXISTS " + tbn_items);
        db.execSQL("Drop table if EXISTS " + tbn_customers);
        onCreate(db);
    }
    //endregion

    //region tbn_items
    /*
    tbn_items
        col_itm_code INTEGER PRIMARY KEY
        col_itm_name text
        col_itm_cost numeric
        col_itm_price NUMERIC
        col_ad_date text
    */
    public boolean addItem(int pCode, String pName, double pCost, double pPrice, String pDate) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues value = new ContentValues();
            value.put(col_itm_code, pCode);
            value.put(col_itm_name, pName);
            value.put(col_itm_cost, pCost);
            value.put(col_itm_price, pPrice);
            value.put(col_ad_date, pDate);
            Long result = db.insertOrThrow(tbn_items, null, value);
            Log.d(Tag, "addItem: Adding " + pCode + " to Table" + tbn_items);
            //if data inserted incorrectly it will return -1
            //if (result==-1){return false;}else{return true;}
            if (result > 0) {
                return true;
            }
            Log.d(Tag, "addItem: Error when inserting " + pCode + " to Table " + tbn_items);
            //throw new SQLException("Failed to insert row into " + tbn_items);
            return false;
        } catch (Exception e) {
            Log.d(Tag, "addItem: Error when inserting " + pCode + " to Table " + tbn_items + " " + e.getMessage());
            e.printStackTrace();
            //throw new SQLException("Failed to insert row into " + tbn_items);
            return false;
        }
    }

    public int UpdateItem(int pCode, String pName, double pCost,
                          double pPrice
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(col_itm_name, pName);
        value.put(col_itm_cost, pCost);
        value.put(col_itm_price, pPrice);
        return (db.update(tbn_items, value, col_itm_code + " = ? ", new String[]{Integer.toString(pCode)}));
    }

    public boolean deleteOneItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = col_itm_code + "=? ";
        String[] whereArgs = new String[]{String.valueOf(id.toString())};
        int result = db.delete(tbn_items, whereClause, whereArgs);
        Log.d(Tag, "deleteOneItem: Deleting " + id + " From Table" + tbn_items);
        Log.d(Tag, "deleteOneItem: " + result + " items ware Deleted From Table" + tbn_items);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
        /*db.execSQL("delete from "+tbn_items+" " +
                "where "+col_itm_code+"="+ Integer.toString(id));*/
    }

    public items[] getAllItems() {
        items list[] = null;
        String query = "SELECT " + col_itm_code + "," + col_itm_name + "" +
                "," + col_itm_cost + "," + col_itm_price + "," + col_ad_date + "" +
                " FROM " + tbn_items;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        list = new items[cursor.getCount()];
        items items = null;
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                items = new items();
                items.set_col_itm_code(cursor.getInt(0));
                items.set_col_itm_name(cursor.getString(1));
                items.set_col_itm_cost(cursor.getDouble(2));
                items.set_col_itm_price(cursor.getDouble(3));
                items.set_col_ad_date(cursor.getString(4));
                list[index] = items;
                index++;
            } while (cursor.moveToNext());
        }
        return list;
    }

    public items[] SearchItem(String itemName) {
        items list[] = null;
        String query = "SELECT " + col_itm_code + "," +
                col_itm_name + "," +
                col_itm_cost + "," +
                col_itm_price + "," +
                col_ad_date +
                " FROM " + tbn_items +
                " where " + col_itm_name + " like '%" + itemName + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        list = new items[cursor.getCount()];
        items items = null;
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                items = new items();
                //items.set_col_itm_code(Integer.parseInt(cursor.getString(cursor.getColumnIndex("i_code"))));
                items.set_col_itm_code(cursor.getInt(0));
                items.set_col_itm_name(cursor.getString(1));
                items.set_col_itm_cost(cursor.getDouble(2));
                items.set_col_itm_price(cursor.getDouble(3));
                items.set_col_ad_date(cursor.getString(4));
                list[index] = items;
                index++;
            } while (cursor.moveToNext());
        }
        return list;
    }

    public items getItemById(int id) {
        String query = "SELECT " + col_itm_code + "," +
                col_itm_name + "," +
                col_itm_cost + "," +
                col_itm_price + "," +
                col_ad_date +
                " FROM " + tbn_items +
                " where " + col_itm_code + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        items item = null;
        if (cursor.moveToFirst()) {
            item = new items();
            item.set_col_itm_code(cursor.getInt(0));
            item.set_col_itm_name(cursor.getString(1));
            item.set_col_itm_cost(cursor.getDouble(2));
            item.set_col_itm_price(cursor.getDouble(3));
            item.set_col_ad_date(cursor.getString(4));
        } else {
            item = new items();
            item.set_col_itm_code(-1);
            item.set_col_itm_name(col_itm_code + " " + id + " Not found");
        }
        return item;
    }

    //endregion

    //region tbn_customers
    /*
        col_c_code integer PRIMARY KEY AUTOINCREMENT
        col_c_name TEXT NOT NULL
        col_ad_date TEXT
        col_phone TEXT
        col_address TEXT
    */
    public boolean addCustomer(String pCode, String pName, String pAd_date,
                               String pPhone, String pAddress) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues value = new ContentValues();
            value.put(col_c_code, pCode);
            value.put(col_c_name, pName);
            value.put(col_ad_date, pAd_date);
            value.put(col_phone, pPhone);
            value.put(col_address, pAddress);
            Long result = db.insertOrThrow(tbn_customers, null, value);
            Log.d(Tag, "addCustomer: Adding " + pCode + " to Table" + tbn_customers);
            //if data inserted incorrectly it will return -1
            //if (result==-1){return false;}else{return true;}
            if (result > 0) {
                return true;
            }
            Log.d(Tag, "addCustomer: Error when inserting" + pCode + " to Table" + tbn_customers);
            return false;
            //throw new SQLException("Failed to insert row into " + tbn_customers);
        } catch (Exception e) {
            Log.d(Tag, "addCustomer: Error when inserting" + pCode + " to Table" + tbn_customers + " " + e.getMessage());
            e.printStackTrace();
            //throw new SQLException("Failed to insert row into " + tbn_customers);
            return false;
        }
    }

    public int UpdateCustomer(String pCode, String pName,
                              String pPhone, String pAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(col_c_name, pName);
        value.put(col_phone, pPhone);
        value.put(col_address, pAddress);
        return (db.update(tbn_customers, value, col_c_code + " = ? ", new String[]{pCode}));
    }

    public boolean deleteOneCustomer(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = col_c_code + "=? ";
        String[] whereArgs = new String[]{id};
        int result = db.delete(tbn_customers, whereClause, whereArgs);
        Log.d(Tag, "deleteOneCustomer: Deleting " + id + " From Table" + tbn_customers);
        Log.d(Tag, "deleteOneCustomer: " + result + " customers ware Deleted From Table" + tbn_customers);
        if (result == 0) {
            return false;
        } else {
            return true;
        }
        /*db.execSQL("delete from "+tbn_customers+" " +
                "where "+col_c_code+"="+ Integer.toString(id));*/
    }

    public customers[] getAllCustomers() {
        customers list[] = null;
        String query = "SELECT " + col_c_code + "," + col_c_name + "" +
                "," + col_phone + "," + col_ad_date + "," + col_address + "" +
                " FROM " + tbn_customers;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        list = new customers[cursor.getCount()];
        customers customers = null;
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                customers = new customers();
                customers.set_col_c_code(cursor.getString(0));
                customers.set_col_c_name(cursor.getString(1));
                customers.set_col_phone(cursor.getString(2));
                customers.set_col_ad_date(cursor.getString(3));
                customers.set_col_address(cursor.getString(4));
                list[index] = customers;
                index++;
            } while (cursor.moveToNext());
        }
        return list;
    }

    public customers[] SearchCustomer(String itemName) {
        customers list[] = null;
        String query = "SELECT " + col_c_code + "," +
                col_c_name + "," +
                col_phone + "," +
                col_ad_date + "," +
                col_address +
                " FROM " + tbn_customers +
                " where " + col_c_name + " like '%" + itemName + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        list = new customers[cursor.getCount()];
        customers customers = null;
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                customers = new customers();
                //customers.setCol_c_code(Integer.parseInt(cursor.getString(cursor.getColumnIndex("i_code"))));
                customers.set_col_c_code(cursor.getString(0));
                customers.set_col_c_name(cursor.getString(1));
                customers.set_col_phone(cursor.getString(2));
                customers.set_col_ad_date(cursor.getString(3));
                customers.set_col_address(cursor.getString(4));
                list[index] = customers;
                index++;
            } while (cursor.moveToNext());
        }
        return list;
    }

    public customers getCustomerById(String id) {
        String query = "SELECT " + col_c_code + "," +
                col_c_name + "," +
                col_phone + "," +
                col_ad_date + "," +
                col_address +
                " FROM " + tbn_customers +
                " where " + col_c_code + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        customers item = null;
        if (cursor.moveToFirst()) {
            item = new customers();
            item.set_col_c_code(cursor.getString(0));
            item.set_col_c_name(cursor.getString(1));
            item.set_col_phone(cursor.getString(2));
            item.set_col_ad_date(cursor.getString(3));
            item.set_col_address(cursor.getString(4));
        } else {
            item = new customers();
            //item.set_col_c_code( );
            item.set_col_c_name(col_c_code + " " + id + " Not found");
        }
        return item;
    }
    //endregion


/*    addbill_m
            addbill_d
            UpdateBill_m
                    UpdateBill_d
    deleteOneBill_m
    deleteBill_d(Integer id)
    deleteBill_d(Integer id,String isec)

        public BILL_MCALSS[] getAllBill_m()
        public  BILL_DCLASSFROMDATDBASE[] getBill_d (int code)
*/


}