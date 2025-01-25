package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class activityPreMatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pre_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText scoutName = findViewById(R.id.scout_name);
        EditText matchNumber = findViewById(R.id.match_number);
        EditText teamNumber = findViewById(R.id.team_number);
        RadioGroup teamColorRadioGroup = findViewById(R.id.team_color_radio_group);
        RadioButton teamColorRed = findViewById(R.id.team_color_red);
        RadioButton teamColorBlue = findViewById(R.id.team_color_blue);
        Button saveButton = findViewById(R.id.save_button);

        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);

        saveButton.setOnClickListener((l) -> {
            // Check if all fields are full
            boolean teamColorEmpty = teamColorRadioGroup.getCheckedRadioButtonId() == -1;
            ScrollView mainView = findViewById(R.id.scroll_view);
            String response = "";

            if(scoutName.getText().toString().isEmpty()){
                response = getResources().getString(R.string.prompt_scout_name) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(matchNumber.getText().toString().isEmpty()){
                response = getResources().getString(R.string.prompt_match_number) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(teamNumber.getText().toString().isEmpty()){
                response = getResources().getString(R.string.prompt_team_number) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(teamColorEmpty){
                response = "Please choose a team color";
            }else{
                //TODO: Add some form of save system

                this.startActivity(new Intent(this, activityAutonomous.class));
            }

            if(!response.isBlank()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });

    }
}