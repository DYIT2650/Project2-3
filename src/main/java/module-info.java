module edu.tric.danielyoo {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.tric.danielyoo to javafx.fxml;
    exports edu.tric.danielyoo;
}
