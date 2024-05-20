package model;

public abstract class Componente {

    protected Integer idComponente;
    private String tipo;
    private String nome;
    private Double total;
    private Double minimo;
    private Double maximo;
    private Integer totem;

    public Componente() {}

    public Componente(Integer idComponente, String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        this.idComponente = idComponente;
        this.tipo = tipo;
        this.nome = nome;
        this.total = total;
        this.minimo = minimo;
        this.maximo = maximo;
        this.totem = totem;
    }

    public Componente(String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        this.tipo = tipo;
        this.nome = nome;
        this.total = total;
        this.minimo = minimo;
        this.maximo = maximo;
        this.totem = totem;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }
}
