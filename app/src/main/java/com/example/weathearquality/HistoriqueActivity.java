package com.example.weathearquality;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class HistoriqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
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
