package com.example.gladosadmin;

import android.os.Bundle;
import android.view.View;
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

public class CrearUsuarioActivity extends AppCompatActivity {

    private EditText editNombre, editCorreo, editPassword;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        // Inicializar vistas
        editNombre = findViewById(R.id.editNombre);
        editCorreo = findViewById(R.id.editCorreo);
        editPassword = findViewById(R.id.editPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Manejar clic del botón Registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = editNombre.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto User
        User nuevoUsuario = new User();
        nuevoUsuario.setNombre_user(nombre);
        nuevoUsuario.setCorreoUser(correo);
        nuevoUsuario.setPassword_user(password);

        // Llamar al endpoint de registro
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.registrarUsuario(nuevoUsuario);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CrearUsuarioActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar actividad después de registrar
                } else {
                    Toast.makeText(CrearUsuarioActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CrearUsuarioActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
