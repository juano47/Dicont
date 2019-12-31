package dicont.dicont.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dicont.dicont.R;

public class Registro extends AppCompatActivity {

    TextInputLayout tilEmail;
    TextInputLayout tilClave;

    EditText editTextEmail;
    EditText editTextClave;

    Button btnRegistrarse;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        tilEmail = findViewById(R.id.til_registro_email);
        tilClave = findViewById(R.id.til_registro_clave);

        editTextEmail = findViewById(R.id.edit_text_registro_email);
        editTextClave = findViewById(R.id.edit_text_registro_clave);

        btnRegistrarse = findViewById(R.id.button_registro_registrarse);

        mAuth = FirebaseAuth.getInstance();

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String clave = editTextClave.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Registro.this, "El usuario se registro con éxito", Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Registro.this, "Se envió el email de verificación!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Registro.this, "El envió del email de verificación falló!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Log.e("", "entro");
                            Toast.makeText(Registro.this, "El usuario no se registro. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //Traducir y poner en un AlertDialog los mensajes de task.getException().getMessage()
                        }
                    }
                });
            }
        });
    }
}
