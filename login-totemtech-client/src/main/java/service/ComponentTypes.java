package service;

public enum ComponentTypes {
    DISCO(3),
    REDE(4),
    MEMORIA(2),
    CPU(1),
    TOTAL(5);

    private final Integer tipo;

    ComponentTypes(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }
}
