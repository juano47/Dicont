package dicont.dicont.Controller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dicont.dicont.EventBus.MessageEvent;
import dicont.dicont.EventBus.MessageEventUserAuthFirebase;
import dicont.dicont.EventBus.MessageEventUserDatabaseFirebase;
import dicont.dicont.Repository.Firebase.Authentication.UserAuthFirebase;
import dicont.dicont.Repository.Firebase.Database.UserDatabaseFirebase;
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
    }

    public void setmCallbackRegistro (Registro.CallbackInterfaceRegistro mCallbackRegistro){
        ourInstance.mCallbackRegistro = mCallbackRegistro;
    }

    public void userRegister(String nombre, String apellido, String email, String clave) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

        //Nos registramos al bus de eventos para recibir la respuesta asincrona de los métodos de Firebase
        EventBus.getDefault().register(RegistroController.this);

        UserAuthFirebase.getInstance().userRegister(email, clave);
    }

        @Subscribe
        public void onResultUserRegister(MessageEventUserAuthFirebase.userRegister messageEvent) {
            mCallbackRegistro.showMessageSuccess("El usuario se registro con éxito");
            UserAuthFirebase.getInstance().sendEmailVerification();
        }

        @Subscribe
        public void onResultSendEmailVerification(MessageEventUserAuthFirebase.sendEmailVerification messageEvent) {
            mCallbackRegistro.showMessageSuccess("Se envió el email de verificación!");
            UserDatabaseFirebase.getInstance().crearUser(nombre, apellido, email);
        }

        @Subscribe
        public void showMessageError(MessageEvent messageEvent) {
            EventBus.getDefault().unregister(RegistroController.this);
            mCallbackRegistro.showMessageError(messageEvent.messageError);
        }

        @Subscribe
        public void onResultCrearUser(MessageEventUserDatabaseFirebase.crearUser messageEvent) { //Único punto exitoso de salida para el registro
            EventBus.getDefault().unregister(RegistroController.this);
            mCallbackRegistro.showMessageSuccess("Se creo el usuario en la base de datos!");
            mCallbackRegistro.onResultUserRegister();
        }
}
