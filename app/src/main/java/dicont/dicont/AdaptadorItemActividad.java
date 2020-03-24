package dicont.dicont;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dicont.dicont.Model.Actividad;

public class AdaptadorItemActividad extends RecyclerView.Adapter<AdaptadorItemActividad.ItemViewHolder>{
    Context context;
    private List<Actividad> actividades;


    public AdaptadorItemActividad(List<Actividad> itemList, Context context) {
        actividades = itemList;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View productoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_actividad, parent, false);
        ItemViewHolder gvh = new ItemViewHolder(productoView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.txtActividadMensaje.setText(actividades.get(position).getMensaje());
        if(actividades.get(position).getPrecio()!=null) {
            holder.txtActividadPrecio.setText("$ " + actividades.get(position).getPrecio().toString());
        }
        holder.txtActividadFecha.setText(actividades.get(position).getFecha().toString().substring(0,16));
        switch (actividades.get(position).getTipo()){
            case "accion_pago":
                holder.actividadIcono.setImageResource(R.drawable.ic_actividad_pago_realizado);
                break;
            case "accion_enviar_peticion":
                holder.actividadIcono.setImageResource(R.drawable.ic_actividad_solicitud_enviada);
                break;
        }
    }

    //Método necesario
    @Override
    public int getItemCount() {
        if(actividades != null)
            return actividades.size();
        else
            return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //Defino las variables que van a referenciarse con los objetos en pantalla
        TextView txtActividadMensaje;
        TextView txtActividadPrecio;
        TextView txtActividadFecha;
        ImageView actividadIcono;

        public ItemViewHolder(View view) {
            super(view);
            //obtengo datos de la pantalla y los asigno a mis variables
            txtActividadMensaje=view.findViewById(R.id.textView_actividad_mensaje);
            txtActividadPrecio = view.findViewById(R.id.textView_actividad_precio);
            txtActividadFecha = view.findViewById(R.id.textView_actividad_fecha);
            actividadIcono = view.findViewById(R.id.imageView_icono_actividad);
        }
    }

}