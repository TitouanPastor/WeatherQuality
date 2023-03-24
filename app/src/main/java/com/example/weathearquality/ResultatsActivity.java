package com.example.weathearquality;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultatsActivity extends AppCompatActivity {
    private TextView tVille;
    private TextView tTemperature;
    private TextView tQualiteAir;

    public static final String TAG = "TAG";
    static final String BASE_URL = "https://api.waqi.info/feed/";
    Retrofit retrofit;
    WeatherAPI weatherAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultats_activity);

        // Récupération des vues du formulaire
        tVille = findViewById(R.id.ville_resultats);
        tTemperature = findViewById(R.id.temperature_resultats);
        tQualiteAir = findViewById(R.id.qualite_air_resultats);

        // Récupération des données passées en paramètre
        Intent intent = getIntent();
        String ville = intent.getStringExtra("ville");
        boolean afficherTemperature = intent.getBooleanExtra("afficherTemperature", false);

        //API


        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.weatherAPI = retrofit.create(WeatherAPI.class);
        Call<JsonElement> call = weatherAPI.dataVille(ville);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    JsonElement jsonElement = response.body();
                    JsonObject jsonGlobal = jsonElement.getAsJsonObject();


                    JsonObject jsonData = jsonGlobal.getAsJsonObject("data");
                    JsonObject jsonIaqi = jsonData.getAsJsonObject("iaqi");
                    JsonObject jsonTemperature = jsonIaqi.getAsJsonObject("t");
                    String temperature = jsonTemperature.get("v").getAsString();

                    tTemperature.setText(temperature);

                    String notePollution = jsonData.get("aqi").getAsString();
                    tQualiteAir.setText(notePollution);


                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "Erreur : " + t.getMessage());
            }
        });

        // Affichage des données
        tVille.setText(ville);
        if (!afficherTemperature) {
            tTemperature.setVisibility(View.GONE);

        }
    }
}
