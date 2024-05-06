package entities.register;

import java.sql.Timestamp;

public class MemoriaRegistro {
    /*
    idmemoriaRegistro INT auto_increment,
  valor DOUBLE,
  horario DATETIME,
  memoria INT,
  totem INT,
     */
    private Integer idmemoriaRegistro;
    private Double valor;
    private Timestamp horario;
    private Integer memoria;
    private Integer totem;

    public MemoriaRegistro() {}

    public MemoriaRegistro(Double valor, Timestamp horario, Integer memoria, Integer totem) {
        this.valor = valor;
        this.horario = horario;
        this.memoria = memoria;
        this.totem = totem;
    }

    public MemoriaRegistro(Integer idmemoriaRegistro, Double valor, Timestamp horario, Integer memoria, Integer totem) {
        this.idmemoriaRegistro = idmemoriaRegistro;
        this.valor = valor;
        this.horario = horario;
        this.memoria = memoria;
        this.totem = totem;
    }

    public Integer getIdmemoriaRegistro() {
        return idmemoriaRegistro;
    }

    public void setIdmemoriaRegistro(Integer idmemoriaRegistro) {
        this.idmemoriaRegistro = idmemoriaRegistro;
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

    public Integer getMemoria() {
        return memoria;
    }

    public void setMemoria(Integer memoria) {
        this.memoria = memoria;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    @Override
    public String toString() {
        return "MemoriaRegistro{" +
                "idmemoriaRegistro=" + idmemoriaRegistro +
                ", valor=" + valor +
                ", horario=" + horario +
                ", memoria=" + memoria +
                ", totem=" + totem +
                '}';
    }
}
