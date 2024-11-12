package com.ulp.samsinmibiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentNuevoBinding;
import com.ulp.samsinmibiliaria.modelo.Inmueble;
import com.ulp.samsinmibiliaria.modelo.TipoInmueble;
import com.ulp.samsinmibiliaria.servicios.ObtenerUbicacion;
import com.ulp.samsinmibiliaria.servicios.CustomToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NuevoFragment extends Fragment {
    private FragmentNuevoBinding binding;
    private NuevoViewModel viewModel;
    private Intent intent;
    private CustomToast toast;
    private ActivityResultLauncher<Intent> arl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNuevoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NuevoViewModel.class);
        Inmueble inmueble = new Inmueble();
        List<TipoInmueble> tiposInmueble = new ArrayList<>();
        tiposInmueble.add(new TipoInmueble(1, "Casa"));
        tiposInmueble.add(new TipoInmueble(2, "Departamento"));
        tiposInmueble.add(new TipoInmueble(3, "Deposito"));
        tiposInmueble.add(new TipoInmueble(4, "Local"));
        tiposInmueble.add(new TipoInmueble(5, "Cabaña"));
        tiposInmueble.add(new TipoInmueble(6, "Quinta"));
        tiposInmueble.add(new TipoInmueble(7, "Hostel"));
        tiposInmueble.add(new TipoInmueble(8, "Camping"));
        tiposInmueble.add(new TipoInmueble(9, "Hotel"));
        ArrayAdapter<TipoInmueble> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tiposInmueble);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.TipoInmueble.setAdapter(adapter);
        binding.TipoInmueble.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoInmueble tipoInmuebleSeleccionado = (TipoInmueble) parent.getItemAtPosition(position);
                int tipoInmuebleId = tipoInmuebleSeleccionado.getId();
                inmueble.setTipoInmuebleId(tipoInmuebleId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toast.mostrarMensaje(getContext(),"Seleccione un tipo de inmueble");
            }
        });

        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result, requireContext());
            }
        });
        binding.Galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        viewModel.getUriMutable().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFoto.setImageURI(uri);
            }
        });
        binding.guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObtenerUbicacion ubicacion = new ObtenerUbicacion(getContext());
                String direccion = binding.Direccion.getText().toString();
                Double precio = Double.valueOf(binding.Precio.getText().toString());
                String uso = binding.Uso.getText().toString();
                int ambientes = Integer.parseInt(binding.Ambientes.getText().toString());
                String estado = "Retirado";
                String descripcion = binding.Descripcion.getText().toString();
                inmueble.setDireccion(direccion);
                inmueble.setPrecio(precio);
                inmueble.setCoordenadas(ubicacion.obtenerUltimaUbicacion());
                inmueble.setUso(uso);
                inmueble.setCant_ambientes(ambientes);
                inmueble.setEstado(estado);
                inmueble.setDescripcion(descripcion);
                Uri imagenUri = viewModel.getUriMutable().getValue();
                viewModel.agregarInmuebleConImagen(inmueble, imagenUri);
            }
        });
        viewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                toast.mostrarMensaje(getContext(),"Volviendo al listado de inmuebles");
                Navigation.findNavController(requireView()).navigate(R.id.action_nav_NuevoInmueble_to_nav_Inmuebles);
            }
        });
        return binding.getRoot();
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl.launch(intent);
    }
}
