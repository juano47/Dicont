package dicont.dicont;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import dicont.dicont.Domain.DataUser;
import dicont.dicont.Domain.User;
import dicont.dicont.Login.Ingreso;
import dicont.dicont.Login.Registro;

public class MainActivity extends AppCompatActivity {
    TextView textViewIngreso;
    Button btnIngresar;
    Button btnRegistrarse;

    private ProgressDialog progressDialog;
    User user;
    private FirebaseAuth mAuth;
    private ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("android");

        progressDialog= new ProgressDialog(this);

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
/*
    @Override
    protected void onStop(){
        super.onStop();
        if(mAuth.getCurrentUser() != null) {
            //obtenemos una referencia al usuario actual
            DatabaseReference estado =
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(mAuth.getCurrentUser().getUid());

            //nos desuscribimos de la referencia para evitar que la app se abra ante cualquier cambio en la base de datos aunque la app este en segundo plano
            estado.removeEventListener(valueEventListener);
        }
    }*/

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            if(currentUser.isEmailVerified()) {
                progressDialog.setMessage("Espera unos instantes..");
                progressDialog.show();

                //obtenemos una referencia al usuario actual
                DatabaseReference estado =
                        FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(mAuth.getCurrentUser().getUid());

                                /*
                                nos suscribimos a la referencia de estado
                                este método se ejecuta en un hilo secundario creo
                                */
                valueEventListener = estado.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            user = dataSnapshot.getValue(User.class);
                            Log.e("Casi legal", "No entra al último if y estado del user es: " + user.getEstado().toString());
                            if (user.getEstado()==0) {    //Actualizar emailVerificado y cambiar estado a 1;
                                Log.e("Todo legal", "Entra al último if");
                                user.setEstado(1);
                                user.setEmailVerificado(true);
                                //obtenemos la referencia a la base de datos de nuevo por estar dentro de un método
                                DatabaseReference mDatabase;
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            }

                            //Guardamos en una clase singleton la instancia de user obtenida de la bd para usarla en toda la aplicacion
                            DataUser.getInstance().setUser(user);
                            progressDialog.dismiss();
                            Intent i = new Intent(MainActivity.this, Inicio.class);
                            startActivity(i);
                        }
                        else {
                            progressDialog.dismiss();
                            Log.e("Error!", "No existe dataSnapshot");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Log.e("Error!","onCancelled");
                    }
                });
            }
            else {
                //Si el usuario no verificó su correo
                textViewIngreso.setVisibility(View.VISIBLE);
                btnIngresar.setVisibility(View.VISIBLE);
                btnRegistrarse.setVisibility(View.VISIBLE);

            }

        } else {
            //Si el usuario no se encontraba logueado
            textViewIngreso.setVisibility(View.VISIBLE);
            btnIngresar.setVisibility(View.VISIBLE);
            btnRegistrarse.setVisibility(View.VISIBLE);
        }
    }

    //se supone que soluciona un cierre inesperado por culpa del progressDialog
    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        super.onDestroy();
    }

}
