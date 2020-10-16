package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class List_compute extends AppCompatActivity {
    TextView tv_title,tv_out;
    EditText tv_input;
    String detail;
    public static final String TAG="kkk";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_compute);
        tv_title=findViewById(R.id.tv_title);
        tv_input=findViewById(R.id.tv_input);
        tv_out=findViewById(R.id.tv_out);
        tv_input.addTextChangedListener(watcher);
        Intent intent = getIntent();
        String title=intent.getStringExtra("title");
        detail=intent.getStringExtra("detail");
        tv_title.setText(title);
    }
    private TextWatcher watcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence paramCharSequence,
                                      int paramInt1, int paramInt2, int paramInt3) {
            // TODO Auto-generated method stub
            Log.i("TAG","afterTextChanged--------------->");
        }

        @Override
        public void onTextChanged(CharSequence paramCharSequence,
                                  int paramInt1, int paramInt2, int paramInt3) {
            // TODO Auto-generated method stub
            String inp=tv_input.getText().toString();
            float inp_f=Float.parseFloat(inp);
            float detail_f=Float.parseFloat(detail);
            Log.i(TAG,"detail"+detail_f);
            Log.i(TAG,"input"+inp_f);
            float t=detail_f*inp_f;
            tv_out.setText(String.valueOf(t));
        }

        @Override
        public void afterTextChanged(Editable paramEditable) {
            // TODO Auto-generated method stub
            Log.i("TAG","afterTextChanged--------------->");
        }
    };
    public void return_list(View btn){
        finish();
    }
}
