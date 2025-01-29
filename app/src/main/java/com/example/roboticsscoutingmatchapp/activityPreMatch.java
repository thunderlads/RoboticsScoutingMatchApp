package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activityPreMatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Defines an object for the utility file because of weird compat with static methods
        U u = new U();

        /*
         Checks for if there is any data sent over with the intent when switching to current
         activity, save strings will be compiled and saved as csv in final activity page
         */
        String preMatchSaveString, autoSaveString,
                teleOpSaveString, postMatchSaveString;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            preMatchSaveString = extras.getString("preMatch", "");
            autoSaveString = extras.getString("auto", "");
            teleOpSaveString = extras.getString("teleOp", "");
            postMatchSaveString = extras.getString("postMatch", "");
        } else {
            preMatchSaveString = "";
            autoSaveString = "";
            teleOpSaveString = "";
            postMatchSaveString = "";
        }

        // Checks for insets changing (screen rotation) -- Auto-generated
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pre_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Defining all the relevant components in the activity
        EditText scoutName = findViewById(R.id.scout_name);
        EditText matchNumber = findViewById(R.id.match_number);
        EditText teamNumber = findViewById(R.id.team_number);
        RadioGroup teamColorRadioGroup = findViewById(R.id.team_color_radio_group);
        findViewById(R.id.team_color_red);
        findViewById(R.id.team_color_blue);
        Button saveButton = findViewById(R.id.save_button);

        // TODO: If save string exists for current activity, fill in fields automatically

        // Defines a toast (pop-up) to be used when a field is left unfilled
        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);

        saveButton.setOnClickListener((l) -> {
            // Check if all fields are full
            findViewById(R.id.scroll_view);
            String response = "";

            if(u.getData(scoutName).isEmpty()){
                response = getResources().getString(R.string.prompt_scout_name) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(u.getData(matchNumber).isEmpty()){
                response = getResources().getString(R.string.prompt_match_number) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(u.getData(teamNumber).isEmpty()){
                response = getResources().getString(R.string.prompt_team_number) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(u.getData(teamColorRadioGroup).isEmpty()){
                response = "Please choose a team color";
            }else{
                // TODO: Add some form of save system
                Intent i = new Intent(this, activityAutonomous.class);
                // TODO: Add data stripping to get rid of unnecessary whitespace/delimiters
                String preMatchInfo = "";
                preMatchInfo += "data entry #,"; // TODO: Add data entry numbering system
                preMatchInfo += u.DATA_VERSION + ",";
                preMatchInfo += u.getData(scoutName) + ",";
                preMatchInfo += u.getData(teamNumber) + ",";
                preMatchInfo += u.getData(matchNumber) + ",";
//                preMatchInfo += u.getData(coralNumber);

                i.putExtra("preMatch", preMatchInfo);
                i.putExtra("auto", autoSaveString);
                i.putExtra("teleOp", teleOpSaveString);
                i.putExtra("postMatch", postMatchSaveString);

                this.startActivity(i);
            }

            if(!response.isBlank()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });

    }
}