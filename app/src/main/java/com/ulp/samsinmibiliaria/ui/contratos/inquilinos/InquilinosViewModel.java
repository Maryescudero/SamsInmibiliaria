package com.ulp.samsinmibiliaria.ui.contratos.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.modelo.Contrato;
import com.ulp.samsinmibiliaria.modelo.Inquilino;
import com.ulp.samsinmibiliaria.servicios.CustomToast;

public class InquilinosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inquilino> inquilinos;
    private CustomToast toast;
    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Inquilino> getInquilinos() {
        if(inquilinos==null){
            inquilinos=new MutableLiveData<>();
        }
        return inquilinos;
    }

    public void recuperarInquilino(Bundle bundle){
        Contrato contrato = (Contrato) bundle.get("contrato");
        inquilinos.postValue(contrato.getInquilino());
    }
}
