package com.ulp.samsinmibiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.modelo.Inmueble;
import com.ulp.samsinmibiliaria.request.ApiClient;
import com.ulp.samsinmibiliaria.servicios.CustomToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmueblesLiveData;
    private CustomToast toast;
    private Context context;
    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        context =application.getApplicationContext();
    }

    public MutableLiveData<List<Inmueble>> getInmueblesLiveData() {
        if (inmueblesLiveData == null) {
            inmueblesLiveData = new MutableLiveData<>();
        }
        return inmueblesLiveData;
    }


    public void cargarInmuebles() {
        String token = recuperarToken();
        if (token != null) {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getInmobiliaria();
            Call<List<Inmueble>> inmueble = endpoint.getInmuebles(token);
            inmueble.enqueue(new Callback<List<Inmueble>>() {
                @Override

                public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                    if (response.isSuccessful()) {
                        List<Inmueble> inmuebleList = response.body();
                        for (Inmueble inmueble : inmuebleList) {
                            Log.d("InmuebleAvatarURL", "URL de imagen: " + inmueble.getAvatarUrl());
                        }
                        inmueblesLiveData.setValue(inmuebleList);
                    } else {
                        toast.mostrarMensaje(context, "Ocurrió un error al consultar sus Inmuebles: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                    toast.mostrarMensaje(context,"Ocurrió un error al consultar sus Inmuebles " + t.getMessage());
                }
            });
        } else {
            toast.mostrarMensaje(context,"Token vencido, por favor inicie sesión nuevamente");
        }
    }
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }

}