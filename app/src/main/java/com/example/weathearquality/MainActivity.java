package com.example.weathearquality;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int  orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.connexion);
        }else{
            setContentView(R.layout.connexion_land);
        }


        //Création de la base de données si elle existe pas
        ClientDbHelper bdd = new ClientDbHelper(this);
        SQLiteDatabase bd = bdd.getWritableDatabase();

        // Récupération des vues du formulaire
          Button bConnexion = findViewById(R.id.connexion_button);
          Button bInscription = findViewById(R.id.inscription_button);

        // Gestion du clic sur le bouton "Valider"
        bConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des données saisies
                TextView vUtilisateur = findViewById(R.id.username_edit_text);
                TextView vMotDePasse = findViewById(R.id.password_edit_text);
                String utilisateur = vUtilisateur.getText().toString();
                String motDePasse = vMotDePasse.getText().toString();

                String[] colonnes = {"utilisateur", "motDePasse"};
                String[] args = {utilisateur, motDePasse};
                Cursor req = bd.query("utilisateur", colonnes, "utilisateur = ? AND motDePasse = ?", args, null, null, null);
                if (req.moveToFirst()) {
                    // le curseur contient au moins une ligne
                    Intent intent = new Intent(MainActivity.this, FormActivity.class);
                    intent.putExtra("utilisateur", utilisateur);
                    startActivity(intent);
                } else {
                // Création de l'intention pour passer à l'activité suivante
                TextView vErreur = findViewById(R.id.connexion_error);
                vErreur.setText("Indentifiant ou mot de passe invalide !");
                }
            }
        });

        bInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des données saisies
                TextView vUtilisateur = findViewById(R.id.inscription_username_edit_text);
                TextView vMotDePasse = findViewById(R.id.inscription_password_edit_text);
                String utilisateur = vUtilisateur.getText().toString();
                String motDePasse = vMotDePasse.getText().toString();

                if(!utilisateur.isEmpty() && !motDePasse.isEmpty()) {
                    String[] colonnes = {"utilisateur"};
                    String[] args = {utilisateur};
                    Cursor req = bd.query("utilisateur", colonnes, "utilisateur = ?", args, null, null, null);

                    if (req.moveToFirst()) {
                        // le curseur contient au moins une ligne
                        TextView vErreur = findViewById(R.id.inscription_error);
                        vErreur.setText("Cet utilisateur existe déjà ! Veuillez vous connectez");
                    } else {
                        //Insertion des valeurs dans la base de données
                        ContentValues valuesInscription = new ContentValues();
                        valuesInscription.put("utilisateur", utilisateur);
                        valuesInscription.put("motDePasse", motDePasse);

                        bd.insert("utilisateur", null, valuesInscription);
                        // Création de l'intention pour passer à l'activité suivante
                        Intent intent = new Intent(MainActivity.this, FormActivity.class);
                        intent.putExtra("utilisateur", utilisateur);
                        startActivity(intent);
                    }
                }else{
                    TextView vErreur = findViewById(R.id.inscription_error);
                    vErreur.setText(R.string.champVide);
                }


            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(this, R.string.connexionEtablie, Toast.LENGTH_SHORT).show();
// Traitement si le réseau est OK
        }
        else
        {
            Toast.makeText(this, R.string.connexionEchouee, Toast.LENGTH_SHORT).show();
        }


    }
}