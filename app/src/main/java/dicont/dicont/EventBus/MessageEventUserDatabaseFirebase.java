package dicont.dicont.EventBus;

import dicont.dicont.Model.User;

public class MessageEventUserDatabaseFirebase extends MessageEvent {

    public MessageEventUserDatabaseFirebase(String messageError) {
        super(messageError);
    }

    public static class getUser {
        public User user;

        public getUser(User user) {
            this.user = user;
        }
    }

    public static class updateUser {
        public User user;
        public updateUser(User user) {
            this.user = user;
        }
    }

    public static class crearUser {
    }
}
