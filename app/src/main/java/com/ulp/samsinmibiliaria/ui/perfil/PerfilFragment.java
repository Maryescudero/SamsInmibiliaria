package com.ulp.samsinmibiliaria.ui.perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentPerfilBinding;
import com.ulp.samsinmibiliaria.modelo.Propietario;
import com.ulp.samsinmibiliaria.request.ApiClient;


public class PerfilFragment extends Fragment {

    private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setMenuVisibility(true);
        TextView nombre = binding.etNombre;
        TextView apellido = binding.etApellido;
        TextView dni = binding.etDni;
        TextView email = binding.etMail;
        TextView telefono = binding.etTelefono;
        ImageView foto = binding.ivFoto;

        viewModel.getCamposEditablesLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean habilitar) {
                nombre.setEnabled(habilitar);
                apellido.setEnabled(habilitar);
                dni.setEnabled(habilitar);
                email.setEnabled(false);
                telefono.setEnabled(habilitar);
            }
        });

        Button btnEditar = binding.btEditar;
        viewModel.getTextoBotonLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String texto) {
                btnEditar.setText(texto);
            }
        });

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.habilitarCampos();
                viewModel.cambiarTextoBoton();
                binding.btEditar.setVisibility(View.INVISIBLE);
                binding.btGuardarCambios.setVisibility(View.VISIBLE);
            }
        });

        binding.btGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String dni = binding.etDni.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                Propietario propietario = new Propietario();
                propietario.setNombre(nombre);
                propietario.setApellido(apellido);
                propietario.setTelefono(telefono);
                propietario.setDni(dni);
                viewModel.editarPerfil(propietario);
                binding.btGuardarCambios.setVisibility(View.INVISIBLE);
                binding.btEditar.setVisibility(View.VISIBLE);
                viewModel.habilitarCampos();
            }
        });

        // Observa el propietario y configura los campos de perfil y foto
        viewModel.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                nombre.setText(propietario.getNombre());
                apellido.setText(propietario.getApellido());
                dni.setText(propietario.getDni());
                email.setText(propietario.getEmail());
                telefono.setText(propietario.getTelefono() + "");

                // Configuración de la URL de la imagen
                String urlBase = ApiClient.URLBASE + "img/uploads/";
                String avatarUrl = propietario.getAvatarUrl();
                String urlFoto;

                if (avatarUrl != null && (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://"))) {
                    // Si avatarUrl es una URL completa, úsala directamente
                    urlFoto = avatarUrl;
                } else {
                    // Si no, agrega la base si es relativa
                    urlFoto = urlBase + avatarUrl;
                }

                // Verificar la URL en el log (opcional, para depuración)
                Log.d("PerfilFragment", "URL de la imagen: " + urlFoto);

                // Cargar la imagen usando Glide
                Glide.with(requireContext())
                        .load(urlFoto)
                        .placeholder(R.drawable.loading) // Imagen de carga
                        .error(R.drawable.error)         // Imagen de error
                        .into(foto);
            }
        });

        viewModel.cargarPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}