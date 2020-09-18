package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    EditText input,out;
    private static String TAG="kkkk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        input=(EditText) findViewById(R.id.inp);
        out=(EditText)findViewById(R.id.textout);

        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new ButtonListener());
        Button btn2=(Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new ButtonListener());
    }
    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            String str=input.getText().toString();
            double c,f;
            String outtext=null;
            switch (v.getId()){
                case R.id.button:
                    c=Double.valueOf(str);
                    f=c*1.8+32;
                    outtext=String.valueOf(f);
                    break;
                case R.id.button2:
                    f=Double.valueOf(str);
                    c=(f-32)/1.8;
                    outtext=String.valueOf(c);
                    break;
            }
            out.setText(outtext);
            //Log.i("main","onClick msg...");
        }
    }

}