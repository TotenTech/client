package dao;

import model.register.Registro;

import java.util.List;

public interface IRegisterDAO {

    void insert(Registro registro) throws Exception;

    List<Registro> selectAll(Integer componente) throws Exception;

    List<Registro> selectLast(Integer componente) throws Exception;
}
