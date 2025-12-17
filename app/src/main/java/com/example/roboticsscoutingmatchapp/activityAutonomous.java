package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
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

public class activityAutonomous extends AppCompatActivity {

    public void clearGroup(RadioGroup field1, RadioGroup field2, RadioGroup field3){
        field1.setOnCheckedChangeListener(null);
        field1.check(-1);
        field2.setOnCheckedChangeListener(null);
        field2.check(-1);
        field1.setOnCheckedChangeListener((l,w)->clearGroup(field3, field2, field1));
        field2.setOnCheckedChangeListener((l,w)->clearGroup(field1, field3, field2));
    }



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
        RadioGroup positionGroup3 = findViewById(R.id.staring_position_radio_group3);
        RadioButton position1Button = findViewById(R.id.Position_1);
        RadioButton position2Button = findViewById(R.id.Position_2);
        RadioButton position3Button = findViewById(R.id.Position_3);
        RadioButton position4Button = findViewById(R.id.position_4);
        RadioButton position5Button = findViewById(R.id.position_5);
        RadioButton position6Button = findViewById(R.id.position_6);
        RadioButton position7Button = findViewById(R.id.position_7);
        RadioButton position8Button = findViewById(R.id.position_8);
        RadioButton position9Button = findViewById(R.id.position_9);

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



        Button backButton = findViewById(R.id.back_button);
        Button saveButton = findViewById(R.id.save_button);

        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);


        if(!autoSaveString.isEmpty()){
            // Starting Position | Left starting Position | #ACL1 | #ACL2 | #ACL3 | #ACL4 |
            // #SCL1 | #SCL2 | #SCL3 | #SCL4 | #Barge attempted | #barge scored |
            // #processor attempted | #processor scored |#algae removed ||
            String position = u.untilNextComma(autoSaveString);
//            Log.d(position, position);
            switch (position){
                case "Position 1":
                    position1Button.toggle();
                    break;
                case "Position 2":
                    position2Button.toggle();
                    break;
                case "Position 3":
                    position3Button.toggle();
                    break;
                case "Position 4":
                    position4Button.toggle();
                    break;
                case "Position 5":
                    position5Button.toggle();
                    break;
                case "Position 6":
                    position6Button.toggle();
                    break;
                case "Position 7":
                    position7Button.toggle();
                    break;
                case "Position 8":
                    position8Button.toggle();
                    break;
                case "Position 9":
                    position9Button.toggle();
                    break;

            }
            autoSaveString = u.nextCommaOn(autoSaveString); // Removes starting position
            if(u.untilNextComma(autoSaveString).equals("True"))
                leftStarting.toggle();
            autoSaveString = u.nextCommaOn(autoSaveString); // Removes left starting
            AC1Field.setText(u.untilNextComma(autoSaveString));
            autoSaveString = u.nextCommaOn(autoSaveString); // Removes #ACL1
            AC2Field.setText(u.untilNextComma(autoSaveString));
            autoSaveString = u.nextCommaOn(autoSaveString); // Removes #ACL2
            RC1Field.setText(u.untilNextComma(autoSaveString));
            autoSaveString = u.nextCommaOn(autoSaveString); // Remove #SCL1
            RC2Field.setText(u.untilNextComma(autoSaveString));


        }



        positionGroup1.setOnCheckedChangeListener((l, w)->clearGroup(positionGroup2, positionGroup3, positionGroup1));
        positionGroup2.setOnCheckedChangeListener((l, w)->clearGroup(positionGroup3, positionGroup1, positionGroup2));
        positionGroup3.setOnCheckedChangeListener((l, w)->clearGroup(positionGroup1, positionGroup2, positionGroup3));

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


        backButton.setOnClickListener((l)-> {
            // Starting Position | Left starting Position | #ACL1 | #ACL2 | #ACL3 | #ACL4 |
            // #SCL1 | #SCL2 | #SCL3 | #SCL4 | #Barge attempted | #barge scored | 
            // #processor attempted | #processor scored |#algae removed ||
            String autoInfo = "";

            if (!u.getData(positionGroup1).isEmpty() || !u.getData(positionGroup2).isEmpty() || !u.getData(positionGroup3).isEmpty()) {
                if (!u.getData(positionGroup1).isEmpty()) {
                    autoInfo += u.getData(positionGroup1);
                } else if (!u.getData(positionGroup2).isEmpty()) {
                    autoInfo += u.getData(positionGroup2);
                } else {
                    autoInfo += u.getData(positionGroup3);
                }
            }
            autoInfo += ","; // Starting position # end
            autoInfo += u.getData(leftStarting) + ","; // Left starting end

            autoInfo += u.getData(AC1Field) + ",";
            autoInfo += u.getData(AC2Field) + ",";

            autoInfo += u.getData(RC1Field) + ",";
            autoInfo += u.getData(RC2Field) + ",";


            Intent i = new Intent(this, activityPreMatch.class);
            i.putExtra("preMatch", preMatchSaveString);
            i.putExtra("auto", autoInfo);
            i.putExtra("teleOp", teleOpSaveString);
            i.putExtra("postMatch", postMatchSaveString);

            this.startActivity(i);
        });

        saveButton.setOnClickListener((l)-> {
            String response = "";

            if(u.getData(AC1Field).isEmpty()) {
                AC1Field.setText("0");
            }
            if(u.getData(RC1Field).isEmpty()) {
                RC1Field.setText("0");
            }
            if(u.getData(AC2Field).isEmpty()) {
                AC2Field.setText("0");
            }
            if(u.getData(RC2Field).isEmpty()) {
                RC2Field.setText("0");
            }

            if((u.getData(positionGroup1).isEmpty()) && (u.getData(positionGroup2).isEmpty()) && (u.getData(positionGroup3).isEmpty()))
                response = "Please fill position";
            else if(Integer.parseInt(u.getData(AC1Field)) < Integer.parseInt(u.getData(RC1Field)))
                response = "Attempted Coral L1 cannot be less than Scored Coral L1";
            else if(Integer.parseInt(u.getData(AC2Field)) < Integer.parseInt(u.getData(RC2Field)))
                response = "Attempted Coral L2 cannot be less than Scored Coral L2";
            {

                String autoInfo = "";


                if (!u.getData(positionGroup1).isEmpty()) {
                    autoInfo += u.getData(positionGroup1);
                } else if (!u.getData(positionGroup2).isEmpty()) {
                    autoInfo += u.getData(positionGroup2);
                } else {
                    autoInfo += u.getData(positionGroup3);
                }
                autoInfo += ","; // Starting position # end
                autoInfo += u.getData(leftStarting) + ","; // Left starting end

                autoInfo += u.getData(AC1Field) + ",";
                autoInfo += u.getData(AC2Field) + ",";

                autoInfo += u.getData(RC1Field) + ",";
                autoInfo += u.getData(RC2Field) + ",";



                Intent i = new Intent(this, activityTeleOp.class);
                i.putExtra("preMatch", preMatchSaveString);
                i.putExtra("auto", autoInfo);
                i.putExtra("teleOp", teleOpSaveString);
                i.putExtra("postMatch", postMatchSaveString);

                this.startActivity(i);
            }

            if(!response.isEmpty()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });
    }
}