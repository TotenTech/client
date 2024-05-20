package controller;

import dao.ComponentDAOImpl;
import model.Componente;
import model.Rede;
import service.ComponentTypes;

import java.util.List;

public class RedeController {

    static ComponentDAOImpl dao = new ComponentDAOImpl();

    public static Rede getRede(Integer totem, ComponentTypes tipo) throws Exception {
        try {
            List<Componente> list = dao.getFromDatabase(totem, tipo);
            if (list != null) {
                return (Rede) list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }
}
