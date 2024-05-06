package entities;

import java.sql.Timestamp;

public class Interrupcoes {

    private Integer idinterrupcoes;
    private Timestamp horario;
    private String motivo;
    private Integer totem;

    public Interrupcoes() {}

    public Interrupcoes(Timestamp horario, String motivo, Integer totem) {
        this.horario = horario;
        this.motivo = motivo;
        this.totem = totem;
    }

    public Interrupcoes(Integer idinterrupcoes, Timestamp horario, String motivo, Integer totem) {
        this.idinterrupcoes = idinterrupcoes;
        this.horario = horario;
        this.motivo = motivo;
        this.totem = totem;
    }

    public Integer getIdinterrupcoes() {
        return idinterrupcoes;
    }

    public void setIdinterrupcoes(Integer idinterrupcoes) {
        this.idinterrupcoes = idinterrupcoes;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    @Override
    public String toString() {
        return "Interrupcoes{" +
                "idinterrupcoes=" + idinterrupcoes +
                ", horario=" + horario +
                ", motivo='" + motivo + '\'' +
                ", totem=" + totem +
                '}';
    }
}
