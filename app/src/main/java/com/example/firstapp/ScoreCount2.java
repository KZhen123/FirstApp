package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreCount2 extends AppCompatActivity {
    TextView score1, score2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_4);

        score1 = findViewById(R.id.textView6);
        score2 = findViewById(R.id.textView11);

        Button btn = findViewById(R.id.button7);
        btn.setOnClickListener(new ScoreCount2.ButtonListener());
        Button btn2 = findViewById(R.id.button8);
        btn2.setOnClickListener(new ScoreCount2.ButtonListener());
        Button btn3 = findViewById(R.id.button9);
        btn3.setOnClickListener(new ScoreCount2.ButtonListener());
        Button btn4 = findViewById(R.id.button12);
        btn4.setOnClickListener(new ScoreCount2.ButtonListener());
        Button btn5 = findViewById(R.id.button11);
        btn5.setOnClickListener(new ScoreCount2.ButtonListener());
        Button btn6 = findViewById(R.id.button10);
        btn6.setOnClickListener(new ScoreCount2.ButtonListener());
        Button btn7 = findViewById(R.id.button13);
        btn7.setOnClickListener(new ScoreCount2.ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String strA = score1.getText().toString();
            int scA = Integer.valueOf(strA);
            String strB = score2.getText().toString();
            int scB = Integer.valueOf(strB);
            String outtextA, outtextB;
            switch (v.getId()) {
                case R.id.button7:
                    scA = scA + 3;
                    break;
                case R.id.button8:
                    scA = scA + 2;
                    break;
                case R.id.button9:
                    scA = scA + 1;
                    break;
                case R.id.button12:
                    scB = scB + 3;
                    break;
                case R.id.button11:
                    scB = scB + 2;
                    break;
                case R.id.button10:
                    scB = scB + 1;
                    break;
                case R.id.button13:
                    scA=0;
                    scB = 0;
                    break;
            }
            outtextA = String.valueOf(scA);
            outtextB = String.valueOf(scB);
            score1.setText(outtextA);
            score2.setText(outtextB);

        }
    }
}
