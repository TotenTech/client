package model;

public class Rede extends Componente {

    public Rede() {}

    public Rede(Integer idComponente, String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(idComponente, tipo, nome, total, minimo, maximo, totem);
    }

    public Rede(String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(tipo, nome, total, minimo, maximo, totem);
    }
}
