package com.example.gladosadmin;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/auth/login")
    Call<ResponseBody> login(@Body AdminUser adminUser);

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/consejos")
    Call<List<Consejo>> getAllConsejos();

    @PUT("/api/users/{id}")
    Call<ResponseBody> actualizarUsuario(
            @Path("id") int id,
            @Body User usuario,
            @Query("adminId") int adminId
    );


}

