package com.mojahid2021.shortly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;





public class HomeFragment extends Fragment {
    private TextInputEditText urlInputText;
    private MaterialButton btnSubmit;
    private TextView tvURL;
    private Button btnCopy;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        urlInputText = view.findViewById(R.id.etURL);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        tvURL = view.findViewById(R.id.tvURL);
        btnCopy = view.findViewById(R.id.btnCopy);
        progressBar = view.findViewById(R.id.progressBar);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlInputText.getText().toString();
                if (url.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter url...", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    getShortUrl(url);
                }
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Shortened URL", tvURL.getText().toString());
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), "URL copied to clipboard!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void getShortUrl(String url) {
        Map<String, String> shortenRequest = new HashMap<>();
        shortenRequest.put("alias", "");
        shortenRequest.put("original_url", url);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ShortenResponse> call = apiService.shortenUrl(shortenRequest);
        call.enqueue(new Callback<ShortenResponse>() {
            @Override
            public void onResponse(Call<ShortenResponse> call, Response<ShortenResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    ShortenResponse shortenResponse = response.body();
                    if (shortenResponse != null) {
                        String shortUrl = shortenResponse.getShortUrl();
                        String originalUrl = shortenResponse.getOriginalUrl();
                        Toast.makeText(getContext(), "Shortened URL: " + shortUrl, Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Original URL: " + originalUrl, Toast.LENGTH_LONG).show();
                        tvURL.setText(shortUrl);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to shorten URL", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ShortenResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
