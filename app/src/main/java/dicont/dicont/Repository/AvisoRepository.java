package dicont.dicont.Repository;

public class AvisoRepository {
    private static final AvisoRepository ourInstance = new AvisoRepository();

    public static AvisoRepository getInstance() {
        return ourInstance;
    }

    private AvisoRepository() {
    }
}
