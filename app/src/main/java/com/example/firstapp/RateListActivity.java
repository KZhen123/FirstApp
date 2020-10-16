package com.example.firstapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//继承ListActivity实现列表
public class RateListActivity extends ListActivity implements Runnable{
    public static final String TAG="kkk";
    Handler handler;
    //以列表显示子网络获取到的汇率数据
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t =new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){//身份验证
                    List<String> list2=(List<String>)msg.obj;
                    Log.i(TAG, "run: list2=" + list2);
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
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
            List<String> list1=new ArrayList<>();
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
            handler.sendMessage(msg);//从handler发送msg
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
