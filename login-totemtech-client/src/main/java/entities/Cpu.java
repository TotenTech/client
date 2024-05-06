package entities;
import java.sql.Timestamp;
public class Cpu {

    private Integer idcpu;
    private String medidaVelocidade;
    private Double velocidade;
    private Integer totem;

    public Cpu() {}

    public Cpu(
            String medidaVelocidade,
            Double velocidade,
            Integer totem
    ) {
        this.medidaVelocidade = medidaVelocidade;
        this.velocidade = velocidade;
        this.totem = totem;
    }

    public Cpu(
            Integer idcpu,
            String medidaVelocidade,
            Double velocidade,
            Integer totem
    ) {
        this.idcpu = idcpu;
        this.medidaVelocidade = medidaVelocidade;
        this.velocidade = velocidade;
        this.totem = totem;
    }

    public Integer getIdCpu() {
        return idcpu;
    }

    public void setIdCpu(Integer idCpu) {
        this.idcpu = idCpu;
    }

    public String getMedidaVelocidade() {
        return medidaVelocidade;
    }

    public void setMedidaVelocidade(String medidaVelocidade) {
        this.medidaVelocidade = medidaVelocidade;
    }

    public Double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Double velocidade) {
        this.velocidade = velocidade;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    @Override
    public String toString() {
        return "Cpu{" +
                "idcpu=" + idcpu +
                ", medidaVelocidade='" + medidaVelocidade + '\'' +
                ", velocidade=" + velocidade +
                ", totem=" + totem +
                '}';
    }
}
