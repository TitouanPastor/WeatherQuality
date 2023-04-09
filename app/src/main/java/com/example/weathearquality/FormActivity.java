package com.example.weathearquality;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    private EditText villeEditText;
    private RadioButton ouiRadioButton;
    private Button validerButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        // Récupération de l'utilisateur connecté
        Intent intent = getIntent();
        String utilisateur = intent.getStringExtra("utilisateur");

        // Affichage du message de bienvenue
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
                // Récupération des données saisies si la ville est saisie
                if (!villeEditText.getText().toString().isEmpty()) {
                    String ville = villeEditText.getText().toString();
                    boolean afficherTemperature = ouiRadioButton.isChecked();
                    // Création de l'intent pour passer à l'activité suivante
                    Intent intent = new Intent(FormActivity.this, ResultatsActivity.class);
                    intent.putExtra("utilisateur", utilisateur);
                    intent.putExtra("ville", ville);
                    intent.putExtra("afficherConseils", afficherTemperature);
                    startActivity(intent);
                }else{
                    Toast.makeText(FormActivity.this, R.string.choisirVille, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflaterMenu  = getMenuInflater();
        inflaterMenu.inflate(R.menu.menu, menu);
        return true;
    }

    // Gestion des actions du menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.histMenu:
                Intent intentHist = new Intent(FormActivity.this, HistoriqueActivity.class);
                intentHist.putExtra("utilisateur", getIntent().getStringExtra("utilisateur"));
                startActivity(intentHist);
                break;

            case R.id.deconnectMenu:
                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.quitMenu:
                this.finishAffinity();
                break;
        }
        return true;
    }
}
