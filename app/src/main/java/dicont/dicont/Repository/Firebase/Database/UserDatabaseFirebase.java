package dicont.dicont.Repository.Firebase.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dicont.dicont.Controller.IngresoController;
import dicont.dicont.Controller.RegistroController;
import dicont.dicont.Model.User;
import dicont.dicont.Views.Login.Registro;

public class UserDatabaseFirebase {

    private static UserDatabaseFirebase ourInstance;

    private IngresoController.CallbackInterfaceIngresoController mCallbackIngresoController;
    private RegistroController.CallbackInterfaceRegistroController mCallbackRegistroController;

    public static UserDatabaseFirebase getInstance() {
        if (ourInstance == null){
            ourInstance = new UserDatabaseFirebase();
        }
        return ourInstance;
    }

    private UserDatabaseFirebase() {
    }

    public void setmCallbackIngresoController (IngresoController.CallbackInterfaceIngresoController mCallbackIngresoController){
        ourInstance.mCallbackIngresoController = mCallbackIngresoController;
    }

    public void setmCallbackRegistroController(RegistroController.CallbackInterfaceRegistroController mCallbackRegistroController) {
        ourInstance.mCallbackRegistroController = mCallbackRegistroController;
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
                    mCallbackIngresoController.onResultGetUser(user);
                }
                else {
                    mCallbackIngresoController.showMessageError("Hubo un error al acceder. Intentalo de nuevo");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mCallbackIngresoController.showMessageError("Hubo un error al acceder. Intentalo de nuevo");
            }
        });
    }

    public void updateUser(User user) {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);

            mCallbackIngresoController.onResultUpdateUser(user);
        } catch (Exception e){
            mCallbackIngresoController.showMessageError("Hubo un error al actualizar el usuario en la base de datos");
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

            mCallbackRegistroController.onResultCrearUser();
        }catch (Exception e){
            mCallbackRegistroController.showMessageError("Hubo un error al guardar el usuario en la base de datos");
            //Falta la lógica para este error!
        }


    }
}
