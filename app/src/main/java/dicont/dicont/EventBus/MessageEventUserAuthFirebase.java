package dicont.dicont.EventBus;

public class MessageEventUserAuthFirebase extends MessageEvent {

    public MessageEventUserAuthFirebase(String messageError) {
        super(messageError);
    }

    public static class validarUser {
        public boolean result;

        public validarUser(boolean result) {
            this.result = result;
        }
    }

    public static class restablecerClave {
        public boolean result;
        public restablecerClave(boolean result) {
            this.result = result;
        }
    }

    public static class userRegister {
    }

    public static class sendEmailVerification {
    }
}
