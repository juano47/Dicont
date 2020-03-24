package dicont.dicont.Repository.Firebase.Database;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dicont.dicont.Model.Aviso;


public class AvisoDatabaseFirebase {
    public static final int _CONSULTA_AVISO =10;

    public static final int _ERROR_AVISO =-1;


    private ArrayList<Aviso> listaAvisosCompleta;
    private ValueEventListener valueEventListener;

    private static final AvisoDatabaseFirebase ourInstance = new AvisoDatabaseFirebase();

    public static AvisoDatabaseFirebase getInstance() {
        return ourInstance;
    }

    private AvisoDatabaseFirebase() {
    }

    public void listarAvisos(final Handler h) {

        //obtenemos una referencia al usuario actual
        DatabaseReference avisos =
                FirebaseDatabase.getInstance().getReference()
                        .child("Avisos");

                                /*
                                nos suscribimos a la referencia de estado
                                este método se ejecuta en un hilo secundario creo
                                */
        valueEventListener = avisos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listaAvisosCompleta = new ArrayList<Aviso>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        listaAvisosCompleta.add(postSnapshot.getValue(Aviso.class));
                    }
                    Message m = new Message();
                    m.arg1 = _CONSULTA_AVISO;
                    h.sendMessage(m);
                } else {
                    Log.e("Error!", "No existe dataSnapshot");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Message m = new Message();
                m.arg1 = _ERROR_AVISO;
                h.sendMessage(m);
                Log.e("Error!","onCancelled");
            }
        });
    }

    public ArrayList<Aviso> getListaPlatosCompleta() { return listaAvisosCompleta; }
}
