package com.ulp.samsinmibiliaria.login;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.samsinmibiliaria.MainActivity;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.ActivityLoginBinding;
import com.ulp.samsinmibiliaria.servicios.CustomToast;

import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private Intent intent;
    private LoginActivityViewModel viewModel;
    private LlamarViewModel viewModelLlamada;
    private LlamadaBroadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Pedir permiso de llamada y registrar el broadcast
        pedirPermiso();
        registrarBrodcast();

        // Inicializar animación y mover los títulos
        iniciarAnimacionTitulos();

        // Obtener datos almacenados en SharedPreferences
        SharedPreferences sp = this.getSharedPreferences("datosPropietario", this.MODE_PRIVATE);
        String email = sp.getString("emailPropietario", "");
        String password = sp.getString("passwordPropietario", "");

        // Inicializar los ViewModels
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        viewModelLlamada = new ViewModelProvider(this).get(LlamarViewModel.class);

        // Observador para errores de login
        viewModel.getMensajeError().observe(this, s -> {
            binding.mensaje.setText(s);
            CustomToast.mostrarMensaje(getApplicationContext(), s);
        });

        // Configurar botón para iniciar sesión
        Button btnLogin = binding.btIngresar;
        btnLogin.setOnClickListener(v -> {
            String mail = binding.etMail.getText().toString();
            String pass = binding.etPass.getText().toString();
            if (mail.isEmpty() || pass.isEmpty()) {
                CustomToast.mostrarMensaje(getApplicationContext(), "Por favor ingresa tu correo y contraseña.");
                return;
            }
            viewModel.login(mail, pass); // Llamar al ViewModel para realizar el login
        });

        // Configurar botón de recuperación de contraseña
        binding.btRecuperar.setOnClickListener(v -> {
            intent = new Intent(LoginActivity.this, RecuperarPassActivity.class);
            startActivity(intent);
        });

        // Observador de la respuesta del login
        viewModel.getLoginResponse().observe(this, response -> {
            if (response != null && response.isSuccessful()) {
                // Guardar las credenciales en SharedPreferences si es necesario
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("emailPropietario", binding.etMail.getText().toString());
                editor.putString("passwordPropietario", binding.etPass.getText().toString());
                editor.apply();

                // Redirigir a la siguiente actividad (por ejemplo, Dashboard)
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar el LoginActivity
            } else {
                // Mostrar un mensaje de error si el login no es exitoso
                CustomToast.mostrarMensaje(getApplicationContext(), "Credenciales incorrectas.");
            }
        });

        // Configurar observador para la detección de agitación (o evento de llamada)
        viewModelLlamada.getDeteccionAgitado().observe(this, aBoolean -> LlamadaBroadcast.appIniciada = false);
    }

    private void registrarBrodcast() {
        this.broadcast = new LlamadaBroadcast();
        Log.d("salida", "Aca llega el metodo");
        registerReceiver(broadcast, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void iniciarAnimacionTitulos() {
        TextView titleInmobiliaria = binding.titleInmobiliaria;
        TextView titleSams = binding.titleSams;
        LinearLayout loginForm = binding.loginForm;

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        titleInmobiliaria.startAnimation(slideUp);
        titleSams.startAnimation(slideUp);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> loginForm.setVisibility(View.VISIBLE));
            }
        }, 4000); // Esperar 4 segundos antes de mostrar el formulario
    }

    public void pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}




   /* private ActivityLoginBinding binding;
    private Intent intent;
    private LoginActivityViewModel viewModel;
    private LlamarViewModel viewModelLlamada;
    private LlamadaBroadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.titulo.setText("Bienvenido de nuevo a: InmobiliApp");
        pedirPermiso();
        registrarBrodcast();
        SharedPreferences sp = this.getSharedPreferences("datosPropietario", this.MODE_PRIVATE);
        String email = sp.getString("emailPropietario", "");
        String password = sp.getString("passwordPropietario", "");

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        viewModelLlamada =ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LlamarViewModel.class);


        binding.recuperarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, RecuperarPassActivity.class);
                startActivity(intent);
            }
        });

        viewModel.getMensajeError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
                CustomToast.mostrarMensaje(getApplicationContext(), s);
            }
        });



        Button btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.EditEmail.getText().toString();
                String pass = binding.EditPass.getText().toString();
                viewModel.login(email, pass);
            }
        });

        viewModelLlamada.getDeteccionAgitado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                LlamadaBroadcast.appIniciada = false;

            }
        });

    }

    public void registrarBrodcast(){
        this.broadcast = new LlamadaBroadcast();
        Log.d("salida","Aca llega el metodo");
        registerReceiver(broadcast,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
    public void pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}*/


