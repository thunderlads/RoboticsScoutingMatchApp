package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
// Fully Commented by your's truly
// Any questions, contact Akash Ghoshroy at:
//  -email: aghoshroy@wpi.edu :: please head emails with Scouting App: Topic!!
//  -157's slack - Akash Ghoshroy
//  -phone: +1(508)308-5080 :: i'd prefer you didn't this way tho....

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
                teleOpSaveString, postMatchSaveString, competitionString, scoutNameString;
        // Gets the "extras", which are the savestrings
        Bundle extras = getIntent().getExtras();
        // If the savestrings exist, assign their values to string variables
        if(extras != null){
            preMatchSaveString = extras.getString("preMatch", "");
            autoSaveString = extras.getString("auto", "");
            teleOpSaveString = extras.getString("teleOp", "");
            postMatchSaveString = extras.getString("postMatch", "");
            competitionString = extras.getString("competition", "Test");
            scoutNameString = extras.getString("scoutName", "");
        // And if the savestrings don't exist, assign the values of those string vars to be null
        } else {
            preMatchSaveString = "";
            autoSaveString = "";
            teleOpSaveString = "";
            postMatchSaveString = "";
            competitionString = "Test"; // Default for competition selection should not be blank i think
            scoutNameString = "";
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
        // End of Auto-generated code

        // Defining all the relevant components in the activity
        EditText scoutName = findViewById(R.id.scout_name);
        EditText matchNumber = findViewById(R.id.match_number);
        EditText teamNumber = findViewById(R.id.team_number);
        RadioGroup teamColorRadioGroup = findViewById(R.id.team_color_radio_group);
        Button saveButton = findViewById(R.id.save_button); // Not actually the save button, but the next button :P
        CheckBox preloadedCoral = findViewById(R.id.checkBox_preloaded_coral);
        Button backButton = findViewById(R.id.back_button);
        if(!scoutNameString.isEmpty()){
            scoutName.setText(scoutNameString);
        }

        // If there is a savestring
        if(!preMatchSaveString.isEmpty()){
        // Go thru each "field" within the savestring
            // Set competition var to that field
            competitionString = u.untilNextComma(preMatchSaveString);
            // Remove competition field from the savestring
            preMatchSaveString = u.nextCommaOn(preMatchSaveString); // remove competition
            // Also removing redundant fields from the savestring (like the data version, that isn't user inputted)
            preMatchSaveString = u.nextCommaOn(preMatchSaveString);
            // Sets necessary component values to their savestring field values, then removes their fields in the savestring
            scoutName.setText(u.untilNextComma(preMatchSaveString));
            preMatchSaveString = u.nextCommaOn(preMatchSaveString);
            teamNumber.setText(u.untilNextComma(preMatchSaveString));
            preMatchSaveString = u.nextCommaOn(preMatchSaveString);
            // Special case:
            // With the team color RadioGroup, since its not a "write-able" field, each button needs to be set depending on the field
            if (u.untilNextComma(preMatchSaveString).equalsIgnoreCase("red")) {
                teamColorRadioGroup.check(R.id.team_color_red);
            } else {
                teamColorRadioGroup.check(R.id.team_color_blue);
            }
            preMatchSaveString = u.nextCommaOn(preMatchSaveString); // remove team color
            matchNumber.setText(u.untilNextComma(preMatchSaveString));
            preMatchSaveString = u.nextCommaOn(preMatchSaveString); // remove match number
            preloadedCoral.setChecked(Boolean.parseBoolean(u.untilNextComma(preMatchSaveString)));
            preMatchSaveString = u.nextCommaOn(preMatchSaveString); // Remove Checked

        }

        // Defines a toast (pop-up) to be used when a field is left unfilled, and set it's show-time to be short
        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);

        // idk
        String finalCompetitionString = competitionString;
        // When the "save" (next) button is clicked
        saveButton.setOnClickListener((l) -> {
            // Check if all fields are full
//            findViewById(R.id.scroll_view);

            // this "response" string is to be used in the toast, providing the reason why they can't go to the
            String response = "";

            // Checks through each of the "necessary" fields, and if they're empty then sets the response for the toast
            if(u.getData(scoutName).isEmpty()){
                response = getResources().getString(R.string.prompt_scout_name) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(u.getData(matchNumber).isEmpty()){
                response = getResources().getString(R.string.prompt_match_number) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(u.getData(teamNumber).isEmpty()){
                response = getResources().getString(R.string.prompt_team_number) + " " + getResources().getString(R.string.is_empty_identifier);
            }else if(u.getData(teamColorRadioGroup).isEmpty()){
                response = "Please choose a team color";
            }else{
            // However, if they're not empty, you can finally go to the next page!
                // Utilizes "savestrings"
                // Creates an "Intent" which specifies the current page, the desired page, and any ->
                // -> data that you want to be passed between the two pages
                Intent i = new Intent(this, activityAutonomous.class);
                // All of these +=s are compiling all of the savestring data into the correct order
                String preMatchInfo = "";
                preMatchInfo += finalCompetitionString + ",";
                preMatchInfo += u.DATA_VERSION + ",";
                preMatchInfo += u.stripText(u.getData(scoutName), u.DELIMITER_AND_WHITESPACE) + ",";
                preMatchInfo += u.stripText(u.getData(teamNumber)) + ",";
                preMatchInfo += u.stripText(u.getData(teamColorRadioGroup)) + ",";
                preMatchInfo += u.stripText(u.getData(matchNumber)) + ",";
                preMatchInfo += u.stripText(u.getData(preloadedCoral)) + ",";

                // Now, since the data is compiled, pass all of the savestrings for each individual->
                // ->page into the next page
                i.putExtra("preMatch", preMatchInfo);
                i.putExtra("auto", autoSaveString);
                i.putExtra("teleOp", teleOpSaveString);
                i.putExtra("postMatch", postMatchSaveString);

                // Start the next page
                this.startActivity(i);
            }

            // This only gets triggered if one of the "necessary" fields was not filled out, ->
            // ->therefore response must have a value, and a toast gets pushed to the screen to ->
            // ->let the user know what they left blank
            if(!response.isBlank()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });

        // When the back button is pressed
        backButton.setOnClickListener((l)->{
            // Create the intent to go to the competition selection page
            Intent i = new Intent(this, ActivityCompetitionSelection.class);
            // Put the value that YOU WANT TO CHANGE THE COMPETITION
            i.putExtra("chooseNewCompetition", true);
            // Go to the competition selection page
            this.startActivity(i);
        });

    }
}