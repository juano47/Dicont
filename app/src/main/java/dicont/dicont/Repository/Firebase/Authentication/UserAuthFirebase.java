package dicont.dicont.Repository.Firebase.Authentication;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dicont.dicont.Controller.IngresoController;
import dicont.dicont.Controller.RegistroController;
import dicont.dicont.Model.User;
import dicont.dicont.Views.Login.Ingreso;
import dicont.dicont.Views.Login.Registro;

public class UserAuthFirebase {

    private static UserAuthFirebase ourInstance;

    private IngresoController.CallbackInterfaceIngresoController mCallbackIngresoController;
    private RegistroController.CallbackInterfaceRegistroController mCallbackRegistroController;

    public static UserAuthFirebase getInstance() {
        if (ourInstance == null){
            ourInstance = new UserAuthFirebase();
        }
        return ourInstance;
    }

    private UserAuthFirebase() {
    }

    public void setmCallbackIngresoController(IngresoController.CallbackInterfaceIngresoController mCallbackIngresoController){
        ourInstance.mCallbackIngresoController = mCallbackIngresoController;
    }

    public void setmCallbackRegistroController(RegistroController.CallbackInterfaceRegistroController mCallbackRegistroController) {
        ourInstance.mCallbackRegistroController = mCallbackRegistroController;
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

    public void userRegister(String email, String clave) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mCallbackRegistroController.onResultRegisterUser();
                }else{
                    mCallbackRegistroController.showMessageError(task.getException().getMessage());
                }
            }
        });
    }

    public void sendEmailVerification() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mCallbackRegistroController.onResultSendEmailVerification();
                }else{
                    mCallbackIngresoController.showMessageError("El envío del email de verificación falló!");
                    //Falta lógica que maneje este fallo!
                }
            }
        });
    }


}
