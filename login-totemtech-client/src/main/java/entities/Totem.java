package entities;

public class Totem {

    private Integer idtotem;
    private String nome;
    private String login;
    private String senha;
    private String sistemaOperacional;
    private Integer empresa;


    public Totem() {}

    public Totem(
            Integer idtotem,
            String nome,
            String login,
            String senha,
            String sistemaOperacional,
            Integer empresa
    ) {
        this.idtotem = idtotem;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.sistemaOperacional = sistemaOperacional;
        this.empresa = empresa;
    }

    public Totem(
            String nome,
            String login,
            String senha,
            String sistemaOperacional,
            Integer empresa
    ) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.sistemaOperacional = sistemaOperacional;
        this.empresa = empresa;
    }

    public Integer getIdTotem() {
        return idtotem;
    }

    public void setIdTotem(Integer idTotem) {
        this.idtotem = idTotem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Totem{" +
                "nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", sistemaOperacional='" + sistemaOperacional + '\'' +
                '}';
    }
}
