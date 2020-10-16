package com.example.firstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
//gridview多栏列表
public class Multi_list extends AppCompatActivity implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener,Runnable{
    GridView listview;
    Handler handler;
    private static final String TAG = "MyAdapter";
    MyAdapter myAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list);
        listview=findViewById(R.id.my_multilist);//
        Thread t =new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){//身份验证
                    //传来的msg
                    ArrayList<HashMap<String, String>> listItems=(ArrayList<HashMap<String, String>>)msg.obj;
                    myAdapter = new MyAdapter(Multi_list.this,
                            R.layout.mylist2,//listitem的行布局实现
                            listItems);//数据源
                    // listview.setOnItemClickListener();
                    listview.setAdapter(myAdapter);
                    listview.setEmptyView(findViewById(R.id.nodata));
                }
                super.handleMessage(msg);
            }
        };
        //监听
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //删除点击的数据
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

    //长按删除
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick: 对话框事件处理");
                        //删除数据项
                        myAdapter.remove(listview.getItemAtPosition(position));
                        /*//更新适配器
                        listItemAdapter.notifyDataSetChanged();*/
                    }
                }).setNegativeButton("否", null);
        builder.create().show();

        //不触发短按
        return true;
    }

    @Override
    public void run() {
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
                String val=String.valueOf(Float.parseFloat(td2.text())/100);
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
