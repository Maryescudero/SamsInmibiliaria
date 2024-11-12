package com.ulp.samsinmibiliaria.modelo;

import java.io.Serializable;

public class LoginModel implements Serializable {
    private String email;
    private String password;
    private String tokenGenerado ="";

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenGenerado() {
        return tokenGenerado;
    }

    public void setToken(String token) {
        this.tokenGenerado = token;
    }
}
