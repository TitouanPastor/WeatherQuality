package com.example.weathearquality;

import android.annotation.SuppressLint;
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
                intent.putExtra("utilisateur", utilisateur);
                intent.putExtra("ville", ville);
                intent.putExtra("afficherTemperature", afficherTemperature);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflaterMenu  = getMenuInflater();
        inflaterMenu.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.histMenu:
                Intent intentHist = new Intent(FormActivity.this, HistoriqueActivity.class);
                startActivity(intentHist);
                break;

            case R.id.deconnectMenu:
                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.quitMenu:
                System.exit(0);
                break;
        }
        return true;
    }
}
