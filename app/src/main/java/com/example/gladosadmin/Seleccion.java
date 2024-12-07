package com.example.gladosadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gladosadmin.databinding.ActivitySeleccionBinding;

public class Seleccion extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivitySeleccionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySeleccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar la barra de herramientas (toolbar)
        setSupportActionBar(binding.appBarSeleccion.toolbar);

        // Configurar el botón de acción flotante (opcional)
        binding.appBarSeleccion.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Acción personalizada aquí", Snackbar.LENGTH_LONG)
                        .setAction("Acción", null)
                        .setAnchorView(R.id.toolbar).show();
            }
        });

        // Configuración del DrawerLayout y NavigationView
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Cargar datos del administrador desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String adminName = sharedPreferences.getString("nombre_user", "Administrador");

        // Actualizar el header del Navigation Drawer
        View headerView = navigationView.getHeaderView(0);
        TextView headerSubtitle = headerView.findViewById(R.id.nav_header_subtitle);
        headerSubtitle.setText(adminName);  // Establecer el nombre del administrador

        // Configuración de destinos de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_usuarios, R.id.nav_consejos) // IDs de tus destinos
                .setOpenableLayout(drawer)
                .build();

        // Configurar el NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_seleccion);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Verificar si se debe redirigir a un fragmento específico
        Intent intent = getIntent();
        String fragmentToLoad = intent.getStringExtra("fragment");
        if (fragmentToLoad != null && fragmentToLoad.equals("usuarios")) {
            navController.navigate(R.id.nav_usuarios);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seleccion, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_seleccion);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}