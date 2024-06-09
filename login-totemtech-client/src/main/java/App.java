import com.github.britooo.looca.api.core.Looca;
import controller.*;
import dao.InterrupcoesDAO;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import model.*;
import model.register.Registro;
import repository.local.LocalDatabaseConnection;
import service.ComponentTypes;
import service.Convertions;
import shell.PowerShell;
import shell.TerminalLinux;
import slack.EnvioAlertas;
import slack.Slack;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static service.ComponentTypes.*;

public class App {

    static Totem logged = null;
    static Interrupcoes interrupcoes = null;
    static Cpu cpu = null;
    static Memoria memoria = null;
    static List<Disco> discos = null;
    static Rede rede = null;
    static Disco totalDiscos = null;
    static int system;
    static Looca looca = new Looca();
    static List<Boolean> memoriaError = new ArrayList<>();
    static List<Boolean> cpuError = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        verificarSo();
        inicio();

        Slack slack = new Slack();
        LocalDatabaseConnection conexaoDoBanco = new LocalDatabaseConnection();
        EnvioAlertas envioAlertas = new EnvioAlertas(conexaoDoBanco, slack);

        if (logged != null) {
            try {
                cpu = CpuController.getCpu(logged.getIdTotem(), CPU);

                memoria = MemoriaController.getMemoria(logged.getIdTotem(), MEMORIA);

                discos = DiscoController.getDiscos(logged.getIdTotem(), DISCO);

                rede = RedeController.getRede(logged.getIdTotem(), REDE);

                totalDiscos = DiscoController.getDiscos(logged.getIdTotem(), ComponentTypes.TOTAL).get(0);

                System.out.println("Iniciando monitoramento...");
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("Um erro ocorreu no processo de login, tente novamente mais tarde " + e);
            }

            while (true) {
                inserts();
                envioAlertas.verificarDados();
                Thread.sleep(120000);
            }
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
            discosRegistro.get(i).setValor((DiscoController.getUtilizadoPercentage(discos.get(i).getTotal(), i, discosRegistro.size())).toString());
            RegistroController.insertRegistro(discosRegistro.get(i));
        }

        Registro totalDiscosRegistro = new Registro();
        totalDiscosRegistro.setComponente(totalDiscos.getIdComponente());
        totalDiscosRegistro.setValor(DiscoController.getTotalDiscosPercentage(totalDiscos.getTotal()).toString());
        System.out.println("Armazenamento utilizado em porcentagem: " + totalDiscosRegistro.getValor());
        RegistroController.insertRegistro(totalDiscosRegistro);

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
                verifyStatus(Double.parseDouble(memoriaRegistro.getValor()), Double.parseDouble(cpuRegistro.getValor()), Double.parseDouble(totalDiscosRegistro.getValor()), Double.parseDouble(redeRegistro.getValor()), horarioFormatado);
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                System.out.println("Totem sem conexão de rede");
                redeRegistro.setValor(null);
                RegistroController.insertRegistro(redeRegistro);

