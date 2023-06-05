package algonquin.cst2355.tornuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button LoginButton;

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
        LoginButton.setOnClickListener( click -> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(nextPage);
        });

    }
}