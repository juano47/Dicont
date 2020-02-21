package dicont.dicont.DAO.Firebase.Database;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dicont.dicont.Domain.Aviso;
import dicont.dicont.Domain.DataUser;
import dicont.dicont.Domain.User;
import dicont.dicont.Inicio;
import dicont.dicont.MainActivity;
import retrofit2.Call;


public class DaoAviso {
    public static final int _CONSULTA_AVISO =10;

    public static final int _ERROR_AVISO =-1;


    private ArrayList<Aviso> listaPlatosCompleta;
    private FirebaseAuth mAuth;
    private ValueEventListener valueEventListener;
    Aviso aviso;

    private static final DaoAviso ourInstance = new DaoAviso();

    public static DaoAviso getInstance() {
        return ourInstance;
    }

    private DaoAviso() {
    }

    public void listarPlatos(final Handler h) {

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
                    listaPlatosCompleta = new ArrayList<Aviso>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        listaPlatosCompleta.add(postSnapshot.getValue(Aviso.class));
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

    public ArrayList<Aviso> getListaPlatosCompleta() { return listaPlatosCompleta; }
}
