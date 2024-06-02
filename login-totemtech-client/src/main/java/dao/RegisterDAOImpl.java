package dao;

import model.register.Registro;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.local.LocalDatabaseConnection;
import repository.remote.RemoteDatabaseConnection;

import java.util.List;

public class RegisterDAOImpl implements IRegisterDAO {

    static LocalDatabaseConnection dbLocal = new LocalDatabaseConnection();
    static RemoteDatabaseConnection dbRemote = new RemoteDatabaseConnection();
    static JdbcTemplate dbr;
    static JdbcTemplate dbl;

    @Override
    public void insert(Registro registro) throws Exception {
        try {
            dbr = dbRemote.getConexaoDoBanco();
            dbr.update("INSERT INTO registro (valor, componente) VALUES (?, ?)",
                    registro.getValor(), registro.getComponente());

            dbl = dbLocal.getConexaoDoBanco();
            dbl.update("INSERT INTO registro (valor, componente) VALUES (?, ?)",
                    registro.getValor(), registro.getComponente());
        } catch (Exception e) {
            throw new Exception("Falha ao inserir registro" + registro + " " + e.getMessage());
        }
    }

    @Override
    public List<Registro> selectAll(Integer componente) throws Exception {
        try {
            dbr = dbRemote.getConexaoDoBanco();
            List<Registro> list = dbr.query("SELECT * FROM registro", new BeanPropertyRowMapper<>(Registro.class));
            if (!list.isEmpty()) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Falha ao recuperar registros " + e.getMessage());
        }
    }

    @Override
    public List<Registro> selectLast(Integer componente) throws Exception {
        try {
            dbr = dbRemote.getConexaoDoBanco();
            List<Registro> list = dbr.query("SELECT * FROM registro WHERE componente = ?", new BeanPropertyRowMapper<>(Registro.class), componente);
            if (!list.isEmpty()) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Falha ao recuperar registros " + e.getMessage());
        }
    }

}
