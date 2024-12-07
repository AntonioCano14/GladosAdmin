package com.example.gladosadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gladosadmin.ApiService;
import com.example.gladosadmin.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText editNombre, editCorreo, editPassword;
    private Button btnGuardarCambios;
    private int userId;
    private int adminId; // ID del administrador desde SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Inicializar vistas
        editNombre = findViewById(R.id.editNombre);
        editCorreo = findViewById(R.id.editCorreo);
        editPassword = findViewById(R.id.editPassword);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        // Obtener datos del Intent
        userId = getIntent().getIntExtra("userId", -1);
        String nombre = getIntent().getStringExtra("nombre");
        String correo = getIntent().getStringExtra("correo");
        String password = getIntent().getStringExtra("password");

        // Establecer los valores en los campos
        editNombre.setText(nombre);
        editCorreo.setText(correo);
        editPassword.setText(password);

        // Obtener el ID del administrador desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        adminId = sharedPreferences.getInt("id_user", -1);

        // Configurar botón de guardar cambios
        btnGuardarCambios.setOnClickListener(v -> actualizarUsuario());
    }

    private void actualizarUsuario() {
        String nombre = editNombre.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        User usuarioActualizado = new User();
        usuarioActualizado.setNombre_user(nombre);
        usuarioActualizado.setCorreoUser(correo);
        usuarioActualizado.setPassword_user(password);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.actualizarUsuario(userId, usuarioActualizado, adminId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String message = response.body().string(); // Leer la respuesta como texto
                        Toast.makeText(EditarUsuarioActivity.this, message, Toast.LENGTH_SHORT).show();

                        // Regresar a la pantalla de usuarios
                        Intent intent = new Intent(EditarUsuarioActivity.this, Seleccion.class);
                        intent.putExtra("fragment", "usuarios"); // O cualquier identificador para el fragmento
                        startActivity(intent);
                        finish(); // Cierra la actividad y regresa a la lista de usuarios
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(EditarUsuarioActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditarUsuarioActivity.this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditarUsuarioActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
