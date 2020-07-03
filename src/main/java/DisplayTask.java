
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Надо сделать Task
// 1) Выполнение команды shell
// 2) Выполнение команды shell в цикле
// 3) Выполнение команды shell через ssh
// 4) Выполнение команды shell через ssh в цикле

/**
 *
 * @author atlas
 */
public class DisplayTask extends Task<String> {
    
    private final TextArea log;
    StringBuilder results = new StringBuilder();
    
    public DisplayTask(TextArea log) {
        this.log = log;
    }
    
    @Override
    protected String call() throws Exception {
        final int max = 100;
        int i = 0;
        while (i < max) {
            i++;
            if (isCancelled()) {
                break;
            }
            results.append("I: ").append(i).append("\n");
            updateValue(results.toString());
            updateProgress((100*i)/90, 90);
            try {
                Thread.sleep(100);
//                            updateProgress(i, max);
//                            updateMessage("Iteration " + i);
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return results.toString();
    }
}
