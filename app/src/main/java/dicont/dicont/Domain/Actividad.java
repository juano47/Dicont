package dicont.dicont.Domain;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Actividad {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Date fecha;
    String mensaje;
    Double precio;
    String tipo;

    public Actividad() {
    }

    public Actividad(Integer id, Date fecha, String mensaje, Double precio, String tipo) {
        this.id = id;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.precio = precio;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
