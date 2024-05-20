package model.register;

import java.sql.Timestamp;

public class Registro {

    private Integer idRegistro;
    private String valor;
    private String unidadeMedida;
    private Timestamp horario;
    private Integer componente;

    public Registro() {}

    public Registro(Integer idRegistro, String valor, String unidadeMedida, Timestamp horario, Integer componente) {
        this.idRegistro = idRegistro;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
        this.horario = horario;
        this.componente = componente;
    }

    public Registro(String valor, String unidadeMedida, Timestamp horario, Integer componente) {
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
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

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
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
