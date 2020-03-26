package dicont.dicont.Controller;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dicont.dicont.EventBus.MessageEvent;
import dicont.dicont.EventBus.MessageEventUserAuthFirebase;
import dicont.dicont.EventBus.MessageEventUserDatabaseFirebase;
import dicont.dicont.Model.DataUser;
import dicont.dicont.Model.User;
import dicont.dicont.Repository.Firebase.Authentication.UserAuthFirebase;
import dicont.dicont.Repository.Firebase.Database.UserDatabaseFirebase;
import dicont.dicont.Views.Login.Ingreso;

public class IngresoController {

    private static IngresoController ourInstance;

    private Ingreso.CallbackInterfaceIngreso mCallbackIngreso;

    public static IngresoController getInstance() {
        if(ourInstance == null){
            ourInstance =new IngresoController();
        }

        return ourInstance;
    }

    private IngresoController() {
    }

    public void setmCallbackIngreso (Ingreso.CallbackInterfaceIngreso mCallbackIngreso){
        ourInstance.mCallbackIngreso = mCallbackIngreso;
    }

    public void loginUser(String email, String pass) {
        //Nos registramos al bus de eventos para recibir la respuesta asincrona de los métodos de Firebase
        EventBus.getDefault().register(IngresoController.this);
        //primero verificamos si los datos son correctos. Se hace usando el servicio de Firebase Auth
        UserAuthFirebase.getInstance().validarUser(email, pass);
        //Continua en onResultValidarUser
    }

    private void ingresoExitoso(User user){
        EventBus.getDefault().unregister(IngresoController.this);
        //seteamos el user en el singleton DataUser
        DataUser.getInstance().setUser(user);
        //devolvemos la ejecución al activity Ingreso
        mCallbackIngreso.onResultLoginUser(); //Único punto de accceso exitoso del Login
    }

    public void restablecerClave(String email) {
        //Nos registramos al bus de eventos para recibir la respuesta asincrona de los métodos de Firebase
        EventBus.getDefault().register(IngresoController.this);
        UserAuthFirebase.getInstance().restablecerClave(email);
        //sigue en onResultRestablecerClave
    }

    @Subscribe
    public void onResultValidarUser(MessageEventUserAuthFirebase.validarUser messageEvent) {

        if (messageEvent.result){
            if (UserAuthFirebase.getInstance().checkearEmailVerificado()){
                Log.e("if 1 loginUser", "entra");
                //obtenemos el usuario de la base de datos. Usamos el servicio de Firebase Database
                UserDatabaseFirebase.getInstance().getUser();
                //Continua en el método onResultGetUser
            } else {
                EventBus.getDefault().unregister(IngresoController.this);
                mCallbackIngreso.showMessageError("Verifica tu correo para poder ingresar");
            }
        }else {
            EventBus.getDefault().unregister(IngresoController.this);
            mCallbackIngreso.showMessageError("Los datos ingresados no son correctos");
        }
    }

    @Subscribe
    public void onResultGetUser(MessageEventUserDatabaseFirebase.getUser messageEvent) {
        //Si el estado está en 0: lo actualizamos a 1 -> pasa del estado "email no verificado" a "email verificado"
        User user = messageEvent.user;
        if (user.getEstado()==0) {
            user.setEstado(1);
            UserDatabaseFirebase.getInstance().updateUser(user);
            //continúa en el método onResultUpdateUser o showMessageError
        } else {
            ingresoExitoso(user);
        }
    }

        @Subscribe
        public void onResultUpdateUser(MessageEventUserDatabaseFirebase.updateUser messageEvent) {
            ingresoExitoso(messageEvent.user);
        }

        @Subscribe
        public void showMessageError(MessageEvent messageEvent) {
            EventBus.getDefault().unregister(IngresoController.this);
            mCallbackIngreso.showMessageError(messageEvent.messageError);
        }

        @Subscribe
        public void onResultRestablecerClave(MessageEventUserAuthFirebase.restablecerClave messageEvent) {
            EventBus.getDefault().unregister(IngresoController.this);
            if (messageEvent.result){
                mCallbackIngreso.onResultRestablecerClave();
            } else {
                mCallbackIngreso.showMessageError("No tenemos un usuario registrado con ese correo. Intenta de nuevo o prueba registrarte sino lo has hecho");
            }
        }
}