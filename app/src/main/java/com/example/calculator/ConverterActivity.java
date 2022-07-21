package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import org.mariuszgromada.math.mxparser.*;

public class ConverterActivity extends AppCompatActivity {

    private Button numberList[],clearButton,dotButton,plusMinus;
    private ImageButton backSpace;
    private ArrayList<Quantity> quantities;
    private ArrayList<Unit> temp1;
    private Quantity temp2;
    private int MASS=0,LENGTH=1,AREA=2,SPEED=3,VOLUME=4;
    private Spinner quantitySpinner,unitsSpinner[];
    private EditText queryBoxes[];
    private ArrayAdapter<String> quantityAdapter;
    private ArrayList<ArrayAdapter<String>> unitsAdapter;
    private int KEY=0,QUANTITY,UNIT[];
    private int MAXIMUM_LIMIT_LENGTH =15;
    private boolean isDecimal[],isNegative[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        quantities=new ArrayList<>();
        temp1= new ArrayList<>(Arrays.asList(new Unit("Microgram", "ug", 1.0000E+9),
                                            new Unit("Milligram", "mg", 1.0000E+6),
                                            new Unit("Kilogram","kg",1),
                                            new Unit("Pound","lb",2.20462262),
                                            new Unit("Gram","g",1000),
                                            new Unit("Carat","ct",5000),
                                            new Unit("Metric Ton","t",0.001),
                                            new Unit("Short Ton","ton",0.00110231),
                                            new Unit("Long Ton","lt",0.00098421),
                                            new Unit("Stone","st",0.15747304)));
        updateQuantities(MASS);
        temp1.addAll(Arrays.asList(new Unit("Millimeter","mm",1000000),
                new Unit("Centimeter","cm",100000),
                new Unit("Meter","m",1000),
                new Unit("Kilometer","km",1),
                new Unit("Mile","mi",0.62137119),
                new Unit("Yard","yd",1093.6133),
                new Unit("Foot","ft",3280.8399),
                new Unit("Inch","in",39370.0787),
                new Unit("Nautical mile","nmi",0.5399568)));
        updateQuantities(LENGTH);
        temp1.addAll(Arrays.asList(new Unit("Square Meter","m²",1000000),
                new Unit("Square KIlometer","km²",1),
                new Unit("Square Mile","mi²",0.38610216),
                new Unit("Square Yard","yd²",1196000),
                new Unit("Square Foot","ft²",1.0764E+7),
                new Unit("Square Inch","in²",1.5500E+9),
                new Unit("Acre","ac",247.105381),
                new Unit("Centiare","ca",1000000),
                new Unit("Decare","daa",10000000),
                new Unit("Are","a",1.0000E+8),
                new Unit("Hectare","ha",100)));
        updateQuantities(AREA);
        temp1.addAll(Arrays.asList(new Unit("Mile/Hour","mph",0.62137119),
                new Unit("Kilometer/Hour","kmph",1),
                new Unit("Foot/Second","ft/s",0.91134462),
                new Unit("Meter/Second","m/s",0.27777778),
                new Unit("Knot","knot",0.53995665)));
        updateQuantities(SPEED);
        temp1.addAll(Arrays.asList(new Unit("Milliliter","ml",1000),
                new Unit("Litre","l",1),
                new Unit("US Pint","pt",2.11337642),
                new Unit("US Ounce","oz",33.8140227),
                new Unit("Imperial Gallon","gal",0.21996915),
                new Unit("Imperial Pint","pt",1.75975321),
                new Unit("Imperial Ounce","oz",35.1950642),
                new Unit("Cubic Meter","m",0.001),
                new Unit("Cubic Foot","ft",0.03531467),
                new Unit("Cubic Inch","in",61.0237441)));
        updateQuantities(VOLUME);
        quantitySpinner=findViewById(R.id.quantity_spinner);
        unitsSpinner=new Spinner[]{findViewById(R.id.spinner2),findViewById(R.id.spinner3)};
        queryBoxes=new EditText[]{findViewById(R.id.query_column_1),findViewById(R.id.query_column_2)};
        for(EditText editText:queryBoxes){
            editText.setShowSoftInputOnFocus(false);
        }
        unitsAdapter =new ArrayList<>();
        numberList=new Button[]{findViewById(R.id.button20),findViewById(R.id.button13),findViewById(R.id.button14),findViewById(R.id.button15),
                findViewById(R.id.button9),findViewById(R.id.button10),findViewById(R.id.button11),
                findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7),findViewById(R.id.button19)};
        clearButton=findViewById(R.id.button);
        dotButton=findViewById(R.id.button18);
        plusMinus=findViewById(R.id.button2);
        backSpace=findViewById(R.id.button3);
        createUnitAdapter();
        quantityAdapter =new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,getQuantityArrayList());
        quantitySpinner.setAdapter(quantityAdapter);
        isDecimal=new boolean[]{false,false};
        isNegative=new boolean[]{false,false};
        UNIT=new int[2];
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(Spinner spinner:unitsSpinner){
                    spinner.setAdapter(unitsAdapter.get(position));
                }
                QUANTITY=position;
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for(int i=0;i<2;i++){
            int finalI = i;
            unitsSpinner[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    UNIT[finalI]=position;
                    convert();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        queryBoxes[0].setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                KEY=0;
            }
        });
        queryBoxes[1].setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                KEY=1;
            }
        });
        //Onclick Listeners for handling numbers
        for(Button number: numberList){
            number.setOnClickListener(v -> {
                String input= queryBoxes[KEY].getText().toString();
                if(isWithinLimit((input.length()+number.getText().toString().length()))){
                    editInput(number.getText().toString());
                    //queryBoxes[KEY].setSelection(result.length()+1);
                    //editQuestion(number.getText().toString());
                    //evaluateExp();
                }
                else{
                    Toast.makeText(this, "limit", Toast.LENGTH_SHORT).show();
                }
                convert();
            });
        }

        clearButton.setOnClickListener(v->{
            queryBoxes[KEY].setText("");
            convert();
        });

        dotButton.setOnClickListener(v->{
            editInput(".");
            convert();
        });

        backSpace.setOnClickListener(v->{
            String question,questionBeforeCursor, questionAfterCursor,questionNew;
            question = queryBoxes[KEY].getText().toString();
            int cursorPos=queryBoxes[KEY].getSelectionStart();
            if(cursorPos!=0&&question.length()!=0){
                if(question.charAt(cursorPos-1)=='.'){
                    isDecimal[KEY]=false;
                }
                if(question.charAt(cursorPos-1)=='-'){
                    isNegative[KEY]=false;
                }
                questionBeforeCursor=question.substring(0,cursorPos-1);
                questionAfterCursor =question.substring(cursorPos);
                questionNew=questionBeforeCursor+ questionAfterCursor;
                queryBoxes[KEY].setText(questionNew);
                queryBoxes[KEY].setSelection(cursorPos-1);
            }
            convert();
        });

        plusMinus.setOnClickListener(v->{
            String question,questionNew;
            int k=1;
            question = queryBoxes[KEY].getText().toString();
            int cursorPos=queryBoxes[KEY].getSelectionStart();
            if(isNegative[KEY]){
                questionNew=question.substring(1);
                isNegative[KEY]=false;
                if(cursorPos!=0){
                    k=-1;
                }
            }
            else{
                questionNew="-"+question;
                k=1;
                isNegative[KEY]=true;
            }
            queryBoxes[KEY].setText(questionNew);
            queryBoxes[KEY].setSelection(cursorPos+k);
            convert();
        });
    }

    private void updateQuantities(int K){
        String name="";
        switch (K){
            case 0:name="Mass";
                    break;
            case 1:name="Length";
                    break;
            case 2:name="Area";
                    break;
            case 3:name="Speed";
                    break;
            case 4:name="Volume";
                    break;
        }
        temp2=new Quantity(name, (ArrayList<Unit>) temp1.clone());
        quantities.add(temp2);
        temp1.clear();
    }


    private ArrayList<String> getQuantityArrayList(){
        ArrayList<String> result=new ArrayList<>();
        for(Quantity quantity: quantities){
            result.add(quantity.getName());
        }
        return result;
    }

    private ArrayList<String> getUnitsArrayList(Quantity quantity){
        ArrayList<String> result=new ArrayList<>();
        for(Unit unit:quantity.getUnits()){
            result.add(unit.getName()+"("+unit.getId()+")");
        }
        return result;
    }

    private void createUnitAdapter(){
        for(Quantity quantity:quantities){
            unitsAdapter.add(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,getUnitsArrayList(quantity)));
        }
    }

    private void editInput(String s){
        if(s.equals(".")){
            if(isDecimal[KEY]) {
                return;
            }
            else{
                isDecimal[KEY]=true;
            }
        }
        String question,questionBeforeCursor,questionAfterCursor,questionNew;
        question=queryBoxes[KEY].getText().toString();
        int cursorPos=queryBoxes[KEY].getSelectionStart();
        questionBeforeCursor=question.substring(0,cursorPos);
        questionAfterCursor=question.substring(cursorPos);
        questionNew=questionBeforeCursor+s+questionAfterCursor;
        queryBoxes[KEY].setText(questionNew);
        queryBoxes[KEY].setSelection(cursorPos+s.length());
    }

    private boolean isWithinLimit(int k){
        if(isDecimal[KEY]&&isNegative[KEY]){
            return k<=MAXIMUM_LIMIT_LENGTH+2;
        }
        else if(isDecimal[KEY]||isNegative[KEY]){
            return k<=MAXIMUM_LIMIT_LENGTH+1;
        }
        else{
            return k<=MAXIMUM_LIMIT_LENGTH;
        }
    }

    private void convert(){
        String input=queryBoxes[KEY].getText().toString();
        Expression exp=new Expression(input);
        if(exp.checkSyntax()){
            double ans=quantities.get(QUANTITY).convert(Double.parseDouble(input),UNIT[KEY],UNIT[1-KEY]);
            queryBoxes[1-KEY].setText(String.valueOf(ans));
        }
        if(input.length()==0){
            queryBoxes[1-KEY].setText("");
        }
    }

}