package dicont.dicont.Views.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import dicont.dicont.Controller.IngresoController;
import dicont.dicont.Controller.RegistroController;
import dicont.dicont.R;

public class Registro extends AppCompatActivity {

    TextInputLayout tilNombre;
    TextInputLayout tilAPellido;
    TextInputLayout tilEmail;
    TextInputLayout tilClave;

    EditText editTextNombre;
    EditText editTextApellido;
    EditText editTextEmail;
    EditText editTextClave;

    Button btnRegistrarse;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //asignamos la interfaz correspondiente al controlador por única vez
        RegistroController.getInstance().setmCallbackRegistro(callbackInterfaceRegistro);

        progressDialog= new ProgressDialog(this);

        //find views
        tilNombre = findViewById(R.id.til_registro_nombre);
        tilAPellido = findViewById(R.id.til_registro_apellido);
        tilEmail = findViewById(R.id.til_registro_email);
        tilClave = findViewById(R.id.til_registro_clave);
        editTextNombre = findViewById(R.id.edit_text_registro_nombre);
        editTextApellido = findViewById(R.id.edit_text_registro_apellido);
        editTextEmail = findViewById(R.id.edit_text_registro_email);
        editTextClave = findViewById(R.id.edit_text_registro_clave);
        btnRegistrarse = findViewById(R.id.button_registro_registrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String email = editTextEmail.getText().toString();
                String clave = editTextClave.getText().toString();

                progressDialog.setMessage("Iniciando registro. Espera unos instantes..");
                progressDialog.show();
                RegistroController.getInstance().userRegister(nombre, apellido, email, clave);

            }
        });
    }

    public interface CallbackInterfaceRegistro {
        void onResultUserRegister();
        void showMessageError(String message);
        void showMessageSuccess(String message);
    }

    private CallbackInterfaceRegistro callbackInterfaceRegistro = new CallbackInterfaceRegistro() {
        @Override
        public void onResultUserRegister() {
            progressDialog.dismiss();
            //Pasamos a la pantalla de Login. Único punto de éxito
            Intent i = new Intent(Registro.this, Ingreso.class);
            startActivity(i);
        }

        @Override
        public void showMessageError(String message) {
            progressDialog.dismiss();
            Toast.makeText(Registro.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showMessageSuccess(String message) {
            Toast.makeText(Registro.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
