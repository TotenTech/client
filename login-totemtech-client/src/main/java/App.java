import com.github.britooo.looca.api.core.Looca;
import controller.*;
import model.*;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import model.register.Registro;
import service.Convertions;
import shell.PowerShell;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static service.ComponentTypes.*;
import static service.Specifications.TOTAL;

public class App {

    static Totem logged = null;
    static Interrupcoes interrupcoes = null;
    static Cpu cpu = null;
    static Memoria memoria = null;
    static List<Disco> discos = null;
    static Rede rede = null;
    static int system;
    static Looca looca = new Looca();

    public static void main(String[] args) throws Exception {


        verificarSo();
        inicio();

        if (logged != null) {
            try {
                cpu = CpuController.getCpu(logged.getIdTotem(), CPU);

                memoria = MemoriaController.getMemoria(logged.getIdTotem(), MEMORIA);

                discos = DiscoController.getDiscos(logged.getIdTotem(), DISCO);

                rede = RedeController.getRede(logged.getIdTotem(), REDE);

                System.out.println("Iniciando monitoramento...");
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("Um erro ocorreu no processo de login, tente novamente mais tarde " + e);
            }

            while (true) {
                inserts();
                Thread.sleep(120000);
            }

//            Executar a inovação, reiniciar pc
//            if (system == 1) {
//                PowerShell prompt = new PowerShell();
//            prompt.restart();
//                prompt.executePowerShellCommand("restart-computer");
//            } else {
//                TerminalLinux prompt = new TerminalLinux();
//                prompt.executeLinuxCommand("shutdown -r now");
//            }

        }
    }

    public static void inicio() throws Exception {
        Scanner input = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("""
                    Bem vindo(a)!
                    Digite o número equivalente para escolher uma opção
                    1-Entrar || 2-Sair""");

            opcao = input.nextInt();

            switch (opcao) {
                case 1 -> entrar();
                case 2 -> System.exit(0);
                default -> {
                    System.out.println("Escolha uma opção válida!");
                }
            }
        } while (opcao != 1 && opcao != 2);
        input.close();
    }


    public static void entrar() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("Digite seu login: ");
        String inputEmail = input.nextLine();

        System.out.print("Digite sua senha: ");
        String inputSenha = input.nextLine();

        Totem totemLog = TotemController.login(inputEmail, inputSenha);
        if (totemLog != null) {
            System.out.println("Login realizado com sucesso");
            logged = totemLog;
        } else {
            int opcao;
            do {
                System.out.println("""
                        Usuário não encontrado!
                        Digite o número equivalente para escolher uma opção
                        1-Tentar novamente || 2-Sair""");

                opcao = input.nextInt();

                switch (opcao) {
                    case 1 -> entrar();
                    case 2 -> System.exit(0);
                }
            } while (opcao != 1 && opcao != 2);
        }
        input.close();
    }

    public static void verificarSo() {
        String so = System.getProperty("os.name").toLowerCase();

        if (so.contains("win")) {
            system = 1;
        } else {
            system = 2;
        }
    }

    public static void inserts() {
        LocalTime inicioTestes = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horarioFormatado = inicioTestes.format(formatter);
        System.out.println();
        System.out.println("Executando testes de monitoramento - " + horarioFormatado);

        Registro memoriaRegistro = new Registro();
        memoriaRegistro.setComponente(memoria.getIdComponente());

        memoriaRegistro.setValor((MemoriaController.getUsingPercentage(memoria.getTotal())).toString());
        System.out.println("Memoria RAM sendo utilizada em porcentagem: " + memoriaRegistro.getValor());
        RegistroController.insertRegistro(memoriaRegistro);

        List<Registro> discosRegistro = new ArrayList<>();
        for (int i = 0; i < discos.size(); i++) {
            discosRegistro.add(new Registro());
            discosRegistro.get(i).setComponente(discos.get(i).getIdComponente());
        }
        for (int i = 0; i < discosRegistro.size(); i++) {
            discosRegistro.get(i).setValor((DiscoController.getUtilizadoPercentage(discos.get(i).getTotal(), i)).toString());
            System.out.println("Disco " + i + 1 + " espaço utilizado em porcentagem: " + discosRegistro.get(i).getValor());
            RegistroController.insertRegistro(discosRegistro.get(i));
        }

        Registro cpuRegistro2 = new Registro();
        cpuRegistro2.setComponente(cpu.getIdComponente());
        cpuRegistro2.setValor((CpuController.getProcessos()).toString());
        System.out.println("Total de processos: " + cpuRegistro2.getValor());
        RegistroController.insertRegistro(cpuRegistro2);

        Registro cpuRegistro = new Registro();
        cpuRegistro.setComponente(cpu.getIdComponente());
        cpuRegistro.setValor((CpuController.getUsingPercentage()).toString());
        System.out.println("Cpu sendo utilizada em porcentagem: " + cpuRegistro.getValor());
        RegistroController.insertRegistro(cpuRegistro);


        Registro redeRegistro = new Registro();
        redeRegistro.setComponente(rede.getIdComponente());

        SpeedTestSocket speedTestSocket = new SpeedTestSocket();
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(SpeedTestReport report) {
                System.out.println("Velocidade da rede em mb/s: " + Convertions.toDoubleTwoDecimals(report.getTransferRateBit().divide(new BigDecimal(1000000)).doubleValue()));
                redeRegistro.setValor((Convertions.toDoubleTwoDecimals(report.getTransferRateBit().divide(new BigDecimal(1000000)).doubleValue())).toString());
                RegistroController.insertRegistro(redeRegistro);

                LocalTime fimTestes = LocalTime.now();
                String horarioFormatado = fimTestes.format(formatter);
                System.out.println("Monitoramento finalizado em " + horarioFormatado + " os componentes passarão por testes de monitoramento novamente em aproximadamente 2 minutos");
                System.out.println();
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                System.out.println("Totem sem conexão de rede");
                redeRegistro.setValor(null);
                RegistroController.insertRegistro(redeRegistro);

                LocalTime fimTestes = LocalTime.now();
                String horarioFormatado = fimTestes.format(formatter);
                System.out.println("Monitoramento finalizado em " + horarioFormatado + " os componentes passarão por testes de monitoramento novamente em aproximadamente 2 minutos");
                System.out.println();
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
//                System.out.println("Progresso teste de internet : " + percent + "%");
            }
        });
        speedTestSocket.startDownload("https://link.testfile.org/15MB");
    }

    public static void restart() {
        try {
            System.out.println("Problema crítico encontrado, reiniciando o totem em 5 segundos");
            Thread.sleep(5000);
            PowerShell prompt = new PowerShell();
            prompt.executePowerShellCommand("restart-computer");
        } catch (Exception e) {
        }
    }
}

