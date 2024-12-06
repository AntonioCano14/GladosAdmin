package com.example.gladosadmin;

public class AdminUser {
    private int id;  // ID del administrador
    private String nombre;  // Nombre del administrador
    private String password;  // Contraseña del administrador

    // Constructor para crear un objeto AdminUser con nombre y password
    public AdminUser(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    // Constructor para la respuesta con ID y nombre
    public AdminUser(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
