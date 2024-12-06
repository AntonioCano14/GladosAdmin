package com.example.gladosadmin;

import java.io.Serializable;
import java.util.Date;

// User.java (en el lado de Android)
public class User implements Serializable {

    private int ID_user;
    private String nombre_user;
    private String correoUser;
    private String password_user;
    private Date fecha_registro;
    private Date Ultima_sesion;
    private int RedSocial_ID_Social;

    // Getters y setters
    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    public String getNombre_user() {
        return nombre_user;
    }

    public void setNombre_user(String nombre_user) {
        this.nombre_user = nombre_user;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getUltima_sesion() {
        return Ultima_sesion;
    }

    public void setUltima_sesion(Date ultima_sesion) {
        this.Ultima_sesion = ultima_sesion;
    }

    public int getRedSocial_ID_Social() {
        return RedSocial_ID_Social;
    }

    public void setRedSocial_ID_Social(int redSocial_ID_Social) {
        this.RedSocial_ID_Social = redSocial_ID_Social;
    }
}

