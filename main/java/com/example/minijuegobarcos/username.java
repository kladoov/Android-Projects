package com.example.minijuegobarcos;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class username extends AppCompatActivity {

    private EditText username;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username);

        username = findViewById(R.id.username);

        ImageView nubeImageView = findViewById(R.id.nubeMovimiento);
        Animation animacionNube = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        animacionNube.setDuration(5000);
        animacionNube.setRepeatCount(Animation.INFINITE);
        nubeImageView.startAnimation(animacionNube);
    }

    public void saludar(View view) {
        ImageView img = findViewById(R.id.pinguinoSaludo);
        Animation animationPinguino = AnimationUtils.loadAnimation(this, R.anim.animation_pinguino);
        img.startAnimation(animationPinguino);

        //musica
        mediaPlayer = MediaPlayer.create(this, R.raw.boing);
        mediaPlayer.start();
    }
    public void volver(View view) {
        Intent volver = new Intent(this, pantalla_inicio.class);
        startActivity(volver);
    }

    //metodo para que salga una notificacion por pantalla
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void comenzar(View view) {
        Intent comenzar = new Intent(this, juego.class);
        String campo = username.getText().toString();
        comenzar.putExtra("Username", campo);

        if(TextUtils.isEmpty(campo)) {
            showToast("Debes introducir un nombre.");
        } else {
            startActivity(comenzar);
        }
    }
}
