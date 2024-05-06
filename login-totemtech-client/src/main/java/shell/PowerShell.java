package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PowerShell {

    public String executePowerShellCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(String.format("powershell.exe (%s)", command));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        process.waitFor();
        process.destroy();
        return output.toString();
    }

    public void restart() {
        try {
            executePowerShellCommand("Restart-Computer");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
