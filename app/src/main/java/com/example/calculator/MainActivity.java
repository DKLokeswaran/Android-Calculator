package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button numberList[],symbolList[],clearButton;
    private TextView questionBox;


    private String question;
    private int pos;
    private ImageButton backSpace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberList=new Button[]{findViewById(R.id.button20),findViewById(R.id.button13),findViewById(R.id.button14),findViewById(R.id.button15),
                findViewById(R.id.button9),findViewById(R.id.button10),findViewById(R.id.button11),
                findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7)};
        symbolList=new Button[]{findViewById(R.id.button2),findViewById(R.id.button3),findViewById(R.id.button4),findViewById(R.id.button8),
                findViewById(R.id.button12),findViewById(R.id.button16),findViewById(R.id.button18)};
        clearButton=findViewById(R.id.button);
        questionBox=findViewById(R.id.textView);
        backSpace=findViewById(R.id.button17);


    }
    public boolean isOperator(String ch){
        switch(ch){
            case "+":
            case "-":
            case "x":
            case "/": return true;
            default: return false;
        }
    }
    public boolean isBracket(String ch){
        switch (ch){
            case "(":
            case ")": return true;
            default: return false;
        }
    }
}