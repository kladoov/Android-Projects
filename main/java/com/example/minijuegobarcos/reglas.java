package com.example.minijuegobarcos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class reglas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reglas);
    }

    public void volver(View view) {
        Intent volver = new Intent(this, pantalla_inicio.class);
        startActivity(volver);
    }
}
