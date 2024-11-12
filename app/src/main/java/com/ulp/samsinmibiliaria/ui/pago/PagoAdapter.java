package com.ulp.samsinmibiliaria.ui.pago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ulp.samsinmibiliaria.R;
import com.ulp.samsinmibiliaria.modelo.Pago;
import com.ulp.samsinmibiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder> {

    private List<Pago> listaPagos;
    private Context context;
    private ClickListener clickListener;

    // Constructor inicial
    public PagoAdapter(List<Pago> listaPagos, Context context) {
        this.listaPagos = listaPagos != null ? listaPagos : new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public PagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para cada item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pago = listaPagos.get(position);

        // Asignar los valores a las vistas del ViewHolder
        holder.direccionPago.setText("Direccion del inmueble: " + pago.getContrato().getInmueble().getDireccion());
        holder.precioPago.setText("Importe abonado :$" + pago.getImporte());
        holder.num_pago.setText("Número de pago: " + pago.getCod_pago());

        // Cargar la imagen usando Glide
        String urlBase = ApiClient.URLBASE + "img/uploads/";
        String urlFoto = urlBase + pago.getContrato().getInmueble().getAvatarUrl();
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imagenPago);

        // Manejo del click en el item
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.clickDetalle(pago);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    // Método para actualizar la lista de pagos
    public void setPagos(List<Pago> pagos) {
        this.listaPagos = pagos != null ? pagos : new ArrayList<>();
        notifyDataSetChanged();  // Notificar al RecyclerView que los datos han cambiado
    }

    // ViewHolder para cada item de pago
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView direccionPago;
        TextView precioPago;
        TextView num_pago;
        ImageView imagenPago;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccionPago = itemView.findViewById(R.id.DireccionPago);
            precioPago = itemView.findViewById(R.id.PrecioPago);
            num_pago = itemView.findViewById(R.id.numero_pago);
            imagenPago = itemView.findViewById(R.id.imagenPago);
        }
    }

    // Interfaz para manejar el clic en un item
    public interface ClickListener {
        void clickDetalle(Pago pago);
    }

    // Método para establecer el listener de clic
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
