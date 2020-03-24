package dicont.dicont.Repository;

import dicont.dicont.Controller.IngresoController;
import dicont.dicont.Model.User;
import dicont.dicont.Repository.Firebase.Authentication.UserAuthFirebase;
import dicont.dicont.Repository.Firebase.Database.UserDatabaseFirebase;

public class UserRepository {
    private static UserRepository ourInstance;

    public static UserRepository getInstance() {
        if (ourInstance == null){
            ourInstance = new UserRepository();
        }
        return ourInstance;
    }

    private UserRepository() {
    }

    public void validarUser(String email, String pass) {
        UserAuthFirebase.getInstance().validarUser(email, pass);
    }

    public boolean checkearEmailVerificado() {
        return UserAuthFirebase.getInstance().checkearEmailVerificado();
    }

    public void getUser() {
        UserDatabaseFirebase.getInstance().getUser();
    }

    public void updateUser(User user) {
        UserDatabaseFirebase.getInstance().updateUser(user);
    }
}
