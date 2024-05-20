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
            db = dbLocal.getConexaoDoBanco();
            List<Totem> listaTotem = db.query("SELECT * FROM totem WHERE login = ? and senha = ?", new BeanPropertyRowMapper<>(Totem.class), login, senha);
                if (!listaTotem.isEmpty() && listaTotem != null) {
                    return listaTotem.get(0);
                }
        } catch (Exception e) {
            throw new Exception("Exceção no dao" + e.getMessage(), e);
        }
        return null;
    }
}
