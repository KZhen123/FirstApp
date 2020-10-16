package com.example.firstapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//自定义布局实现list并显示汇率信息
public class List_test2 extends ListActivity implements Runnable{
    Handler handler;
    public static final String TAG="kkk";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String>map=new HashMap<String,String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail"+i);
            listItems.add(map);
        }*/
        //生成适配器的Item和动态数组对应的元素
        /*SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.mylist2,
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail});
        setListAdapter(listItemAdapter);*/
        Thread t =new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){//身份验证
                    //传来的msg
                    ArrayList<HashMap<String, String>> listItems=(ArrayList<HashMap<String, String>>)msg.obj;
                    //Log.i(TAG, "run: data=" + data);
                    SimpleAdapter listItemAdapter = new SimpleAdapter(List_test2.this,
                            listItems,
                            R.layout.mylist2,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };
    }

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
            ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
            for(int i=0;i<tds.size();i+=6){//元素位置
                Element td1=tds.get(i);//第一列
                Element td2=tds.get(i+5);//第六列
                String str1=td1.text();
                String val=td2.text();
                //定义传递的map
                HashMap<String,String>map=new HashMap<String,String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",val);
                listItems.add(map);
                //String list_info=str1+"==>"+val;
                //list1.add(list_info);
            }
            Message msg=handler.obtainMessage(5);
            msg.obj=listItems;
            handler.sendMessage(msg);//从handler发送msg
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

