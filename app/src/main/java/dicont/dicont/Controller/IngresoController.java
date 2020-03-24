package dicont.dicont.Controller;

import android.util.Log;

import dicont.dicont.Model.DataUser;
import dicont.dicont.Model.User;
import dicont.dicont.Repository.Firebase.Authentication.UserAuthFirebase;
import dicont.dicont.Repository.Firebase.Database.UserDatabaseFirebase;
import dicont.dicont.Repository.UserRepository;
import dicont.dicont.Views.Login.Ingreso;

public class IngresoController{

    private static IngresoController ourInstance;

    private Ingreso.CallbackInterfaceIngreso mCallbackIngreso;

    public static IngresoController getInstance() {
        if(ourInstance == null){
            ourInstance =new IngresoController();
        }
        return ourInstance;
    }

    private IngresoController() {
        //seteamos la interfaz de comunicacion con UserDatabaseFirebase y UserAuthFirebase por única vez
        UserAuthFirebase.getInstance().setmCallbackIngresoController(callbackInterfaceUserDatabaseFirebase);
        UserDatabaseFirebase.getInstance().setmCallbackIngresoController(callbackInterfaceUserDatabaseFirebase);
    }

    public void setmCallbackIngreso (Ingreso.CallbackInterfaceIngreso mCallbackIngreso){
        ourInstance.mCallbackIngreso = mCallbackIngreso;
    }

    public void loginUser(String email, String pass) {
        //primero verificamos si los datos son correctos. Se hace usando el servicio de Firebase Auth
        UserRepository.getInstance().validarUser(email, pass);
        //Continua en
    }

    private void ingresoExitoso(User user){
        //seteamos el user en el singleton DataUser
        DataUser.getInstance().setUser(user);
        //devolvemos la ejecución al activity Ingreso
        mCallbackIngreso.onResultLoginUser();
    }

    public interface CallbackInterfaceUserDatabaseFirebase {
        void onResultValidarUser(Boolean result);
        void onResultGetUser(User user);
        void onResultUpdateUser(User user);
        void showMessageError(String message);
    }

    private CallbackInterfaceUserDatabaseFirebase callbackInterfaceUserDatabaseFirebase = new CallbackInterfaceUserDatabaseFirebase() {

        @Override
        public void onResultValidarUser(Boolean result){
            if (result){
                if (UserRepository.getInstance().checkearEmailVerificado()){
                    Log.e("if 1 loginUser", "entra");
                    //obtenemos el usuario de la base de datos. Usamos el servicio de Firebase Database
                    UserRepository.getInstance().getUser();
                    //Continua en el método onResultGetUser
                } else {
                    mCallbackIngreso.showMessageError("Verifica tu correo para poder ingresar");
                }
            }else {
                mCallbackIngreso.showMessageError("Los datos ingresados no son correctos");
            }

        }

        @Override
        public void onResultGetUser(User user) {
            //Si el estado está en 0: lo actualizamos a 1 -> pasa del estado "email no verificado" a "email verificado"
            if (user.getEstado()==0) {
                user.setEstado(1);
                UserRepository.getInstance().updateUser(user);
                //continúa en el método onResultUpdateUser

            } else {
                ingresoExitoso(user);
            }
        }

        @Override
        public void onResultUpdateUser(User user) {
            ingresoExitoso(user);
        }

        @Override
        public void showMessageError(String message) {
            mCallbackIngreso.showMessageError(message);
        }
    };
}
