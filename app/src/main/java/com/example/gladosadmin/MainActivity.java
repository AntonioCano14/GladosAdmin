package com.example.gladosadmin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Animación del logo
        ImageView logoImageView = findViewById(R.id.imageViewLogo);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        logoImageView.startAnimation(scaleAnimation);

        int SPLASH_DISPLAY_LENGTH = 3000; // Duración de la animación: 3 segundos

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Redirigir directamente a AdminLogin
                Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
