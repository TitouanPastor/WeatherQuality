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

            Button btn_valider = findViewById(R.id.dialog_positive_button);
            Button btn_annuler = findViewById(R.id.dialog_negative_button);

            btn_valider.setOnClickListener(v -> {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            });
            btn_annuler.setOnClickListener(v -> {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            });


        }
}
