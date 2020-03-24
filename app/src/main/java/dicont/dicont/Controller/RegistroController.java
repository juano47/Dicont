package dicont.dicont.Controller;

import dicont.dicont.Model.User;
import dicont.dicont.Repository.Firebase.Authentication.UserAuthFirebase;
import dicont.dicont.Repository.Firebase.Database.UserDatabaseFirebase;
import dicont.dicont.Repository.UserRepository;
import dicont.dicont.Views.Login.Ingreso;
import dicont.dicont.Views.Login.Registro;

public class RegistroController {
    private static RegistroController ourInstance;

    //Datos de usuario
    private String nombre;
    private String apellido;
    private String email;

    private Registro.CallbackInterfaceRegistro mCallbackRegistro;

    public static RegistroController getInstance() {
        if(ourInstance == null){
            ourInstance =new RegistroController();
        }
        return ourInstance;
    }

    private RegistroController() {
        //seteamos la interfaz de comunicacion con UserDatabaseFirebase y UserAuthFirebase por única vez
        UserAuthFirebase.getInstance().setmCallbackRegistroController(callbackInterfaceRegistroController);
        UserDatabaseFirebase.getInstance().setmCallbackRegistroController(callbackInterfaceRegistroController);
    }

    public void setmCallbackRegistro (Registro.CallbackInterfaceRegistro mCallbackRegistro){
        ourInstance.mCallbackRegistro = mCallbackRegistro;
    }

    public void userRegister(String nombre, String apellido, String email, String clave) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

        UserRepository.getInstance().userRegister(email, clave);
    }

    public interface CallbackInterfaceRegistroController {
        void onResultRegisterUser();
        void onResultSendEmailVerification();
        void showMessageError(String message);
        void onResultCrearUser();
    }

    public CallbackInterfaceRegistroController callbackInterfaceRegistroController = new CallbackInterfaceRegistroController() {
        @Override
        public void onResultRegisterUser() {
            mCallbackRegistro.showMessageSuccess("El usuario se registro con éxito");
            UserRepository.getInstance().sendEmailVerification();
        }

        @Override
        public void onResultSendEmailVerification() {
            mCallbackRegistro.showMessageSuccess("Se envió el email de verificación!");
            UserRepository.getInstance().crearUser(nombre, apellido, email);
        }

        @Override
        public void showMessageError(String message) {
            mCallbackRegistro.showMessageError(message);
        }

        @Override
        public void onResultCrearUser() {
            mCallbackRegistro.showMessageSuccess("Se creo el usuario en la base de datos!");
            mCallbackRegistro.onResultUserRegister();
        }
    };
}
