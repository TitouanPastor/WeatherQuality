package com.example.weathearquality;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

public class HistoriqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        //Création de la base de données si elle existe pas
        ClientDbHelper bdd = new ClientDbHelper(this);

        //Récupération de l'utilisateur
        Intent intent = getIntent();
        String utilisateur = intent.getStringExtra("utilisateur");

        //Création de la liste d'historique
        ListView listView = findViewById(R.id.historiqueListView);
        ArrayList<String> arrayListVille = bdd.getVille(utilisateur);
        ArrayList<String> arrayListDate = bdd.getDate(utilisateur);
        AdapterHistoriqueList adapterHistoriqueList = new AdapterHistoriqueList(this, R.layout.historique_list_view, bdd.getVille(utilisateur), bdd.getDate(utilisateur));
        listView.setAdapter(adapterHistoriqueList);

        //en cliquant sur un item, on va sur la page des résultats avec la ville
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent1 = new Intent(HistoriqueActivity.this, ResultatsActivity.class);
            intent1.putExtra("ville", arrayListVille.get(position));
            intent1.putExtra("utilisateur", utilisateur);
            startActivity(intent1);
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
                Toast.makeText(this, "Vous êtes déjà sur l'historique", Toast.LENGTH_SHORT).show();
                break;

            case R.id.deconnectMenu:
                Intent intent = new Intent(HistoriqueActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.quitMenu:
                System.exit(0);
                break;
        }
        return true;
    }
}
