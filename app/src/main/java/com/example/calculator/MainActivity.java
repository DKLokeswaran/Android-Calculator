package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button numberList[],symbolList[];
    private TextView questionBox;
    private ArrayList<Integer> integerArray=new ArrayList<>();
    private ArrayList<Double> doubleArray=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberList=new Button[]{findViewById(R.id.button20),findViewById(R.id.button13),findViewById(R.id.button14),findViewById(R.id.button15),
                findViewById(R.id.button9),findViewById(R.id.button10),findViewById(R.id.button11),
                findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7)};
        symbolList=new Button[]{findViewById(R.id.button2),findViewById(R.id.button3),findViewById(R.id.button4),findViewById(R.id.button8),
                findViewById(R.id.button12),findViewById(R.id.button16),findViewById(R.id.button18)};
        questionBox=findViewById(R.id.textView);
        for(Button button:numberList ){
            button.setOnClickListener(v -> {

            });
        }
    }
}