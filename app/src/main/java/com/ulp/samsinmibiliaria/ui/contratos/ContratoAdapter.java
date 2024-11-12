package com.ulp.samsinmibiliaria.ui.contratos;


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
import com.ulp.samsinmibiliaria.modelo.Contrato;
import com.ulp.samsinmibiliaria.request.ApiClient;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder>{

    private List<Contrato> listaContratos;
    private Context context;
    private ContratoAdapter.ClickListener clickListener;

    public ContratoAdapter(List<Contrato> listaContratos, Context context) {
        this.listaContratos = listaContratos;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contrato, parent, false);
        return new ViewHolder(view);
    }
    public void setClickListener(ContratoAdapter.ClickListener listener) {
        clickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, int position) {

        Contrato contrato= listaContratos.get(position);
        holder.direccionContrato.setText("Direccion :"+contrato.getInmueble().getDireccion());
        holder.precioContrato.setText("Abono mensual :$"+contrato.getMonto()+"");
        holder.fechasContrato.setText("Fecha de inicio: "+contrato.getFecha_inicio() +" \n "+"Fecha de fin: "+contrato.getFecha_final());
        // Construye la URL completa para la imagen del inmueble en el contrato
        String urlBase = ApiClient.URLBASE + "img/uploads/";
        String avatarUrl = contrato.getInmueble().getAvatarUrl();
        String urlFoto;

        if (avatarUrl != null && (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://"))) {
            // Si avatarUrl es una URL completa, úsala directamente
            urlFoto = avatarUrl;
        } else {
            // Si no, agrega la base si es relativa
            urlFoto = urlBase + avatarUrl;
        }

        // Verificar la URL en el log (opcional, para depuración)
        Log.d("ContratoAdapter", "URL de la imagen: " + urlFoto);

        // Cargar la imagen usando Glide
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading) // Imagen de carga
                .error(R.drawable.error)         // Imagen de error
                .into(holder.imagenContrato);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.clickDetalle(contrato);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaContratos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView direccionContrato;
        TextView precioContrato;
        TextView fechasContrato;
        ImageView imagenContrato;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccionContrato=itemView.findViewById(R.id.DireccionContrato);
            precioContrato = itemView.findViewById(R.id.PrecioContrato);
            fechasContrato = itemView.findViewById(R.id.fechas);
            imagenContrato=itemView.findViewById(R.id.imagenContrato);
        }
    }
    public interface ClickListener {
        void clickDetalle(Contrato contrato);
    }
}