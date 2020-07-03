/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.atlas.task2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import ru.atlas.utils.U;

/**
 *
 * @author atlas
 */
public class TestCommand extends Application {
    
    private StringProperty statusMessagesProperty;
    private BooleanProperty progressProperty;
    
    
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new StackPane(), 640, 480);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);        
        
        stage.setScene(scene);

        login();
        
    }

    public static void main(String[] args) {
        launch();
    }

    public boolean login() {
        
        String cmd = null;
        
        Dialog dialog = new Dialog();
        
        HBox  hbox = new HBox();
        final Label lbl  = new Label();
        lbl.setPrefWidth(500);
        final ProgressIndicator progress = new ProgressIndicator();
        progress.setPrefSize(20, 20);

        //Service commandService = new CommandService("ls");
        
        hbox.getChildren().addAll(lbl, progress);
        dialog.getDialogPane().setContent(hbox);
                
        lbl.textProperty().bind(statusMessagesProperty());
        progress.visibleProperty().bind(progressProperty());

        statusMessagesProperty().set("Start");
        progressProperty().set(Boolean.FALSE);
        
        // Запуск сервисов
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        // Отлаживаемая часть
        //1) Выполнение единичной команды с отображением результата
        cmd = "ls -la";
        Service commandService = new CommandService(cmd);
        commandService.messageProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            System.out.println("LISTENER= oldValue="+oldValue+" newValue="+newValue + " lbl.getText()="+lbl.getText());
            statusMessagesProperty().set(newValue);
        });    
        
        commandService.setExecutor(executor);
        commandService.start();
        executor.shutdown();
        
        // Конец отлаживаемой части
        
        dialog.showAndWait();
        
        return false;

    }
    
    private StringProperty statusMessagesProperty() {
        if (statusMessagesProperty == null) {
            statusMessagesProperty = new SimpleStringProperty();
        }
        return statusMessagesProperty;
    }

    private BooleanProperty progressProperty() {
        if (progressProperty == null) {
            progressProperty = new SimpleBooleanProperty(Boolean.FALSE);
        }
        return progressProperty;
    }
    
    
}
