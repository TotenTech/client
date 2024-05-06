package controller;

import entities.Interrupcoes;
import dao.InterrupcoesDAO;

public class InterrupcoesController {

    public static Interrupcoes getInterrupcoes(Integer idTotem) throws Exception {
        try {
            Interrupcoes interrupcoes = InterrupcoesDAO.getInterrupcoes(idTotem);
            if (interrupcoes != null) {
                return interrupcoes;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }
}
