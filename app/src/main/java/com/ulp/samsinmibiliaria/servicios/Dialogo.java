package com.ulp.samsinmibiliaria.servicios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.ulp.samsinmibiliaria.R;

public class Dialogo extends DialogFragment {

    //puedo reutilizar esta clase en un fragment pasando el contexto
//y tambien puedo reutilizarla en una activity
    public static void mostrarDialogoConfirmacion(Context contexto, String titulo, String mensaje, DialogInterface.OnClickListener listenerPositivo) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(contexto);
        constructorDialogo.setTitle(titulo);
        constructorDialogo.setMessage(mensaje);
        constructorDialogo.setPositiveButton("Sí", listenerPositivo);
        constructorDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }

    public static void mostrarDialogoConEntrada(Context contexto, String titulo, DialogInterface.OnClickListener listenerPositivo, CambioContraseñaListener cambioContraseñaListener) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(contexto);
        constructorDialogo.setTitle(titulo);
        View Dialogo = LayoutInflater.from(contexto).inflate(R.layout.dialogo_custom, null);
        constructorDialogo.setView(Dialogo);

        EditText password= Dialogo.findViewById(R.id.password1);
        EditText passwordConfirm = Dialogo.findViewById(R.id.password2);
        constructorDialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String nuevaContraseña = password.getText().toString();
                String passworConfirmada =passwordConfirm.getText().toString();
                if (cambioContraseñaListener != null) {
                    if (nuevaContraseña.equals(passworConfirmada)) {
                        cambioContraseñaListener.onAceptar(nuevaContraseña);
                    } else {
                        mostrarDialogoInformativo(contexto, "Contraseñas diferentes", "Por favor, escriba las contraseñas nuevamente.");
                    }
                }
            }
        });

        constructorDialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        constructorDialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }

    public static void mostrarDialogoInformativo(Context contexto, String titulo, String mensaje) {
        AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(contexto);
        constructorDialogo.setTitle(titulo);
        constructorDialogo.setMessage(mensaje);
        constructorDialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = constructorDialogo.create();
        dialogo.show();
    }

    public interface CambioContraseñaListener {
        void onAceptar(String nuevaContraseña);
    }
}
