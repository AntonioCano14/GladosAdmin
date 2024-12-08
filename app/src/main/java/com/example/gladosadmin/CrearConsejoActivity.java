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

public class CrearConsejoActivity extends AppCompatActivity {

    private EditText editDescripcion, editTipo;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_consejo);

        // Inicializar vistas
        editDescripcion = findViewById(R.id.createDescripcion);
        editTipo = findViewById(R.id.createTipo);
        btnRegistrar = findViewById(R.id.btnRegistrarConsejo);

        // Manejar clic del botón Registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarConsejo();
            }
        });
    }

    private void registrarConsejo() {
        String descripcion = editDescripcion.getText().toString().trim();
        String tipo = editTipo.getText().toString().trim();

        if (descripcion.isEmpty() || tipo.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto Consejo
        Consejo nuevoConsejo = new Consejo();
        nuevoConsejo.setDescripcionConsejo(descripcion);
        nuevoConsejo.setTipoConsejo(tipo);

        // Llamar al endpoint de registro
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.registrarConsejo(nuevoConsejo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CrearConsejoActivity.this, "Consejo registrado exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad después de registrar
                } else {
                    Toast.makeText(CrearConsejoActivity.this, "Error al registrar consejo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CrearConsejoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
