package com.example.laundryfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "db_laundry";
    public static final String tb_laundry = "tb_laundry";

    public static final String row_id = "_id";
    public static final String row_nama = "nama";
    public static final String row_latitude = "latitude";
    public static final String row_longitude = "longitude";
    public static final String row_status = "status";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tb_laundry + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nama + " TEXT," + row_latitude + " TEXT," + row_longitude + " TEXT," + row_status + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tb_laundry);
        onCreate(db);
    }

    //Get All SQLite Data
    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + tb_laundry + " ORDER BY " + row_id + " DESC ", null);
        return cur;
    }

    //GET 1 DATA BY ID
    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + tb_laundry + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(tb_laundry, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(tb_laundry, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(tb_laundry, row_id + "=" + id, null);
    }
}

