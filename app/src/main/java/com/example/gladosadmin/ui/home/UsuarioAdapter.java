package com.example.gladosadmin.ui.home;

import android.annotation.SuppressLint;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gladosadmin.R;
import com.example.gladosadmin.User;
import java.util.List;

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
            // Lógica para editar el usuario
        });

        holder.btnEliminar.setOnClickListener(v -> {
            // Lógica para eliminar el usuario
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