package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activityAutonomous extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        U u = new U();

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


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_autonomous);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RadioGroup positionGroup1 = findViewById(R.id.staring_position_radio_group1);
        RadioGroup positionGroup2 = findViewById(R.id.staring_position_radio_group2);
        RadioButton position1Button = findViewById(R.id.Position_1);
        RadioButton position2Button = findViewById(R.id.Position_2);
        RadioButton position3Button = findViewById(R.id.Position_3);
        RadioButton position4Button = findViewById(R.id.position_4);
        RadioButton position5Button = findViewById(R.id.position_5);
        RadioButton position6Button = findViewById(R.id.position_6);

        CheckBox leftStarting = findViewById(R.id.left_starting_area);

        Button incrementAC1 = findViewById(R.id.up_count_button_ac1);
        Button decrementAC1 = findViewById(R.id.down_count_button_ac1);
        EditText AC1Field = findViewById(R.id.edit_text_ac1);

        Button incrementRC1 = findViewById(R.id.up_count_button_rc1);
        Button decrementRC1 = findViewById(R.id.down_count_button_rc1);
        EditText RC1Field = findViewById(R.id.edit_text_rc1);

        Button incrementAC2 = findViewById(R.id.up_count_button_ac2);
        Button decrementAC2 = findViewById(R.id.down_count_button_ac2);
        EditText AC2Field = findViewById(R.id.edit_text_ac2);

        Button incrementRC2 = findViewById(R.id.up_count_button_rc2);
        Button decrementRC2 = findViewById(R.id.down_count_button_rc2);
        EditText RC2Field = findViewById(R.id.edit_text_rc2);

        Button incrementAC3 = findViewById(R.id.up_count_button_ac3);
        Button decrementAC3 = findViewById(R.id.down_count_button_ac3);
        EditText AC3Field = findViewById(R.id.edit_text_ac3);

        Button incrementRC3 = findViewById(R.id.up_count_button_rc3);
        Button decrementRC3 = findViewById(R.id.down_count_button_rc3);
        EditText RC3Field = findViewById(R.id.edit_text_rc3);

        Button incrementAC4 = findViewById(R.id.up_count_button_ac4);
        Button decrementAC4 = findViewById(R.id.down_count_button_ac4);
        EditText AC4Field = findViewById(R.id.edit_text_ac4);

        Button incrementRC4 = findViewById(R.id.up_count_button_rc4);
        Button decrementRC4 = findViewById(R.id.down_count_button_rc4);
        EditText RC4Field = findViewById(R.id.edit_text_rc4);

        Button incrementAA = findViewById(R.id.up_count_button_aa); // AA = Attempted Algae
        Button decrementAA = findViewById(R.id.down_count_button_aa);
        EditText AAField = findViewById(R.id.edit_text_aa);

        Button incrementRA = findViewById(R.id.up_count_button_ra); // RA = Real Algae (scored)
        Button decrementRA = findViewById(R.id.down_count_button_ra);
        EditText RAField = findViewById(R.id.edit_text_ra);

        Button incrementREA = findViewById(R.id.up_count_button_rea); // REA = Algae Removed
        Button decrementREA = findViewById(R.id.down_count_button_rea);
        EditText REAField = findViewById(R.id.edit_text_rea);

        Button backButton = findViewById(R.id.back_button);
        Button saveButton = findViewById(R.id.save_button);

        // Sets all the buttons to either increment or decrement their respective buttons.
        // Can be simplified. Not now.
        incrementAC1.setOnClickListener((l)->u.incrementText(AC1Field));
        decrementAC1.setOnClickListener((l)->u.incrementText(AC1Field, -1));
        incrementRC1.setOnClickListener((l)->u.incrementText(RC1Field));
        decrementRC1.setOnClickListener((l)->u.incrementText(RC1Field, -1));
        incrementAC2.setOnClickListener((l)->u.incrementText(AC2Field));
        decrementAC2.setOnClickListener((l)->u.incrementText(AC2Field, -1));
        incrementRC2.setOnClickListener((l)->u.incrementText(RC2Field));
        decrementRC2.setOnClickListener((l)->u.incrementText(RC2Field, -1));
        incrementAC3.setOnClickListener((l)->u.incrementText(AC3Field));
        decrementAC3.setOnClickListener((l)->u.incrementText(AC3Field, -1));
        incrementRC3.setOnClickListener((l)->u.incrementText(RC3Field));
        decrementRC3.setOnClickListener((l)->u.incrementText(RC3Field, -1));
        incrementAC4.setOnClickListener((l)->u.incrementText(AC4Field));
        decrementAC4.setOnClickListener((l)->u.incrementText(AC4Field, -1));
        incrementRC4.setOnClickListener((l)->u.incrementText(RC4Field));
        decrementRC4.setOnClickListener((l)->u.incrementText(RC4Field, -1));
        incrementAA.setOnClickListener((l)->u.incrementText(AAField));
        decrementAA.setOnClickListener((l)->u.incrementText(AAField, -1));
        incrementRA.setOnClickListener((l)->u.incrementText(RAField));
        decrementRA.setOnClickListener((l)->u.incrementText(RAField, -1));
        incrementREA.setOnClickListener((l)->u.incrementText(REAField));
        decrementREA.setOnClickListener((l)->u.incrementText(REAField, -1));

        backButton.setOnClickListener((l)-> {
            // Starting Position | Left starting Position | #ACL1 | #ACL2 | #ACL3 | #ACL4 |
            // #SCL1 | #SCL2 | #SCL3 | #SCL4 | #attempted algae | #scored algae | #algae removed ||
            String autoInfo = "";

            if (!u.getData(positionGroup1).isEmpty() || !u.getData(positionGroup2).isEmpty()) {
                if (u.getData(positionGroup1).isEmpty()) {
                    autoInfo += u.getData(positionGroup2);
                } else {
                    autoInfo += u.getData(positionGroup1);
                }
            }
            autoInfo += ","; // Starting position # end
            autoInfo += u.getData(leftStarting) + ","; // Left starting end

            autoInfo += u.getData(AC1Field) + ",";
            autoInfo += u.getData(AC2Field) + ",";
            autoInfo += u.getData(AC3Field) + ",";
            autoInfo += u.getData(AC4Field) + ","; // Attempted Coral end

            autoInfo += u.getData(RC1Field) + ",";
            autoInfo += u.getData(RC2Field) + ",";
            autoInfo += u.getData(RC3Field) + ",";
            autoInfo += u.getData(RC4Field) + ","; // Scored Coral End

            autoInfo += u.getData(AAField) + ",";
            autoInfo += u.getData(RAField) + ",";
            autoInfo += u.getData(REAField) + ",";

            Intent i = new Intent(this, activityPreMatch.class);
            i.putExtra("preMatch", preMatchSaveString);
            i.putExtra("auto", autoInfo);
            i.putExtra("teleOp", teleOpSaveString);
            i.putExtra("postMatch", postMatchSaveString);

            this.startActivity(i);
        });
    }
}