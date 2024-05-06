package controller;

import dao.RedeDAO;
import entities.Rede;

public class RedeController {

    public static void insertRede(Rede rede) {
        RedeDAO.insertRede(rede);
    }
}
