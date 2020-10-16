package com.example.firstapp;

import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

//自定义适配器Adapter，自定义行布局，点击跳转页面
public class List_test3 extends AppCompatActivity implements AdapterView.OnItemClickListener,Runnable{
    private static final String TAG = "MyAdapter";
    public static ListView listview;
    Handler handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //////
        /*ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", ""+i); // 标题文字
            map.put("ItemDetail",""+i); // 详情描述
            listItems.add(map);
        }
        MyAdapter myAdapter = new MyAdapter(this,
                R.layout.mylist2,//listitem的行布局实现
                listItems);//数据源
        listview=findViewById(R.id.mylist);//
        // listview.setOnItemClickListener();
        listview.setAdapter(myAdapter);
        //监听
        listview.setOnItemClickListener(this);*/
        ////////////
        Thread t =new Thread(this);
        listview=findViewById(R.id.mylist);//
        t.start();
        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){//身份验证
                    //传来的msg
                    ArrayList<HashMap<String, String>> listItems=(ArrayList<HashMap<String, String>>)msg.obj;
                    MyAdapter myAdapter = new MyAdapter(List_test3.this,
                            R.layout.mylist2,//listitem的行布局实现
                            listItems);//数据源
                    // listview.setOnItemClickListener();
                    listview.setAdapter(myAdapter);
                }
                super.handleMessage(msg);
            }
        };
        //监听
        listview.setOnItemClickListener(this);

    }
    //获取点击数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtPosition = listview.getItemAtPosition(position);
        //从map中获得数据
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);
        //从布局中获得数据
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);
        //跳转页面，传递信息
        Intent config=new Intent(this,List_compute.class);//当前session
        config.putExtra("title",title2);
        config.putExtra("detail",detail2);
        //请求可返回窗口
        startActivityForResult(config,1);//打开新窗口
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
