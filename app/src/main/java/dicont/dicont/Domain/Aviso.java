package dicont.dicont.Domain;
import java.io.Serializable;

public class Aviso implements Serializable {
    String titulo;
    String tipo;
    String mensaje;

    public Aviso() {
    }

    public Aviso(String titulo, String mensaje, String tipo) {
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
