package com.wuxianggujun.hooktest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.text_view);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> {
            int i = 1 / 0;
        });
    }
}