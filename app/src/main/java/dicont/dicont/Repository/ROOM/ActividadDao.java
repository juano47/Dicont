package dicont.dicont.Repository.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dicont.dicont.Model.Actividad;

@Dao
public interface ActividadDao {
    @Query("SELECT * FROM actividad")
    List<Actividad> getAll();

    @Insert
    void insert(Actividad actividad);

    @Insert
    void insertAll(Actividad... actividades);

    @Delete
    void delete(Actividad actividad);

    @Update
    void actualizar(Actividad actividad);

    @Query("SELECT * FROM actividad WHERE id=:idActividad")
    Actividad getActividad(Integer idActividad);
}
