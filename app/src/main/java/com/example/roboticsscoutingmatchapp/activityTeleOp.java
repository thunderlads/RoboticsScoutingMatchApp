package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activityTeleOp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tele_op);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        U u = new U();

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

        // Defining all the access-necessary components within the page
        Button backButton = findViewById(R.id.back_button);
        Button saveButton = findViewById(R.id.save_button);
        Button CAplus = findViewById(R.id.up_count_button_ca); // Attempted Coral L1
        Button CAminus = findViewById(R.id.down_count_button_ca);
        EditText CAfield = findViewById(R.id.edit_text_ca);
        Button AOplus = findViewById(R.id.up_count_button_ao); // Scored Coral L1
        Button AOminus = findViewById(R.id.down_count_button_ao);
        EditText AOfield = findViewById(R.id.edit_text_ao);
        Button CSplus = findViewById(R.id.up_count_button_cs); // Attempted Coral L2
        Button CSminus = findViewById(R.id.down_count_button_cs);
        EditText CSfield = findViewById(R.id.edit_text_cs);
        Button SOplus = findViewById(R.id.up_count_button_so); // Scored Coral L2
        Button SOminus = findViewById(R.id.down_count_button_so);
        EditText SOfield = findViewById(R.id.edit_text_so);
        Button DAAplus = findViewById(R.id.up_count_button_daa); // Scored Processor
        Button DAAminus = findViewById(R.id.down_count_button_daa);
        EditText DAAfield = findViewById(R.id.edit_text_daa);
        Button DASplus = findViewById(R.id.up_count_button_das); // Attempted Barge
        Button DASminus = findViewById(R.id.down_count_button_das);
        EditText DASfield = findViewById(R.id.edit_text_das);
        RadioGroup parkRadioGroup = findViewById(R.id.endgame_location); // Endgame RadioGroup
        RadioButton didNotParkButton = findViewById(R.id.did_not_park);
        RadioButton partialSoloButton = findViewById(R.id.partial_solo);
        RadioButton partialDuoButton = findViewById(R.id.partial_duo);
        RadioButton fullAboveButton = findViewById(R.id.full_above);
        RadioButton fullBelowButton = findViewById(R.id.full_below);
        RadioButton fullSoloButton = findViewById(R.id.full_solo);
        RadioGroup endgameTimeGroup = findViewById(R.id.endgame_time);
        RadioButton twentyFiveButton = findViewById(R.id.twenty_five);
        RadioButton twentyButton = findViewById(R.id.twenty);
        RadioButton fifteenButton = findViewById(R.id.fifteen);
        RadioButton tenButton = findViewById(R.id.ten);
        RadioButton fiveButton = findViewById(R.id.five);
        RadioButton zeroButton = findViewById(R.id.zero);

        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);

        // Setting all fields which have data
        if(!teleOpSaveString.isEmpty()){
            // #ACL1 | #ACL2 | #ACL3 | #ACL4 | # SCL1 | #SCL2 | #SCL3 | #SCL4 |
            // #Attempted processor | #Scored Processor | #Attempted Barge | #Scored Barge |
            // Park/Shallow/Deep | Time to hang | Algae Pickup | Coral Pickup ||
            CAfield.setText(u.untilNextComma(teleOpSaveString));
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);
            CSfield.setText(u.untilNextComma(teleOpSaveString));
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);

            AOfield.setText(u.untilNextComma(teleOpSaveString));
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);
            SOfield.setText(u.untilNextComma(teleOpSaveString));
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);

            teleOpSaveString = u.nextCommaOn(teleOpSaveString);
            DAAfield.setText(u.untilNextComma(teleOpSaveString));
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);
            DASfield.setText(u.untilNextComma(teleOpSaveString));
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);
            teleOpSaveString  = u.nextCommaOn(teleOpSaveString);

            String currentButton = u.untilNextComma(teleOpSaveString);
            switch(currentButton){
                case "Did Not Park":
                    didNotParkButton.toggle();
                    break;
                case "Half Solo":
                    partialSoloButton.toggle();
                    break;
                case "Half Duo":
                    partialDuoButton.toggle();
                    break;
                case "Full Top":
                    fullAboveButton.toggle();
                    break;
                case "Full Bottom":
                    fullBelowButton.toggle();
                    break;
                case "Full Solo":
                    fullSoloButton.toggle();
                    break;
            }
            teleOpSaveString = u.nextCommaOn(teleOpSaveString);

            String timeToHang = u.untilNextComma(teleOpSaveString);
            switch(timeToHang){
                case "25":
                    twentyFiveButton.toggle();
                    break;
                case "20":
                    twentyButton.toggle();
                    break;
                case "15":
                    fifteenButton.toggle();
                    break;
                case "10":
                    tenButton.toggle();
                    break;
                case "5":
                    fiveButton.toggle();
                    break;
                case "0":
                    zeroButton.toggle();
                    break;
            }
        }

        // Setting increment and decrement listeners for all buttons
        CAplus.setOnClickListener((l)->u.incrementText(CAfield));
        CAminus.setOnClickListener((l)->u.incrementText(CAfield, -1));
        AOplus.setOnClickListener((l)->u.incrementText(AOfield));
        AOminus.setOnClickListener((l)->u.incrementText(AOfield, -1));

        CSplus.setOnClickListener((l)->u.incrementText(CSfield));
        CSminus.setOnClickListener((l)->u.incrementText(CSfield, -1));
        SOplus.setOnClickListener((l)->u.incrementText(SOfield));
        SOminus.setOnClickListener((l)->u.incrementText(SOfield, -1));

        DAAplus.setOnClickListener((l)->u.incrementText(DAAfield));
        DAAminus.setOnClickListener((l)->u.incrementText(DAAfield, -1));

        DASplus.setOnClickListener((l)->u.incrementText(DASfield));
        DASminus.setOnClickListener((l)->u.incrementText(DASfield, -1));


        // Back button, which sends data backwards even if it's unfilled
        backButton.setOnClickListener((l)->{
            String teleOpInfo = "";
            // #ACL1 | #ACL2 | #ACL3 | #ACL4 | # SCL1 | #SCL2 | #SCL3 | #SCL4 |
            // #Attempted processor | #Scored Processor | #Attempted Barge | #Scored Barge |
            // Park/Shallow/Deep | Time to hang | Algae Pickup | Coral Pickup ||

            teleOpInfo += u.getData(CAfield) + ",";
            teleOpInfo += u.getData(CSfield) + ",";// Attempted Done

            teleOpInfo += u.getData(AOfield) + ",";
            teleOpInfo += u.getData(SOfield) + ",";// Scored Done

            teleOpInfo += u.getData(DAAfield) + ",";
            teleOpInfo += u.getData(DASfield) + ",";

            teleOpInfo += u.getData(parkRadioGroup) + ",";
            teleOpInfo += u.getData(endgameTimeGroup) + ",";

            Intent i = new Intent(this, activityAutonomous.class);
            i.putExtra("preMatch", preMatchSaveString);
            i.putExtra("auto", autoSaveString);
            i.putExtra("teleOp", teleOpInfo);
            i.putExtra("postMatch", postMatchSaveString);

            this.startActivity(i);
        });

        saveButton.setOnClickListener((l) -> {
            String response = "";
            if (u.getData(CAfield).isEmpty())
                CAfield.setText("0");
            if(u.getData(AOfield).isEmpty())
                AOfield.setText("0");
            if(u.getData(CSfield).isEmpty())
                CSfield.setText("0");
            if(u.getData(SOfield).isEmpty())
                SOfield.setText("0");
            if(u.getData(DAAfield).isEmpty())
                DAAfield.setText("0");
            if(u.getData(DASfield).isEmpty())
                DASfield.setText("0");
            if(u.getData(parkRadioGroup).isEmpty())
                response = "Please select an endgame position";
            else if(u.getData(endgameTimeGroup).isEmpty())
                response = "Please select park time";
            else if(Integer.parseInt(u.getData(CAfield)) < Integer.parseInt(u.getData(AOfield)))
                response = "Attempted Coral L1 cannot be less than Scored Coral L1";
            else if(Integer.parseInt(u.getData(CSfield)) < Integer.parseInt(u.getData(SOfield)))
                response = "Attempted Coral L2 cannot be less than Scored Coral L2";
            else{
                String teleOpInfo = "";

                teleOpInfo += u.getData(CAfield) + ",";
                teleOpInfo += u.getData(CSfield) + ",";// Attempted Done

                teleOpInfo += u.getData(AOfield) + ",";
                teleOpInfo += u.getData(SOfield) + ",";// Scored Done

                teleOpInfo += u.getData(DAAfield) + ",";
                teleOpInfo += u.getData(DASfield) + ",";

                teleOpInfo += u.getData(parkRadioGroup) + ",";
                teleOpInfo += u.getData(endgameTimeGroup) + ",";

                Intent i = new Intent(this, activityAfterMatch.class);
                i.putExtra("preMatch", preMatchSaveString);
                i.putExtra("auto", autoSaveString);
                i.putExtra("teleOp", teleOpInfo);
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