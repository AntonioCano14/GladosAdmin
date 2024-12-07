package com.example.gladosadmin.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gladosadmin.ApiService;
import com.example.gladosadmin.Consejo;
import com.example.gladosadmin.R;
import com.example.gladosadmin.RetrofitClient;
import com.example.gladosadmin.Seleccion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarConsejoActivity extends AppCompatActivity {

    private EditText editDescripcion, editTipo;
    private Button btnGuardarCambios;
    private int consejoId, adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_consejo);

        // Inicializar vistas
        editDescripcion = findViewById(R.id.editDescripcion);
        editTipo = findViewById(R.id.editTipo);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        // Obtener datos pasados en el Intent
        Intent intent = getIntent();
        consejoId = intent.getIntExtra("consejoId", -1);
        adminId = getSharedPreferences("UserSession", MODE_PRIVATE).getInt("id_user", -1);
        String descripcion = intent.getStringExtra("descripcion");
        String tipo = intent.getStringExtra("tipo");

        // Establecer valores iniciales
        if (descripcion != null) editDescripcion.setText(descripcion);
        if (tipo != null) editTipo.setText(tipo);

        // Configurar el botón para guardar los cambios
        btnGuardarCambios.setOnClickListener(v -> actualizarConsejo());
    }

    private void actualizarConsejo() {
        String descripcion = editDescripcion.getText().toString().trim();
        String tipo = editTipo.getText().toString().trim();

        if (descripcion.isEmpty() || tipo.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto Consejo con los datos actualizados
        Consejo consejoActualizado = new Consejo();
        consejoActualizado.setDescripcionConsejo(descripcion);
        consejoActualizado.setTipoConsejo(tipo);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.actualizarConsejo(consejoId, consejoActualizado, adminId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarConsejoActivity.this, "Consejo actualizado exitosamente", Toast.LENGTH_SHORT).show();

                    // Redirigir al fragmento de consejos
                    Intent intent = new Intent(EditarConsejoActivity.this, Seleccion.class);
                    intent.putExtra("fragment", "consejos");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditarConsejoActivity.this, "Error al actualizar consejo: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditarConsejoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
