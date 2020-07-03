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
public class DisplayTask extends Task<Void> {
    
    private final TextArea log;
    StringBuilder results = new StringBuilder();
    
    public DisplayTask(TextArea log) {
        this.log = log;
    }
    
    @Override
    protected Void call() throws Exception {
        final int max = 100;
        int i = 0;
        while (i < max) {
            i++;
            if (isCancelled()) {
                break;
            }
            results.append("I: ").append(i).append("\n");            
            log.appendText(results.toString());   
              // Вроде как документация советует это в Platform.runLater
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    log.appendText(results.toString());                    
//                }
//            });

            try {
                Thread.sleep(100);
//                            updateProgress(i, max);
//                            updateMessage("Iteration " + i);
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
