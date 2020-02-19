package dicont.dicont;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Avance", "entra al onActivityResult");
        if (requestCode == REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
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

}