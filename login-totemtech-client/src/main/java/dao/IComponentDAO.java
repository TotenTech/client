package dao;

import model.Componente;
import service.ComponentTypes;

import java.util.List;

public interface IComponentDAO {

    List<Componente> getFromDatabase(Integer totem, ComponentTypes tipo) throws Exception;

    void insertOnLocal(List<Componente> components);
}
