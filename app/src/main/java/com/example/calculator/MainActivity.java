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
    private ArrayList<Integer> integerArray=new ArrayList<>();
    private ArrayList<Double> doubleArray=new ArrayList<>();
    private ArrayList<String> operators=new ArrayList<>();
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
        question="";
        pos=1;
        integerArray.add(new Integer(pos));
        //doubleArray.add(new Double());
        for(Button button:numberList ){
            button.setOnClickListener(v -> {
                int key= java.lang.Integer.parseInt(button.getText().toString());
                boolean temp;
                if((!integerArray.isEmpty())&&integerArray.get(integerArray.size()-1).getPos()==pos){
                    temp=integerArray.get(integerArray.size()-1).updateNumber(key);
                }
                else{
                    temp=doubleArray.get(doubleArray.size()-1).updateDecimal(key);
                }
                //boolean temp=integerArray.get(integerArray.size()-1).updateNumber(key);
                if(!temp){
                    Toast.makeText(this, "large", Toast.LENGTH_SHORT).show();
                }
                else{
                    question+=button.getText();
                    questionBox.setText(question);
                }

            });
        }
        for(Button button:symbolList){
            button.setOnClickListener(v -> {
                question+=button.getText();
                questionBox.setText(question);
                if(button.getText().toString().equals(".") &&integerArray.get(integerArray.size()-1).getPos()==pos){
                    doubleArray.add(new Double(integerArray.get(integerArray.size()-1)));
                    integerArray.remove(integerArray.size()-1);
                }
                else if(isOperator(button.getText().toString())){
                    pos++;
                    integerArray.add(new Integer(pos));
                }
                if(isOperator(button.getText().toString())||isBracket(button.getText().toString())){
                    operators.add(button.getText().toString());
                }
            });
        }
        clearButton.setOnClickListener(v -> {
            question="";
            questionBox.setText(question);
            integerArray.clear();
            doubleArray.clear();
            operators.clear();
            pos=1;
            integerArray.add(new Integer(pos));
            doubleArray.add(new Double());

        });



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