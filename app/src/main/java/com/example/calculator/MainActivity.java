package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button numberList[],symbolList[],clearButton,dotButton,plusMinus;
    private TextView answerBox;
    private EditText questionBox;
    private int KEY=15;
    private String question;
    private int k;
    private ImageButton backSpace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberList=new Button[]{findViewById(R.id.button20),findViewById(R.id.button13),findViewById(R.id.button14),findViewById(R.id.button15),
                findViewById(R.id.button9),findViewById(R.id.button10),findViewById(R.id.button11),
                findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7)};
        symbolList=new Button[]{findViewById(R.id.button2),findViewById(R.id.button3),findViewById(R.id.button4),findViewById(R.id.button8),
                findViewById(R.id.button12),findViewById(R.id.button16)};
        dotButton=findViewById(R.id.button18);
        clearButton=findViewById(R.id.button);
        questionBox=findViewById(R.id.question);
        questionBox.setShowSoftInputOnFocus(false);
        answerBox=findViewById(R.id.textView);
        backSpace=findViewById(R.id.button17);
        plusMinus=findViewById(R.id.button19);
        k=0;
        for(Button number: numberList){
            number.setOnClickListener(v -> {
                if(isWithinLimit()){
                    editQuestion(number.getText().toString());
                }
                else{
                    Toast.makeText(this, "limit", Toast.LENGTH_SHORT).show();
                }
            });
        }
        for(Button symbol: symbolList){
            symbol.setOnClickListener(v->{
                editQuestion(symbol.getText().toString());
                k=0;
            });
        }
        dotButton.setOnClickListener(v->{
            editQuestion(".");
        });
        clearButton.setOnClickListener(v->{
            questionBox.setText("");
            k=0;
        });
        backSpace.setOnClickListener(v->{
            String str,s1,s2,sNew;
            str = questionBox.getText().toString();
            int pos=questionBox.getSelectionStart();
            if(pos!=0&&str.length()!=0){
                s1=str.substring(0,pos-1);
                s2=str.substring(pos);
                sNew=s1+s2;
                questionBox.setText(sNew);
                questionBox.setSelection(pos-1);
            }
        });
        plusMinus.setOnClickListener(v->{
            String str,s1,s2,strNew;
            str = questionBox.getText().toString();
            strNew=str;
            int pos=questionBox.getSelectionStart();
            boolean minusPresent=false;
            if(pos==0){
                strNew="(-"+str;
                questionBox.setText(strNew);
                questionBox.setSelection(2);
            }
            else {
                for (int i = pos - 1; i >= 0; i--) {
                    char ch=str.charAt(i);
                    if (i == 0){
                        minusPresent=false;
                        break;
                    }
                    if(ch=='-'&&str.charAt(i-1)=='('){
                        minusPresent=true;
                        break;
                    }
                    if(isBracket(ch)||isOperator(ch)){
                        minusPresent=false;
                        break;
                    }
                }
                if(minusPresent){
                    int key = 0;
                    for(int i=pos-1;i>=0;i--){
                        char ch=str.charAt(i);
                        if(ch=='-'&&str.charAt(i-1)=='('){
                            key=i;
                            break;
                        }
                    }
                    s1=str.substring(0,key-1);
                    s2=str.substring(key+1);
                    strNew=s1+s2;
                    questionBox.setText(strNew);
                    questionBox.setSelection(pos-2);

                }
                else{
                    int key = 0;
                    for(int i=pos-1;i>=0;i--){
                        char ch=str.charAt(i);
                        if(isBracket(ch)||isOperator(ch)){
                            key=i;
                            break;
                        }
                    }
                    if(key==0){
                        strNew="(-"+str;
                    }
                    else {
                        s1 = str.substring(0, key + 1);
                        s2 = str.substring(key + 1);
                        strNew = s1 + "(-" + s2;
                    }
                    questionBox.setText(strNew);
                    questionBox.setSelection(pos+2);
                }
            }

        });



    }
    public boolean isWithinLimit(){
        int k=0;
        String str=questionBox.getText().toString();
        int pos=questionBox.getSelectionStart();
        for(int i=pos-1;i>=0;i--){
            char ch=str.charAt(i);
            if(isBracket(ch)||isOperator(ch)){
                break;
            }
            if(ch=='.'){
                continue;
            }
            k++;
        }
        for(int i=pos;i<str.length();i++)
        {
            char ch=str.charAt(i);
            if(isBracket(ch)||isOperator(ch)){
                break;
            }
            if(ch=='.'){
                continue;
            }
            k++;
        }
        return k<KEY;
    }
    public void editQuestion(String s){
        String q,q1,q2,qNew;
        q=questionBox.getText().toString();
        int pos=questionBox.getSelectionStart();
        q1=q.substring(0,pos);
        q2=q.substring(pos);
        qNew=q1+s+q2;
        questionBox.setText(qNew);
        questionBox.setSelection(pos+1);
    }
    public boolean isOperator(char ch){
        switch(ch){
            case '+':
            case '-':
            case 'x':
            case '/': return true;
            default: return false;
        }
    }
    public boolean isBracket(char ch){
        switch (ch){
            case '(':
            case ')': return true;
            default: return false;
        }
    }
}