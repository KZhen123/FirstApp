package com.example.firstapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RateList_refresh extends ListActivity implements Runnable{
    public static final String TAG="kkk";
    Handler handler;
    private SharedPreferences sp;
    Thread t;int day;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);//当前日期
        sp=getSharedPreferences("ratelist", Activity.MODE_PRIVATE);
        //把时间存储下来，每次启动读取时间判断是不是同一天
        //是同一天则将存储的汇率信息显示出来，不是则重新启动子线程
        PreferenceManager.getDefaultSharedPreferences(this);
        int day_save = sp.getInt("day_save",0);
        Set<String> rate_info = sp.getStringSet("rate_info",null);
        Log.i(TAG,"today:"+day+"saveday:"+day_save);
        if(day_save==day){//存储的内容是当天的
            Log.i(TAG, "run: list2=" + rate_info);
            Set<String> list2=rate_info;
            ListAdapter adapter=new ArrayAdapter<String>(RateList_refresh.this,android.R.layout.simple_list_item_1, (List<String>) list2);
            setListAdapter(adapter);
        }else{
            t=new Thread(this);
            t.start();
            handler=new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==5){//身份验证
                        Set<String> list2=(Set<String>)msg.obj;
                        Log.i(TAG, "run: list2=" + list2);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putInt("day_save",day);
                        editor.putStringSet("rate_info",list2);
                        ListAdapter adapter=new ArrayAdapter<String>(RateList_refresh.this,android.R.layout.simple_list_item_1, (List<String>) list2);
                        setListAdapter(adapter);
                    }
                    super.handleMessage(msg);
                }
            };

        }
        //SharedPreferences.Editor editor=sp.edit();
    }
    //在子线程获取网络数据
    public void run(){
        Log.i(TAG,"run:");
        String url="http://www.usd-cny.com/bankofchina.htm";
        Document doc= null;
        try {
            doc = Jsoup.connect(url).get();
            // Elements body=doc.getElementsBytag
            Elements tables=doc.getElementsByTag("table");
            Element table6=tables.get(0);
            Elements tds=table6.getElementsByTag("td");
            Set<String> list1=new HashSet<String>();
            for(int i=0;i<tds.size();i+=6){//元素位置
                Element td1=tds.get(i);//第一列
                Element td2=tds.get(i+5);//第六列
                String str1=td1.text();
                String val=td2.text();
                String list_info=str1+"==>"+val;
                list1.add(list_info);
            }
            Message msg=handler.obtainMessage(5);
            msg.obj=list1;
            Log.i(TAG,"list1:"+list1);
            handler.sendMessage(msg);//从handler发送msg
        } catch (IOException e) {
            e.printStackTrace();
        }
        //handler.postDelayed(this,10);//定时时间
    }
}
