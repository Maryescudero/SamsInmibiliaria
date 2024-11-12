package com.ulp.samsinmibiliaria;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.samsinmibiliaria.login.LoginActivity;
import com.ulp.samsinmibiliaria.servicios.Dialogo;
import com.ulp.samsinmibiliaria.servicios.CustomToast;
import com.ulp.samsinmibiliaria.ui.perfil.PerfilViewModel;

public class MainActivityViewModel extends AndroidViewModel implements Dialogo.CambioContraseñaListener{

    private int OPCION_CONTRASEÑA = R.id.action_settings;
    private MutableLiveData<Integer> _menuItemSelection;
    private LiveData<Integer> menuItemSelectionLiveData;
    private MutableLiveData<Integer> menuAction;
    private Context context;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context=getApplication().getApplicationContext();
    }

    public LiveData<Integer> getMenuItemSelectionLiveData() {
        if(menuItemSelectionLiveData==null)menuItemSelectionLiveData=new MutableLiveData<>();
        return menuItemSelectionLiveData;
    }
    public void onMenuItemSelected(int itemId) {
        if(_menuItemSelection==null)_menuItemSelection=new MutableLiveData<>();
        _menuItemSelection.postValue(itemId);
    }
    public LiveData<Integer> getMenuActionLiveData() {
        if(menuAction==null)menuAction=new MutableLiveData<>();
        return menuAction;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void cerrarSesion(){
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenAcceso","");
        editor.commit();
    }
    public void handleMenuAction(int actionId) {
        if (actionId == OPCION_CONTRASEÑA) {
            if (context instanceof Activity && !((Activity) context).isFinishing()) {
                Dialogo.mostrarDialogoConEntrada(context, "Cambio de contraseña", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.mostrarMensaje(context, "Cambiando contraseña");
                    }
                }, this);
            }
        }

    }
    @Override
    public void onAceptar(String nuevaContraseña) {
        PerfilViewModel.cambiarPassword(nuevaContraseña);
        cerrarSesion();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
