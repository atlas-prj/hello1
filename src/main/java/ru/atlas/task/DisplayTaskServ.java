package ru.atlas.task;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author atlas
 */
public class DisplayTaskServ extends Task<String> {
    
    private final String cmd;
    StringBuilder results = new StringBuilder();
    
    public DisplayTaskServ(String cmd) {
        this.cmd = cmd;
    }
    
    @Override
    protected String call() throws Exception {
        final int max = 30;
        int i = 0;
        while (i < max) {
            i++;
            if (isCancelled()) {
                break;
            }
            System.out.println("cmd="+cmd+" i="+i);
            results.append(cmd + " I: ").append(i).append("\n"); 
            updateValue(results.toString());
            updateMessage(results.toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return results.toString();
    }
}
