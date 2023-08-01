package algonquin.cst2355.tornuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.DowonsAndroidLabs.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        queue = Volley.newRequestQueue(this);

        setContentView(binding.getRoot());

        binding.button.setOnClickListener(click -> {

            String cityName = binding.theEditText.getText().toString();

            // Server name
            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + URLEncoder.encode(cityName)
                    + "&appid=cddf7c1b95f2f229bc5b127ee462c1bf&units=metric";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (successfulResponse) -> {
                        JSONObject main = null;
                        try {
                            main = successfulResponse.getJSONObject("main");
                            double temp = main.getDouble("temp");
                            double min = main.getDouble("temp_min");
                            double max = main.getDouble("temp_max");
                            int humidity = main.getInt("humidity");

                            runOnUiThread( (  )  -> {

                                binding.temp.setText("The current temperature is " + temp + " degrees");
                                binding.temp.setVisibility(View.VISIBLE);

                                binding.minTemp.setText("The min temperature is " + min);
                                binding.minTemp.setVisibility(View.VISIBLE);

                                binding.maxTemp.setText("The max temperature is " + max);
                                binding.maxTemp.setVisibility(View.VISIBLE);

                                binding.humitidy.setText("The humidity is " + humidity);
                                binding.humitidy.setVisibility(View.VISIBLE);

                            });


                            JSONArray weatherArray = successfulResponse.getJSONArray("weather");

                            JSONObject pos0 = weatherArray.getJSONObject(0);
                            String iconName = pos0.getString("icon");

                            String description = pos0.getString("description");

                            binding.description.setText(description);
                            binding.description.setVisibility(View.VISIBLE);

                            String pictureURL = "https://openweathermap.org/img/w/" + iconName + ".png";

                            ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    // Save the bitmap to the device's storage
                                    saveBitmapToStorage(MainActivity.this, bitmap, iconName);

                                    Bitmap savedIcon = loadBitmapFromStorage(MainActivity.this, iconName);
                                    binding.icon.setImageBitmap(savedIcon);
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,
                                    (error) -> {
                                        int i = 0;
                                    });

                            queue.add(imgReq);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    },
                    (vError) -> {
                        int i = 0;
                    });

            queue.add(request);

        });



    }

    // Function to save the bitmap to the device's storage
    private void saveBitmapToStorage(Context context, Bitmap bitmap, String fileName) {
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName + ".png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Function to load the bitmap from the device's storage
    private Bitmap loadBitmapFromStorage(Context context, String fileName) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName + ".png");
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
