package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.mariuszgromada.math.mxparser.*;

public class CalculatorActivity extends AppCompatActivity {

    //Declaring the variables required
    private Button numberList[],symbolList[],clearButton,dotButton,plusMinus,equalButton;
    private TextView answerBox;
    private EditText questionBox;
    private int MAXIMUM_LIMIT_LENGTH =15;

    private ImageButton backSpace;
    private Expression exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        //Initialising the variables required
        numberList=new Button[]{findViewById(R.id.button20),findViewById(R.id.button13),findViewById(R.id.button14),findViewById(R.id.button15),
                findViewById(R.id.button9),findViewById(R.id.button10),findViewById(R.id.button11),
                findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7)};
        symbolList=new Button[]{findViewById(R.id.button2),findViewById(R.id.button3),findViewById(R.id.button4),findViewById(R.id.button8),
                findViewById(R.id.button12),findViewById(R.id.button16)};
        dotButton=findViewById(R.id.button18);
        clearButton=findViewById(R.id.button);
        questionBox=findViewById(R.id.question);

        //Disabling the virtual keyboard when edittext is clicked
        questionBox.setShowSoftInputOnFocus(false);

        answerBox=findViewById(R.id.textView);
        backSpace=findViewById(R.id.button17);
        plusMinus=findViewById(R.id.button19);
        equalButton=findViewById(R.id.button21);


        //Onclick Listeners for handling numbers
        for(Button number: numberList){
            number.setOnClickListener(v -> {
                if(isLengthWithinLimit()){
                    editQuestion(number.getText().toString());
                    evaluateExp();
                }
                else{
                    Toast.makeText(this, "limit", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //Onclick Listeners for handling symbols
        for(Button symbol: symbolList){
            symbol.setOnClickListener(v->{
                editQuestion(symbol.getText().toString());
                evaluateExp();
            });
        }


        dotButton.setOnClickListener(v->{
            editQuestion(".");
            evaluateExp();
        });


        clearButton.setOnClickListener(v->{
            questionBox.setText("");
            answerBox.setText("");
        });


        backSpace.setOnClickListener(v->{
            String question,questionBeforeCursor, questionAfterCursor,questionNew;
            question = questionBox.getText().toString();
            int cursorPos=questionBox.getSelectionStart();
            if(cursorPos!=0&&question.length()!=0){
                questionBeforeCursor=question.substring(0,cursorPos-1);
                questionAfterCursor =question.substring(cursorPos);
                questionNew=questionBeforeCursor+ questionAfterCursor;
                questionBox.setText(questionNew);
                questionBox.setSelection(cursorPos-1);
            }
            evaluateExp();
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
                        break;
                    }
                    if(ch=='-'&&str.charAt(i-1)=='('){
                        minusPresent=true;
                        break;
                    }
                    if(isBracket(ch)||isOperator(ch)){
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
            evaluateExp();

        });


        equalButton.setOnClickListener(v->{
            String question=questionBox.getText().toString().replaceAll("x","*");
            exp=new Expression(question);
            if(exp.checkSyntax()){
                String answer=String.valueOf(exp.calculate());
                answerBox.setText("");
                questionBox.setText(answer);
                questionBox.setSelection(answer.length());
            }
            else{
                Toast.makeText(this, "Wrong Syntax", Toast.LENGTH_SHORT).show();
            }

        });




    }


    //Function for evaluating the expression in the textbox
    private void evaluateExp(){
        String question=questionBox.getText().toString().replaceAll("x","*");
        exp=new Expression(question);
        if(exp.checkSyntax()){
            answerBox.setText(String.valueOf(exp.calculate()));
        }
        else {
            answerBox.setText("");
        }

    }


    //Function to check whether length of each number in expression is within the limit
    private boolean isLengthWithinLimit(){
        int counter=0;
        String question=questionBox.getText().toString();
        int cursorPos=questionBox.getSelectionStart();
        for(int i=cursorPos-1;i>=0;i--){
            char ch=question.charAt(i);
            if(isBracket(ch)||isOperator(ch)){
                break;
            }
            if(ch=='.'){
                continue;
            }
            counter++;
        }
        for(int i=cursorPos;i<question.length();i++)
        {
            char ch=question.charAt(i);
            if(isBracket(ch)||isOperator(ch)){
                break;
            }
            if(ch=='.'){
                continue;
            }
            counter++;
        }
        return counter< MAXIMUM_LIMIT_LENGTH;
    }

    //Function for editing question when any button is clicked
    private void editQuestion(String s){
        String question,questionBeforeCursor,questionAfterCursor,questionNew;
        question=questionBox.getText().toString();
        int cursorPos=questionBox.getSelectionStart();
        questionBeforeCursor=question.substring(0,cursorPos);
        questionAfterCursor=question.substring(cursorPos);
        questionNew=questionBeforeCursor+s+questionAfterCursor;
        questionBox.setText(questionNew);
        questionBox.setSelection(cursorPos+1);
    }


    private boolean isOperator(char ch){
        switch(ch){
            case '+':
            case '-':
            case 'x':
            case '/': return true;
            default: return false;
        }
    }


    private boolean isBracket(char ch){
        switch (ch){
            case '(':
            case ')': return true;
            default: return false;
        }
    }

}