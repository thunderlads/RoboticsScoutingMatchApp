package com.example.roboticsscoutingmatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        Button backButton = findViewById(R.id.back_button);
        Button saveButton = findViewById(R.id.save_button);



        backButton.setOnClickListener((l)-> {
            //TODO: Add info getter for auto page. NOT ALL FIELDS NEED TO BE FILLED

            Intent i = new Intent(this, activityPreMatch.class);
            i.putExtra("preMatch", preMatchSaveString);
            i.putExtra("auto", autoSaveString);
            i.putExtra("teleOp", teleOpSaveString);
            i.putExtra("postMatch", postMatchSaveString);

            this.startActivity(i);
        });
    }
}