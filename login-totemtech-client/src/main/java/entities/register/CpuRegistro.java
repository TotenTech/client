package entities.register;

import java.sql.Timestamp;

public class CpuRegistro {
    /*
    idcpuRegistro INT auto_increment,
  utilizacao DOUBLE,
  horario DATETIME,
  velocidade DOUBLE,
  processos INT,
  cpu INT,
  totem INT,
     */
    private Integer idcpuRegistro;
    private Double utilizacao;
    private Timestamp horario;
    private Double velocidade;
    private Integer processos;
    private Integer cpu;
    private Integer totem;

    public CpuRegistro() {}

    public CpuRegistro(Double utilizacao, Timestamp horario, Double velocidade, Integer processos, Integer cpu, Integer totem) {
        this.utilizacao = utilizacao;
        this.horario = horario;
        this.processos = processos;
        this.cpu = cpu;
        this.totem = totem;
    }

    public CpuRegistro(Integer idcpuRegistro, Double utilizacao, Timestamp horario, Integer processos, Integer cpu, Integer totem) {
        this.idcpuRegistro = idcpuRegistro;
        this.utilizacao = utilizacao;
        this.horario = horario;
        this.processos = processos;
        this.cpu = cpu;
        this.totem = totem;
    }

    public Integer getIdcpuRegistro() {
        return idcpuRegistro;
    }

    public void setIdcpuRegistro(Integer idcpuRegistro) {
        this.idcpuRegistro = idcpuRegistro;
    }

    public Double getUtilizacao() {
        return utilizacao;
    }

    public void setUtilizacao(Double utilizacao) {
        this.utilizacao = utilizacao;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public Integer getProcessos() {
        return processos;
    }

    public void setProcessos(Integer processos) {
        this.processos = processos;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    @Override
    public String toString() {
        return "CpuRegistro{" +
                "idcpuRegistro=" + idcpuRegistro +
                ", utilizacao=" + utilizacao +
                ", horario=" + horario +
                ", velocidade=" + velocidade +
                ", processos=" + processos +
                ", cpu=" + cpu +
                ", totem=" + totem +
                '}';
    }
}
