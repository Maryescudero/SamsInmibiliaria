package com.ulp.samsinmibiliaria.ui.salida;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.ulp.samsinmibiliaria.MainActivity;

public class Dialogo {
    public static void mostrarDialogo(Context context) {
        new AlertDialog.Builder(context)
                .setMessage("¿Desea salir del explorador?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Agregué esto para que si se elegia la opcion "No" no quedara en una pantalla
                        /// vacia.
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .show();
    }
}
