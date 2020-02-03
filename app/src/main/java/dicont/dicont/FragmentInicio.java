package dicont.dicont;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dicont.dicont.Login.Registro;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicio extends Fragment {

    Button btnCerrarSesion;
    TextView textView;

    private FirebaseAuth mAuth;

    public FragmentInicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_inicio, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        btnCerrarSesion = view.findViewById(R.id.button_cerrar_sesion);
        textView = view.findViewById(R.id.textViewNada);
        textView.setText(DataUser.getInstance().getUser().getNombre());

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                getActivity().finishAffinity();

            }
        });
    }

}
