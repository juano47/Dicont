package dicont.dicont.Domain;

public class DataUser {
    private static final DataUser ourInstance = new DataUser();

    private User user;

    public static DataUser getInstance() {
        return ourInstance;
    }

    private DataUser() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
