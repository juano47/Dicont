package dicont.dicont.Views.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import dicont.dicont.Controller.IngresoController;
import dicont.dicont.Views.Inicio;
import dicont.dicont.R;

public class Ingreso extends AppCompatActivity{

    TextInputLayout tilEmail;
    TextInputLayout tilContraseña;

    EditText editTextEmail;
    EditText editTextContraseña;

    Button btnConfirmarIngreso;
    Button btnRestablecerClave;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        //find view
        tilEmail = findViewById(R.id.til_email);
        tilContraseña = findViewById(R.id.til_contraseña);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextContraseña = findViewById(R.id.edi_text_contraseña);
        btnConfirmarIngreso = findViewById(R.id.button_confirmar_ingreso);
        btnRestablecerClave = findViewById(R.id.button_restablecer_clave);

        progressDialog= new ProgressDialog(this);

        //set listener
        btnConfirmarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextContraseña.getText().toString();

                progressDialog.setMessage("Autenticando");
                progressDialog.show();

                IngresoController.getInstance().setmCallbackIngreso(callbackInterface);
                IngresoController.getInstance().loginUser(email, pass);
            }
        });

        btnRestablecerClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

                        } else {
                                Toast.makeText(Ingreso.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //Traducir y poner en un AlertDialog los mensajes de task.getException().getMessage()
                        }
                    }
                });
            }
        });
    }

    public interface CallbackInterfaceIngreso {
        void onResultLoginUser();
        void showMessageError(String message);
    }
    private CallbackInterfaceIngreso callbackInterface = new CallbackInterfaceIngreso() {
        @Override
        public void onResultLoginUser(){
            progressDialog.dismiss();
            Intent i = new Intent(Ingreso.this, Inicio.class);
            startActivity(i);
        }

        @Override
        public void showMessageError(String message){
            progressDialog.dismiss();
            Toast.makeText(Ingreso.this, message, Toast.LENGTH_SHORT).show();
        }
    };

}
