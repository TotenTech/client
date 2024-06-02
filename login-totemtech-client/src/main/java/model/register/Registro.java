package model.register;

import java.sql.Timestamp;

public class Registro {

    private Integer idRegistro;
    private String valor;
    private Timestamp horario;
    private Integer componente;

    public Registro() {}

    public Registro(Integer idRegistro, String valor, Timestamp horario, Integer componente) {
        this.idRegistro = idRegistro;
        this.valor = valor;
        this.horario = horario;
        this.componente = componente;
    }

    public Registro(String valor, Timestamp horario, Integer componente) {
        this.valor = valor;
        this.horario = horario;
        this.componente = componente;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public Integer getComponente() {
        return componente;
    }

    public void setComponente(Integer componente) {
        this.componente = componente;
    }
}
