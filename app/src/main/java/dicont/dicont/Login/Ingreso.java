package dicont.dicont.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Ingreso extends AppCompatActivity {

    TextInputLayout tilEmail;
    TextInputLayout tilContraseña;

    EditText editTextEmail;
    EditText editTextContraseña;

    Button btnConfirmarIngreso;
    Button btnRestablecerClave;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        tilEmail = findViewById(R.id.til_email);
        tilContraseña = findViewById(R.id.til_contraseña);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextContraseña = findViewById(R.id.edi_text_contraseña);
        btnConfirmarIngreso = findViewById(R.id.button_confirmar_ingreso);
        btnRestablecerClave = findViewById(R.id.button_restablecer_clave);

        mAuth = FirebaseAuth.getInstance();



        btnConfirmarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextContraseña.getText().toString();

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Ingreso.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("", "signInWithEmail:success");
                                Toast.makeText(Ingreso.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                            }
                            else {
                                // Si el usuario no verificó su correo
                                Log.w("", "signInWithEmail:failure", task.getException());
                                Toast.makeText(Ingreso.this, "Verificar correo", Toast.LENGTH_SHORT).show();
                                //Traducir y poner en un AlertDialog los mensajes de task.getException().getMessage()
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Ingreso.this, "Los datos ingresados no son correctos", Toast.LENGTH_SHORT).show();
                            //Traducir y poner en un AlertDialog los mensajes de task.getException().getMessage()
                        }
                    }
                });
            }
        });

        btnRestablecerClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                //ProgressDialog progressDialog = new ProgressDialog(Ingreso.this);
                //progressDialog.show(Ingreso.this,"","Enviando correo...");
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                // Crear un builder y vincularlo a la actividad que lo mostrará
                                AlertDialog.Builder builder = new AlertDialog.Builder(Ingreso.this);
                                //Configurar las características
                                builder.setMessage("Te hemos enviado un correo electrónico (Revisar spam)")
                                        .setTitle("Restablecimiento de contraseña")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){

                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //no hace falta hacer nada, solo mostramos un mensaje.
                                            }
                                        });
                                //Obtener una instancia de cuadro de dialogo
                                AlertDialog dialog = builder.create();
                                //Mostrarlo
                                dialog.show();
                            Toast.makeText(Ingreso.this, "An email has been sent to you.", Toast.LENGTH_SHORT).show();

                        } else {
                                Toast.makeText(Ingreso.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //Traducir y poner en un AlertDialog los mensajes de task.getException().getMessage()
                        }
                    }
                });
            }
        });
    }
}
