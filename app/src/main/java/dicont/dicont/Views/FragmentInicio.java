package dicont.dicont.Views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import dicont.dicont.AdaptadorItemActividad;
import dicont.dicont.AdaptadorItemAviso;
import dicont.dicont.Repository.Firebase.Database.AvisoDatabaseFirebase;
import dicont.dicont.Repository.ROOM.ActividadDao;
import dicont.dicont.Repository.ROOM.DBActividad;
import dicont.dicont.Model.Actividad;
import dicont.dicont.Model.Aviso;
import dicont.dicont.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicio extends Fragment {

    private RecyclerView mRecyclerViewAvisos;
    private AdaptadorItemAviso miAdaptadorAviso;
    private ArrayList<Aviso>  listaDataSetCompleta;
    private ArrayList<Aviso> listaCompleta;

    List<Actividad> listaActividadesInicial;
    List<Actividad> listaActividadesDataset;
    private RecyclerView mRecyclerViewActividades;
    private AdaptadorItemActividad miAdaptadorActividad;

    private FirebaseAuth mAuth;
    public FragmentInicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_inicio, container, false);

        ////AVISOS obtenidos desde Firebase////
        //solicitamos la lista de avisos guardada en la base de datos
        AvisoDatabaseFirebase.getInstance().listarAvisos(miHandler);

        //getting the recyclerview from xml
        mRecyclerViewAvisos = view.findViewById(R.id.recyclerView_avisos);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAvisos.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCompleta = new ArrayList<>();

        //set adapter to recyclerview
        miAdaptadorAviso = new AdaptadorItemAviso(listaCompleta, getContext());
        mRecyclerViewAvisos.setAdapter(miAdaptadorAviso);
        miAdaptadorAviso.notifyDataSetChanged();
        //////////////////////////////////////

        ////ACTIVIDADES obtenidas con ROOM////
        //getting the recyclerview from xml
        mRecyclerViewActividades = view.findViewById(R.id.recyclerView_actividad);
        mRecyclerViewActividades.setHasFixedSize(true);
        mRecyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));

        listaActividadesInicial = new ArrayList<>();

        //set adapter to recyclerview
        miAdaptadorActividad = new AdaptadorItemActividad(listaActividadesInicial, getContext());
        mRecyclerViewActividades.setAdapter(miAdaptadorActividad);
        miAdaptadorActividad.notifyDataSetChanged();

        final Runnable hiloUpdateLista = new Runnable() {
            @Override
            public void run() {
                listaActividadesInicial.addAll(listaActividadesDataset);
                miAdaptadorActividad.notifyDataSetChanged();

            }
        };
        final Runnable cargarPedidos = new Runnable() {
            @Override
            public void run() {
                ActividadDao dao = DBActividad.getInstance(getContext()).getDicontDB().actividadDao();
                listaActividadesDataset = new ArrayList<>();
                listaActividadesDataset  = dao.getAll();
                getActivity().runOnUiThread(hiloUpdateLista);
            }
        };
        Thread t1 = new Thread(cargarPedidos);
        t1.start();

        initUI(view);
        return view;
    }

    private void initUI(View view) {


    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1 ) {
                case AvisoDatabaseFirebase._CONSULTA_AVISO:
                    listaDataSetCompleta = AvisoDatabaseFirebase.getInstance().getListaPlatosCompleta();
                    //solo al momento de tener la lista de avisos desde el servidor la seteamos en pantalla
                    listaCompleta.addAll(listaDataSetCompleta);
                    miAdaptadorAviso.notifyDataSetChanged();
                    break;
                case AvisoDatabaseFirebase._ERROR_AVISO:
                    Log.e("", "Error al solicitar los avisos de la base de datos");
                    break;
            }
        }
    };

}
