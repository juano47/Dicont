package dicont.dicont.Domain;

public class Formulario {
    String dni;
    String sexo;
    String celular;
    String companiaCelular;


    public Formulario(String dni, String sexo, String celular, String companiaCelular) {
        this.dni = dni;
        this.sexo = sexo;
        this.celular = celular;
        this.companiaCelular = companiaCelular;
    }

    public Formulario() {

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCompaniaCelular() {
        return companiaCelular;
    }

    public void setCompaniaCelular(String companiaCelular) {
        this.companiaCelular = companiaCelular;
    }
}
