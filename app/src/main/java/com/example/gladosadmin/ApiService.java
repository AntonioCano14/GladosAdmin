package com.example.gladosadmin;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/auth/login")
    Call<ResponseBody> login(@Body AdminUser adminUser);

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/consejos")
    Call<List<Consejo>> getAllConsejos();
}

