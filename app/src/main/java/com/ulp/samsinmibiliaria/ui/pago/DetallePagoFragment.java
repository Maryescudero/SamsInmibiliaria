package com.ulp.samsinmibiliaria.ui.pago;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentDetallePagoBinding;
import com.ulp.samsinmibiliaria.modelo.Pago;
import com.ulp.samsinmibiliaria.request.ApiClient;


public class DetallePagoFragment extends Fragment {

    private FragmentDetallePagoBinding binding;
    private DetallePagoViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel =new ViewModelProvider(this).get(DetallePagoViewModel.class);
        binding = FragmentDetallePagoBinding.inflate(inflater, container, false);
        viewModel.getPagoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Pago>() {
            @Override
            public void onChanged(Pago pago) {
                binding.DireccionPago.setText("Dirección: "+pago.getContrato().getInmueble().getDireccion());
                binding.PrecioPago.setText("Importe del contrato: $"+pago.getContrato().getMonto());
                binding.numeroPago.setText("N° de pago: "+pago.getCod_pago());
                binding.FechaPago.setText("Fecha de pago: "+pago.getFecha_pago());
                binding.importe.setText("Importe abonado: $"+pago.getImporte());
                binding.DetallePago.setText("Detalle del pago: "+pago.getDetalle());
                String urlFoto = ApiClient.URLBASE + "img/uploads/" +pago.getContrato().getInmueble().getAvatarUrl();
                Glide.with(getContext())
                        .load(urlFoto)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(binding.imagenDetallePago);
            }
        });

        viewModel.recuperarPago(getArguments());
        return binding.getRoot();
    }
}