package algonquin.cst2355.tornuse.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

        // part 1
        variableBinding.mybutton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has " + s);
        });


        // part 2 - checkbox, switch, radiobutton
        model.isSelected.observe(this, selected -> {
           variableBinding.checkbox.setChecked(selected);
           variableBinding.radiobutton.setChecked(selected);
           variableBinding.switchWidget.setChecked(selected);

           // Show Toast Message
            String message = "The value is now: " + selected;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        variableBinding.checkbox.setOnCheckedChangeListener( (btn, selected) -> {
            model.isSelected.postValue(selected);
        });

        variableBinding.switchWidget.setOnCheckedChangeListener( (btn, selected) -> {
            model.isSelected.postValue(selected);
        });

        variableBinding.radiobutton.setOnCheckedChangeListener( (btn, selected) -> {
            model.isSelected.postValue(selected);
        });


        // Last part - image
        variableBinding.myimagebutton.setOnClickListener(click -> {
            int width = click.getWidth();
            int height = click.getHeight();
            String message = "The width = " + width + " and height = " + height;
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        variableBinding.mybutton2.setOnClickListener(click -> {
            int width = click.getWidth();
            int height = click.getHeight();
            String message = "The width = " + width + " and height = " + height;
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        });

    }
}