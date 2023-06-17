package algonquin.cst2355.tornuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.algonquin.cst2355.tornuse.R;

public class MainActivity extends AppCompatActivity {

    /** This is a javadoc comment */

    /*   This is a normal comment */

    /** This holds the "Hello world" text view */
    private TextView theText;

    /** This holds the "Click me" button */
    private Button myButton;

    /** This holds the edit text for typing into */
    private EditText theEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theText = findViewById(R.id.textView);
        myButton = findViewById(R.id.button);
        theEditText = findViewById(R.id.theEditText);

        myButton.setOnClickListener( click -> {
            String password = theEditText.getText().toString();

            checkPasswordComplexity(password);
        });

    }

    /** This function checks the password string
     *
     * @param password  The string to search
     * @return true if all condition passed, false otherwise
     */
    boolean checkPasswordComplexity(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialSymbol = false;

        // Check each character in the password
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (isSpecialSymbol(ch)) {
                hasSpecialSymbol = true;
            }
        }

        // Display Toast messages for missing requirements
        if (!hasUpperCase) {
            showToast("Your password does not have an upper case letter");
        }
        if (!hasLowerCase) {
            showToast("Your password does not have a lower case letter");
        }
        if (!hasNumber) {
            showToast("Your password does not have a number");
        }
        if (!hasSpecialSymbol) {
            showToast("Your password does not have a special symbol");
        }

        // Return true if all requirements are met, false otherwise
        return hasUpperCase && hasLowerCase && hasNumber && hasSpecialSymbol;
    }

    /** This function checks the password string
     *
     * @param ch check text include special word
     * @return true if special character is included
     */
    boolean isSpecialSymbol(char ch) {
        // Define the set of special symbols
        String specialSymbols = "#$%^&*!@?";

        // Check if the character is a special symbol
        return specialSymbols.indexOf(ch) != -1;
    }

    void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }



}