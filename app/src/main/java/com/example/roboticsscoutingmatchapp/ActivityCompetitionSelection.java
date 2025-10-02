package com.example.roboticsscoutingmatchapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

// Fully Commented by your's truly
// Any questions, contact Akash Ghoshroy at:
//  -email: aghoshroy@wpi.edu :: please head emails with Scouting App: Topic!!
//  -157's slack - Akash Ghoshroy
//  -phone: +1(508)308-5080 :: i'd prefer you didn't this way tho....

public class ActivityCompetitionSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto-generated code begin
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_competition_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Auto-generated code end

        // Create utilities object
        U u = new U();
        // Define component's variables, you actually only need to define the group!
        RadioGroup competitionRadioGroup = findViewById(R.id.competition_radio_group);
//        RadioButton wpiButton = findViewById(R.id.comp_wpi_button);
//        RadioButton uvmButton = findViewById(R.id.comp_uvm_button);
//        RadioButton dcmpButton = findViewById(R.id.comp_dcmp);
//        RadioButton worldsButton = findViewById(R.id.comp_worlds);
//        RadioButton testButton = findViewById(R.id.comp_test);
        Button saveButton = findViewById(R.id.save_button);
        // Create the toast (pop-up message)
        Toast unfilledMessage = new Toast(this);
        unfilledMessage.setDuration(Toast.LENGTH_SHORT);


        // Creates the scoutName variable, and the changeCompetition variable
        // The changeCompetition variable is built to handle whether or not the app is using    ->
        // ->the same competition as the previous times, which is decided through if the user   ->
        // ->intentionally goes back to this page from the PreMatch page, or if the date is     ->
        // ->different than the last recorded match
        String scoutName;
        // Initially assuming that the competition is not going to be changed
        boolean changeCompetition = false;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            // If there is info passed in from the last activity, then pass it along (scout name)
            scoutName = extras.getString("scoutName", "");
            // If coming from the PreMatch page, then changeCompetition will be true
            changeCompetition = extras.getBoolean("chooseNewCompetition", false);
        } else {
            scoutName = "";
        }

        // Making a rudimentary file to store the date of the last inputted match
        final String FILENAME = "matchAndDate";
        // Makes a var that points to the file-space
        File file = new File(this.getFilesDir(), FILENAME);
        // Gets the current time and sets a string to have the value we need YYYYMMDD
        // i.e. 20070724 (birthday!!)
        Calendar now = Calendar.getInstance();
        String currentDate = "";
        currentDate += now.get(Calendar.YEAR);
        currentDate += now.get(Calendar.MONTH);
        currentDate += now.get(Calendar.DAY_OF_MONTH);

        // String of the last date/match entered into the file
        String dateAndMatchString = "";

        // If not changing the competition
        if(!changeCompetition){
            // Check if the file exists at the location
            if (file.exists()) {
                // This is here to catch any errors without shutting down the whole program
                try {
                    // Open a "stream" that we can access the file from
                    FileInputStream fis = this.openFileInput(FILENAME);
                    // Create a reader for the file
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    // Set the string to what was inputted into the file
                    dateAndMatchString += reader.readLine();
                    // Close the file, good garbage cleanup
                    reader.close();
                // Errors and such
                } catch (FileNotFoundException e) {
                    Log.e("File Not Found Exception", e.toString());
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                }
            }
            // If there exists something within the file
            if (!dateAndMatchString.isEmpty()) {
                // The date of the last entry
                String fileDate = u.untilNextComma(dateAndMatchString);
                // If they are equal, and competition is not to be changed from another activity
                if (fileDate.equals(currentDate)) {
                    // Create a new intent to go to prematch, with the scoutname and competition
                    Intent i = new Intent(this, activityPreMatch.class);
                    i.putExtra("competition", u.nextCommaOn(dateAndMatchString));
                    i.putExtra("scoutName", scoutName);
                    this.startActivity(i);
                }
            }
        }

        // This only happens if the competition is to be saved, or if the file date doesn't match
        String finalCurrentDate = currentDate;
        // When next button clicked
        saveButton.setOnClickListener((l)->{
            // For the toast
            String response = "";
            // Don't let them proceed if they didn't choose a competition, and throw em an error
            if(u.getData(competitionRadioGroup).isEmpty()){
                response = "Please choose current competition to be scouting";
            }else{
                // What to put into the file, since it needs to be changed: date,competition
                String fileContents = finalCurrentDate + "," + u.getData(competitionRadioGroup);
                // Try opening the file
                try(FileOutputStream fos = this.openFileOutput(FILENAME, Context.MODE_PRIVATE)){
                    // Write to the file
                    fos.write(fileContents.getBytes());
                // Errors and the such
                } catch(FileNotFoundException e){
                    Log.e("File Not Found Exception", e.toString());
                } catch(IOException e){
                    Log.e("IO Exception", e.toString());
                }
                // Create new intent to go to next page, put the competition and scoutName into it
                Intent i = new Intent(this, activityPreMatch.class);
                i.putExtra("competition", u.getData(competitionRadioGroup));
                i.putExtra("scoutName", scoutName);
                this.startActivity(i);
            }
            // If they need to fill in a competition location, pop that up on the screen
            if(!response.isEmpty()){
                unfilledMessage.setText(response);
                unfilledMessage.show();
            }
        });
    }
}