package model;

import java.util.List;

public class Componente {

    protected Integer idComponente;
    protected Integer tipo;
    protected String nome;
    protected Integer totem;
    protected List<Especificacoes> especificacoes;

    public Componente() {}

    public Componente(Integer idComponente, Integer totem, String nome, Integer tipo) {
        this.idComponente = idComponente;
        this.tipo = tipo;
        this.nome = nome;
        this.totem = totem;
    }

    public Componente(Integer tipo, String nome, Integer totem) {
        this.tipo = tipo;
        this.nome = nome;
        this.totem = totem;
    }

    public Componente(Integer idComponente, Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        this.idComponente = idComponente;
        this.tipo = tipo;
        this.nome = nome;
        this.totem = totem;
        this.especificacoes = especificacoes;
    }

    public Componente(Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        this.tipo = tipo;
        this.nome = nome;
        this.totem = totem;
        this.especificacoes = especificacoes;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public List<Especificacoes> getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(List<Especificacoes> especificacoes) {
        this.especificacoes = especificacoes;
    }
}
