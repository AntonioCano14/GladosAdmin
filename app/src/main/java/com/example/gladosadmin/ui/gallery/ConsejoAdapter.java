package com.example.gladosadmin.ui.gallery;

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
import com.example.gladosadmin.R;
import com.example.gladosadmin.Consejo;
import com.example.gladosadmin.RetrofitClient;
import com.example.gladosadmin.Seleccion;

import java.text.BreakIterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder> {

    private SparseBooleanArray expandState = new SparseBooleanArray();
    private List<Consejo> consejos;

    public ConsejoAdapter(List<Consejo> consejos) {
        this.consejos = consejos;
    }

    @NonNull
    @Override
    public ConsejoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_consejo, parent, false);
        return new ConsejoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejoViewHolder holder, int position) {
        Consejo consejo = consejos.get(position);

        // Asignar valores a las vistas
        holder.textIdConsejo.setText("ID Consejo: " + consejo.getIdConsejo());
        holder.textDescripcion.setText(consejo.getDescripcionConsejo());
        holder.textTipo.setText("Tipo: " + consejo.getTipoConsejo());

        // Manejo de expansión y colapso
        boolean isExpanded = expandState.get(position);
        holder.layoutExpandible.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            expandState.put(position, !isExpanded);
            notifyItemChanged(position);
        });

        // Lógica de botones
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditarConsejoActivity.class);
            intent.putExtra("consejoId", consejo.getIdConsejo());
            intent.putExtra("descripcion", consejo.getDescripcionConsejo());
            intent.putExtra("tipo", consejo.getTipoConsejo());
            v.getContext().startActivity(intent);
        });


        holder.btnEliminar.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            int adminId = v.getContext()
                    .getSharedPreferences("UserSession", v.getContext().MODE_PRIVATE)
                    .getInt("id_user", -1);

            if (adminId == -1) {
                Toast.makeText(v.getContext(), "Error: No se pudo obtener el ID del administrador", Toast.LENGTH_SHORT).show();
                return;
            }

            Call<ResponseBody> call = apiService.eliminarConsejo(consejo.getIdConsejo(), adminId);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Consejo eliminado exitosamente", Toast.LENGTH_SHORT).show();

                        // Recargar el fragmento de consejos
                        Intent intent = new Intent(v.getContext(), Seleccion.class);
                        intent.putExtra("fragment", "consejos");
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "Error al eliminar consejo: " + response.message(), Toast.LENGTH_SHORT).show();
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
        return consejos.size();
    }

    public static class ConsejoViewHolder extends RecyclerView.ViewHolder {
        TextView textIdConsejo, textDescripcion, textTipo;
        View layoutExpandible;
        Button btnEditar, btnEliminar;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);
            textIdConsejo = itemView.findViewById(R.id.textIdConsejo);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            textTipo = itemView.findViewById(R.id.textTipo);
            layoutExpandible = itemView.findViewById(R.id.layoutExpandible);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

}
