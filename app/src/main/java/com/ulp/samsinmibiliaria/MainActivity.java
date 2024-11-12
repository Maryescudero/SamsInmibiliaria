package com.ulp.samsinmibiliaria;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ulp.samsinmibiliaria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
//    private boolean isPerfilFragmentVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitarPermisos();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        viewModel.setContext(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.mapFragment,
                R.id.nav_perfil, R.id.nav_inmuebles, R.id.nav_contratos, R.id.nav_inquilinos,
                R.id.nav_NuevoInmueble, R.id.nav_pagos)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        viewModel.getMenuItemSelectionLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer itemId) {
                viewModel.onMenuItemSelected(itemId);
            }
        });
        viewModel.getMenuActionLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

    }

    private void mostrarDialogoSalir() {
        new AlertDialog.Builder(this)
                .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> finish())//Cierra la aplicación
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())//Cierra el diálogo
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_perfil, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.setGroupVisible(R.id.nav_Inmuebles, isPerfilFragmentVisible);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    public void setPerfilFragmentVisible(boolean visible) {
//        isPerfilFragmentVisible = visible;
//        invalidateOptionsMenu();
//    }

    public void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1000);
            }
        } else {
            Toast.makeText(this, "Estás utilizando una versión de Android anterior a la 6.0", Toast.LENGTH_SHORT).show();
        }
    }


}