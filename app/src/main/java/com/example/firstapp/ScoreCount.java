package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreCount extends AppCompatActivity {
    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);
        score=findViewById(R.id.textView10);

        Button btn=findViewById(R.id.button3);
        btn.setOnClickListener(new ButtonListener());
        Button btn2=findViewById(R.id.button4);
        btn2.setOnClickListener(new ButtonListener());
        Button btn3=findViewById(R.id.button5);
        btn3.setOnClickListener(new ButtonListener());
        Button btn4=findViewById(R.id.button6);
        btn4.setOnClickListener(new ButtonListener());
    }
    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            String str=score.getText().toString();
            int sc=Integer.valueOf(str);
            String outtext;
            switch (v.getId()){
                case R.id.button3:
                    sc=sc+3;
                    break;
                case R.id.button4:
                    sc=sc+2;
                    break;
                case R.id.button5:
                    sc=sc+1;
                    break;
                case R.id.button6:
                    sc=0;
                    break;
            }
            outtext=String.valueOf(sc);
            score.setText(outtext);
            //Log.i("main","onClick msg...");
        }
    }
}
