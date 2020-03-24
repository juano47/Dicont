package dicont.dicont;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import dicont.dicont.Model.Aviso;


public class AdaptadorItemAviso extends RecyclerView.Adapter<AdaptadorItemAviso.ItemViewHolder>{
    Context context;
    private List<Aviso> avisos;
    private int fila2;

    public AdaptadorItemAviso(List<Aviso> avisoItemList, Context context) {
        avisos = avisoItemList;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View productoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_aviso, parent, false);
        ItemViewHolder gvh = new ItemViewHolder(productoView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.txtAvisoTitulo.setText(avisos.get(position).getTitulo());
        holder.txtAvisoMensaje.setText(avisos.get(position).getMensaje());
        switch (avisos.get(position).getTipo()){
            case "vencimiento":
                holder.avisoIcono.setImageResource(R.drawable.ic_vencimiento);
                break;
            case "promocion":
                holder.avisoIcono.setImageResource(R.drawable.ic_promocion);
                break;
        }
    }

    //Método necesario
    @Override
    public int getItemCount() {
        if(avisos != null)
            return avisos.size();
        else
            return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //Defino las variables que van a referenciarse con los objetos en pantalla
        TextView txtAvisoTitulo;
        TextView txtAvisoMensaje;
        ImageView avisoIcono;

        public ItemViewHolder(View view) {
            super(view);
            //obtengo datos de la pantalla y los asigno a mis variables
            txtAvisoTitulo = view.findViewById(R.id.textView_aviso_titulo);
            txtAvisoMensaje=view.findViewById(R.id.textView_aviso_mensaje);
            avisoIcono = view.findViewById(R.id.imageView_icono_aviso);
        }
    }

}