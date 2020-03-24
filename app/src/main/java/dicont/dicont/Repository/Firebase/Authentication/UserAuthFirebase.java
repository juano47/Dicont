package dicont.dicont.Repository.Firebase.Authentication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dicont.dicont.Controller.IngresoController;

public class UserAuthFirebase {

    private static UserAuthFirebase ourInstance;

    private IngresoController.CallbackInterfaceUserDatabaseFirebase mCallbackIngresoController;

    public static UserAuthFirebase getInstance() {
        if (ourInstance == null){
            ourInstance = new UserAuthFirebase();
        }
        return ourInstance;
    }

    private UserAuthFirebase() {
    }

    public void setmCallbackIngresoController (IngresoController.CallbackInterfaceUserDatabaseFirebase mCallbackIngresoController){
        ourInstance.mCallbackIngresoController = mCallbackIngresoController;
    }

    public void validarUser(String email, String pass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    mCallbackIngresoController.onResultValidarUser(true);
                }
                else {
                    mCallbackIngresoController.onResultValidarUser(false);
                }
            }
        });
    }

    public boolean checkearEmailVerificado() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser().isEmailVerified();
    }

    public void restablecerClave(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    mCallbackIngresoController.onResultRestablecerClave(true);

                } else {
                    mCallbackIngresoController.onResultRestablecerClave(false);
                }
            }
        });
    }
}
