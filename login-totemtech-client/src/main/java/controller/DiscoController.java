package controller;

import entities.Disco;
import dao.DiscoDAO;
import entities.register.DiscoRegistro;
import service.Convertions;
import service.LoocaService;

import java.util.List;

public class DiscoController {


    public static List<Disco> getDiscos(Integer idTotem) throws Exception {
        try {
            List<Disco> discos = DiscoDAO.getDiscos(idTotem);
            if (discos != null) {
                return discos;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static void insertDisco(Disco disco) throws Exception {
        try {
            DiscoDAO.insertDisco(disco);
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static List<com.github.britooo.looca.api.group.discos.Disco> getDiscosLooca() {
        return LoocaService.getDiscos();
    }

    public static Double getUtilizadoPercentage(Double total, Integer id) {
        return Convertions.toDoubleTwoDecimals(Convertions.bytesParaGb(LoocaService.getDiscoUtilizadoPercentage(id)) / total * 100);
    }

    public static void insertDiscoRegistro(DiscoRegistro discoRegistro) {
        DiscoDAO.insertDiscoRegistro(discoRegistro);
    }
}
