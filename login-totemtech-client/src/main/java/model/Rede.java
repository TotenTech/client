package model;

import java.util.List;

public class Rede extends Componente {

    public Rede() {}

    public Rede(Integer idComponente, Integer totem, String nome, Integer tipo) {
        super(idComponente, totem, nome, tipo);
    }

    public Rede(Integer tipo, String nome, Integer totem) {
        super(tipo, nome, totem);
    }

    public Rede(Integer idComponente, Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        super(idComponente, tipo, nome, totem, especificacoes);
    }

    public Rede(Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        super(tipo, nome, totem, especificacoes);
    }
}
