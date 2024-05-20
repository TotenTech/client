package model;

public class Disco extends Componente {

    public Disco() {}

    public Disco(Integer idComponente, String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(idComponente, tipo, nome, total, minimo, maximo, totem);
    }

    public Disco(String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(tipo, nome, total, minimo, maximo, totem);
    }
}
