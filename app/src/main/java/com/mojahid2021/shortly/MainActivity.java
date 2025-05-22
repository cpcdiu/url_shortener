package com.mojahid2021.shortly;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText urlInputText;
    private LinearLayout btnSubmit;
    private TextView tvURL;
    private Button btnCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInputText = findViewById(R.id.etURL);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvURL = findViewById(R.id.tvURL);
        btnCopy = findViewById(R.id.btnCopy);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlInputText.getText().toString();
                if (url.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter url...", Toast.LENGTH_SHORT).show();
                } else {
                    getShortUrl(url);
                }
            }
        });

        // Set up the click listener for the Copy button
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clipboard manager
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                // Create a ClipData object with the text you want to copy
                ClipData clip = ClipData.newPlainText("Shortened URL", tvURL.getText().toString());

                // Set the ClipData to the clipboard
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    // Show a confirmation Toast message
                    Toast.makeText(MainActivity.this, "URL copied to clipboard!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getShortUrl(String url) {
        // Create the request payload using a Map
        Map<String, String> shortenRequest = new HashMap<>();
        shortenRequest.put("alias", "");
        shortenRequest.put("original_url", url);

        // Create an API service instance
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Send the POST request
        Call<ShortenResponse> call = apiService.shortenUrl(shortenRequest);

        call.enqueue(new Callback<ShortenResponse>() {
            @Override
            public void onResponse(Call<ShortenResponse> call, Response<ShortenResponse> response) {
                if (response.isSuccessful()) {
                    // Successfully shortened the URL
                    ShortenResponse shortenResponse = response.body();
                    if (shortenResponse != null) {
                        String shortUrl = shortenResponse.getShortUrl();
                        String originalUrl = shortenResponse.getOriginalUrl();

                        // Display the shortened URL and original URL
                        Toast.makeText(MainActivity.this, "Shortened URL: " + shortUrl, Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "Original URL: " + originalUrl, Toast.LENGTH_LONG).show();
                        tvURL.setText(shortUrl);
                    }
                } else {
                    // Handle failure (non-success response)
                    Toast.makeText(MainActivity.this, "Failed to shorten URL", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ShortenResponse> call, Throwable t) {
                // Handle network failure
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}