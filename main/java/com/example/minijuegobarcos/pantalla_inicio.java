package com.example.minijuegobarcos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class pantalla_inicio extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        TextView txt = findViewById(R.id.title);
        //Animation es la clase base para hacer animaciones
        //AnimationUtils es la clase que proporciona métodos de utilidad para cargar y aplicar animaciones desde recursos XML.
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);
        txt.startAnimation(scaleAnimation);

    }

    public void saltar(View view) {
        ImageView img = findViewById(R.id.pinguino);
        Animation animationPinguino = AnimationUtils.loadAnimation(this, R.anim.animation_pinguino);
        img.startAnimation(animationPinguino);

        //musica
        mediaPlayer = MediaPlayer.create(this, R.raw.boing);
        mediaPlayer.start();
    }

    public void iniciar(View view) {
        Intent iniciar = new Intent(this, username.class);
        startActivity(iniciar);
    }

    public void reglas(View view) {
        Intent reglas = new Intent(this, reglas.class);
        startActivity(reglas);
    }
}
