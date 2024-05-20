package controller;

import dao.ComponentDAOImpl;
import model.Componente;
import model.Disco;
import model.Memoria;
import service.ComponentTypes;
import service.Convertions;
import service.LoocaService;

import java.util.List;

public class MemoriaController {

    static ComponentDAOImpl dao = new ComponentDAOImpl();

    public static Memoria getMemoria(Integer totem, ComponentTypes tipo) throws Exception {
        try {
            List<Componente> list = dao.getFromDatabase(totem, tipo);
            if (list != null) {
                return (Memoria) list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static Double getTotal() {
        return Convertions.toDoubleTwoDecimals(Convertions.bytesParaGb(LoocaService.getTotalMemoria()));
    }

    public static Double getUsingPercentage(Double total) {
        return Convertions.toDoubleTwoDecimals(Convertions.bytesParaGb(LoocaService.getUsingMemory()) / total * 100);
    }

//    public static void insertMemoriaRegistro(MemoriaRegistro memoriaRegistro) {
//        MemoriaDAO.insertMemoriaRegistro(memoriaRegistro);
//    }
}
