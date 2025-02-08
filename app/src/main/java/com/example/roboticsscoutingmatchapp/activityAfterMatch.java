package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    }
}