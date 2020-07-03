package ru.atlas.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
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
    private StringProperty statusMessagesProperty;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(), 640, 480);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);        
        
        stage.setScene(scene);
//        stage.show();


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
        
        // Вариант 1, работет. Запуск через thread
//        Task<Void> task = new DisplayTask(log);
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
        
//        // Вариант 3 Запуск через ExecutorService и Callbale
//        //ExecutorService executor = Executors.newFixedThreadPool(1, new ShellThreadFactory("sequential"));
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//        
//        //Task task1 = new DisplayTask(log);
//        Callable<String> task = () -> {
//            StringBuilder results = new StringBuilder();
//            final int max = 10;
//            int i = 0;
//            while (i < max) {
//                i++;
//                results.append("I: ").append(i).append("\n");            
//                log.appendText(results.toString());  
//                Thread.sleep(100);
//            }   
//            return results.toString();
//        };
//        
//        Future<String> future = executor.submit(task);
//        
//        String ret = null;
//        try {
//            ret = future.get();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(HelloFX.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(HelloFX.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("ret="+ret);
//        
//        executor.shutdown();
            
//finally{
//  executorService.shutdown();
//}
// Вариант 2 Запуск через Service, работает но две службы подряд не транслируют сообщение как надо

        //ExecutorService executor = Executors.newFixedThreadPool(1, new FirstLineThreadFactory("sequential"));
        backgroundThread = new DisplayService("Run command 1");
        //log.textProperty().bind(backgroundThread.valueProperty());
        log.textProperty().bind(statusMessagesProperty());
        
        backgroundThread.setExecutor(executor);
        backgroundThread.messageProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            statusMessagesProperty().set(newValue);
        });

        backgroundThread.start();
        //executor.shutdown();
        
        System.out.println("1 log.textProperty().get()="+ log.textProperty().get()+" statusMessagesProperty="+statusMessagesProperty);        
        
        backgroundThread = new DisplayService("Run command 2");
        backgroundThread.setExecutor(executor);
        backgroundThread.start();
        backgroundThread.messageProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            statusMessagesProperty().set(newValue);
        });
        
//        backgroundThread = new DisplayService("Run command 2");
//        log.textProperty().bind(Bindings.concat(log.textProperty().get(), backgroundThread.valueProperty()));
//        
//        backgroundThread.setExecutor(executor);
//
//        backgroundThread.start();
        

        executor.shutdown();
        
        
        dialog.showAndWait();
        
        log.appendText("\nAfter show and wait");
        
        return false;
    }

    public void bindToWorker(final Worker<ObservableList<Long>> worker, TextArea log)
    {
        log.textProperty().bind(worker.valueProperty().asString());
        
    }
    private StringProperty statusMessagesProperty() {
        if (statusMessagesProperty == null) {
            statusMessagesProperty = new SimpleStringProperty();
        }
        return statusMessagesProperty;
    }
    
    
    public static void main(String[] args) {
        launch();
    }

    static class FirstLineThreadFactory implements ThreadFactory {

        static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final String type;

        public FirstLineThreadFactory(String type) {
            this.type = type;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "LineService-" + poolNumber.getAndIncrement() + "-thread-" + type);
            thread.setDaemon(true);

            return thread;
        }
    }

    @Override
    public void stop() throws Exception {
        executor.shutdownNow();

    }
    
}