package com.example.gladosadmin.ui.gallery;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gladosadmin.R;
import com.example.gladosadmin.Consejo;

import java.text.BreakIterator;
import java.util.List;

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
            // Lógica para editar el consejo
        });

        holder.btnEliminar.setOnClickListener(v -> {
            // Lógica para eliminar el consejo
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
