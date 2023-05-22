package algonquin.cst2355.tornuse.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2355.tornuse.data.MainViewModel;
import algonquin.cst2355.tornuse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // called parent onCreate()

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( variableBinding.getRoot() ); // loads XML on screen

        variableBinding.mybutton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has " + s);
        });


    }
}