package com.example.roboticsscoutingmatchapp;


import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class U extends AppCompatActivity{


    public final int DATA_VERSION = 1;
    // Characters to strip in input fields for stripText method
    public static final String [] DELIMITER = {",", ";", ":", "|", "\n"};
    public static final String [] WHITESPACE = {" "};
    public static final String [] DELIMITER_AND_WHITESPACE = (String[])concatArrays(DELIMITER, WHITESPACE);


    // Returning data pulled from fields

    /**
     * Returns the two {@code Arrays} concatenated
     * @param a1 {@code Array}: the first {@code Array}
     * @param a2 {@code Array}: the second {@code Array}
     * @return An {@code Array} of type {@code Object[]} of the two {@code Arrays} concatenated
     * @author Akash Ghoshroy
     */
    public static Object[] concatArrays(Object[] a1, Object[] a2){
        // Gets the total length of the new array
        int l = a1.length+a2.length;
        // Declares a new array of total length of both passed in arrays
        Object[] f = new Object[l];
        // Iterator
        int i = 0;
        // Goes through every object in array a1 and sets it to the new array
        for(Object o : a1){
            f[i] = o;
            i++;
        }
        // DOESNT reset iterator and adds every object in a2 to the array
        for(Object o : a2){
            f[i] = o;
            i++;
        }
        return f;
    }

    /**
     * @param field <code>EditText</code> component to pull String data from
     * @return A String with the text from the provided <code>EditText</code>
     * @author Akash Ghoshroy
     */
    public String getData(EditText field){
        return field.getText().toString(); // Literally just returns the String form of the text in the EditText
    }
    /**
     * @param field <code>RadioGroup</code> component to pull String data from
     * @return String with the text of the selected <code>RadioButton</code>
     * @author Akash Ghoshroy
     */
    public String getData(RadioGroup field){
        String response;
        if(field.getCheckedRadioButtonId() == -1){ // If no button is selected, return an empty String
            response = "";
        }else{
            RadioButton checkedButton = field.findViewById(field.getCheckedRadioButtonId()); // Get the currently selected button in the RadioGroup
            response = checkedButton.getText().toString(); // Return the text associated with the currently selected button in the RadioGroup
        }
        return response; // Returns either value
    }
    /**
     * @param field <code>TextView</code> component to pull String data from
     * @return String with the text of the provided <code>TextView</code>
     * @author Akash Ghoshroy
     */
    public String getData(TextView field){
        return field.getText().toString(); // Return the String form of the text in a TextView
    }
    /**
     * @param field <code>CheckBox</code> component to pull String data from.
     * @return String representing the <code>boolean</code> value of the <code>CheckBox</code>.
     * @author Akash Ghoshroy
     */
    public String getData(CheckBox field){
        // If the checkbox is ticked then return true, else false yk
        if(field.isChecked()){ // Kinda self explanatory if you ask me
            return "True";
        } else{
            return "False";
        }
    }

    /**
     * Removes all instances of each element in <code>stripType</code> from <code>text</code>.</br>
     * Can be used with the pre-defined String array constants {@link #DELIMITER}, {@link #WHITESPACE}, and {@link #DELIMITER_AND_WHITESPACE}.
     * @param text <code>String</code> that will be stripped of characters.
     * @param stripType Non-Null <code>String</code> array to be removed from <code>text</code>.
     * @see #DELIMITER
     * @see #WHITESPACE
     * @see #DELIMITER_AND_WHITESPACE
     * @return <code>text</code> sans all appearances of every element in <code>stripType</code>.
     * @author Akash Ghoshroy
     */
    public String stripText(String text,  String[] stripType){
        // Sets the String to be returned to text
        String response = text;
        // Loops through all elements of stripType
        for(String s : stripType){
            // Goes through every instance of each element of stripType and removes it
            while(response.contains(s)){
                response = response.substring(0, response.indexOf(s)) + response.substring(response.indexOf(s)+1);
            }
        }
        // Returns text after it has been modified
        return response;
    }
    /**
     * Removes all appearances of {@link #DELIMITER_AND_WHITESPACE} in <code>text</code>.
     * @param text <code>String</code> to be stripped.
     * @see #DELIMITER_AND_WHITESPACE
     * @return <code>text</code> sans all appearances of every element in {@link #DELIMITER_AND_WHITESPACE}.
     * @author Akash Ghoshroy
     */
    public String stripText(String text){
        // This one assumes you want to just strip both delimiter and whitespace since nothing was defined
        // Sets the String to be returned to text
        String response = text;
        // Loops through all elements of DELIMITER_AND_WHITESPACE
        for(String s : DELIMITER_AND_WHITESPACE){
            // Goes through every instance of each element of stripType and removes it
            while(response.contains(s)){
                response = response.substring(0, response.indexOf(s)) + response.substring(response.indexOf(s)+1);
            }
        }
        // Returns text after it has been modified
        return response;
    }

    /**
     * Returns <code>text</code> from the first comma appearance and on, provided there is a comma.</br>
     * Otherwise, returns the entire <code>text</code>.
     * @param text <code>String</code>: a <code>String</code> to be returned from the next comma.
     * @return A copy of {@code text} from the next available comma on.
     * @author Akash Ghoshroy
     */
    public String nextCommaOn(String text){
        // One-liner, but returns everything from the first comma found until the end of text
        return text.substring(text.indexOf(",")+1);
    }

    /**
     * Returns <code>text</code> from the <code>commaNum</code>th comma on
     * @param text <code>String</code>: the {@code String} from which data should be returned
     * @param commaNum {@code int}: the {@code int} representing the comma from which data should be returned after
     * @return A {@code String} copy of {@code text} from the {@code commaNum}th comma on
     * @author Akash Ghoshroy
     */
    public String nextCommaOn(String text, int commaNum){
        // If its the first (or 0/negative), then return from the first comma on
        if(commaNum <= 1){
            return text.substring(text.indexOf(",")+1);
        }
        // Otherwise recurse with one less comma, while passing in from the next comma until the end of the string
        else{
            return nextCommaOn(text.substring(text.indexOf(",")+1), commaNum-1);
        }
    }

    /**
     * Returns a copy of {@code text} from the beginning of {@code text} to the next comma.</br>
     * Pre-Condition: {@code text} must contain at least one comma.
     * @param text {@code String}: a {@code String} for which a copy of until the next comma will be returned.
     * @return A copy of {@code text} from the beginning until the next comma.
     * @author Akash Ghoshroy
     */
    public String untilNextComma(String text){
        // returns the snippet of "text" from the beginning until the first comma
        return text.substring(0, text.indexOf(","));
    }

    /**
     * Increments the value represented within {@code field} by one.</br>
     * Sets the value represented within {@code field} to one if {@code field} is empty.
     * @param field {@code TextView}: a {@code TextView} with either no value or an integer value.
     * @author Akash Ghoshroy
     */
    public void incrementText(TextView field){
        if(field.getText().toString().isEmpty()){
            // If the TextView is empty, it gets set to have a value of one
            field.setText("1");
        }else{
            // Sets currentNum to the integer value within field
            int currentNum = Integer.parseInt(field.getText().toString());
            // Increments currentNum
            currentNum++;
            // Sets the display value of field to currentNum
            field.setText(Integer.toString(currentNum));
        }
    }

    /**
     * Increments the value represented within {@code field} by the value within {@code incrementBy}.</br>
     * Sets the value represented within {@code field} to the value of {@code incrementBy} if {@code field} is empty.
     * @param field {@code TextView}: a {@code TextView} with either no value or an integer value.
     * @param incrementBy {@code int}: an {@code int} with either a positive or negative value
     * @author Akash Ghoshroy
     */
    public void incrementText(TextView field, int incrementBy){
        if(field.getText().toString().isEmpty()){
            // If the TextView is empty, it gets set to have the value of incrementBy
            field.setText(Integer.toString(incrementBy));
        }else{
            // Sets the display value to the current display value + incrementBy
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+incrementBy));
        }
    }

    /**
     * Increments the value represented within {@code field} by one.</br>
     * Sets the value represented within {@code field} to one if {@code field} is empty.
     * @param field {@code EditText}: an {@code EditText} with either no value or an integer value.
     * @author Akash Ghoshroy
     */
    public void incrementText(EditText field){
        if(field.getText().toString().isEmpty()){
            // If the EditText is empty, it gets set to have a value of one
            field.setText("1");
        }else{
            // Sets currentNum to the integer value within field
            int currentNum = Integer.parseInt(field.getText().toString());
            // Sets the display value to currentNum + one
            field.setText(Integer.toString(currentNum+1));
        }
    }

    /**
     * Increments the value represented within {@code field} by the value within {@code incrementBy}.</br>
     * Sets the value represented within {@code field} to the value of {@code incrementBy} if {@code field} is empty.
     * @param field {@code EditText}: an {@code EditText} with either no value or an integer value.
     * @param incrementBy {@code int}: an {@code int} with either a positive or negative value
     * @author Akash Ghoshroy
     */
    public void incrementText(EditText field, int incrementBy){
        if(field.getText().toString().isEmpty()){
            // If the EditText is empty, it gets set to have the value of incrementBy
            field.setText(Integer.toString(incrementBy));
        }else{
            // Sets currentNum to the integer value within field
            int currentNum = Integer.parseInt(field.getText().toString());
            // Sets the display value to currentNum + incrementBy
            field.setText(Integer.toString(currentNum+incrementBy));
        }
    }

    public int searchArrCol(ArrayList<Object[]> arr, int col, Object obj){
        // Data searching stuff?
        // Looks through each row of an array's column and compares to an object
        int i = 0;
        for(Object[] o: arr){
            if(o[col].equals(obj)){return i;}
            i++;
        }
        return -1;
    }
}
