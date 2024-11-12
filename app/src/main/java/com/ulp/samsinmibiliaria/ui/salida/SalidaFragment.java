package com.ulp.samsinmibiliaria.ui.salida;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.databinding.FragmentSalidaBinding;

public class SalidaFragment extends Fragment {

    private FragmentSalidaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSalidaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Dialogo.mostrarDialogo(getContext());
        return root;
    }
}