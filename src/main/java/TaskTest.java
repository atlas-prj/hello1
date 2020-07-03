
import java.util.concurrent.Executors;
import javafx.concurrent.Task;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author atlas
 */
public class TaskTest {
    
    public static void main(String[] args) {
        TaskTest tt = new TaskTest();
    }
    
    
    Task<Integer> task = new Task<Integer>() {
        @Override
        protected Integer call() throws Exception {
            int iterations;
            for (iterations = 0; iterations < 100; iterations++) {
                System.out.println("iter= "+iterations);
                if (isCancelled()) {
                    updateMessage("Cancelled 1");
                    break;
                }
                updateMessage("Iteration " + iterations);
                updateProgress(iterations, 1000);

                //Block the thread for a short time, but be sure
                //to check the InterruptedException for cancellation
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interrupted) {
                    if (isCancelled()) {
                        updateMessage("Cancelled 2");
                        break;
                    }
                }
            }
            return iterations;
        }
    };        
}
