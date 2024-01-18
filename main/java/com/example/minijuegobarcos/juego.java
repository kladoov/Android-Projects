package com.example.minijuegobarcos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class juego extends AppCompatActivity {

    private int[][] matrix = new int[3][3];
    int intentos = 5;
    int aciertos = 0;
    int explosiones = 0;
    int puntos = 0;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);

        //musica
        mediaPlayer = MediaPlayer.create(this, R.raw.musica);
        mediaPlayer.start();

        //recoge el nombre de usuario de la anterior actividad
        Intent intent = getIntent();
        String nombreUsuario = intent.getStringExtra("Username");
        TextView usuario = findViewById(R.id.username);
        usuario.setText("Bienvenido " + nombreUsuario);

        posicionBarco();
        //almaceno los ids de los botones en un array
        int[] buttonIds = {
                R.id.botonCenter, R.id.botonBottom, R.id.botonTop,
                R.id.botonLeft, R.id.botonRight, R.id.botonBottomRight,
                R.id.botonTopRight, R.id.botonTopLeft, R.id.botonBottomLeft
        };

        for (int i = 0; i < buttonIds.length; i++) {
            final Button btn = findViewById(buttonIds[i]);
            final int row = i / 3;
            final int col = i % 3;

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comprobar(btn, row, col);
                }
            });
        }
    }

    //posición de los barcos aleatoria, para que cada partida no se repitan la posicion
    private void posicionBarco() {
        Random rnd = new Random();
        int n1 = rnd.nextInt(2);
        int n2 = rnd.nextInt(2);
        int n3 = rnd.nextInt(2);
        int n4 = rnd.nextInt(2);
        int n5 = rnd.nextInt(2);
        int n6 = rnd.nextInt(2);

        matrix[n1][n4] = 1;
        matrix[n2][n5] = 1;
        matrix[n3][n6] = 1;
    }

    //jugabilidad
    private void comprobar(Button btn, int row, int col) {
        TextView barco = findViewById(R.id.barco);
        TextView explosion = findViewById(R.id.explosion);
        TextView cont = findViewById(R.id.intentos);
        TextView txtPuntos = findViewById(R.id.puntos);

        if(intentos > 0) {
            if (matrix[row][col] == 1) {
                btn.setEnabled(false);
                btn.setBackgroundResource(R.drawable.barco);
                aciertos = aciertos + 1;
                barco.setText(String.valueOf(aciertos));

                //si llega a 3 aciertos ganas
                if(aciertos==3) {
                    deshabilitarCampos();
                    showToast("¡HAS GANADO!");
                    puntos = puntos + 50;
                    txtPuntos.setText(String.valueOf("Puntos: "+puntos));
                }

            } else {
                btn.setEnabled(false);
                btn.setBackgroundResource(R.drawable.explosion);
                intentos = intentos - 1;
                explosiones++;
                explosion.setText(String.valueOf(explosiones));
                cont.setText(String.valueOf("Tienes " + intentos + " intentos"));

                //si te has quedado sin intentos desaparecen las casillas
                if(intentos == 0) {
                    deshabilitarCampos();
                    cont.setText("Tienes 0 intentos");
                    showToast("¡HAS PERDIDO!");
                    puntos = puntos - 10;
                    txtPuntos.setText(String.valueOf("Puntos: "+puntos));
                }
            }
        }
    }

    //metodo para que salga una notificacion por pantalla
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //metodo que reinicia el juego
    public void reiniciarJuego(View view) {
        intentos = 5;
        aciertos = 0;
        explosiones = 0;

        TextView cont = findViewById(R.id.intentos);
        cont.setText("Tienes 5 intentos");

        TextView barco = findViewById(R.id.barco);
        barco.setText("0");
        TextView explosion = findViewById(R.id.explosion);
        explosion.setText("0");

        //resetea toda la matrix a 0 para que no hayan barcos
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = 0;
            }
        }

        //nuevo matrix
        Random rnd = new Random();
        int n1 = rnd.nextInt(2);
        int n2 = rnd.nextInt(2);
        int n3 = rnd.nextInt(2);
        int n4 = rnd.nextInt(2);
        int n5 = rnd.nextInt(2);
        int n6 = rnd.nextInt(2);
        matrix[n1][n4] = 1;
        matrix[n2][n5] = 1;
        matrix[n3][n6] = 1;

        //vuelve a mostrar todos los botones
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            View child = frameLayout.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setVisibility(View.VISIBLE);
                button.setBackgroundColor(Color.WHITE);
                button.setEnabled(true);

                //boton de reiniciar y finalizar con su estilo correspondiente al .xml
                Button reiniciar = findViewById(R.id.reiniciar);
                GradientDrawable gradientReiniciar = new GradientDrawable();
                gradientReiniciar.setShape(GradientDrawable.RECTANGLE);
                gradientReiniciar.setCornerRadii(new float[]{50, 50, 50, 50, 50, 50, 50, 50});
                gradientReiniciar.setColor(0xFF324D6F);
                gradientReiniciar.setStroke(4, 0xFFFFFFFF);
                reiniciar.setBackground(gradientReiniciar);

                Button finalizar = findViewById(R.id.finalizar);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadii(new float[]{50, 50, 50, 50, 50, 50, 50, 50});
                gradientDrawable.setColor(0xFFCC6161);
                gradientDrawable.setStroke(4, 0xFFFFFFFF);
                finalizar.setBackground(gradientDrawable);
            }
        }
        showToast("Juego reiniciado con nuevos barcos");
    }

    //metodo cuando los intentos son nulos desaparezcan todos los botones
    private void deshabilitarCampos() {
        Button boton1 = findViewById(R.id.botonCenter);
        Button boton2 = findViewById(R.id.botonBottom);
        Button boton3 = findViewById(R.id.botonTop);
        Button boton4 = findViewById(R.id.botonLeft);
        Button boton5 = findViewById(R.id.botonRight);
        Button boton6 = findViewById(R.id.botonBottomRight);
        Button boton7 = findViewById(R.id.botonTopRight);
        Button boton8 = findViewById(R.id.botonTopLeft);
        Button boton9 = findViewById(R.id.botonBottomLeft);

        boton1.setEnabled(false);
        boton2.setEnabled(false);
        boton3.setEnabled(false);
        boton4.setEnabled(false);
        boton5.setEnabled(false);
        boton6.setEnabled(false);
        boton7.setEnabled(false);
        boton8.setEnabled(false);
        boton9.setEnabled(false);
    }

    //metodo para finalizar el juego
    public void volver(View view) {
        Intent volver = new Intent(this, pantalla_inicio.class);
        startActivity(volver);

        showToast("Has conseguido " + puntos + " puntos.");

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
