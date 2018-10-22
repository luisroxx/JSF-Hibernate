/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.deploy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 *
 * @author Zerato
 */
public class GlassFishCommandExecuter {
    
    private final String glassFishRootPath = "C:\\Program Files (x86)\\glassfish-4.1.1\\bin"; //carregar de algum lugar

    public GlassFishCommandExecuter() {
    }
    
    public String deploy(String warPath) throws Exception{
        String command = "asadmin deploy " + warPath;
        return executeCommand(command);
    }
    
    public String undeploy(String warName) throws Exception{
        if (warName.contains(".war"))
            warName = warName.substring(0,warName.lastIndexOf("."));
        String command = "asadmin undeploy " + warName;
        return executeCommand(command);
    }
    
    public String enable(String warName) throws Exception{
        if (warName.contains(".war"))
            warName = warName.substring(0,warName.lastIndexOf("."));
        String command = "asadmin enable " + warName;
        return executeCommand(command);
    }
     
    public String disable(String warName) throws Exception{
        if (warName.contains(".war"))
            warName = warName.substring(0,warName.lastIndexOf("."));
        String command = "asadmin disable " + warName;
        return executeCommand(command);
    }
    
    private String executeCommand(String command) throws Exception{
        StringBuilder output = new StringBuilder();
        String commandFull = "cd @glassFishPath && "+ command;
        commandFull = commandFull.replace("@glassFishPath", glassFishRootPath);
        System.err.println(commandFull);
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", commandFull);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            output.append(line);
            if (line == null) { break; }
        }
        return output.toString();
    }
}
