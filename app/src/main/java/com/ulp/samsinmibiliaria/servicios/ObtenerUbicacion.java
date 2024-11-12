package com.ulp.samsinmibiliaria.servicios;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ObtenerUbicacion {

    private FusedLocationProviderClient fused;
    private Context context;

    public ObtenerUbicacion(Context context) {
        this.context = context.getApplicationContext();
        fused = LocationServices.getFusedLocationProviderClient(this.context);
    }

    public String obtenerUltimaUbicacion() {
        final String[] locationString = {""};
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return "Permisos de ubicación no concedidos";
        }
        fused.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            locationString[0] = location.getLatitude() + "," + location.getLongitude();
                        }
                    });
                }
            }
        });

        return locationString[0];
    }
}
