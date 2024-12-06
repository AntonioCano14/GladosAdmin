package com.example.gladosadmin.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gladosadmin.ApiService;
import com.example.gladosadmin.R;
import com.example.gladosadmin.Consejo;
import com.example.gladosadmin.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsejosFragment extends Fragment {

    private RecyclerView recyclerViewConsejos;
    private ConsejoAdapter consejoAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_consejos, container, false);

        recyclerViewConsejos = root.findViewById(R.id.recyclerViewConsejos);
        recyclerViewConsejos.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarConsejos();

        return root;
    }

    private void cargarConsejos() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Consejo>> call = apiService.getAllConsejos();

        call.enqueue(new Callback<List<Consejo>>() {
            @Override
            public void onResponse(Call<List<Consejo>> call, Response<List<Consejo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Consejo> consejos = response.body();
                    consejoAdapter = new ConsejoAdapter(consejos);
                    recyclerViewConsejos.setAdapter(consejoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Consejo>> call, Throwable t) {
                // Manejo de errores
                t.printStackTrace();
            }
        });
    }
}
