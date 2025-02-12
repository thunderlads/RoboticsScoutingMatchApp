package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
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

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class activityAfterMatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        U u = new U();

        CheckBox floorIntake = findViewById(R.id.floor_intake);
        CheckBox humanPlayerStation = findViewById(R.id.human_player_intake);

        RadioGroup stopReasonGroup = findViewById(R.id.why_robot_stopped);
        RadioButton diedButton = findViewById(R.id.died);
        RadioButton tippedButton = findViewById(R.id.tipped);
        RadioButton physicallyBrokeButton = findViewById(R.id.robot_break);
        RadioButton eStoppedButton = findViewById(R.id.e_stopped);

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
        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);

        String preMatchSaveString, autoSaveString,  // Gets all savestrings from wherever coming in from
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

        if(!postMatchSaveString.isEmpty()){
            // Coral floor pickup able | Coral Source pickup able | Defense received |
            // Stop reason | team rank among alliance | other comments questions or concerns ||
            floorIntake.setChecked(Boolean.parseBoolean(u.untilNextComma(postMatchSaveString)));
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            humanPlayerStation.setChecked(Boolean.parseBoolean(u.untilNextComma(postMatchSaveString)));
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            if(u.untilNextComma(postMatchSaveString).equals("No Defense")){
                noDefenseButton.toggle();
            }else if(u.untilNextComma(postMatchSaveString).equals("Light Defense")){
                lightDefenseButton.toggle();
            }else if(u.untilNextComma(postMatchSaveString).equals("Heavy Defense")){
                heavyDefenseButton.toggle();
            }
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

            String stopReasonString = u.untilNextComma(postMatchSaveString);
            if(stopReasonString.equals("Died"))
                diedButton.toggle();
            else if(stopReasonString.equals("Tipped"))
                tippedButton.toggle();
            else if(stopReasonString.equals("Physically Broke"))
                physicallyBrokeButton.toggle();
            else if(stopReasonString.equals("E-stopped")){
                eStoppedButton.toggle();
            }
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);

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
            postMatchSaveString = u.nextCommaOn(postMatchSaveString);
        }

        backButton.setOnClickListener((l)->{
            // Coral floor pickup able | Coral Source pickup able | Defense received |
            // Stop reason | team rank among alliance | other comments questions or concerns ||
            String afterMatchInfo = "";

            afterMatchInfo += u.getData(floorIntake) + ",";
            afterMatchInfo += u.getData(humanPlayerStation) + ",";
            afterMatchInfo += u.getData(defenseReceivedGroup) + ",";
            afterMatchInfo += u.getData(stopReasonGroup) + ",";
            afterMatchInfo += u.getData(rankGroup) + ",";
            afterMatchInfo += u.stripText(finalText.getText().toString(), u.DELIMITER) + ",";

            Intent i = new Intent(this, activityTeleOp.class);
            i.putExtra("preMatch", preMatchSaveString);
            i.putExtra("auto", autoSaveString);
            i.putExtra("teleOp", teleOpSaveString);
            i.putExtra("postMatch", afterMatchInfo);

            this.startActivity(i);
        });

        saveButton.setOnClickListener((l)->{
            String response = "";
            if(u.getData(defenseReceivedGroup).isEmpty())
                response = "Please fill in defense received";
            else if(u.getData(rankGroup).isEmpty())
                response = "Please fill in rank";
            else{
                Date now = Calendar.getInstance().getTime();
                // TODO: Set filename to date + .csv, with some identifier before it
//                now.getDate();
//                String fileName;
//                File file
            }

            if(!response.isEmpty()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });
    }
}