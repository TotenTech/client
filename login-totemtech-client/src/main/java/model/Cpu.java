package model;

import java.util.List;

public class Cpu extends Componente{

    public Cpu() {}

    public Cpu(Integer idComponente, Integer totem, String nome, Integer tipo) {
        super(idComponente, totem, nome, tipo);
    }

    public Cpu(Integer tipo, String nome, Integer totem) {
        super(tipo, nome, totem);
    }

    public Cpu(Integer idComponente, Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        super(idComponente, tipo, nome, totem, especificacoes);
    }

    public Cpu(Integer tipo, String nome, Integer totem, List<Especificacoes> especificacoes) {
        super(tipo, nome, totem, especificacoes);
    }
}
