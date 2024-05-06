package controller;
import entities.Totem;
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


//    public static Boolean buscarEmail(String email) throws Exception {
//        try {
//            if (TotemDAO.buscarEmail(email)) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            throw new Exception("Exceção no controller", e);
//        }
//    }
//
//    public static Boolean cadastrar(User usuario) throws Exception {
//        try {
//            if (TotemDAO.cadastrar(usuario)) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            throw new Exception("Exceção no controller", e);
//        }
//    }
//
//    public static Boolean validarEmail(String email) {
//        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(email);
//
//        return matcher.matches();
//    }
//
//    public static Boolean validarCodigoEmpresa(String codigo) throws Exception {
//        return TotemDAO.buscarCodigoEmpresa(codigo);
//    }
//
//    public static Boolean validarSenha(String senha, String confirmaSenha) {
//        return senha.equals(confirmaSenha);
//    }
}
