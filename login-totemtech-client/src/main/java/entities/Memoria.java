package entities;

import java.sql.Timestamp;

public class Memoria {

    private Integer idmemoria;
    private Double total;
    private String medida;
    private Integer totem;

    public Memoria() {}

    public Memoria(Double total, String medida, Integer totem) {
        this.total = total;
        this.medida = medida;
        this.totem = totem;
    }

    public Memoria(Integer idmemoria, Double total, String medida, Integer totem) {
        this.idmemoria = idmemoria;
        this.total = total;
        this.medida = medida;
        this.totem = totem;
    }

    public Integer getIdmemoria() {
        return idmemoria;
    }

    public void setIdmemoria(Integer idmemoria) {
        this.idmemoria = idmemoria;
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
        return "Memoria{" +
                "idmemoria=" + idmemoria +
                ", total=" + total +
                ", medida='" + medida + '\'' +
                ", totem=" + totem +
                '}';
    }
}
