package controller;
import model.Totem;
import dao.TotemDAO;

public class TotemController {

    public static Totem login(String login, String senha) throws Exception {
        try {
            Totem totem = TotemDAO.login(login, senha);
            if (totem != null) {
                return totem;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }
}
