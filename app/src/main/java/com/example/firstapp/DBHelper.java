package com.example.firstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    //public static final String create="CREATE TABLE RateView("+"ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"curname text,"+"currate text)";
    public static final String tb_Name="RateView";
    public static final String create="CREATE TABLE "+tb_Name+"("+"ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"curName text,"+"curRate text)";
    //private Context mcontext;
    private static final int Version=1;
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        //mcontext=context;
    }

    //有数据库之后，oncreate不再执行
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
        //Toast.makeText(mcontext,"success",Toast.LENGTH_SHORT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
/*private static final int VERSION = 1;
    private static final String DB_NAME = "myrate.db";
    public static final String TB_NAME = "tb_rates";
    private Context mcontext;
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME TEXT,CURRATE TEXT)");
        //db.execSQL("CREATE TABLE"+TB_NAME+"(id integer primary key autoincrement,curname text,currate text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }*/
}
