package slack;

import repository.local.LocalDatabaseConnection;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnvioAlertas {
    private JdbcTemplate jbdcTemplate;
    private Slack slack;

    private LocalDateTime ultimoAlertaComponente;
    private LocalDateTime ultimoAlertaInterrupcao;

    static List<Boolean> memoriaError = new ArrayList<>();
    static List<Boolean> cpuError = new ArrayList<>();

    public EnvioAlertas(LocalDatabaseConnection conexaoDoBanco, Slack slack) {
        this.jbdcTemplate = conexaoDoBanco.getConexaoDoBanco();
        this.slack = slack;
    }

    public void verificarDados() throws Exception{
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            String formattedDate = localDate.format(dtf);

            //interrupcoes
            String consultaInterrupcoes = "SELECT i.totem, t.nome AS nomeTotem, i.motivo, i.horario FROM interrupcoes AS i JOIN totem AS t ON i.totem = t.idtotem WHERE DATE(i.horario) = '" + formattedDate + "' ORDER BY i.horario DESC;";

            List<Map<String, Object>> interrupcoes = jbdcTemplate.queryForList(consultaInterrupcoes);

            for (Map<String, Object> interrupcao : interrupcoes) {
                LocalDateTime horarioInterrupcao = (LocalDateTime) interrupcao.get("horario");
                String nomeTotem = (String) interrupcao.get("nomeTotem");
                String motivoInterrupcao = (String) interrupcao.get("motivo");
                Integer idTotemInterrupcao = (Integer) interrupcao.get("totem");

                if (ultimoAlertaInterrupcao == null || horarioInterrupcao.isAfter(ultimoAlertaInterrupcao)) {
                    if (interrupcao != null && !interrupcao.isEmpty()) {

                        if (horarioInterrupcao != null && nomeTotem != null && motivoInterrupcao != null && idTotemInterrupcao != null) {
                            JSONObject jsonAlertaInterrupcao = new JSONObject();
                            jsonAlertaInterrupcao.put("text", """
                                    *Alerta: Totem Reiniciado!*
                                            
                                    *Detalhes:*
                                    - Totem: `%d (%s)`
                                    - Motivo: `%s`
                                    - Data/Hora: `%s`
                                            
                                            
                                    :information_source: | Verifique se o totem voltou a funcionar corretamente e monitore para possíveis problemas recorrentes.""".formatted(idTotemInterrupcao, nomeTotem, motivoInterrupcao, horarioInterrupcao));

                            slack.sendSlackMessage("https://hooks.slack.com/services/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", jsonAlertaInterrupcao);
                        }
                    }
                    ultimoAlertaInterrupcao = horarioInterrupcao;
                }
            }

            //monitoramento dos componentes
            String consulta = "SELECT t.idtotem, t.nome AS nomeTotem, c.nome AS nomeComponente, tc.nome AS tipoComponente, r.valor, r.horario FROM totem AS t JOIN componente AS c ON t.idtotem = c.totem JOIN tipoComponente AS tc ON tc.idtipoComponente = c.tipo JOIN registro AS r ON r.componente = c.idcomponente WHERE DATE(r.horario) = '" + formattedDate + "' ORDER BY r.horario DESC;";

            List<RegistroComponentes> registros = jbdcTemplate.query(consulta, (rs, rowNum) -> new RegistroComponentes(
                    rs.getInt("idtotem"),
                    rs.getString("nomeTotem"),
                    rs.getString("nomeComponente"),
                    rs.getString("tipoComponente"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("horario").toLocalDateTime()
            ));

            for (RegistroComponentes registro : registros){

                if (ultimoAlertaComponente == null || registro.getHorario().isAfter(ultimoAlertaComponente)) {
                    JSONObject json = new JSONObject();
                    switch (registro.getTipoComponente()){
                        case "CPU":
                            if (registro.getValor() < 80){
                                cpuError.add(false);
                            } else if(registro.getValor() >= 80 && registro.getValor() <= 90){
                                cpuError.add(false);
                                json.put("text", """
                                *Alerta: Uso de CPU Elevado!*
                                
                                *Detalhes:*
                                - *Totem:* `%d (%s)`
                                - *Valor (CPU):* `%.0f%%`
                                - *Data/Hora:* `%s`
                                
                                
                                :warning: | CPU está sendo utilizada com eficiência, mas pode haver lentidão em momentos de pico.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                sendSlackMessage("https://hooks.slack.com/services/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", json);
                            } else if (registro.getValor() > 90) {
                                if (cpuError.isEmpty()){
                                    cpuError.add(true);
                                    json.put("text", """
                                *Crítico: sobrecarga de CPU!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (CPU): `%.0f%%`
                                - Data/hora: `%s`
                                
                                *Caso o problema persista o totem será reiniciado!!!*
                                
                                :rotating_light: | Sobrecarga da CPU pode resultar em lentidão, travamentos e instabilidades do sistema.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                    sendSlackMessage("https://hooks.slack.com/services/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", json);
                                } else if (!cpuError.get(cpuError.size() - 1)) {
                                    cpuError.add(true);

                                    json.put("text", """
                                *Crítico: sobrecarga de CPU!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (CPU): `%.0f%%`
                                - Data/hora: `%s`
                                
                                *Caso o problema persista o totem será reiniciado!!!*
                                
                                :rotating_light: | Sobrecarga da CPU pode resultar em lentidão, travamentos e instabilidades do sistema.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                    sendSlackMessage("https://hooks.slack.com/services/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", json);
                                }
                            }
                            break;
                        case "Disco":
                            if (registro.getValor() > 80 && registro.getValor() <= 90){
                                json.put("text", """
                                *Alerta: Atenção a utilização do disco!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (disco): `%.0f`
                                - Data/hora: `%s`
                                
                                
                                :warning: | Nível de alerta que exige monitoramento para evitar que a utilização do disco exceda a capacidade.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                sendSlackMessage("https://hooks.slack.com/services/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", json);
                            } else if (registro.getValor() > 90) {
                                json.put("text", """
                                *Crítico: utilização excessiva do disco!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (disco): `%.0f`
                                - Data/hora: `%s`
                                
                                
                                :rotating_light: | Utilização excessiva do disco pode levar a lentidão, travamentos e falhas no sistema.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                sendSlackMessage("https://hooks.slack.com/services/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", json);
                            }
                            break;
                        case "Memória":
                            if (registro.getValor() > 85){
                                memoriaError.add(false);
                            } else if(registro.getValor() >= 85 && registro.getValor() <= 89){
                                memoriaError.add(false);
                                json.put("text", """
                                *Alerta: Atenção a utilização da memória!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (memória): `%.0f`
                                - Data/hora: `%s`
                                
                                
                                :warning: | Nível aceitável de memória, mas exige monitoramento para evitar sobrecarga da memória.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                sendSlackMessage("https://hooks.slack.com/services/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", json);
                            } else if (registro.getValor() > 89) {
                                if (memoriaError.isEmpty()){
                                    memoriaError.add(true);
                                    json.put("text", """
                                    *Crítico: sobrecarga da memória!*
                                    
                                    *Detalhes:*
                                    - Totem: `%d (%s)`
                                    - Valor (memória): `%.0f`
                                    - Data/hora: `%s`
                                    
                                    *Caso o problema persista o totem será reiniciado!!!*
                                    
                                    :rotating_light: | Sobrecarga da memória pode levar a lentidão, travamentos, falhas no sistema e até mesmo perda de dados.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                    sendSlackMessage("https://hooks.slack.com/services/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", json);
                                } else if (!memoriaError.get(memoriaError.size() - 1)){
                                    memoriaError.add(true);
                                    json.put("text", """
                                    *Crítico: sobrecarga da memória!*
                                    
                                    *Detalhes:*
                                    - Totem: `%d (%s)`
                                    - Valor (memória): `%.0f`
                                    - Data/hora: `%s`
                                    
                                    *Caso o problema persista o totem será reiniciado!!!*
                                    
                                    :rotating_light: | Sobrecarga da memória pode levar a lentidão, travamentos, falhas no sistema e até mesmo perda de dados.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                    sendSlackMessage("https://hooks.slack.com/services/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", json);
                                }
                            }
                            break;
                        case "Rede":
                            if (registro.getValor() < 10 && registro.getValor() > 6){
                                json.put("text", """
                                *Alerta: Atenção a velocidade da rede!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (rede): `%.2f MB/s`
                                - Data/hora: `%s`
                                
                                
                                :warning: | O sistema funcionará sem problemas, porém, pode apresentar problemas em horário de pico.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                sendSlackMessage("https://hooks.slack.com/services/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", json);
                            }
                            else if (registro.getValor() < 5){
                                json.put("text", """
                                *Crítico: Rede muito baixa!*
                                
                                *Detalhes:*
                                - Totem: `%d (%s)`
                                - Valor (rede): `%.2f MB/s`
                                - Data/hora: `%s`
                                
                                
                                :rotating_light: | Totem pode indicar lentidão, travamento e instabilidade do sistema.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getValor(), registro.getHorario()));

                                sendSlackMessage("https://hooks.slack.com/services/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", json);
                            }
                            break;
//                        default:
//                            if (registro.getValor() == 0){
//                                json.put("text", """
//                                Alerta: Totem possívelmente sem rede!!
//                                Detalhes:
//                                - Totem: %d (%s)
//                                - Última data/hora com rede: %s
//                                Totem pode indicar lentidão, travamento e instabilidade do sistema.""".formatted(registro.getIdTotem(), registro.getNomeTotem(), registro.getHorario()));
//
//                                sendSlackMessage("https://hooks.slack.com/services/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", json);
//                            }
//                            break;
                    }

                    ultimoAlertaComponente = registro.getHorario();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendSlackMessage(String webhookUrl, JSONObject json) throws Exception {
        slack.sendSlackMessage(webhookUrl, json);
    }
}
