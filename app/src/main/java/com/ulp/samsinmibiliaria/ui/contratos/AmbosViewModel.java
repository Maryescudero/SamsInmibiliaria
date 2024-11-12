package com.ulp.samsinmibiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.modelo.Contrato;

public class AmbosViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> contrato;
    public AmbosViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Contrato> getContrato() {
        if(contrato==null)contrato=new MutableLiveData<>();
        return contrato;
    }

    public void setContrato(Contrato contra) {
        Log.d("Salida", "setContrato: "+contra);
        contrato.setValue(contra);
    }

    public void recuperarContrato(Bundle bundle){
        Contrato contra = (Contrato) bundle.get("contrato");
        contrato.setValue(contra);
    }
}
