package com.example.weathearquality;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    private EditText villeEditText;

    private RadioButton ouiRadioButton;

    private RadioButton nonRadioButton;

    private Button validerButton;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        Intent intent = getIntent();
        String utilisateur = intent.getStringExtra("utilisateur");

        TextView bienvenue = findViewById(R.id.message_bienvenue);
        bienvenue.setText(bienvenue.getText() + " " + utilisateur + " !");


        // Récupération des vues du formulaire
        villeEditText = findViewById(R.id.ville_edit_text);
        ouiRadioButton = findViewById(R.id.ouiButtonRadio);
        validerButton = findViewById(R.id.boutonValider);

        // Gestion du clic sur le bouton "Valider"
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des données saisies
                String ville = villeEditText.getText().toString();
                boolean afficherTemperature = ouiRadioButton.isChecked();
                // Création de l'intention pour passer à l'activité suivante
                Intent intent = new Intent(FormActivity.this, ResultatsActivity.class);
                intent.putExtra("ville", ville);
                intent.putExtra("afficherTemperature", afficherTemperature);
                startActivity(intent);
            }
        });
    }
}
