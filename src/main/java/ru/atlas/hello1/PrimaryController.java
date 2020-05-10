package ru.atlas.hello1;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {
// test
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
