/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.atlas.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

/**
 *
 * @author atlas
 */
public class DisplayService extends Service<String> {
    
    private final String cmd;
    StringBuilder results = new StringBuilder();
    
    public DisplayService(String cmd) {
        this.cmd = cmd;
    }

    @Override
    protected Task<String> createTask() {
        return new DisplayTaskServ(cmd);
    }            
    
}
