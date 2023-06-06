package algonquin.cst2355.tornuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button LoginButton;
    EditText emailText;

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity", "The application is now visible on screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "Any memory used by the application is freed.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w("MainActivity", "In onCreate() - Loading widgets");

        LoginButton = findViewById(R.id.LoginButton);
        emailText = findViewById(R.id.emailText);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        emailText.setText(emailAddress);

        LoginButton.setOnClickListener(click -> {
            String email = emailText.getText().toString();

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            // Save the email address to SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", email);
            editor.putFloat("Hi", 4.5f);
            editor.putInt("Age", 35);
            prefs.getFloat("Hi", 0);
            prefs.getInt("Age", 0);
            editor.apply();

            nextPage.putExtra("EmailAddress", email);
            startActivity(nextPage);
        });


    }
}