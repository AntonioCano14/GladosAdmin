package com.example.gladosadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLogin extends AppCompatActivity {

    private EditText emailAdmin, passwordAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        emailAdmin = findViewById(R.id.emailAdmin);
        passwordAdmin = findViewById(R.id.passwordAdmin);
    }

    public void iniciarSesion(View view) {
        String username = emailAdmin.getText().toString().trim();
        String password = passwordAdmin.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tus credenciales", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.login(new AdminUser(username, password));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        // Agregar log para ver el jsonResponse
                        Log.d("AdminLogin", "jsonResponse: " + jsonResponse);

                        if (jsonResponse.contains("nombre") && jsonResponse.contains("id")) {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            String userName = jsonObject.getString("nombre");
                            int userId = jsonObject.getInt("id");

                            // Agregar log para verificar los datos antes de guardarlos
                            Log.d("AdminLogin", "Nombre: " + userName + ", ID: " + userId);

                            guardarSesion(userId, userName); // Guardar la sesión
                            Toast.makeText(AdminLogin.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminLogin.this, Seleccion.class));
                        } else {
                            Toast.makeText(AdminLogin.this, jsonResponse, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AdminLogin.this, "Error en el servidor: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AdminLogin.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarSesion(int userId, String userName) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);  // Establece que el usuario está logueado
        editor.putInt("id_user", userId);      // Guarda el ID del usuario
        editor.putString("nombre_user", userName);  // Guarda el nombre del usuario
        editor.apply();

        // Agregar log para verificar que se guardan los datos correctamente
        Log.d("AdminLogin", "Datos guardados: id_user=" + userId + ", nombre_user=" + userName);
    }


}
