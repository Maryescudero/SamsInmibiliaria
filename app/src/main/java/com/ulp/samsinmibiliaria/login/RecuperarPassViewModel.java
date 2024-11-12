package com.ulp.samsinmibiliaria.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarPassViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> msjError;
    private MutableLiveData<String> respuesta;
    public RecuperarPassViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<String> getMsjError() {
        if(msjError ==null){
            msjError=new MutableLiveData<>();
        }
        return msjError;
    }

    public MutableLiveData<String> getRespuesta() {
        if(respuesta==null) respuesta=new MutableLiveData<>();
        return respuesta;
    }

    public void recuperarPass(String email){
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getInmobiliaria();
        Call<String> mensajeServidor = endpoint.enviarMail(email);
        mensajeServidor.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    respuesta.postValue("Mensaje: "+response.body());
                } else {
                    msjError.postValue("Mensaje de error:"+response.message());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                msjError.postValue("Mensaje de error del servidor :"+t.getMessage());
            }
        });
    }
}
