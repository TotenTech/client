package slack;

import java.time.LocalDateTime;

public class RegistroComponentes {
    private Integer idTotem;
    private String nomeTotem;
    private String nomeComponente;
    private String tipoComponente;
    private Double valor;
    private LocalDateTime horario;

    public RegistroComponentes(Integer idTotem, String nomeTotem, String nomeComponente, String tipoComponente, Double valor, LocalDateTime horario) {
        this.idTotem = idTotem;
        this.nomeTotem = nomeTotem;
        this.nomeComponente = nomeComponente;
        this.tipoComponente = tipoComponente;
        this.valor = valor;
        this.horario = horario;
    }

    public Integer getIdTotem() {
        return idTotem;
    }

    public void setIdTotem(Integer idTotem) {
        this.idTotem = idTotem;
    }

    public String getNomeTotem() {
        return nomeTotem;
    }

    public void setNomeTotem(String nomeTotem) {
        this.nomeTotem = nomeTotem;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
}

