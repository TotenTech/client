package controller;

import com.github.britooo.looca.api.core.Looca;
import dao.ComponentDAOImpl;
import model.Componente;
import model.Disco;
import service.ComponentTypes;
import service.Convertions;
import service.LoocaService;

import java.util.ArrayList;
import java.util.List;

public class DiscoController {

    static ComponentDAOImpl dao = new ComponentDAOImpl();

    public static List<Disco> getDiscos(Integer totem, ComponentTypes tipo) throws Exception {
        try {
            List<Componente> discos = dao.getFromDatabase(totem, tipo);
            if (discos != null) {
                List<Disco> dl = new ArrayList<>();
                discos.forEach(it -> dl.add((Disco) it));
                return dl;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static List<com.github.britooo.looca.api.group.discos.Disco> getDiscosLooca() {
        return LoocaService.getDiscos();
    }

    public static Double getUtilizadoPercentage(Double total, Integer id, Integer qttDiscos) {
        Double utilization = LoocaService.getVolumesUtilizado() / qttDiscos;
        return Convertions.toDoubleTwoDecimals((Convertions.bytesParaGb(utilization) / total * 100));
    }

    public static Double getTotalDiscosPercentage(Double total) {
        Double utilization = LoocaService.getVolumesUtilizado();
        return Convertions.toDoubleTwoDecimals(Convertions.bytesParaGb(utilization) / total * 100);
    }
}
