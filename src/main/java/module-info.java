module ru.atlas.hello1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.atlas.hello1 to javafx.fxml;
    exports ru.atlas.hello1;
    //requires org.jfxtras.styles.jmetro;
}