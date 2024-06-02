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
                default -> {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new Exception("Falha em conectar-se " + e.getMessage());
        }

        if (!l.isEmpty()) {
            return l;
        } else {
            return null;
        }
    }
}
