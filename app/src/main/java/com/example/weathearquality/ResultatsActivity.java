package com.example.weathearquality;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultatsActivity extends AppCompatActivity {
    private TextView tVille;
    private TextView tTemperature;
    private TextView tQualiteAir;

    String notePollution;

    public static final String TAG = "TAG";
    static final String BASE_URL = "https://api.waqi.info/feed/";
    Retrofit retrofit;
    WeatherAPI weatherAPI;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultats_activity);
        context = this;

        //Création de la base de données si elle existe pas
        ClientDbHelper bdd = new ClientDbHelper(this);
        SQLiteDatabase bd = bdd.getWritableDatabase();


        // Récupération des vues du formulaire
        tVille = findViewById(R.id.ville_resultats);
        tTemperature = findViewById(R.id.temperature_resultats);
        tQualiteAir = findViewById(R.id.qualite_air_resultats);

        // Récupération des données passées en paramètre
        Intent intent = getIntent();
        String ville = intent.getStringExtra("ville");
        boolean afficherTemperature = intent.getBooleanExtra("afficherTemperature", true);
        String utilisateur = intent.getStringExtra("utilisateur");

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

                    tTemperature.setText(temperature + " °C");

                    notePollution = jsonData.get("aqi").getAsString();
                    tQualiteAir.setText(notePollution);


                    View rectangle = findViewById(R.id.rectangle_resultat);
                    TextView rectangleText = findViewById(R.id.rectangle_text);
                    TextView implicationSante = findViewById(R.id.implicationSante);
                    TextView conseil = findViewById(R.id.conseil);

                    int note= Integer.parseInt(notePollution);
                    if (note < 50) {
                        rectangle.setBackgroundColor(ContextCompat.getColor( context, R.color.green));
                        rectangleText.setText(getString(R.string.QABon));
                        implicationSante.setText(implicationSante.getText() + getString(R.string.HIBon));
                        conseil.setText(conseil.getText() + getString(R.string.CSBon));

                    } else if (note < 100) {
                        rectangle.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
                        rectangleText.setText(getString(R.string.QAMoyen));
                        implicationSante.setText(implicationSante.getText() + getString(R.string.HIMoyen));
                        conseil.setText(conseil.getText() + getString(R.string.CSMoyen));

                    } else if (note < 150) {
                        rectangle.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                        rectangleText.setText(getString(R.string.QAMauvais));
                        implicationSante.setText(implicationSante.getText() + getString(R.string.HIMauvais));
                        conseil.setText(conseil.getText() + getString(R.string.CSMoyen));

                    } else if (note < 200) {
                        rectangle.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                        rectangleText.setText(getString(R.string.QATresMauvais));
                        implicationSante.setText(implicationSante.getText() + getString(R.string.HITresMauvais));
                        conseil.setText(conseil.getText() + getString(R.string.HIMoyen));

                    } else if (note < 300) {
                        rectangle.setBackgroundColor(ContextCompat.getColor(context, R.color.purple));
                        rectangleText.setText(getString(R.string.QADangereux));
                        implicationSante.setText(implicationSante.getText() + getString(R.string.CSDangereux));

                    } else {
                        rectangle.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                        rectangleText.setText(getString(R.string.QATresDangereux));
                        rectangleText.setTextColor(ContextCompat.getColor(context, R.color.white));
                        implicationSante.setText(implicationSante.getText() + getString(R.string.HITresDangereux));
                        conseil.setText(conseil.getText() + getString(R.string.CSDangereux));

                    }







                    //Insertion dans la base de données de la ville et de la date actuelle dans la table historique
                    //Insertion des valeurs dans la base de données
                    ContentValues valuesHistorique = new ContentValues();
                    valuesHistorique.put("utilisateur", utilisateur);
                    valuesHistorique.put("ville", ville);
                    //Insertion la date dans la forme mm/dd/yy hh:mm
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mm");
                    String formattedDate = formatter.format(date);
                    valuesHistorique.put("date", formattedDate);
                    bd.insert("historique", null, valuesHistorique);
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
            TextView lTemperature =  findViewById(R.id.temperature_label);
            lTemperature.setVisibility(View.GONE);
            tTemperature.setVisibility(View.GONE);
        }

        // Affichage rectangle en fonction du resultat de la qualite de l'air

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
                Intent intentHist = new Intent(ResultatsActivity.this, HistoriqueActivity.class);
                intentHist.putExtra("utilisateur", getIntent().getStringExtra("utilisateur"));
                startActivity(intentHist);
                break;

            case R.id.deconnectMenu:
                Intent intent = new Intent(ResultatsActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.quitMenu:
                System.exit(0);
                break;
        }
        return true;
    }
}
