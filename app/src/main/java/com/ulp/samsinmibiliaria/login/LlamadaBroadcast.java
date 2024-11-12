package com.ulp.samsinmibiliaria.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.content.ContextCompat;

import com.ulp.samsinmibiliaria.servicios.CustomToast;

public class LlamadaBroadcast extends BroadcastReceiver {
    public static boolean appIniciada = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean deteccionAgitado = intent.getBooleanExtra("deteccion_agitado", false);
        if (deteccionAgitado) {
            realizarLlamada(context);
        }
        appIniciada = true;
    }
    private void realizarLlamada(Context context) {
        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:2664553747"));// llamada a la inmobiliaria
        intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intentLlamada);
        } else {
            CustomToast.mostrarMensaje(context, "Permiso denegado para realizar llamadas telefónicas");
        }
    }
}
