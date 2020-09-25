package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Currency_config extends AppCompatActivity {

    public static final String TAG="kkk";
    EditText dollar_rate,euro_rate,won_rate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        //获取intent对象
        Intent intent = getIntent();
        float dollarRate=intent.getFloatExtra("dollar_rate_key",0.0f);
        float euroRate=intent.getFloatExtra("euro_rate_key",0.0f);
        float wonRate=intent.getFloatExtra("won_rate_key",0.0f);

        dollar_rate=findViewById(R.id.dollar_rate);
        euro_rate=findViewById(R.id.euro_rate);
        won_rate=findViewById(R.id.won_rate);

        dollar_rate.setText(String.valueOf(dollarRate));
        euro_rate.setText(String.valueOf(euroRate));
        won_rate.setText(String.valueOf(wonRate));
    }
    public void save(View btn){
        String dollar_rate_input=dollar_rate.getText().toString();
        String euro_rate_input=euro_rate.getText().toString();
        String won_rate_input=euro_rate.getText().toString();

        Intent config=getIntent();
        config.putExtra("dollar_rate_key",Float.valueOf(dollar_rate_input));
        config.putExtra("euro_rate_key",Float.valueOf(euro_rate_input));
        config.putExtra("won_rate_key",Float.valueOf(won_rate_input));
        Log.i(TAG,"save:dollarRate="+dollar_rate_input);
        //返回
        setResult(2,config);
        finish();
    }
}
