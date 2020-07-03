/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.atlas.hello1;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author atlas
 */
public class Dialog extends javafx.scene.control.Dialog<Boolean> {
    //final static private ResourceBundle bundle = ResourceBundle.getBundle("fore");
    final private static double defaultFontSize = 14.0;

    //private final GridPane      grid;
    private final StackPane     stack;
    private final TextArea      shellArea;
    
    public Dialog() {
        this.setTitle( "LOGIN_TITLE" );
        this.setHeaderText( "LOGIN_HEADER" );

        final DialogPane dialogPane = getDialogPane();

        dialogPane.setStyle("-fx-font-size: " + defaultFontSize + "px");
        
        stack = new StackPane();
        shellArea = new TextArea();
        
//        grid = new GridPane();
//        
//        this.grid.setHgap(5);
//        this.grid.setVgap(10);
//        this.grid.setMaxWidth (Double.MAX_VALUE);
//        this.grid.setAlignment(Pos.CENTER_LEFT );


        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        final Button btOk = (Button) getDialogPane().lookupButton(ButtonType.OK);
        btOk.setId("okButton");
        btOk.addEventFilter(ActionEvent.ACTION, (ActionEvent event) -> {
            if (!onOK()) {
                event.consume();
            }
        });

        
        stack.getChildren().addAll(shellArea);
        
        getDialogPane().setContent(stack);
    
    }
    
    private Boolean onOK() {

            return true;
    }
    
    public TextArea getArea() {
        return shellArea;
    }
    
    public Boolean doModal() {
        Optional<Boolean> result = showAndWait();
        return result.orElse(Boolean.FALSE);
    }
    
    
    
}
