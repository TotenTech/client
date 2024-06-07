package dao;

import model.Interrupcoes;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.local.LocalDatabaseConnection;
import repository.remote.RemoteDatabaseConnection;

import java.util.List;

public class InterrupcoesDAO {

    static LocalDatabaseConnection dbLocal = new LocalDatabaseConnection();
    static RemoteDatabaseConnection dbRemote = new RemoteDatabaseConnection();
    static JdbcTemplate dbr;
    static JdbcTemplate dbl;

    public static Interrupcoes getInterrupcoes(Integer idTotem) throws Exception {
        try {
            dbr = dbRemote.getConexaoDoBanco();
            List<Interrupcoes> listaInterrupcoes = dbr.query("SELECT * FROM interrupcoes WHERE totem = ?", new BeanPropertyRowMapper<>(Interrupcoes.class), idTotem);
                if (!listaInterrupcoes.isEmpty() && listaInterrupcoes != null) {
                    return listaInterrupcoes.get(0);
                }
        } catch (Exception e) {
            throw new Exception("Exceção no dao" + e.getMessage(), e);
        }
        return null;
    }

    public static void insertInterrupcao(String motivo, Integer totem) {
        try {
            dbr = dbRemote.getConexaoDoBanco();
            dbr.update("INSERT INTO interrupcoes (motivo, totem) VALUES (?, ?)", motivo, totem);
        } catch (Exception e) {
            System.out.println("Falha ao registrar interrupção no banco remoto");
        }

        try {
            dbl = dbLocal.getConexaoDoBanco();
            dbl.update("INSERT INTO interrupcoes (motivo, totem) VALUES (?, ?)", motivo, totem);
        } catch (Exception e) {
            System.out.println("Falha ao registrar interrupção no banco local");
        }
    }
}
