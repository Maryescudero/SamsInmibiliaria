package com.ulp.samsinmibiliaria.login;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ulp.samsinmibiliaria.databinding.ActivityRecuperarPassBinding;

import com.ulp.samsinmibiliaria.R;

public class RecuperarPassActivity extends AppCompatActivity {
    private ActivityRecuperarPassBinding binding;
    private RecuperarPassViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecuperarPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RecuperarPassViewModel.class);
        viewModel.getMsjError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
            }
        });
        viewModel.getRespuesta().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
            }
        });
        binding.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= binding.email.getText().toString();
                viewModel.recuperarPass(email);
            }
        });

    }
}