                LocalTime fimTestes = LocalTime.now();
                String horarioFormatado = fimTestes.format(formatter);
                verifyStatus(Double.parseDouble(memoriaRegistro.getValor()), Double.parseDouble(cpuRegistro.getValor()), Double.parseDouble(totalDiscosRegistro.getValor()), null, horarioFormatado);
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
//                System.out.println("Progresso teste de internet : " + percent + "%");
            }
        });
        speedTestSocket.startDownload("https://link.testfile.org/15MB");
    }

    public static void verifyStatus(Double memoria, Double cpu, Double disco, Double rede, String horario) {
        String statusMemoria = "";
        String statusDisco = "";
        String statusCpu = "";
        String statusRede = "";
        Boolean restartTotem = false;
        String motivo = "";

        if (memoria < 85.0) {
            memoriaError.add(false);
            statusMemoria = "Ok";
        } else if (memoria >= 85.0 && memoria <= 89.0) {
            memoriaError.add(false);
            statusMemoria = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema encontrado: Entre 85% e 89% da memória total sendo utilizada.
                    Nível aceitável, mas exige monitoramento para evitar sobrecarga da memória. 
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        } else if (memoria > 89.0) {
            if (memoriaError.isEmpty()) {
                memoriaError.add(true);
                statusMemoria = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Mais de 89% da memória total sendo utilizada.
                    Sobrecarga da memória pode levar a lentidão, travamentos, falhas no sistema e até mesmo perda de dados. 
                    Caso o problema persista o totem será reiniciado!!!
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
            } else if (!memoriaError.get(memoriaError.size() - 1)) {
                memoriaError.add(true);
                statusMemoria = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Mais de 89% da memória total sendo utilizada.
                    Sobrecarga da memória pode levar a lentidão, travamentos, falhas no sistema e até mesmo perda de dados. 
                    Caso o problema persista o totem será reiniciado!!!
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
            } else if (memoriaError.get(memoriaError.size() - 1)) {
                motivo = "Memória RAM";
                restartTotem = true;
                statusMemoria = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Mais de 89% da memória total sendo utilizada.
                    Devido a persistência do problema o totem será reiniciado em 5 segundos!!!
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
            }
        }

        if (cpu < 80.0) {
            cpuError.add(false);
            statusCpu = "Ok";
        } else if (cpu >= 80.0 && cpu <= 90.0) {
            cpuError.add(false);
            statusCpu = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema encontrado: Entre 80% e 90% de utilização da CPU.
                    É sinal de que a CPU está sendo utilizada com eficiência, mas pode haver lentidão em momentos de pico.
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        } else if (cpu > 90.0) {
            if (cpuError.isEmpty()) {
                cpuError.add(true);
                statusCpu = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Acima de 90% de utilização da CPU.
                    Indica sobrecarga da CPU, resultando em lentidão, travamentos e instabilidades do sistema.
                    Caso o problema persista o totem será reiniciado!!!
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
            } else if (!cpuError.get(cpuError.size() - 1)) {
                cpuError.add(true);
                statusCpu = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Acima de 90% de utilização da CPU.
                    Indica sobrecarga da CPU, resultando em lentidão, travamentos e instabilidades do sistema.
                    Caso o problema persista o totem será reiniciado!!!
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
            } else if (cpuError.get(cpuError.size() - 1)) {
                motivo = "CPU";
                restartTotem = true;
                statusCpu = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Acima de 90% de utilização da CPU.
                    Devido a persistência do problema o totem será reiniciado em 5 segundos!!!
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
            }
        }

        if (disco < 80.0) {
            statusDisco = "Ok";
        } else if (disco >= 80.0 && disco <= 90.0) {
            statusDisco = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema encontrado: Entre 80% e 90% do armazenamento do totem utilizado.
                    Nível de alerta que exige monitoramento para evitar que a utilização do disco exceda a capacidade.
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        } else if (disco > 90.0) {
            statusDisco = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Acima de 90% do armazenamento do totem utilizado.
                    Utilização excessiva do disco pode levar a lentidão, travamentos e falhas no sistema.
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        }

        if (rede > 10.0) {
            statusRede = "Ok";
        } else if (rede >= 6.0 && rede <= 10.0) {
            statusRede = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema encontrado: Velocidade da rede entre 10MB/s e 6MB/s.
                    O sistema funcionará sem problemas, porém, pode apresentar problemas em horário de pico.
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        } else if (rede < 5.0) {
            statusRede = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema crítico encontrado: Velocidade da rede abaixo de 5MB/s.
                    Indica lentidão, travamento e instabilidade do sistema
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        } else {
            statusRede = """
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0
                    Problema encontrado: Totem sem conexão com a rede, os dados serão salvos localmente evitando assim a perda dos mesmos.
                    \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0 \u26A0""";
        }

        System.out.println("Status do totem:");
        System.out.println("Memória");
        System.out.println(statusMemoria);
        System.out.println("CPU");
        System.out.println(statusCpu);
        System.out.println("Armazenamento");
        System.out.println(statusDisco);
        System.out.println("Rede");
        System.out.println(statusRede);
        if (restartTotem) {
            InterrupcoesDAO.insertInterrupcao(motivo, logged.getIdTotem());
            new Thread(() -> {
                try {
                    for (int i = 5; i > 0; i--) {
                        System.out.println("O totem será reiniciado em: " + i + " segundos");
                        Thread.sleep(1000);
                        if (i == 1) {
                            restart();
                        }
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } else {
            System.out.println("Monitoramento finalizado em " + horario + " os componentes passarão por testes de monitoramento novamente em aproximadamente 2 minutos");
            System.out.println();
        }
    }

    public static void restart() {
        try {
            if (system == 1) {
                PowerShell prompt = new PowerShell();
                prompt.executePowerShellCommand("restart-computer");
            } else {
                TerminalLinux prompt = new TerminalLinux();
                prompt.executeLinuxCommand("shutdown -r now");
            }
        } catch (Exception e) {
            System.out.println("Falha, o totem não pôde ser reiniciado!");
        }
    }
}


