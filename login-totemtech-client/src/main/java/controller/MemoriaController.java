package controller;

import entities.Memoria;
import dao.MemoriaDAO;
import entities.register.MemoriaRegistro;
import service.Convertions;
import service.LoocaService;

public class MemoriaController {


    public static Memoria getMemoria(Integer idTotem) throws Exception {
        try {
            Memoria memoria = MemoriaDAO.getMemoria(idTotem);
            if (memoria != null) {
                return memoria;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static void insertMemoria(Memoria memoria, Integer idTotem) throws Exception {
        try {
            MemoriaDAO.insertMemoria(memoria, idTotem);
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

    public static void insertMemoriaRegistro(MemoriaRegistro memoriaRegistro) {
        MemoriaDAO.insertMemoriaRegistro(memoriaRegistro);
    }
}
