/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.atlas.task2;

import javafx.concurrent.Task;
import ru.atlas.shell.Shell;
import ru.atlas.shell.ShellException;

/**
 *
 * @author atlas
 */
public class CommandTask extends Task<String> {
    private final String cmd;
    //StringBuilder results = new StringBuilder();
    String executeOutput;
    
    public CommandTask(String cmd) {
        this.cmd = cmd;
    }    
    
    @Override
    protected String call() throws ShellException {
        System.out.println("TASK CALL cmd="+cmd);
        executeOutput = Shell.Execute(cmd);
        System.out.println("executeOutput="+executeOutput);
        updateValue(executeOutput);
        updateMessage(executeOutput);
        
        return executeOutput;
    }
    
}
