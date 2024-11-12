package com.ulp.samsinmibiliaria.ui.inmuebles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentInmueblesBinding;
import com.ulp.samsinmibiliaria.modelo.Inmueble;

import java.util.List;


public class InmuebleFragment extends Fragment {
    private InmuebleViewModel viewModel;
    private FragmentInmueblesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =new ViewModelProvider(this).get(InmuebleViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView listaInmuebles = binding.lista;
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_Inmuebles_to_nav_NuevoInmueble);
            }
        });
        viewModel.getInmueblesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebleList) {
                InmuebleAdapter inmuebleAdapter = new InmuebleAdapter(inmuebleList,getContext());
                GridLayoutManager glm=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                listaInmuebles.setLayoutManager(glm);
                listaInmuebles.setAdapter(inmuebleAdapter);
                inmuebleAdapter.setClickListener(new InmuebleAdapter.ClickListener() {
                    @Override
                    public void clickDetalle(Inmueble inmueble) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("inmueble", inmueble);
                        Navigation.findNavController(requireView()).navigate(R.id.action_nav_Inmuebles_to_detalleInmueble, bundle);
                    }
                });
            }
        });
        viewModel.cargarInmuebles();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}