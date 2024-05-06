package dao;

import entities.Rede;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.local.LocalDatabaseConnection;
import repository.remote.RemoteDatabaseConnection;

public class RedeDAO {

    static LocalDatabaseConnection dbLocal = new LocalDatabaseConnection();
    static RemoteDatabaseConnection dbRemote = new RemoteDatabaseConnection();
    static JdbcTemplate db;

    public static void insertRede(Rede rede) {
        db = dbLocal.getConexaoDoBanco();
        db.update("INSERT INTO redeRegistro (velocidade, totem) VALUES (?, ?)", rede.getDownload(), rede.getTotem());
    }
}
