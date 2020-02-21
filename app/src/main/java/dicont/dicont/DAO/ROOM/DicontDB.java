package dicont.dicont.DAO.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import dicont.dicont.Domain.Actividad;


@Database(entities = Actividad.class, version = 1)
@TypeConverters({Converters.class})
public abstract class DicontDB extends RoomDatabase {
    public abstract ActividadDao actividadDao();
}
