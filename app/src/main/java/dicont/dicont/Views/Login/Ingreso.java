package dicont.dicont.Views.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import dicont.dicont.Controller.IngresoController;
import dicont.dicont.Views.Inicio;
import dicont.dicont.R;

/*Incluye:
2 acciones (dos botones):
                        - LoginUser
                        - RestablecerClave

Interfaz usada por el controlador IngresoController:
                                                    - onResultLoginUser
                                                    - onResultRestablecerClave
                                                    - showMessageError
 */

public class Ingreso extends AppCompatActivity{

    TextInputLayout tilEmail;
    TextInputLayout tilContraseña;

    EditText editTextEmail;
    EditText editTextContraseña;

    Button btnLoginUser;
    Button btnRestablecerClave;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        //asignamos la interfaz correspondiente al controlador por única vez
        IngresoController.getInstance().setmCallbackIngreso(callbackInterfaceIngreso);

        progressDialog= new ProgressDialog(this);

        //find view
        tilEmail = findViewById(R.id.til_email);
        tilContraseña = findViewById(R.id.til_contraseña);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextContraseña = findViewById(R.id.edi_text_contraseña);
        btnLoginUser = findViewById(R.id.button_confirmar_ingreso);
        btnRestablecerClave = findViewById(R.id.button_restablecer_clave);

        //set listener
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextContraseña.getText().toString();

                progressDialog.setMessage("Autenticando");
                progressDialog.show();

                IngresoController.getInstance().loginUser(email, pass);
            }
        });

        btnRestablecerClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                progressDialog.setMessage("Espera..");
                progressDialog.show();
                IngresoController.getInstance().restablecerClave(email);
            }
        });
    }

    public interface CallbackInterfaceIngreso {
        void onResultLoginUser();
        void showMessageError(String message);
        void onResultRestablecerClave();
    }

    private CallbackInterfaceIngreso callbackInterfaceIngreso = new CallbackInterfaceIngreso() {

        @Override
        public void onResultLoginUser(){  //Resultado exitoso. Caso contrario vuelve por showMessageError

            progressDialog.dismiss();
            //Único punto de acceso a la app desde esta clase
            Intent i = new Intent(Ingreso.this, Inicio.class);
            startActivity(i);
        }

        @Override
        public void showMessageError(String message){
            progressDialog.dismiss();
            Toast.makeText(Ingreso.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResultRestablecerClave() { //Resultado exitoso. Caso contrario vuelve por showMessageError

            progressDialog.dismiss();

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
        }
    };

}
