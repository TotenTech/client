package model;

public class Cpu extends Componente{

    public Cpu() {}

    public Cpu(Integer idComponente, String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(idComponente, tipo, nome, total, minimo, maximo, totem);
    }

    public Cpu(String tipo, String nome, Double total, Double minimo, Double maximo, Integer totem) {
        super(tipo, nome, total, minimo, maximo, totem);
    }
}
