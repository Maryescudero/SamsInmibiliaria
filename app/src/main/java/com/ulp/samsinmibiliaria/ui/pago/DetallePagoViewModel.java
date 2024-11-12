package com.ulp.samsinmibiliaria.ui.pago;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.modelo.Pago;

public class DetallePagoViewModel extends AndroidViewModel {

    private MutableLiveData<Pago> pagoMutableLiveData;
    private Context context;
    public DetallePagoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Pago> getPagoMutableLiveData() {
        if(pagoMutableLiveData ==null){
            pagoMutableLiveData=new MutableLiveData<>();
        }
        return pagoMutableLiveData;
    }
    public void recuperarPago(Bundle bundle){
        Pago pago = (Pago) bundle.get("pago");
        pagoMutableLiveData.postValue(pago);
    }
}
