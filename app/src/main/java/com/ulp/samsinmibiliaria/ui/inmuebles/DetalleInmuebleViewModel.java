package com.ulp.samsinmibiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.modelo.Inmueble;
import com.ulp.samsinmibiliaria.request.ApiClient;
import com.ulp.samsinmibiliaria.servicios.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<Boolean> camposEditablesLiveData;
    private MutableLiveData<String> textoBotonLiveData;
    private MutableLiveData<Boolean> clickBtn;
    private boolean editable;
    private CustomToast customToast;
    private Context context;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        editable = false;
    }

    public MutableLiveData<Boolean> getClickBtn(){
        if(clickBtn==null){
            clickBtn=new MutableLiveData<>();
        }
        return clickBtn;
    }
    public MutableLiveData<Inmueble> getInmuebleMutableLiveData(){
        if(inmuebleMutableLiveData==null){
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }
    public LiveData<String> getTextoBotonLiveData() {
        if (textoBotonLiveData == null) {
            textoBotonLiveData = new MutableLiveData<>();
            textoBotonLiveData.setValue("Editar");
        }
        return textoBotonLiveData;
    }
    public void cambiarTextoBoton() {
        if (editable) {
            textoBotonLiveData.setValue("Guardar");
        } else {
            textoBotonLiveData.setValue("Editar");
        }
    }
    public LiveData<Boolean> getCamposEditablesLiveData() {
        if (camposEditablesLiveData == null) {
            camposEditablesLiveData = new MutableLiveData<>();
            camposEditablesLiveData.setValue(editable);
        }
        return camposEditablesLiveData;
    }
    public void habilitarCampos() {
        editable = !editable;
        camposEditablesLiveData.setValue(editable);
    }
    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.get("inmueble");
//        Log.d("salida", "recuperarInmueble: "+inmueble.toString());
        inmuebleMutableLiveData.setValue(inmueble);
    }
    public void habilitarInmueble(Bundle bundle) {
        Inmueble inmuebleRecuperado = (Inmueble) bundle.get("inmueble");
//        Log.d("salida", "habilitarInmueble: "+inmuebleRecuperado.toString());
        String token = recuperarToken();
        if (token != null) {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getInmobiliaria();
            Call<Inmueble> call = endpoint.habilitarInmueble(token, inmuebleRecuperado.getId());
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    inmuebleMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse habilitado: "+response.body());
                    customToast.mostrarMensaje(context,"Su inmueble se actualizó correctamente");
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    customToast.mostrarMensaje(context,"Ocurrio un error al actualizar el inmueble:" +t.getMessage());
                }
            });
        } else {
            customToast.mostrarMensaje(context,"Token vencido, por favor inicie sesión nuevamente");
        }
    }
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }

}