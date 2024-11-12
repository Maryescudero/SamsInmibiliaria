package com.ulp.samsinmibiliaria.ui.inmuebles;

import static com.ulp.samsinmibiliaria.request.ApiClient.URLBASE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.ulp.samsinmibiliaria.modelo.Inmueble;
import com.ulp.samsinmibiliaria.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private FragmentDetalleInmuebleBinding binding;
    private DetalleInmuebleViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        viewModel= new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        String urlFoto = URLBASE+"api";
        viewModel.getCamposEditablesLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean habilitar) {
                binding.EstadoSwitch.setEnabled(habilitar);
            }
        });
        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.habilitarCampos();
                viewModel.cambiarTextoBoton();
                binding.EstadoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            viewModel.habilitarInmueble(getArguments());
                        }
                    }
                });
            }
        });

        viewModel.getTextoBotonLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.editButton.setText(s);
            }
        });
        viewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.Direccion.setText("Direccion: "+inmueble.getDireccion());
                binding.Precio.setText("Precio: $"+inmueble.getPrecio());
                binding.TipoInmueble.setText("Tipo: "+inmueble.getTipo());
                binding.Uso.setText("Uso: "+inmueble.getUso());
                binding.Ambientes.setText("Ambientes: "+inmueble.getCant_ambientes()+"");
                boolean estado = inmueble.getEstado().equals("Disponible");
                binding.EstadoSwitch.setChecked(estado);
                binding.Descripcion.setText("Descripción: "+inmueble.getDescripcion());

                // Construye la URL completa para la imagen del inmueble
                String urlBase = ApiClient.URLBASE + "img/uploads/";
                String avatarUrl = inmueble.getAvatarUrl();
                String urlFoto;

                if (avatarUrl != null && (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://"))) {
                    // Si avatarUrl es una URL completa, úsala directamente
                    urlFoto = avatarUrl;
                } else {
                    // Si no, agrega la base si es relativa
                    urlFoto = urlBase + avatarUrl;
                }

                // Verificar la URL en el log (opcional, para depuración)
                Log.d("InmuebleAdapter", "URL de la imagen: " + urlFoto);



                Glide.with(getContext())
                        .load(urlFoto)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(binding.imagen);

            }
        });
        viewModel.recuperarInmueble(getArguments());
        return binding.getRoot();
    }
}