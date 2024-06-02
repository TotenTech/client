package model;

import java.util.List;

import static service.Specifications.TOTAL;

public class Disco extends Componente {

    public Disco() {}

    public Disco(Integer idComponente, Integer totem, String nome, Integer tipo) {
        super(idComponente, totem, nome, tipo);
    }

    public Disco(Integer tipo, String nome, Integer totem) {
        super(tipo, nome, totem);
    }

    public Disco(Integer idComponente, Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        super(idComponente, tipo, nome, totem, especificacoes);
    }

    public Disco(Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        super(tipo, nome, totem, especificacoes);
    }

    public Double getTotal() {
        return especificacoes.stream().filter(it -> it.getNome().equalsIgnoreCase(TOTAL.getSpecification()) && it.getComponente() == idComponente).mapToDouble(it -> Double.parseDouble(it.getValor())).sum();
    }
}
