package entities.register;

import java.sql.Timestamp;

public class DiscoRegistro {

    private Integer iddiscoRegistro;
    private Double valor;
    private Timestamp horario;
    private Integer disco;
    private Integer totem;

    public DiscoRegistro() {}

    public DiscoRegistro(Double valor, Timestamp horario, Integer disco, Integer totem) {
        this.valor = valor;
        this.horario = horario;
        this.disco = disco;
        this.totem = totem;
    }

    public DiscoRegistro(Integer iddiscoRegistro, Double valor, Timestamp horario, Integer disco, Integer totem) {
        this.iddiscoRegistro = iddiscoRegistro;
        this.valor = valor;
        this.horario = horario;
        this.disco = disco;
        this.totem = totem;
    }

    public Integer getIddiscoRegistro() {
        return iddiscoRegistro;
    }

    public void setIddiscoRegistro(Integer iddiscoRegistro) {
        this.iddiscoRegistro = iddiscoRegistro;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public Integer getDisco() {
        return disco;
    }

    public void setDisco(Integer disco) {
        this.disco = disco;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    @Override
    public String toString() {
        return "DiscoRegistro{" +
                "iddiscoRegistro=" + iddiscoRegistro +
                ", valor=" + valor +
                ", horario=" + horario +
                ", disco=" + disco +
                ", totem=" + totem +
                '}';
    }
}
