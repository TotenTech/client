package controller;

import entities.Cpu;
import dao.CpuDAO;
import entities.register.CpuRegistro;
import service.Convertions;
import service.LoocaService;

public class CpuController {


    public static Cpu getCpu(Integer idTotem) throws Exception {
        try {
            Cpu cpu = CpuDAO.getCpu(idTotem);
            if (cpu != null) {
                return cpu;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Exceção no controller" + e.getMessage(), e);
        }
    }

    public static void insertCpu(Cpu cpu, Integer idTotem) throws Exception {
        try {
            CpuDAO.insertCpu(cpu, idTotem);
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

    public static void insertCpuRegistro(CpuRegistro cpuRegistro) {
        CpuDAO.insertCpuRegistro(cpuRegistro);
    }

}
