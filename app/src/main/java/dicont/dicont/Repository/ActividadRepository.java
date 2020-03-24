package dicont.dicont.Repository;

public class ActividadRepository {
    private static final ActividadRepository ourInstance = new ActividadRepository();

    public static ActividadRepository getInstance() {
        return ourInstance;
    }

    private ActividadRepository() {
    }
}
