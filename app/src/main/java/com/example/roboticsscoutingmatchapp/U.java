package com.example.roboticsscoutingmatchapp;


import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class U extends AppCompatActivity{

    public final int DATA_VERSION = 1;
    // Characters to remove in input fields
    public final String [] DELIMITER = {",", ";", ":", "|"};
    public final String [] WHITESPACE = {" "};
    public final String [] DELIMITER_AND_WHITESPACE = {",", ";", ":", "|", " "};

    // Returning data pulled from fields
    public String getData(EditText field){
        return field.getText().toString();
    }
    public String getData(RadioGroup field){
        String response;
        if(field.getCheckedRadioButtonId() == -1){
            response = "";
        }else{
            RadioButton checkedButton = field.findViewById(field.getCheckedRadioButtonId());
            response = checkedButton.getText().toString();
        }
        return response;
    }
    public String getData(TextView field){
        return field.getText().toString();
    }



    public String stripText(String text, int stripType){
        String response = text;
        String[] chars = DELIMITER_AND_WHITESPACE;

        if(stripType == 0){ // delimiter only
            chars = DELIMITER.clone();
        } else if (stripType == 1) {
            chars = WHITESPACE.clone();
        }else if (stripType == 2){
            chars = DELIMITER_AND_WHITESPACE.clone();
        }

        for(String s : chars){
            while(response.contains(s)){
                response = response.substring(0, response.indexOf(s)) + response.substring(response.indexOf(s)+1);
            }
        }

        return response;
    }
    public String stripText(String text){
        String response = text;

        for(String s : DELIMITER_AND_WHITESPACE){
            while(response.contains(s)){
                response = response.substring(0, response.indexOf(s)) + response.substring(response.indexOf(s)+1);
            }
        }

        return response;
    }

    public String nextCommaOn(String text){
        return text.substring(text.indexOf(",")+1);
    }
    public String untilNextComma(String text){
        return text.substring(0, text.indexOf(","));
    }

    public void incrementText(TextView field){
        if(field.getText().toString().isEmpty()){
            field.setText("1");
        }else{
            int currentNum = Integer.parseInt(field.getText().toString());
            currentNum++;
            field.setText(Integer.toString(currentNum));
        }
    }
    public void incrementText(TextView field, int incrementBy){
        if(field.getText().toString().isEmpty()){
            field.setText(Integer.toString(incrementBy));
        }else{
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+incrementBy));
        }
    }
    public void incrementText(EditText field){
        if(field.getText().toString().isEmpty()){
            field.setText("1");
        }else{
            int currentNum = Integer.parseInt(field.getText().toString());
            field.setText(Integer.toString(currentNum+1));
        }
    }
    public void incrementText(EditText field, int incrementBy){
        if(field.getText().toString().isEmpty()){
            field.setText(Integer.toString(incrementBy));
        }else{
            int currentNum = Integer.parseInt(field.getText().toString());
            field.setText(Integer.toString(currentNum+incrementBy));
        }
    }
}
