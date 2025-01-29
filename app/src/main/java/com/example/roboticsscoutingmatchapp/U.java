package com.example.roboticsscoutingmatchapp;


import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;


public class U extends AppCompatActivity{
    public String getData(EditText field){
        return field.getText().toString();
    }
    public String getData(RadioGroup field){
        String response;
        if(field.getCheckedRadioButtonId() == -1){
            response = "";
        }else{
            RadioButton checkedButton = findViewById(field.getCheckedRadioButtonId());
            response = checkedButton.getText().toString();
        }
        return response;
    }
}
