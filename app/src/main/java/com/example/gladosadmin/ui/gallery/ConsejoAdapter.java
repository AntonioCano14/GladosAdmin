package com.example.gladosadmin.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gladosadmin.R;
import com.example.gladosadmin.Consejo;
import java.util.List;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder> {

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
        holder.textDescripcion.setText(consejo.getDescripcionConsejo());
        holder.textTipo.setText(consejo.getTipoConsejo());
    }

    @Override
    public int getItemCount() {
        return consejos.size();
    }

    public static class ConsejoViewHolder extends RecyclerView.ViewHolder {
        TextView textDescripcion, textTipo;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            textTipo = itemView.findViewById(R.id.textTipo);
        }
    }
}
