package dao;

import model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.local.LocalDatabaseConnection;
import repository.remote.RemoteDatabaseConnection;
import service.ComponentTypes;

import java.util.ArrayList;
import java.util.List;

import static service.ComponentTypes.*;

public class ComponentDAOImpl implements IComponentDAO {

    static LocalDatabaseConnection dbLocal = new LocalDatabaseConnection();
    static RemoteDatabaseConnection dbRemote = new RemoteDatabaseConnection();
    static JdbcTemplate db;

    @Override
    public List<Componente> getFromDatabase(Integer totem, ComponentTypes tipo) throws Exception {
        List<Componente> l = new ArrayList<>();
        try {
            db = dbRemote.getConexaoDoBanco();
            switch (tipo) {
                case DISCO -> {
                    List<Disco> listaDiscos = db.query("SELECT * FROM componente WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Disco.class), totem, tipo.getTipo());
                    if (!listaDiscos.isEmpty()) {
                        for (Disco disco : listaDiscos) {
                            List<Especificacoes> especificacoesList = db.query("SELECT * FROM especificacao WHERE componente = ?", new BeanPropertyRowMapper<>(Especificacoes.class), disco.getIdComponente());
                            disco.setEspecificacoes(especificacoesList);
                        }
                        l.addAll(listaDiscos);
                    }
                }
                case MEMORIA -> {
                    List<Memoria> listaMemoria = db.query("SELECT * FROM componente WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Memoria.class), totem, tipo.getTipo());
                    if (!listaMemoria.isEmpty()) {
                        for (Memoria memoria : listaMemoria) {
                            List<Especificacoes> especificacoesList = db.query("SELECT * FROM especificacao WHERE componente = ?", new BeanPropertyRowMapper<>(Especificacoes.class), memoria.getIdComponente());
                            memoria.setEspecificacoes(especificacoesList);
                        }
                        l.add(listaMemoria.get(0));
                    }
                }
                case CPU -> {
                    List<Cpu> listaCpu = db.query("SELECT * FROM componente WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Cpu.class), totem, tipo.getTipo());
                    if (!listaCpu.isEmpty()) {
                        for (Cpu cpu : listaCpu) {
                            List<Especificacoes> especificacoesList = db.query("SELECT * FROM especificacao WHERE componente = ?", new BeanPropertyRowMapper<>(Especificacoes.class), cpu.getIdComponente());
                            cpu.setEspecificacoes(especificacoesList);
                        }
                        l.add(listaCpu.get(0));
                    }
                }
                case REDE -> {
                    List<Rede> listaRede = db.query("SELECT * FROM componente WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Rede.class), totem, tipo.getTipo());
                    if (!listaRede.isEmpty()) {
                        for (Rede rede : listaRede) {
                            List<Especificacoes> especificacoesList = db.query("SELECT * FROM especificacao WHERE componente = ?", new BeanPropertyRowMapper<>(Especificacoes.class), rede.getIdComponente());
                            rede.setEspecificacoes(especificacoesList);
                        }
                        l.add(listaRede.get(0));
                    }
                }
                case TOTAL -> {
                        List<Disco> listaDisco = db.query("SELECT * FROM componente WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Disco.class), totem, tipo.getTipo());
                        if (!listaDisco.isEmpty()) {
                            for (Disco disco : listaDisco) {
                                List<Especificacoes> especificacoesList = db.query("SELECT * FROM especificacao WHERE componente = ?", new BeanPropertyRowMapper<>(Especificacoes.class), disco.getIdComponente());
                                disco.setEspecificacoes(especificacoesList);
                            }
                            l.add(listaDisco.get(0));
                        }
                }
                default -> {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new Exception("Falha em conectar-se " + e.getMessage());
        }

        if (!l.isEmpty()) {
            insertOnLocal(l);
            return l;
        } else {
            return null;
        }
    }

    @Override
    public void insertOnLocal(List<Componente> components) {
        try {
            List<Componente> listaComponents = new ArrayList<>();
            db = dbLocal.getConexaoDoBanco();
            components.forEach(it -> {
                List<Componente> l = db.query("SELECT * FROM componente WHERE idcomponente = ?", new BeanPropertyRowMapper<>(Componente.class), it.getIdComponente());
                if (l.isEmpty()) {
                    listaComponents.add(it);
                }
            });
            List<Especificacoes> listaEspecifications = new ArrayList<>();
            listaComponents.forEach(it -> {
                db.update("INSERT INTO componente VALUES (?, ?, ?, ?)", it.getIdComponente(), it.getTotem(), it.getNome(), it.getTipo());
                if (it.getEspecificacoes() != null) {
                    it.getEspecificacoes().forEach(e -> {
                        List<Especificacoes> es = db.query("SELECT * FROM especificacao WHERE idespecificacao = ? AND componente = ?", new BeanPropertyRowMapper<>(Especificacoes.class), e.getIdespecificacao(), e.getComponente());
                        if (es.isEmpty()) {
                            listaEspecifications.add(e);
                        }
                    });
                }
            });
            listaEspecifications.forEach(it -> {
                db.update("INSERT INTO especificacao VALUES (?, ?, ?, ?, ?, ?)", it.getIdespecificacao(), it.getNome(), it.getValor(), it.getUnidadeMedida(), it.getComponente(), it.getTipo());
            });
        } catch(Exception e) {
            System.out.println("Falha ao atualizar banco Componentes " + e.getMessage());
        }
    }
}
