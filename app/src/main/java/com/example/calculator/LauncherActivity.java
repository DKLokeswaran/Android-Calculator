package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity {

    private Button calc,conv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        calc=findViewById(R.id.button23);
        conv=findViewById(R.id.button22);
        calc.setOnClickListener(v->{
            Intent intent=new Intent(LauncherActivity.this,CalculatorActivity.class);
            startActivity(intent);
        });
        conv.setOnClickListener(v->{
            Intent intent=new Intent(LauncherActivity.this,ConverterActivity.class);
            startActivity(intent);
        });
    }
}