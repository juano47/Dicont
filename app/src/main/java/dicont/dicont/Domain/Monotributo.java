package dicont.dicont.Domain;

import com.google.firebase.database.snapshot.IndexedNode;

import java.io.Serializable;

public class Monotributo implements Serializable {

    double ingresoAnual;
    String relacionDependencia;
    String cajaProfesionales;
    String obraSocial;
    String actividadComercial;
    String categoria;
    double monto;

    public Monotributo() {
    }

    public Monotributo(double ingresoAnual, String relacionDependencia, String cajaProfesionales, String obraSocial, String actividadComercial, String categoria, double monto) {
        this.ingresoAnual = ingresoAnual;
        this.relacionDependencia = relacionDependencia;
        this.cajaProfesionales = cajaProfesionales;
        this.obraSocial = obraSocial;
        this.actividadComercial = actividadComercial;
        this.categoria = categoria;
        this.monto = monto;
    }

    public double getIngresoAnual() {
        return ingresoAnual;
    }

    public void setIngresoAnual(double ingresoAnual) {
        this.ingresoAnual = ingresoAnual;
    }

    public String getRelacionDependencia() {
        return relacionDependencia;
    }

    public void setRelacionDependencia(String relacionDependencia) {
        this.relacionDependencia = relacionDependencia;
    }

    public String getCajaProfesionales() {
        return cajaProfesionales;
    }

    public void setCajaProfesionales(String cajaProfesionales) {
        this.cajaProfesionales = cajaProfesionales;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getActividadComercial() {
        return actividadComercial;
    }

    public void setActividadComercial(String actividadComercial) {
        this.actividadComercial = actividadComercial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
