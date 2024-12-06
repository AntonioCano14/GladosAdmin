package com.example.gladosadmin;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "http://34.56.155.250:8080/"; // URL de tu backend
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Configuración del interceptor para logs
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Nivel de logs detallado

            // Configuración de OkHttpClient con tiempos de espera
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // Agrega el interceptor para logs
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo máximo para establecer conexión
                    .readTimeout(30, TimeUnit.SECONDS)   // Tiempo máximo para leer datos
                    .writeTimeout(30, TimeUnit.SECONDS)  // Tiempo máximo para escribir datos
                    .build();

            // Configuración de Retrofit con OkHttpClient personalizado
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Usa el cliente configurado
                    .addConverterFactory(GsonConverterFactory.create()) // Conversor para JSON
                    .build();
        }
        return retrofit;
    }
}