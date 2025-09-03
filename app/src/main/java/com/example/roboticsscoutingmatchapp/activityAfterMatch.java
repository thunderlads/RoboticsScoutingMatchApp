package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class activityAfterMatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Default code start
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); // Default code end

        // defines new utilities object to be used
        U u = new U();

        // *---Defines all the components on the current page as variables---*
        CheckBox floorIntake = findViewById(R.id.floor_intake);
        CheckBox humanPlayerStation = findViewById(R.id.human_player_intake);

        RadioGroup stopReasonGroup = findViewById(R.id.why_robot_stopped);
        RadioButton diedButton = findViewById(R.id.died);
        RadioButton tippedButton = findViewById(R.id.tipped);
        RadioButton physicallyBrokeButton = findViewById(R.id.robot_break);
        RadioButton eStoppedButton = findViewById(R.id.e_stopped);
        RadioButton notStoppedButton = findViewById(R.id.never_stopped);

        RadioGroup defenseReceivedGroup = findViewById(R.id.defense_received);
        RadioButton noDefenseButton = findViewById(R.id.defense_none);
        RadioButton lightDefenseButton = findViewById(R.id.defense_light);
        RadioButton heavyDefenseButton = findViewById(R.id.defense_heavy);

        RadioGroup rankGroup = findViewById(R.id.ranking_of_teams);
        RadioButton rank1Button = findViewById(R.id.rank1);
        RadioButton rank2Button = findViewById(R.id.rank2);
        RadioButton rank3Button = findViewById(R.id.rank3);

        EditText finalText = findViewById(R.id.End_of_app_text);
        Button saveButton = findViewById(R.id.save_button);
        Button backButton = findViewById(R.id.back_button);
        // Creates a Toast (pop-up message object)
        Toast unfilledMessage = new Toast(this);
        // Sets it to only be angry a short while
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);

        // Define savestring String objects
        String preMatchSaveString, autoSaveString,
                teleOpSaveString, postMatchSaveString;
        // Gets the savestrings and stores them to extras
        Bundle extras = getIntent().getExtras();
        // If savestrings exist, set individual strings to values
        if(extras != null){
            preMatchSaveString = extras.getString("preMatch", "");
            autoSaveString = extras.getString("auto", "");
            teleOpSaveString = extras.getString("teleOp", "");
            postMatchSaveString = extras.getString("postMatch", "");
        // If savestrings don't exist, no value
        } else {
            preMatchSaveString = "";
            autoSaveString = "";
            teleOpSaveString = "";
            postMatchSaveString = "";
        }

        // Sets all the components to the values within the savestring
        // if the savestring is not empty, upon opening the page
        if(!postMatchSaveString.isEmpty()){
            // Coral floor pickup able | Coral Source pickup able | Defense received |
            // Stop reason | team rank among alliance | other comments questions or concerns ||

            // Sets the value to the parsed value in the savestring
            floorIntake.setChecked(Boolean.parseBoolean(u.untilNextComma(postMatchSaveString)));
            // removes the value from the savestring
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);
            // So on an so forth
            humanPlayerStation.setChecked(Boolean.parseBoolean(u.untilNextComma(postMatchSaveString)));
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            // Basically a switch-case for which radio button to toggle with the savestring value
            if(u.untilNextComma(postMatchSaveString).equals("No Defense")){
                noDefenseButton.toggle();
            }else if(u.untilNextComma(postMatchSaveString).equals("Light Defense")){
                lightDefenseButton.toggle();
            }else if(u.untilNextComma(postMatchSaveString).equals("Heavy Defense")){
                heavyDefenseButton.toggle();
            }
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            // Actual switch-case for radio button togle
            String stopReasonString = u.untilNextComma(postMatchSaveString);
            switch (stopReasonString) {
                case "Died":
                    diedButton.toggle();
                    break;
                case "Tipped":
                    tippedButton.toggle();
                    break;
                case "Physically Broke":
                    physicallyBrokeButton.toggle();
                    break;
                case "E-stopped":
                    eStoppedButton.toggle();
                    break;
                case "Not Stopped":
                    notStoppedButton.toggle();
                    break;
            }
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            // etc. etc. etc.
            String teamRank = u.untilNextComma(postMatchSaveString);
            switch(teamRank){
                case "Rank 1":
                    rank1Button.toggle();
                    break;
                case "Rank 2":
                    rank2Button.toggle();
                    break;
                case "Rank 3":
                    rank3Button.toggle();
                    break;
            }
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            finalText.setText(u.untilNextComma(postMatchSaveString));
            // Should in theory be removing everything from the savestring
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

        }

        // Sets current savestring to current values of components
        // Because it's the back button, these values can have no value
        backButton.setOnClickListener((l)->{
            // Coral floor pickup able | Coral Source pickup able | Defense received |
            // Stop reason | team rank among alliance | other comments questions or concerns ||
            String afterMatchInfo = "";

            // Here, just keeps appending the necessary info to the savestring
            // IN A SPECIFIC ORDER!!
            // TODO: Make a dynamic list that doesn't need to be in a specific order (like dictionary)
            afterMatchInfo += u.getData(floorIntake) + ",";
            afterMatchInfo += u.getData(humanPlayerStation) + ",";
            afterMatchInfo += u.getData(defenseReceivedGroup) + ",";
            afterMatchInfo += u.getData(stopReasonGroup) + ",";
            afterMatchInfo += u.getData(rankGroup) + ",";
            afterMatchInfo += u.stripText(finalText.getText().toString(), U.DELIMITER) + ",";

            // Creates an "Intent" which is initialized with the current page, and the page to go to
            // then, "extras", or key:value pairs with the savestrings are put into the intent
            Intent i = new Intent(this, activityTeleOp.class);
            i.putExtra("preMatch", preMatchSaveString);
            i.putExtra("auto", autoSaveString);
            i.putExtra("teleOp", teleOpSaveString);
            i.putExtra("postMatch", afterMatchInfo);

            // The intent then gets sent along and starts the new page
            this.startActivity(i);
        });

        saveButton.setOnClickListener((l)->{
            // Sets current savestring to current component values
            // Checks that components that need to be filled in are filled in

            // Creates a string that will be used in the Toast (pop-up message)
            String response = "";

            // If a NECESSARY component is not filled in
            if(u.getData(defenseReceivedGroup).isEmpty())
                // Sets the Toast message to error message
                response = "Please fill in defense received";
            else if(u.getData(rankGroup).isEmpty())
                response = "Please fill in rank";
            else if(u.getData(stopReasonGroup).isEmpty())
                response = "Please fill in stop reason";
            else{
                // Only once nothing is wrong can the savestring be made
                // Coral floor pickup able | Coral Source pickup able | Defense received |
                // Stop reason | team rank among alliance | other comments questions or concerns ||

                // Initializes post-match savestring
                String postMatchInfo = "";
                // Utilizes the handy utilities file to scrape data from all necessary sources
                // IN ORDER
                postMatchInfo += u.getData(floorIntake) + ",";
                postMatchInfo += u.getData(humanPlayerStation) + ",";
                postMatchInfo += u.getData(defenseReceivedGroup) + ",";
                postMatchInfo += u.getData(stopReasonGroup) + ",";
                postMatchInfo += u.getData(rankGroup) + ",";
                postMatchInfo += u.stripText(u.getData(finalText), u.DELIMITER) + ",";

                // Competition Location | Save Version | Scout Name | Team # | Team Color |
                // Match Number | Preloaded coral ||
                // Filename format: "match_scouting_" + [team_number] + [match number]

                // Since this is the final page, gets all the necessary vars for the file name
                String teamNumber = u.untilNextComma(u.nextCommaOn(u.nextCommaOn(u.nextCommaOn(preMatchSaveString))));
                String matchNumber = u.untilNextComma(u.nextCommaOn(u.nextCommaOn(u.nextCommaOn(u.nextCommaOn(u.nextCommaOn(preMatchSaveString))))));
                String competitionLocation = u.untilNextComma(preMatchSaveString);
                String scoutName = u.untilNextComma(u.nextCommaOn(u.nextCommaOn(preMatchSaveString)));

                // Compounds the name of the file
                String fileName = "match_scouting_"+ competitionLocation + "_" + teamNumber + "_" + matchNumber + ".csv";
                Log.d("File Name: ", fileName);

                // Oh phooey heres where it gets kinda clamplicated
                // Tries to make a new file
                try {
                    // Creates a new "file" variable wherever the documents on the device are stored with the filename
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
                    // If the file doesn't exist (GOOD THING!!!)
                    if (!file.exists()) {

                        // Create the "physical" file from the variable abstraction (returns true if it does)
                        boolean fileCreated = file.createNewFile();
                        // Prints the output of whether or not the file has been created
                        if(!fileCreated) {
                            Toast.makeText(this, "File " + fileName + " has not been created", Toast.LENGTH_SHORT).show();
                            Log.d("File written: ", "File not created");
                        }else {
                            Toast.makeText(this, "File " + fileName + " has been created", Toast.LENGTH_SHORT).show();
                            Log.d("File written: ", "File created");
                        }
                    }else{
                        // Well if it did exist it already did exist. PRINT THAT!!!
                        Log.d("File already existed", "File already existed");
                    }

                    // Create a new file writer for the "physical" file (not the variable)
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    // Then make a buffered writer (safer? i think?) from the file writer
                    BufferedWriter bw = new BufferedWriter(fw);

                    // Write the savestrings to the file
                    bw.write(preMatchSaveString);
                    bw.write(autoSaveString);
                    bw.write(teleOpSaveString);
                    bw.write(postMatchInfo);
                    // Flushes any bits (etiquette?? and safer?? idk man thats what it looks like)
                    bw.flush();
                    // Close the file
                    bw.close();
                } catch (IOException e) {
                    // This is why there's a try. If goes wrong, report the error in the log
                    Log.d("Error thrown:", "+==========+Error Thrown+==========+");
                    Log.getStackTraceString(e);
                }

                // Same thing with the intent from before....
                // Creates an Intent referencing current activity page, and the page to go to
                Intent i = new Intent(this, ActivityCompetitionSelection.class);
                // Puts the scout name as an extra cuz people complained SO MUCH that they were
                // annoyed that the scout name didn't save
                i.putExtra("scoutName", scoutName);
                // Starts the new page (which is actually competition selection!)
                this.startActivity(i);
            }

            // See this confused me a lil bit....
            // But after that whole if-else statement, if there is an "error" for the toast,
            // put it on the screen!!
            if(!response.isEmpty()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });
    }
}