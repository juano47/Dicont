package dicont.dicont.Domain;

import android.provider.SearchRecentSuggestions;

import java.io.FileOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    String nombre;
    String apellido;
    String email;
    Integer estado;
    Boolean emailVerificado;
    Monotributo monotributo;
    Formulario formulario;

    public User(){

    }

    public User(String nombre, String apellido, String email, Integer estado, Boolean emailVerificado, Monotributo monotributo, Formulario formulario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.estado = estado;
        this.emailVerificado = emailVerificado;
        this.monotributo = monotributo;
        this.formulario = formulario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Boolean getEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(Boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    public Monotributo getMonotributo(){
        return monotributo;
    }

    public void setMonotributo(Monotributo monotributo){
        this.monotributo = monotributo;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
}

