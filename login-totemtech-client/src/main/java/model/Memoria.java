package model;

public class Memoria extends Componente {

    public Memoria() {}

    public Memoria(Integer idComponente, String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(idComponente, tipo, nome, total, minimo, maximo, totem);
    }

    public Memoria(String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(tipo, nome, total, minimo, maximo, totem);
    }
}
