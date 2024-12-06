package com.example.gladosadmin.ui.home;

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
import com.example.gladosadmin.RetrofitClient;
import com.example.gladosadmin.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosFragment extends Fragment {

    private RecyclerView recyclerViewUsuarios;
    private UsuarioAdapter usuarioAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);

        recyclerViewUsuarios = root.findViewById(R.id.recyclerViewUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarUsuarios();

        return root;
    }

    private void cargarUsuarios() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<User>> call = apiService.getAllUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> usuarios = response.body();
                    usuarioAdapter = new UsuarioAdapter(usuarios);
                    recyclerViewUsuarios.setAdapter(usuarioAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Manejo de errores
                t.printStackTrace();
            }
        });
    }
}
