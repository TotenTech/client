package dao;

import model.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.local.LocalDatabaseConnection;
import repository.remote.RemoteDatabaseConnection;

import java.util.List;

public class TotemDAO {

    static LocalDatabaseConnection dbLocal = new LocalDatabaseConnection();
    static RemoteDatabaseConnection dbRemote = new RemoteDatabaseConnection();
    static JdbcTemplate db;

    public static Totem login(String login, String senha) throws Exception {

        try {
            db = dbRemote.getConexaoDoBanco();
            List<Totem> listaTotem = db.query("SELECT * FROM totem WHERE login = ? and senha = ?", new BeanPropertyRowMapper<>(Totem.class), login, senha);
                if (!listaTotem.isEmpty() && listaTotem != null) {
                    insertOnLocal(listaTotem.get(0));
                    return listaTotem.get(0);
                }
        } catch (Exception e) {
            throw new Exception("Exceção no dao" + e.getMessage(), e);
        }
        return null;
    }

    public static void insertOnLocal(Totem t) {

        try {
            db = dbLocal.getConexaoDoBanco();
            List<Totem> lt = db.query("SELECT * FROM totem WHERE idtotem = ?", new BeanPropertyRowMapper<>(Totem.class), t.getIdTotem());
            if (lt.isEmpty()) {
                db.update("INSERT INTO totem VALUES (?, ?, ?, ?, ?, ?)", t.getIdTotem(), t.getNome(), t.getLogin(), t.getSenha(), t.getSistemaOperacional(), t.getEmpresa());
            }
        } catch (Exception e) {
            System.out.println("Falha ao atualizar totem no banco local " + e.getMessage());
        }
    }
}
