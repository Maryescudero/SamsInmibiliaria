package com.ulp.samsinmibiliaria.ui.inmuebles;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.modelo.Inmueble;
import com.ulp.samsinmibiliaria.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {
    private List<Inmueble> listaInmuebles;
    private Context context;
    private ClickListener clickListener;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolder(view);
    }

    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolder holder, int position) {
        Inmueble inmueble = listaInmuebles.get(position);

        // Configura la información del inmueble
        holder.direccion.setText(inmueble.getDireccion());
        holder._precio.setText(String.valueOf(inmueble.getPrecio()));

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

        // Cargar la imagen usando Glide
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading) // Imagen de carga
                .error(R.drawable.error)         // Imagen de error
                .into(holder.imagen);

        // Configura el listener para el clic en el item
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.clickDetalle(inmueble);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView direccion;
        TextView _precio;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.Direccion);
            _precio = itemView.findViewById(R.id.Precio);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }

    public interface ClickListener {
        void clickDetalle(Inmueble inmueble);
    }
}

