package com.hosny.quizappspinnerspinner_from_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class maxscore extends AppCompatActivity {
    TextView tvmax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxscore);
        tvmax=findViewById(R.id.tvmax);
        byte maxv=getIntent().getByteExtra("max", (byte) 0);
        tvmax.setText("max score="+maxv);
    }
}
