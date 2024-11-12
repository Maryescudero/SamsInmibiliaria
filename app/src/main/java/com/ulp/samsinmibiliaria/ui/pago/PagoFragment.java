package com.ulp.samsinmibiliaria.ui.pago;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentPagoBinding;
import com.ulp.samsinmibiliaria.modelo.Contrato;
import com.ulp.samsinmibiliaria.modelo.Pago;
import com.ulp.samsinmibiliaria.ui.contratos.AmbosViewModel;

import java.util.ArrayList;
import java.util.List;

public class PagoFragment extends Fragment {

    private FragmentPagoBinding binding;
    private PagoViewModel viewModel;
    private AmbosViewModel ambosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar los ViewModels
        viewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        ambosViewModel = new ViewModelProvider(requireActivity()).get(AmbosViewModel.class);

        // Inicializar el binding
        binding = FragmentPagoBinding.inflate(inflater, container, false);

        // Obtener el RecyclerView desde el binding
        RecyclerView listaPagos = binding.listaPago;

        // Crear el Adapter (inicializarlo aquí para no crear uno nuevo cada vez)
        final PagoAdapter pagoAdapter = new PagoAdapter(new ArrayList<>(), getContext());
        GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        listaPagos.setLayoutManager(glm);
        listaPagos.setAdapter(pagoAdapter);

        // Observar el Contrato y obtener su ID para obtener los pagos
        ambosViewModel.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                if (contrato != null) {
                    int contratoId = contrato.getId();
                    // Llamar a la función para obtener los pagos del contrato
                    viewModel.getListPagos(contratoId);
                }
            }
        });

        // Observar los pagos obtenidos y actualizar el RecyclerView
        viewModel.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagosList) {
                if (pagosList != null && !pagosList.isEmpty()) {
                    Log.d("PagoFragment", "Pagos recibidos: " + pagosList.size());

                    // Actualizar los datos en el Adapter y notificar el cambio
                    pagoAdapter.setPagos(pagosList);  // Actualiza la lista de pagos
                    pagoAdapter.notifyDataSetChanged();  // Notifica que los datos han cambiado
                } else {
                    Log.d("PagoFragment", "No hay pagos disponibles.");
                    // Opcional: mostrar un mensaje si no hay pagos
                }
            }
        });

        // Configurar el listener de clics del Adapter
        pagoAdapter.setClickListener(new PagoAdapter.ClickListener() {
            @Override
            public void clickDetalle(Pago pago) {
                // Crear un bundle y pasar el objeto Pago al siguiente fragmento
                Bundle bundle = new Bundle();
                bundle.putSerializable("pago", pago);
                Navigation.findNavController(requireView()).navigate(R.id.action_nav_pagos_to_detallePagoFragment, bundle);
            }
        });

        return binding.getRoot();
    }
}
