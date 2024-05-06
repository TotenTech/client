package service;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;

import java.util.List;

public class LoocaService {

    private static Looca looca = new Looca();

    public static Double getTotalMemoria() {
        return looca.getMemoria().getTotal().doubleValue();
    }

    public static Double getFrequenciaProcessador() {
        return looca.getProcessador().getFrequencia().doubleValue();
    }

    public static List<Disco> getDiscos() {
        return looca.getGrupoDeDiscos().getDiscos();
    }

    public static Double getUsingMemory() {
        return looca.getMemoria().getEmUso().doubleValue();
    }

    public static Double getUsingCpu() {
        return looca.getProcessador().getUso();
    }

    public static Integer getProcessos() {
        return looca.getGrupoDeProcessos().getTotalProcessos();
    }

    public static Double getDiscoUtilizadoPercentage(Integer id) {
        return looca.getGrupoDeDiscos().getDiscos().get(id).getBytesDeEscritas().doubleValue() + looca.getGrupoDeDiscos().getDiscos().get(id).getBytesDeLeitura();
    }
}
