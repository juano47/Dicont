package dicont.dicont.Repository.ROOM;

import android.content.Context;

import androidx.room.Room;

public class DBActividad {
    private static DBActividad DB = null;

    private DicontDB dicontDB;

    private DBActividad(Context ctx){
        dicontDB = Room.databaseBuilder(ctx,
                dicont.dicont.Repository.ROOM.DicontDB.class, "dicont-db").allowMainThreadQueries().build();
    }

    public synchronized static DBActividad getInstance(Context ctx){
        if(DB==null){
            DB = new DBActividad(ctx);
        }
        return DB;
    }

    public dicont.dicont.Repository.ROOM.DicontDB getDicontDB() {
        return dicontDB;
    }
}
