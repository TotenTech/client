package controller;

import dao.ComponentDAOImpl;
import dao.RegisterDAOImpl;
import model.register.Registro;

public class RegistroController {

    static RegisterDAOImpl dao = new RegisterDAOImpl();

    public static void insertRegistro(Registro registro) {
        try {
            dao.insert(registro);
        } catch (Exception e) {
            System.out.println("Não foi possível inserir registro no banco da aplicação, seu registro será salvo localmente para" +
                    " que os dados não sejam perdidos " + e.getMessage());
        }
    }
}
