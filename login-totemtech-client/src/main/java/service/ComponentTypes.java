package service;

public enum ComponentTypes {
    DISCO("Disco"),
    REDE("Rede"),
    MEMORIA("Memoria"),
    CPU("Cpu");

    private final String tipo;

    ComponentTypes(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
