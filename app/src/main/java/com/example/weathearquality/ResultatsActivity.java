package com.example.weathearquality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultatsActivity extends AppCompatActivity {
    private TextView villeTextView;
    private TextView temperatureTextView;
    private TextView qualiteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultats_activity);

        // Récupération des vues du formulaire
        villeTextView = findViewById(R.id.ville_resultats);
        temperatureTextView = findViewById(R.id.temperature_resultats);
        qualiteTextView = findViewById(R.id.qualite_air_resultats);

        // Récupération des données passées en paramètre
        Intent intent = getIntent();
        String ville = intent.getStringExtra("ville");
        boolean afficherTemperature = intent.getBooleanExtra("afficherTemperature", false);

        // Affichage des données
        villeTextView.setText(ville);
        if (afficherTemperature) {
            temperatureTextView.setText("20°C");
        } else {
            temperatureTextView.setVisibility(View.GONE);
        }
        qualiteTextView.setText("Bon");
    }
}
