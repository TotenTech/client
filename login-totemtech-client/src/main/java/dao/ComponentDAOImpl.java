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
                    List<Disco> listaDiscos = db.query("SELECT * FROM componentes WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Disco.class), totem, tipo.getTipo());
                    if (!listaDiscos.isEmpty()) {
                        l.addAll(listaDiscos);
                    }
                }
                case MEMORIA -> {
                    List<Memoria> listaMemoria = db.query("SELECT * FROM componentes WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Memoria.class), totem, tipo.getTipo());
                    if (!listaMemoria.isEmpty()) {
                        l.add(listaMemoria.get(0));
                    }
                }
                case CPU -> {
                    List<Cpu> listaCpu = db.query("SELECT * FROM componentes WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Cpu.class), totem, tipo.getTipo());
                    if (!listaCpu.isEmpty()) {
                        l.add(listaCpu.get(0));
                    }
                }
                case REDE -> {
                    List<Rede> listaRede = db.query("SELECT * FROM componentes WHERE totem = ? AND tipo = ?", new BeanPropertyRowMapper<>(Rede.class), totem, tipo.getTipo());
                    if (!listaRede.isEmpty()) {
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
