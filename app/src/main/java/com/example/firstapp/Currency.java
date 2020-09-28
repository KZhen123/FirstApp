package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Currency extends AppCompatActivity {
    EditText rmb;
    TextView showout;
    public static final String TAG="kkk";
    public float dollarRate,euroRate,wonRate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_5);
        Intent intent = getIntent();
        dollarRate=2f;
        euroRate=3f;
        wonRate=4f;

        rmb=findViewById(R.id.rmb);
        showout=findViewById(R.id.showout);
    }

    @Override
    //接收可返回页面传来的data
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==2){
//            Intent intent = getIntent();
            dollarRate=data.getFloatExtra("dollar_rate_key",2f);
            euroRate=data.getFloatExtra("euro_rate_key",3f);
            wonRate=data.getFloatExtra("won_rate_key",4f);
            Log.i(TAG,"result:dollarRate="+dollarRate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View btn) {
        Toast.makeText(this,"Hello msg",Toast.LENGTH_SHORT).show();
        String rmb_sc = rmb.getText().toString();
        float rmb_f = Float.valueOf(rmb_sc);
        float showout_f=0;String showout_sc;
        if(btn.getId()==R.id.btn_dollar){
            //dollar
            showout_f=rmb_f*dollarRate;
        }
        else if(btn.getId()==R.id.btn_euro){
            //euro
            showout_f=rmb_f*euroRate;
        }else if(btn.getId()==R.id.btn_won){
            //won
            showout_f=rmb_f*wonRate;
        }
        showout_sc=String.valueOf(showout_f);
        showout.setText(showout_sc);
    }

    public void open(View btn){
        Intent config=new Intent(this,Currency_config.class);//当前session
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        //请求可返回窗口
        startActivityForResult(config,1);//打开新窗口
        //Log.i(TAG,"open:dollarRate=");
    }



    //菜单内容
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }
    //示例，点击菜单第一个下拉框，进入汇率配置页面
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item1){
            Intent config=new Intent(this,Currency_config.class);//当前session
            config.putExtra("dollar_rate_key",dollarRate);
            config.putExtra("euro_rate_key",euroRate);
            config.putExtra("won_rate_key",wonRate);
            //请求可返回窗口
            startActivityForResult(config,1);//打开新窗口
            //Log.i(TAG,"open:dollarRate=");
        }else if(item.getItemId()==R.id.item2){

        }
        return true;
        //return super.onOptionsItemSelected(item);
    }
    //protected void onA
}
