package controller;

import dao.ComponentDAOImpl;
import model.Componente;
import model.Cpu;
import service.ComponentTypes;
import service.Convertions;
import service.LoocaService;

import java.util.List;

public class CpuController {

    static ComponentDAOImpl dao = new ComponentDAOImpl();

    public static Cpu getCpu(Integer totem, ComponentTypes tipo) throws Exception {
        try {
            List<Componente> list = dao.getFromDatabase(totem, tipo);
            if (list != null) {
                return (Cpu) list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static Double getFrequenciaProcessador() {
        return Convertions.toDoubleTwoDecimals(Convertions.hzParaGhz(LoocaService.getFrequenciaProcessador()));
    }

    public static Double getUsingPercentage() {
        return Convertions.toDoubleTwoDecimals(LoocaService.getUsingCpu());
    }

    public static Integer getProcessos() {
        return LoocaService.getProcessos();
    }

//    public static void insertCpuRegistro(CpuRegistro cpuRegistro) {
//        CpuDAO.insertCpuRegistro(cpuRegistro);
//    }

}
