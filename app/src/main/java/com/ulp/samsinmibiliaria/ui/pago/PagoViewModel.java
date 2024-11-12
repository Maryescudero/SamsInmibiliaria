package com.ulp.samsinmibiliaria.ui.pago;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.modelo.Pago;
import com.ulp.samsinmibiliaria.request.ApiClient;
import com.ulp.samsinmibiliaria.servicios.CustomToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> pagos;
    //    private Bundle bundle;
    private Context context;

    public PagoViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }

    public MutableLiveData<List<Pago>> getPagos() {
        if(pagos==null){
            pagos = new MutableLiveData<>();
        }
        return pagos;
    }
    public void getListPagos(int id){
        Log.d("salida", "getListPagos: "+id);
        String token = recuperarToken();
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getInmobiliaria();
        Call<List<Pago>> lista= endpoint.pagosPorContrato(token,id);
        lista.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()){
                    pagos.postValue(response.body());

                }else{
                    if(response.message().equalsIgnoreCase("Not found")){
                        CustomToast.mostrarMensaje(context,"No hay Pagos registrados aun");
                    }else{
                        CustomToast.mostrarMensaje(context,"Ocurrio un error: "+response.message());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                CustomToast.mostrarMensaje(context,"Respuesta del servidor: "+t.getMessage());
            }
        });

    }

    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }


}
