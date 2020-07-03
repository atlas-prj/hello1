import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import ru.atlas.hello1.Dialog;

public class HelloFX extends Application {
    
    int counter = 0;
    private volatile Service<String> backgroundThread;

    @Override
    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(), 640, 480);
        stage.setScene(scene);
//        stage.show();

//        JMetro jMetro = new JMetro(Style.LIGHT);
//        jMetro.setScene(scene);        

        login();
        
    }

    public boolean login() {
        
        Dialog dialog = new Dialog();
        
        VBox vbox = new VBox();
        TextArea log = new TextArea();
        ProgressBar bar = new ProgressBar();
        bar.setMaxWidth(Double.MAX_VALUE);
        
        vbox.getChildren().addAll(log, bar);
        dialog.getDialogPane().setContent(vbox);
        
        //log = dialog.getArea();
        log.setText("Start");
        
        backgroundThread = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new DisplayTask(log);
                
//                return new Task<String>() {
//                    StringBuilder results = new StringBuilder();
//                    @Override 
//                    protected String call() {
//                        final int max = 100;
//                        int i = 0;
//                        while (i < max) {
//                            i++;
//                            if (isCancelled()) {
//                                break;
//                            }
//                            results.append("i: ").append(i).append("\n");
//                            updateValue(results.toString());
//                            updateProgress((100*i)/90, 90);
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(HelloFX.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                        return results.toString();
//                    }
//                };
            }    
        };
        
        log.textProperty().bind(backgroundThread.valueProperty());        
        bar.progressProperty().bind(backgroundThread.progressProperty());
        
        
        backgroundThread.start();        
        
        // longrunning operation runs on different thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        outputTxt(log, "Before Start");
                    }
                };

                while (counter < 100) {
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        
        // don't let thread prevent JVM shutdown
//        thread.setDaemon(true);
//        thread.start();
        
        dialog.showAndWait();
        
        log.appendText("\nAfter show and wait");
        
        return false;
    }
    
    public void outputTxt(TextArea ta, String dp) {
        counter++;
        ta.appendText("\nDebud point = " + dp + " Iteration # " + counter);
    }
    
    
    public static void main(String[] args) {
        launch();
    }

}