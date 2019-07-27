package com.example.testsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLHelperNew extends SQLiteOpenHelper {
    final static String DB_NAME = "Customer.db";
    final static String DB_NAME_TABLE = "customer";
    final static int DB_VERSION = 12;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;
    private static final String TAG = "SQLHelper";

    public SQLHelperNew(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String querySQL = "CREATE TABLE  customer (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                " pass TEXT NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(querySQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("drop table if exists " + DB_NAME_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public void insertValues() {
        sqLiteDatabase = getWritableDatabase();
        for (int i=0;i<5;i++) {
            contentValues = new ContentValues();
            contentValues.put("name", "hoan"+i);
            contentValues.put("pass", "Hoan1997@");
            sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        }
        closeDB();
    }

public ArrayList<String> getAllUser(){
        ArrayList<String> listUser=new ArrayList<>();
        String selectQuery= "Select name From customer";
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                String name =cursor.getString(cursor.getColumnIndex("name"));
                listUser.add(name);
            }while (cursor.moveToNext());
        }
        closeDB();
        return listUser ;
}
//    public void updateProduct(String id, String name, String pass) {
//        sqLiteDatabase = getWritableDatabase();
//        contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("pass", pass);
//        sqLiteDatabase.update(DB_NAME_TABLE, contentValues, "id=?", new String[]{String.valueOf(id)});
//    }

    public int deleteitem(String id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, "id=?", new String[]{String.valueOf(id)});
    }

    public boolean delAllProduct() {

        int result;
        sqLiteDatabase = getWritableDatabase();
        result = sqLiteDatabase.delete(DB_NAME_TABLE, null, null);
        closeDB();
        if (result == 1) return true;
        else return false;
    }



public String getPassByUser(String user){
//        String sqlgetPass= "Select pass From customer Where name=?", new String[]{user};
    sqLiteDatabase = this.getWritableDatabase();
    Cursor cursor =sqLiteDatabase.rawQuery("Select pass From customer Where name=?", new String[]{user});
        cursor.moveToFirst();
        String pass = cursor.getString(cursor.getColumnIndex("pass"));
        return pass;
}
    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}
