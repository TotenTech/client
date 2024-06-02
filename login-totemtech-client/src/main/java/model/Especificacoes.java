package model;

public class Especificacoes {
    private Integer idespecificacao;
    private String nome;
    private String valor;
    private String unidadeMedida;
    private Integer componente;
    private Integer tipo;

    public Especificacoes() {}

    public Especificacoes(Integer idespecificacao, String nome, String valor, String unidadeMedida, Integer componente, Integer tipo) {
        this.idespecificacao = idespecificacao;
        this.nome = nome;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
        this.componente = componente;
        this.tipo = tipo;
    }

    public Especificacoes(String nome, String valor, String unidadeMedida, Integer componente) {
        this.nome = nome;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
        this.componente = componente;
    }

    public Especificacoes(String nome, String valor, String unidadeMedida, Integer componente, Integer tipo) {
        this.nome = nome;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
        this.componente = componente;
        this.tipo = tipo;
    }

    public Integer getIdespecificacao() {
        return idespecificacao;
    }

    public void setIdespecificacao(Integer idespecificacao) {
        this.idespecificacao = idespecificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Integer getComponente() {
        return componente;
    }

    public void setComponente(Integer componente) {
        this.componente = componente;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Especificacoes{" +
                "idespecificacao=" + idespecificacao +
                ", nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                ", unidadeMedida='" + unidadeMedida + '\'' +
                ", componente=" + componente +
                ", tipo=" + tipo +
                '}';
    }
}
