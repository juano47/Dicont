package dicont.dicont;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dicont.dicont.Login.Ingreso;
import dicont.dicont.Login.Registro;

public class MainActivity extends AppCompatActivity {
    TextView textViewIngreso;
    Button btnIngresar;
    Button btnRegistrarse;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewIngreso = findViewById(R.id.text_view_ingreso);
        btnIngresar = findViewById(R.id.button_ingresar);
        btnRegistrarse = findViewById(R.id.button_registrarse);

        //por defecto no se visualizan los botones ni el texto de bienvenida
        textViewIngreso.setVisibility(View.GONE);
        btnIngresar.setVisibility(View.GONE);
        btnRegistrarse.setVisibility(View.GONE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Ingreso.class);
                startActivity(i);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            if(currentUser.isEmailVerified()) {
                Toast.makeText(MainActivity.this, currentUser.getEmail() + " - " + currentUser.isEmailVerified(), Toast.LENGTH_SHORT).show();
            }
            else {
                //Si el usuario no verificó su correo
                textViewIngreso.setVisibility(View.VISIBLE);
                btnIngresar.setVisibility(View.VISIBLE);
                btnRegistrarse.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Fallo - correo no verificado", Toast.LENGTH_SHORT).show();
            }

        } else {
            //Si el usuario no se encontraba logueado
            textViewIngreso.setVisibility(View.VISIBLE);
            btnIngresar.setVisibility(View.VISIBLE);
            btnRegistrarse.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Fallo", Toast.LENGTH_SHORT).show();
        }
    }
}
