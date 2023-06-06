package algonquin.cst2355.tornuse;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;


public class SecondActivity extends AppCompatActivity {
    private TextView MainText;
    private Button CallNumber, ChangeImage;
    private EditText phoneEdit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Email input from Main page
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        MainText = findViewById(R.id.mainText);
        MainText.setText("Welcome back " + emailAddress);
        phoneEdit = findViewById(R.id.editTextPhone);
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhoneNumber = sharedPreferences.getString("PhoneNumber", "");
        phoneEdit.setText(savedPhoneNumber);

        // Phone Number from main page
        CallNumber = findViewById(R.id.button1);
        CallNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneEdit.getText().toString();

                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(call);
            }
        });

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");

                            // Set the captured image as the source of the ImageView
                            ImageView profileImageView = findViewById(R.id.imageView);
                            profileImageView.setImageBitmap(thumbnail);
                        }
                    }
                }
        );

        ChangeImage = findViewById(R.id.button2);
        ChangeImage.setOnClickListener( click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });

   }
    @Override
    protected void onPause() {
        super.onPause();
        String phoneNumber = phoneEdit.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PhoneNumber", phoneNumber);
        editor.apply();
    }
}