package dicont.dicont;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.preference.Preference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

import dicont.dicont.Domain.Formulario;
import dicont.dicont.Domain.Monotributo;
import dicont.dicont.Domain.User;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMonotributo extends Fragment {

    static final int REQUEST_CODE = 1;

    final double ingresosBrutosCatA = 208739.25;
    final double ingresosBrutosCatB = 313108.87;
    final double ingresosBrutosCatC = 417478.51;
    final double ingresosBrutosCatD = 626217.78;
    final double ingresosBrutosCatE = 834957.00;
    final double ingresosBrutosCatF = 1043696.27;
    final double ingresosBrutosCatG = 1252435.53;
    final double ingresosBrutosCatH = 1739493.79;
    final double ingresosBrutosCatI = 2043905.21;
    final double ingresosBrutosCatJ = 2348316.62;
    final double ingresosBrutosCatK = 2609240.69;

    final double LoPSCatA = 168.97;
    final double LoPSCatB = 325.54;
    final double LoPSCatC = 556.64;
    final double LoPSCatD = 914.47;
    final double LoPSCatE = 1739.48;
    final double LoPSCatF = 2393.05;
    final double LoPSCatG = 3044.12;
    final double LoPSCatH = 6957.96;

    final double VCMCatA = 168.97;
    final double VCMCatB = 325.54;
    final double VCMCatC = 514.38;
    final double VCMCatD = 844.90;
    final double VCMCatE = 1349.34;
    final double VCMCatF = 1761.85;
    final double VCMCatG = 2196.71;
    final double VCMCatH = 5392.44;
    final double VCMCatI = 8697.46;
    final double VCMCatJ = 10220.77;
    final double VCMCatK = 11741.58;

    final double aportesSIPACatA = 745.49;
    final double aportesSIPACatB = 820.04;
    final double aportesSIPACatC = 902.05;
    final double aportesSIPACatD = 992.25;
    final double aportesSIPACatE = 1091.48;
    final double aportesSIPACatF = 1200.62;
    final double aportesSIPACatG = 1320.68;
    final double aportesSIPACatH = 1452.75;
    final double aportesSIPACatI = 1598.03;
    final double aportesSIPACatJ = 1757.84;
    final double aportesSIPACatK = 1933.61;

    final  double minimoMonotributoPromovido = 745.49;

    final double aportesObraSocialCatTodas = 1041.22;

    String categoria = "";
    double costoMensualAfipCatMonotributo = 0;

    ConstraintLayout constraintLayoutSinMonotributo;
    ConstraintLayout constraintLayoutDefinirCategoriaMonotributo;
    ConstraintLayout constraintLayoutCompletarFormularioDatosPersonales;
    ConstraintLayout constraintLayoutInformaciónSolicitudEnviada;

    Button btnEmpezarMonotributo;
    Button btnSolicitarMonotributo;
    Button btnEnviarSolicitudMonotributo;

    RadioGroup radioGroupFormularioSexo;

    TextInputLayout tilIngresoAnual;
    TextInputLayout tilFormularioDni;
    TextInputLayout tilFormularioClaveFiscal;
    TextInputLayout tilFormularioDomicilioPersonal;
    TextInputLayout tilFormularioCelular;
    TextInputLayout tilFormularioDomicilioComercial;
    TextInputLayout tilFormularioCuitEmpleador;
    TextInputLayout tilFormularioCuitCajaProfesionales;
    TextInputLayout tilFormularioObraSocial;

    EditText editTextIngresoAnual;
    EditText editTextDni;
    //EditText editTextFormularioCuitEmpleador;
    //EditText editTextFormularioCuitCajaProfesionales;
    //EditText editTextFormularioObraSocial;
    //EditText editTextFormularioDomicilioComercial;

    TextView textViewInfoCuit;
    TextView textViewInfoClaveFiscal;
    TextView textViewInfoNombre;
    TextView textViewInfoApellido;
    TextView textViewInfoRelacionDependencia;
    TextView textViewResultadoEleccionMonotributo;
    TextView textViewPaso1de2;
    TextView textViewTituloEleccionMonotributo;
    TextView textViewResumenEleccionMonotributoIngresoAnual;
    TextView textViewResumenEleccionMonotributoRelacionDependencia;
    TextView textViewResumenEleccionMonotributoCajaProfesionales;
    TextView textViewResumenEleccionMonotributoObraSocial;
    TextView textViewResumenEleccionMonotributoDescripcionCajaProfesionales;
    TextView textViewResumenEleccionMonotributoDescripcionObraSocial;
    TextView textViewResumenEleccionMonotributoDescripcionActividadComercial;
    //TextView textViewFormularioCuitEmpleador;
    //TextView textViewFormularioCuitCajaProfesionales;
    //TextView textViewFormularioObraSocial;
    //TextView textViewFormularioDomicilioComercial;

    CardView cardViewCajaProfesionales;
    CardView cardViewActividadComercial;
    CardView cardViewObraSocial;
    CardView cardViewResumenEleccionMonotributo;

    Chip chipRelacionDependenciaSI;
    Chip chipRelacionDependenciaNO;
    Chip chipCajaProfesionalesSI;
    Chip chipCajaProfesionalesNO;
    Chip chipActividadComercialLocacionServicios;
    Chip chipActividadComercialVentaMuebles;
    Chip chipObraSocialSI;
    Chip chipObraSocialNO;
    Chip chipResumenEleccionMonotributoActividadComercial;

    ScrollView scrollViewEleccionMonotributo;
    ScrollView scrollViewResultadoEleccionMonotributo;
    View dividerInferiorEleccionMonotributo;
    View dividerSuperiorEleccionMonotributo;

    double ingresoAnual;
    String sexo;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    public FragmentMonotributo() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //un LocalBroadcast no se debe incluir en el manifest sino que se declara dinamicamente con registerReceiver
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((mMessageReceiver),
                new IntentFilter("preferenceIdMercadoPagoData")
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_monotributo, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {

        progressDialog= new ProgressDialog(getContext());

        User user = DataUser.getInstance().getUser();
        switch (user.getEstado()){
            case 1: //Sin monotributo
                //SIN MONOTRIBUTO
                constraintLayoutSinMonotributo = view.findViewById(R.id.cl_estado_sin_monotributo);
                btnEmpezarMonotributo = view.findViewById(R.id.button_frag_monotributo_empezar_monotributo);

                //DEFINIR CATEGORIA MONOTRIBUTO
                constraintLayoutDefinirCategoriaMonotributo = view.findViewById(R.id.cl_estado_definir_categoria_monotributo);
                btnSolicitarMonotributo = view.findViewById(R.id.button_frag_monotributo_solicitar_monotributo);

                tilIngresoAnual = view.findViewById(R.id.til_em_ingreso_anual);
                editTextIngresoAnual = view.findViewById(R.id.editText_em_ingreso_anual);

                chipRelacionDependenciaSI = view.findViewById(R.id.chip_em_relacion_dependencia_si);
                chipRelacionDependenciaNO = view.findViewById(R.id.chip_em_relacion_dependencia_no);
                chipCajaProfesionalesSI = view.findViewById(R.id.chip_em_caja_profesionales_si);
                chipCajaProfesionalesNO = view.findViewById(R.id.chip_em_caja_profesionales_no);
                chipActividadComercialLocacionServicios = view.findViewById(R.id.chip_em_actividad_comercial_locacion_servicios);
                chipActividadComercialVentaMuebles = view.findViewById(R.id.chip_em_actividad_comercial_venta_muebles);
                chipObraSocialSI = view.findViewById(R.id.chip_em_obra_social_si);
                chipObraSocialNO = view.findViewById(R.id.chip_em_obra_social_no);
                chipResumenEleccionMonotributoActividadComercial = view.findViewById(R.id.chip_resumen_eleccion_monotributo_actividad_comercial);

                cardViewCajaProfesionales = view.findViewById(R.id.cardView_caja_profesionales);
                cardViewActividadComercial = view.findViewById(R.id.cardView_actividad_comercial);
                cardViewObraSocial = view.findViewById(R.id.cardView_obra_social);
                cardViewResumenEleccionMonotributo = view.findViewById(R.id.cardView_resumen_eleccion_monotributo);

                scrollViewEleccionMonotributo = view.findViewById(R.id.scrollView_eleccion_monotributo);
                scrollViewResultadoEleccionMonotributo = view.findViewById(R.id.scrollView_resultado_eleccion_monotributo);
                dividerInferiorEleccionMonotributo = view.findViewById(R.id.divider_inferior_eleccion_monotributo);
                dividerSuperiorEleccionMonotributo = view.findViewById(R.id.divider_superior_eleccion_monotributo);

                textViewResultadoEleccionMonotributo = view.findViewById(R.id.textView_resultado_eleccion_monotributo);
                textViewPaso1de2 = view.findViewById(R.id.textView_paso1de2);
                textViewTituloEleccionMonotributo = view.findViewById(R.id.textView_titulo_eleccion_monotributo);
                textViewResumenEleccionMonotributoIngresoAnual = view.findViewById(R.id.textView_resumen_eleccion_monotributo_ingreso_anual);
                textViewResumenEleccionMonotributoRelacionDependencia = view.findViewById(R.id.textView_resumen_eleccion_monotributo_relacion_dependencia);
                textViewResumenEleccionMonotributoCajaProfesionales = view.findViewById(R.id.textView_resumen_eleccion_monotributo_caja_profesionales);
                textViewResumenEleccionMonotributoObraSocial = view.findViewById(R.id.textView_resumen_eleccion_monotributo_obra_social);
                textViewResumenEleccionMonotributoDescripcionCajaProfesionales = view.findViewById(R.id.textView_resumen_eleccion_monotributo_descripcion_caja_profesionales);
                textViewResumenEleccionMonotributoDescripcionObraSocial = view.findViewById(R.id.textView_resumen_eleccion_monotributo_descripcion_obra_social);
                textViewResumenEleccionMonotributoDescripcionActividadComercial = view.findViewById(R.id.textView_resumen_eleccion_monotributo_descripcion_actividad_comercial);

                //COMPLETAR FORMULARIO DATOS PERSONALES
                constraintLayoutCompletarFormularioDatosPersonales = view.findViewById(R.id.cl_estado_completar_formulario_datos_personales);
                btnEnviarSolicitudMonotributo = view.findViewById(R.id.button_frag_monotributo_enviar_solicitud_monotributo);
                radioGroupFormularioSexo = view.findViewById(R.id.radioGroup_formulario_sexo);

               // textViewFormularioCuitEmpleador = view.findViewById(R.id.textView_formulario_cuit_empleador);
               // textViewFormularioCuitCajaProfesionales = view.findViewById(R.id.textView_formulario_cuit_caja_profesionales);
               // textViewFormularioObraSocial = view.findViewById(R.id.textView_formulario_obra_social);
                //textViewFormularioDomicilioComercial = view.findViewById(R.id.textView_formulario_domicilio_comercial);

                tilFormularioDni = view.findViewById(R.id.til_formulario_dni);
               // tilFormularioClaveFiscal = view.findViewById(R.id.til_formulario_clave_fiscal);

               // tilFormularioDomicilioPersonal = view.findViewById(R.id.til_formulario_domicilio_personal);
                tilFormularioCelular = view.findViewById(R.id.til_formulario_celular);
                //tilFormularioCuitEmpleador = view.findViewById(R.id.til_formulario_cuit_empleador);
                //tilFormularioCuitCajaProfesionales = view.findViewById(R.id.til_formulario_cuit_caja_profesionales);
                //tilFormularioObraSocial = view.findViewById(R.id.til_formulario_obra_social);
               // tilFormularioDomicilioComercial = view.findViewById(R.id.til_formulario_domicilio_comercial);


                editTextDni = view.findViewById(R.id.editText_formulario_dni);
                //editTextFormularioCuitEmpleador = view.findViewById(R.id.editText_formulario_cuit_empleador);
                //editTextFormularioCuitCajaProfesionales = view.findViewById(R.id.editText_formulario_cuit_caja_profesionales);
                //editTextFormularioObraSocial = view.findViewById(R.id.editText_formulario_obra_social);
               // editTextFormularioDomicilioComercial = view.findViewById(R.id.editText_formulario_domicilio_comercial);

                final Spinner spinnerCompaniaCelular = view.findViewById(R.id.spinner_compania_celular);

                //Ponemos visible el primer layout y luego va cambiando la visibilidad con la acción de los botones
                constraintLayoutSinMonotributo.setVisibility(View.VISIBLE);

                btnEmpezarMonotributo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //ocultamos el layout "Sin Monotributo" y habilitamos el layout "definir categoria monotributo"
                        constraintLayoutSinMonotributo.setVisibility(View.GONE);
                        constraintLayoutDefinirCategoriaMonotributo.setVisibility(View.VISIBLE);
                    }
                });

                btnSolicitarMonotributo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //ocultamos el layout "definir categoría monotributo" y habilitamos el layout "completar formulario datos personales"
                        constraintLayoutDefinirCategoriaMonotributo.setVisibility(View.GONE);
                        constraintLayoutCompletarFormularioDatosPersonales.setVisibility(View.VISIBLE);
                    }
                });

                btnEnviarSolicitudMonotributo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Datos para instancia "monotributo"
                        double ingresoAnual = Double.parseDouble(editTextIngresoAnual.getText().toString());
                        String relacionDependencia = "";
                        String cajaProfesionales = "";
                        String actividadComercial = "";
                        String obraSocial = "";

                            if (chipRelacionDependenciaSI.isChecked()) {
                                relacionDependencia = "SI";

                            } else if (chipRelacionDependenciaNO.isChecked()) {
                                relacionDependencia = "NO";
                            }

                            if (chipCajaProfesionalesSI.isChecked()) {
                                cajaProfesionales = "SI";
                            } else if (chipCajaProfesionalesNO.isChecked()) {
                                cajaProfesionales = "NO";
                            }

                            if (chipActividadComercialLocacionServicios.isChecked()) {
                               actividadComercial = "locacion";
                            } else if (chipActividadComercialVentaMuebles.isChecked()) {
                                actividadComercial = "venta";
                            }

                            if (chipObraSocialSI.isChecked()) {
                               obraSocial = "SI";
                            } else if (chipObraSocialNO.isChecked()) {
                                obraSocial = "NO";
                            }

                        //creamos y seteamos una instancia de monotributo
                        Monotributo monotributo = new Monotributo(ingresoAnual, relacionDependencia, cajaProfesionales, obraSocial, actividadComercial, categoria, costoMensualAfipCatMonotributo);

                        //Datos para instancia "formulario"
                        String dni = editTextDni.getText().toString();
                        String celular = tilFormularioCelular.getEditText().getText().toString();
                        //Falta tratar el caso de error si no selecciona nada en el spinner
                        String companiaCelular = spinnerCompaniaCelular.getSelectedItem().toString();
                        sexo = "";
                        //Falta tratar el caso de error cuando no se selecciona nada en el radioGroup

                        if(radioGroupFormularioSexo.getCheckedRadioButtonId() == (R.id.radioButton_femenino)){
                            sexo = "Femenino";
                        }
                         else if(String.valueOf(radioGroupFormularioSexo.getCheckedRadioButtonId()).equals(R.id.radioButton_masculino)){
                             sexo = "Masculino";
                         }

                        Formulario formulario = new Formulario(dni, sexo, celular, companiaCelular);

                        User user = DataUser.getInstance().getUser();

                        //cambiamos el estado:2 "Iniciar proceso de pago"
                        user.setEstado(2);

                        user.setMonotributo(monotributo);
                        user.setFormulario(formulario);

                        //volvemos a guardar el user actualizado en el singleton DataUser
                        DataUser.getInstance().setUser(user);

                        //actualizamos el user en la base de datos
                        //Initialize Firebase Auth
                        mAuth = FirebaseAuth.getInstance();
                        //Initialize Firebase Database
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                        //Toast.makeText(getContext(),"Tu solicitud está siendo analizada por un profesional! Aguarda y verás", Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Iniciando proceso de pago. Espera unos segundos..");
                        progressDialog.show();
                    }
                });

                //Funcionalidad "Elección de monotributo"
                editTextIngresoAnual.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            //Volvemos la visualizacion de los componentes al estado inicial
                            tilIngresoAnual.setError("");
                            chipRelacionDependenciaSI.setChecked(false);
                            chipRelacionDependenciaNO.setChecked(false);
                            cardViewCajaProfesionales.setVisibility(View.GONE);
                            cardViewActividadComercial.setVisibility(View.GONE);
                            scrollViewResultadoEleccionMonotributo.setVisibility(View.GONE);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) { }
                });

                chipRelacionDependenciaSI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        //cerramos el teclado para que no ocupe espacio -- revisar, no funciona
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editTextIngresoAnual.getWindowToken(), 0);

                        if(checked){
                            //ocultamos el cardView "Caja de profesionales"
                            cardViewCajaProfesionales.setVisibility(View.GONE);

                            //ponemos visible el cardView "actividad comercial" dependiendo del valor de "ingresoAnual"
                            if (!editTextIngresoAnual.getText().toString().equals("")) {
                                double ingresoAnual = Integer.parseInt(editTextIngresoAnual.getText().toString());
                                if (ingresoAnual < ingresosBrutosCatA) {
                                    categoria = "A";
                                    costoMensualAfipCatMonotributo = VCMCatA;
                                    textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría A y pagarás $" + VCMCatA + " mensualmente a AFIP");
                                    resultadoEleccionMonotributoVisible();


                                } else {
                                    cardViewActividadComercial.setVisibility(View.VISIBLE);
                                    scrollAbajo(scrollViewEleccionMonotributo);
                                }
                            }
                            else {
                                tilIngresoAnual.setError("Completa esta dato para continuar");
                                chipRelacionDependenciaSI.setChecked(false);
                            }
                        }
                        else {
                            if (!chipRelacionDependenciaNO.isChecked() || !chipCajaProfesionalesSI.isChecked() || !chipRelacionDependenciaNO.isChecked()) {
                                cardViewActividadComercial.setVisibility(View.GONE);
                                scrollViewResultadoEleccionMonotributo.setVisibility(View.GONE);
                            }
                            chipActividadComercialLocacionServicios.setChecked(false);
                            chipActividadComercialVentaMuebles.setChecked(false);
                        }
                    }
                });

                chipRelacionDependenciaNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        //cerramos el teclado para que no ocupe espacio -- revisar, no funciona
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editTextIngresoAnual.getWindowToken(), 0);
                        if (checked) {
                            if (!editTextIngresoAnual.getText().toString().equals("")) {
                                //ponemos visible el cardView "Caja de profesionales"
                                cardViewCajaProfesionales.setVisibility(View.VISIBLE);
                                scrollAbajo(scrollViewEleccionMonotributo);
                            }
                            else {
                                tilIngresoAnual.setError("Completa esta dato para continuar");
                                chipRelacionDependenciaNO.setChecked(false);
                            }
                        }
                        else{
                            //ocultamos el cardView "Caja de profesionales"
                            cardViewCajaProfesionales.setVisibility(View.GONE);
                            chipCajaProfesionalesSI.setChecked(false);
                            chipCajaProfesionalesNO.setChecked(false);
                        }
                    }
                });

                chipCajaProfesionalesSI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (checked) {
                            double ingresoAnual = Integer.parseInt(editTextIngresoAnual.getText().toString());
                            if (ingresoAnual < ingresosBrutosCatA) {
                                categoria = "A";
                                costoMensualAfipCatMonotributo = VCMCatA;
                                textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría A y pagarás $" + VCMCatA + " mensualmente a AFIP");
                                resultadoEleccionMonotributoVisible();

                            }
                            else {
                                cardViewActividadComercial.setVisibility(View.VISIBLE);
                                scrollAbajo(scrollViewEleccionMonotributo);
                            }
                        } else {
                            if (!chipCajaProfesionalesNO.isChecked()) {
                                cardViewActividadComercial.setVisibility(View.GONE);
                                scrollViewResultadoEleccionMonotributo.setVisibility(View.GONE);
                            }
                            chipActividadComercialLocacionServicios.setChecked(false);
                            chipActividadComercialVentaMuebles.setChecked(false);
                        }
                    }
                });

                chipCajaProfesionalesNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (checked) {
                            double ingresoAnual = Integer.parseInt(editTextIngresoAnual.getText().toString());
                            if (ingresoAnual < ingresosBrutosCatA) {
                                cardViewObraSocial.setVisibility(View.VISIBLE);
                                scrollAbajo(scrollViewEleccionMonotributo);
                            }
                            else {
                                cardViewActividadComercial.setVisibility(View.VISIBLE);
                                scrollAbajo(scrollViewEleccionMonotributo);
                            }
                        } else {
                            if (!chipCajaProfesionalesSI.isChecked()) {
                                cardViewActividadComercial.setVisibility(View.GONE);
                                scrollViewResultadoEleccionMonotributo.setVisibility(View.GONE);
                            }
                            cardViewObraSocial.setVisibility(View.GONE);
                            chipActividadComercialLocacionServicios.setChecked(false);
                            chipActividadComercialVentaMuebles.setChecked(false);
                        }
                    }
                });

                chipActividadComercialLocacionServicios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (checked) {

                            double ingresoAnual = Double.parseDouble(editTextIngresoAnual.getText().toString());
                            double impuestoIntegrado = 0;
                            double aportes = 0;
                            if (ingresoAnual <= ingresosBrutosCatB) {
                                categoria = "B";
                                impuestoIntegrado = LoPSCatB;
                                aportes = aportesSIPACatB + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatC) {
                                categoria = "C";
                                impuestoIntegrado = LoPSCatC;
                                aportes = aportesSIPACatC + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatD) {
                                categoria = "D";
                                impuestoIntegrado = LoPSCatD;
                                aportes = aportesSIPACatD + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatE) {
                                categoria = "E";
                                impuestoIntegrado = LoPSCatE;
                                aportes = aportesSIPACatE + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatF) {
                                categoria = "F";
                                impuestoIntegrado = LoPSCatF;
                                aportes = aportesSIPACatF + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatG) {
                                categoria = "G";
                                impuestoIntegrado = LoPSCatG;
                                aportes = aportesSIPACatG + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatH) {
                                categoria = "H";
                                impuestoIntegrado = LoPSCatH;
                                aportes = aportesSIPACatH + aportesObraSocialCatTodas;
                            }
                            if (ingresoAnual > ingresosBrutosCatH) {
                                textViewResultadoEleccionMonotributo.setText("Teniendo en cuenta el monto de ingresos anuales que estimaste, no te corresponde ingresar al regimen de monotributo");
                            } else {
                                if (chipRelacionDependenciaSI.isChecked() || chipCajaProfesionalesSI.isChecked()) {
                                    costoMensualAfipCatMonotributo = impuestoIntegrado;
                                    textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría " + categoria + " y pagarás $" + impuestoIntegrado + " mensualmente a AFIP");
                                } else {
                                    costoMensualAfipCatMonotributo = impuestoIntegrado+aportes;
                                    textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría " + categoria + " y pagarás $" + (impuestoIntegrado + aportes) + " mensualmente a AFIP");
                                }
                            }
                            resultadoEleccionMonotributoVisible();
                        }
                        else {
                            scrollViewResultadoEleccionMonotributo.setVisibility(View.GONE);
                        }
                    }
                });

                chipActividadComercialVentaMuebles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (checked) {

                            ingresoAnual = Double.parseDouble(editTextIngresoAnual.getText().toString());
                            double impuestoIntegrado = 0;
                            double aportes = 0;
                            if (ingresoAnual <= ingresosBrutosCatB) {
                                categoria = "B";
                                impuestoIntegrado = VCMCatB;
                                aportes = aportesSIPACatB + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatC) {
                                categoria = "C";
                                impuestoIntegrado = VCMCatC;
                                aportes = aportesSIPACatC + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatD) {
                                categoria = "D";
                                impuestoIntegrado = VCMCatD;
                                aportes = aportesSIPACatD + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatE) {
                                categoria = "E";
                                impuestoIntegrado = VCMCatE;
                                aportes = aportesSIPACatE + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatF) {
                                categoria = "F";
                                impuestoIntegrado = VCMCatF;
                                aportes = aportesSIPACatF + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatG) {
                                categoria = "G";
                                impuestoIntegrado = VCMCatG;
                                aportes = aportesSIPACatG + aportesObraSocialCatTodas;
                            } else if (ingresoAnual <= ingresosBrutosCatH) {
                                categoria = "H";
                                impuestoIntegrado = VCMCatH;
                                aportes = aportesSIPACatH + aportesObraSocialCatTodas;
                            }else if (ingresoAnual <= ingresosBrutosCatI) {
                                categoria = "I";
                                impuestoIntegrado = VCMCatI;
                                aportes = aportesSIPACatI + aportesObraSocialCatTodas;
                            }else if (ingresoAnual <= ingresosBrutosCatJ) {
                                categoria = "J";
                                impuestoIntegrado = VCMCatJ;
                                aportes = aportesSIPACatJ + aportesObraSocialCatTodas;
                            }else if (ingresoAnual <= ingresosBrutosCatK) {
                                categoria = "K";
                                impuestoIntegrado = VCMCatK;
                                aportes = aportesSIPACatH + aportesObraSocialCatTodas;
                            }

                            if (ingresoAnual > ingresosBrutosCatK) {
                                textViewResultadoEleccionMonotributo.setText("Teniendo en cuenta el monto de ingresos anuales que estimaste, no te corresponde ingresar al regimen de monotributo");
                            } else {
                                if (chipRelacionDependenciaSI.isChecked() || chipCajaProfesionalesSI.isChecked()) {
                                    costoMensualAfipCatMonotributo = impuestoIntegrado;
                                    textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría " + categoria + " y pagarás $" + impuestoIntegrado + " mensualmente a AFIP");
                                } else {
                                    costoMensualAfipCatMonotributo = impuestoIntegrado + aportes;
                                    textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría " + categoria + " y pagarás $" + (impuestoIntegrado + aportes) + " mensualmente a AFIP");
                                }
                            }
                            resultadoEleccionMonotributoVisible();
                        }
                        else {
                            scrollViewResultadoEleccionMonotributo.setVisibility(View.GONE);
                        }
                    }
                });

                chipObraSocialSI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        categoria = "A";
                        costoMensualAfipCatMonotributo = VCMCatA + aportesObraSocialCatTodas;
                        textViewResultadoEleccionMonotributo.setText("Te corresponde la categoría A y pagarás $" + (VCMCatA + aportesObraSocialCatTodas) + " mensualmente a AFIP");
                        resultadoEleccionMonotributoVisible();
                    }
                });

                chipObraSocialNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        categoria = "Promovido";
                        costoMensualAfipCatMonotributo = 0;
                        textViewResultadoEleccionMonotributo.setText("Te corresponde monotributo PROMOVIDO. \n Pagarás $" + minimoMonotributoPromovido
                                + " o el 5% de tu facturación mensual (el valor más alto) mensualmente a AFIP");
                        resultadoEleccionMonotributoVisible();
                    }
                });

                break;
            case 3: //Solicitud de monotributo enviada


                //INFORMACIÓN SOLICITUD ENVIADA
                constraintLayoutInformaciónSolicitudEnviada = view.findViewById(R.id.cl_estado_monotributo_solicitado);
                textViewInfoCuit = view.findViewById(R.id.textView_monotributo_info_cuit);
                textViewInfoClaveFiscal = view.findViewById(R.id.textView_monotributo_info_clave_fiscal);
                textViewInfoNombre= view.findViewById(R.id.textView_monotributo_info_nombre);
                textViewInfoApellido = view.findViewById(R.id.textView_monotributo_info_apellido);
                textViewInfoRelacionDependencia = view.findViewById(R.id.textView_monotributo_info_relacion_dependencia);

                constraintLayoutInformaciónSolicitudEnviada.setVisibility(View.VISIBLE);
                textViewInfoNombre.setText(user.getNombre());
                textViewInfoApellido.setText(user.getApellido());
                break;
        }
    }


    private void resultadoEleccionMonotributoVisible() {
        //seteamos los valores del cardView "resumen"
        double ingresoAnual = Double.parseDouble(editTextIngresoAnual.getText().toString());
        //Si elige locacion de servicios no puede superar ingresos anuales de cat H
        //Y en ningún caso el ingreso anual puede superar la última categoría de monotributo (K)
        if ((ingresoAnual>ingresosBrutosCatH && chipActividadComercialLocacionServicios.isChecked()) || ingresoAnual>ingresosBrutosCatK){
            cardViewResumenEleccionMonotributo.setVisibility(View.GONE);
        }
        else {
            textViewResumenEleccionMonotributoIngresoAnual.setText("$" + editTextIngresoAnual.getText());
            if (chipRelacionDependenciaSI.isChecked()) {
                textViewResumenEleccionMonotributoRelacionDependencia.setText("SI");
            } else if (chipRelacionDependenciaNO.isChecked()) {
                textViewResumenEleccionMonotributoRelacionDependencia.setText("NO");
            }

            if (chipCajaProfesionalesSI.isChecked()) {
                textViewResumenEleccionMonotributoCajaProfesionales.setText("SI");
            } else if (chipCajaProfesionalesNO.isChecked()) {
                textViewResumenEleccionMonotributoCajaProfesionales.setText("NO");
            } else {
                textViewResumenEleccionMonotributoDescripcionCajaProfesionales.setVisibility(View.GONE);
            }

            if (chipActividadComercialLocacionServicios.isChecked()) {
                chipResumenEleccionMonotributoActividadComercial.setText("Locación o prestación de servicios");
            } else if (chipActividadComercialVentaMuebles.isChecked()) {
                chipResumenEleccionMonotributoActividadComercial.setText("Venta de cosas muebles");
            } else {
                chipResumenEleccionMonotributoActividadComercial.setVisibility(View.GONE);
                textViewResumenEleccionMonotributoDescripcionActividadComercial.setVisibility(View.GONE);
            }

            if (chipObraSocialSI.isChecked()) {
                textViewResumenEleccionMonotributoObraSocial.setText("SI");
            } else if (chipObraSocialNO.isChecked()) {
                textViewResumenEleccionMonotributoObraSocial.setText("NO");
            } else {
                textViewResumenEleccionMonotributoDescripcionObraSocial.setVisibility(View.GONE);
            }

            dividerInferiorEleccionMonotributo.setVisibility(View.VISIBLE);
            btnSolicitarMonotributo.setVisibility(View.VISIBLE);
        }


        textViewPaso1de2.setVisibility(View.GONE);
        textViewTituloEleccionMonotributo.setVisibility(View.GONE);
        dividerSuperiorEleccionMonotributo.setVisibility(View.GONE);
        scrollViewEleccionMonotributo.setVisibility(View.GONE);
        scrollViewResultadoEleccionMonotributo.setVisibility(View.VISIBLE);

    }

    private void scrollAbajo (final ScrollView scrollView){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void startMercadoPagoCheckout(final String checkoutPreferenceId) {
        new MercadoPagoCheckout.Builder("TEST-e1ccd5c4-8cf7-4214-a41e-e573f86e9ad6", checkoutPreferenceId).build()
                .startPayment(getContext(), REQUEST_CODE);
        Log.e("startMP", "entra al metodo");
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressDialog.dismiss();

            String preferenceIdMercadoPago = intent.getExtras().getString("preferenceId");
            Log.e("onReceive", "id " + preferenceIdMercadoPago);
            startMercadoPagoCheckout(preferenceIdMercadoPago);

        }
    };



}