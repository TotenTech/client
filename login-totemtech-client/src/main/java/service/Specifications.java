package service;

public enum Specifications {
    MINIMO("minimo"),
    MAXIMO("maximo"),
    TOTAL("total"),
    IDEAL("ideal");

    private final String specification;

    Specifications(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }
}
