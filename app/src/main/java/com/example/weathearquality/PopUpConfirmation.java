package com.example.weathearquality;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpConfirmation extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.popup);

            // On récupère les boutons de la popup
            Button btn_valider = findViewById(R.id.dialog_positive_button);
            Button btn_annuler = findViewById(R.id.dialog_negative_button);

            // On récupère le résultat de la popup et on le renvoie à l'activité appelante
            // si il clique sur oui
            btn_valider.setOnClickListener(v -> {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            });
            // si il clique sur non
            btn_annuler.setOnClickListener(v -> {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            });


        }
}
