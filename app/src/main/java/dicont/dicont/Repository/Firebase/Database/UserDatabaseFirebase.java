package dicont.dicont.Repository.Firebase.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import dicont.dicont.EventBus.MessageEvent;
import dicont.dicont.EventBus.MessageEventUserDatabaseFirebase;
import dicont.dicont.Model.User;

public class UserDatabaseFirebase {

    private static UserDatabaseFirebase ourInstance;

    public static UserDatabaseFirebase getInstance() {
        if (ourInstance == null){
            ourInstance = new UserDatabaseFirebase();
        }
        return ourInstance;
    }

    private UserDatabaseFirebase() {
    }

    public void getUser() {
        Log.e("getUser", "llega");
      //obtenemos la referencia a la base de datos
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //obtenemos una referencia al usuario actual
        DatabaseReference userReference =
                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(mAuth.getCurrentUser().getUid());

        /* ejecución asincrona*/
        //cuando termina devolvemos el hilo de ejecución con onResultGetUser o showMessageError
        //corremos el riesgo de cierre por demora demasiado en el hilo asincrono.Habría que mostrar un progressDialog
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    EventBus.getDefault().post(new MessageEventUserDatabaseFirebase.getUser(user));
                }
                else {
                    EventBus.getDefault().post(new MessageEvent("Hubo un error al acceder. Intentalo de nuevo"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                EventBus.getDefault().post(new MessageEvent("Hubo un error al acceder. Intentalo de nuevo"));
            }
        });
    }

    public void updateUser(User user) {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);

            EventBus.getDefault().post(new MessageEventUserDatabaseFirebase.updateUser(user));
        } catch (Exception e){
            EventBus.getDefault().post(new MessageEvent("Hubo un error al actualizar el usuario en la base de datos"));
            //Falta la lógica para este error!
        }
    }

    public void crearUser(String nombre, String apellido, String email) {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            String userID = mAuth.getCurrentUser().getUid();
            User user = new User();
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setEmail(email);
            user.setEstado(0);
            mDatabase.child("Users").child(userID).setValue(user);

           EventBus.getDefault().post(new MessageEventUserDatabaseFirebase.crearUser());
        }catch (Exception e){
            EventBus.getDefault().post(new MessageEvent("Hubo un error al guardar el usuario en la base de datos"));
            //Falta la lógica para este error!
        }


    }
}
