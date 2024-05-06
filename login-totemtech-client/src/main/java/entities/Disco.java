package entities;

import java.sql.Timestamp;

public class Disco {

    private Integer iddisco;
    private String tipo;
    private Double total;
    private String medida;
    private Integer totem;

    public Disco() {}

    public Disco(String tipo, Double total, String medida, Integer totem) {
        this.tipo = tipo;
        this.total = total;
        this.medida = medida;
        this.totem = totem;
    }

    public Disco(Integer iddisco, String tipo, Double total, String medida, Integer totem) {
        this.iddisco = iddisco;
        this.tipo = tipo;
        this.total = total;
        this.medida = medida;
        this.totem = totem;
    }

    public Integer getIddisco() {
        return iddisco;
    }

    public void setIddisco(Integer iddisco) {
        this.iddisco = iddisco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "iddisco=" + iddisco +
                ", tipo='" + tipo + '\'' +
                ", total=" + total +
                ", medida='" + medida + '\'' +
                ", totem=" + totem +
                '}';
    }
}
