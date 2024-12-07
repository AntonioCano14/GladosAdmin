package com.example.gladosadmin.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gladosadmin.ApiService;
import com.example.gladosadmin.EditarUsuarioActivity;
import com.example.gladosadmin.R;
import com.example.gladosadmin.RetrofitClient;
import com.example.gladosadmin.Seleccion;
import com.example.gladosadmin.User;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<User> usuarios;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public UsuarioAdapter(List<User> usuarios) {
        this.usuarios = usuarios;
        for (int i = 0; i < usuarios.size(); i++) {
            expandState.put(i, false); // Inicialmente todos los elementos están colapsados
        }
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        User usuario = usuarios.get(position);

        holder.textNombre.setText(usuario.getNombre_user());
        holder.textCorreo.setText("Correo: " + (usuario.getCorreoUser() != null ? usuario.getCorreoUser() : "No disponible"));
        holder.textPassword.setText("Contraseña: " + (usuario.getPassword_user() != null ? usuario.getPassword_user() : "No disponible"));
        holder.textID.setText("ID Usuario: " + (usuario.getID_user() != 0 ? usuario.getID_user() : "No disponible"));
        holder.textFechaRegistro.setText("Fecha de Registro: " + (usuario.getFecha_registro() != null ? usuario.getFecha_registro().toString() : "No disponible"));
        holder.textUltimaSesion.setText("Última Sesión: " + (usuario.getUltima_sesion() != null ? usuario.getUltima_sesion().toString() : "No disponible"));
        holder.textRedSocial.setText("Red Social ID: " + (usuario.getRedSocial_ID_Social() != 0 ? usuario.getRedSocial_ID_Social() : "No disponible"));

        // Manejar la visibilidad del layout expandible
        boolean isExpanded = expandState.get(position);
        holder.layoutExpandible.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Manejar clic para expandir/colapsar
        holder.itemView.setOnClickListener(v -> {
            expandState.put(position, !isExpanded);
            notifyItemChanged(position);
        });

        // Botones
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditarUsuarioActivity.class);
            intent.putExtra("userId", usuario.getID_user());
            intent.putExtra("nombre", usuario.getNombre_user());
            intent.putExtra("correo", usuario.getCorreoUser());
            intent.putExtra("password", usuario.getPassword_user());
            v.getContext().startActivity(intent);
        });


        holder.btnEliminar.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            int userId = usuario.getID_user();
            int adminId = v.getContext()
                    .getSharedPreferences("UserSession", v.getContext().MODE_PRIVATE)
                    .getInt("id_user", -1);

            if (adminId == -1) {
                Toast.makeText(v.getContext(), "Error: No se pudo obtener el ID del administrador", Toast.LENGTH_SHORT).show();
                return;
            }

            Call<ResponseBody> call = apiService.eliminarUsuario(userId, adminId);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show();
                        // Redirigir al usuario a la pantalla de selección
                        Intent intent = new Intent(v.getContext(), Seleccion.class);
                        intent.putExtra("fragment", "usuarios"); // Identificar el fragmento al que se debe volver
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "Error al eliminar usuario: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(v.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }


    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCorreo, textPassword, textFechaRegistro, textUltimaSesion, textRedSocial, textID;
        View layoutExpandible;
        Button btnEditar, btnEliminar;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textCorreo = itemView.findViewById(R.id.textCorreo);
            textPassword = itemView.findViewById(R.id.textPassword);
            textFechaRegistro = itemView.findViewById(R.id.textFechaRegistro);
            textUltimaSesion = itemView.findViewById(R.id.textUltimaSesion);
            textRedSocial = itemView.findViewById(R.id.textRedSocial);
            textID = itemView.findViewById(R.id.textID);
            layoutExpandible = itemView.findViewById(R.id.layoutExpandible);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}