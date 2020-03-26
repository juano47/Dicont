package dicont.dicont.Repository.Firebase.Authentication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

import dicont.dicont.EventBus.MessageEvent;
import dicont.dicont.EventBus.MessageEventUserAuthFirebase;

public class UserAuthFirebase {

    private static UserAuthFirebase ourInstance;

    public static UserAuthFirebase getInstance() {
        if (ourInstance == null){
            ourInstance = new UserAuthFirebase();
        }
        return ourInstance;
    }

    private UserAuthFirebase() {
    }

    public void validarUser(String email, String pass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    EventBus.getDefault().post(new MessageEventUserAuthFirebase.validarUser(true));
                }
                else {
                    EventBus.getDefault().post(new MessageEventUserAuthFirebase.validarUser(false));
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
                    EventBus.getDefault().post(new MessageEventUserAuthFirebase.restablecerClave(true));

                } else {
                    EventBus.getDefault().post(new MessageEventUserAuthFirebase.restablecerClave(false));
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
                   EventBus.getDefault().post(new MessageEventUserAuthFirebase.userRegister());
                }else{
                   EventBus.getDefault().post(new MessageEvent(task.getException().getMessage()));
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
                    EventBus.getDefault().post(new MessageEventUserAuthFirebase.sendEmailVerification());
                }else{
                   EventBus.getDefault().post(new MessageEvent("El envío del email de verificación falló!"));
                    //Falta lógica que maneje este fallo!
                }
            }
        });
    }


}
