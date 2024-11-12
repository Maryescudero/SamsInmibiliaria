package com.ulp.samsinmibiliaria.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.MainActivity;
import com.ulp.samsinmibiliaria.modelo.LoginModel;
import com.ulp.samsinmibiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivityViewModel extends AndroidViewModel {
    private Context contexto;
    private MutableLiveData<String> mensajeError;
    private MutableLiveData<Response<LoginModel>> loginResponse;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
        loginResponse = new MutableLiveData<>();
    }

    // LiveData para la respuesta del login
    public MutableLiveData<Response<LoginModel>> getLoginResponse() {
        return loginResponse;
    }

    // LiveData para mensajes de error
    public MutableLiveData<String> getMensajeError() {
        if (mensajeError == null) {
            mensajeError = new MutableLiveData<>();
        }
        return mensajeError;
    }

    public void login(String email, String password) {
        LoginModel propietario = new LoginModel(email, password);
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getInmobiliaria();
        Call<LoginModel> llamada = endpoint.login(propietario);

        llamada.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    guardarDatosPropietario(email, password);
                    LoginModel respuestaLogin = response.body();
                    String token = respuestaLogin.getTokenGenerado();
                    propietario.setToken(token);
                    guardarToken(token);

                    // Actualizamos el LiveData con la respuesta exitosa
                    loginResponse.setValue(response);
                    Log.d("LoginSuccess", "Token recibido: " + token);
                } else {
                    mensajeError.setValue("Inicio de sesión fallido: " + response.message());
                    loginResponse.setValue(response);  // También actualizamos la respuesta en caso de error
                    Log.e("LoginError", "Código de respuesta: " + response.code() + ", Mensaje: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("LoginFailure", "Error de conexión: ", t);
                mensajeError.setValue("No se pudo conectar al servidor. Por favor, verifica tu conexión.");
                loginResponse.setValue(null);  // Establecemos null si hay un fallo
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences sp = contexto.getSharedPreferences("tokenInmobiliaria", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenAcceso", token);
        editor.apply();
    }

    private void guardarDatosPropietario(String email, String password) {
        SharedPreferences sp = contexto.getSharedPreferences("datosPropietario", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("emailPropietario", email);
        editor.putString("passwordPropietario", password);
        editor.apply();
    }
}