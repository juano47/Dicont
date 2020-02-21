package dicont.dicont;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

import dicont.dicont.DAO.ROOM.ActividadDao;
import dicont.dicont.DAO.ROOM.DBActividad;
import dicont.dicont.Domain.Actividad;
import dicont.dicont.Domain.DataUser;
import dicont.dicont.Domain.User;
import dicont.dicont.ui.main.SectionsPagerAdapter;

import static dicont.dicont.FragmentMonotributo.REQUEST_CODE;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_inicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_constancias:
                Intent i1 = new Intent(this, ListaConstancias.class);
                startActivity(i1);
                return true;
            case R.id.action_cerrar_sesion:
                // Crear un builder y vincularlo a la actividad que lo mostrará
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Configurar las características
                builder
                        .setTitle("¿Estás seguro que quieres cerrar tu sesión?")
                        .setPositiveButton("Confirmar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {

                                        // Initialize Firebase Auth
                                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                        mAuth.signOut();
                                        //finalizamos la actividad
                                        finishAffinity();

                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        //para cancelar no hace falta hacer nada, por default se cierra la ventana de dialogo
                                    }
                                });


                //Obtener una instancia de cuadro de dialogo
                AlertDialog dialog = builder.create();
                //Mostrarlo
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Avance", "entra al onActivityResult");
        if (requestCode == REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                onBackPressed();
                final Payment payment = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);
                Log.e("paymentStatus", payment.getPaymentStatus());
                switch(payment.getPaymentStatus()){
                    case "approved":
                        FirebaseAuth mAuth;
                        //actualizamos el estado de la instancia usuario
                       User user =  DataUser.getInstance().getUser();
                       user.setEstado(3);//Pago aprobado
                        //actualizamos el user en la base de datos
                        //Initialize Firebase Auth
                        mAuth = FirebaseAuth.getInstance();
                        //Initialize Firebase Database
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);

                        //creamos una instancia de "actividad" y la guardamos localmente con ROOM
                        Actividad actividad = new Actividad();
                        actividad.setMensaje("Solicitaste el alta de tu monotributo");
                        actividad.setFecha(new Date());
                        actividad.setTipo("accion_enviar_peticion");
                        GuardarActividad tareaGuardarActividad = new GuardarActividad();
                        tareaGuardarActividad.execute(actividad);

                        Actividad actividad2 = new Actividad();
                        actividad2.setMensaje("Pagaste la solicitud del alta de monotributo");
                        actividad2.setFecha(new Date());
                        actividad2.setPrecio(payment.getTransactionAmount().doubleValue());
                        actividad2.setTipo("accion_pago");
                        GuardarActividad tareaGuardarActividad2 = new GuardarActividad();
                        tareaGuardarActividad2.execute(actividad2);
                        break;
                }

            } else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.getExtras() != null
                        && data.getExtras().containsKey(MercadoPagoCheckout.EXTRA_ERROR)) {
                    final MercadoPagoError mercadoPagoError =
                            (MercadoPagoError) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR);
                    Log.e("Error: ", "Error involuntario al realizar el pago");
                    //Log.e("Error: ", mercadoPagoError.getMessage());
                    //Resolve error in checkout
                } else {
                    Log.e("Error", "Pago cancelado por el usuario");
                    //Resolve canceled checkout
                }
            }
        }
    }

    class GuardarActividad extends AsyncTask<Actividad, Void, Void> {

        @Override
        protected Void doInBackground(Actividad... actividades) {
            ActividadDao pedidoDao = DBActividad.getInstance(Inicio.this).getDicontDB().actividadDao();
            if(actividades[0].getId() != null && actividades[0].getId() >0) {
                pedidoDao.actualizar(actividades[0]);
            }else {
                pedidoDao.insert(actividades[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}