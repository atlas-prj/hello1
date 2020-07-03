/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.atlas.task2;

import static java.lang.invoke.MethodHandles.lookup;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author atlas
 */
public class CommandService extends Service<String> {
    
    private final String cmd;
    StringBuilder results = new StringBuilder();
    
    protected static org.slf4j.Logger g_logger         = getLogger(lookup().lookupClass());
    private static final ResourceBundle fore = ResourceBundle.getBundle("fore");

    
    public CommandService(String cmd) {
        this.cmd = cmd;
    }
    
    @Override
    protected void succeeded() {
        super.succeeded();
        g_logger.info("\nCommand "+cmd+" successfully executed");
    }

    @Override
    protected void failed() {
        super.failed();
        g_logger.error("\nCommand "+cmd+" failed");
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        g_logger.info("\nCommand "+cmd+" cancelled");        
    }
    
    @Override
    protected Task<String> createTask() {
        return new CommandTask(cmd);
    }            
    
}