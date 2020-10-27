package com.example.firstapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class sqlTest extends AppCompatActivity {
    private DBHelper dbHelper;
    private String tbName;
    //Handler handler;
    private static final String TAG = "rateManager:";
    private DBHelper dbhelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        dbhelper = new DBHelper(this, "RateView", null, 1);
        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        //创建数据库
        Button createDb = findViewById(R.id.createdb);
        createDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    //database.execSQL("drop table " + mTable);
                //dbhelper.getWritableDatabase();
            }
        });
        //插入数据
        Button insertDb = findViewById(R.id.insertdb);
        insertDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("curName", "china");
                values.put("curRate", "666");
                db.insert("RateView", null, values);
                values.clear();
                values.put("curName", "americaa");
                values.put("curRate", "777");
                db.insert("RateView", null, values);
            }
        });
        //查询数据
        Button queryDb = findViewById(R.id.querydb);
        queryDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SQLiteDatabase db = dbhelper.getWritableDatabase();
                Cursor cursor = db.query("RateView", null, null, null, null, null, null);
                if (cursor!=null&&cursor.moveToFirst()) {
                    do {
                       /* if(cursor.getColumnIndex("curName")==-1){
                            Log.i(TAG, "curName==null");
                            return;
                        }*/
                        String curName[]=cursor.getColumnNames();
                        String curRate=cursor.getString(2);
                        Log.i(TAG, "curName=" + curName[1]);
                        Log.i(TAG, "curRate=" + curRate);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
