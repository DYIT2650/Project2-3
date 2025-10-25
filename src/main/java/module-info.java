module edu.tric.danielyoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens edu.tric.danielyoo to javafx.fxml, com.fasterxml.jackson.databind;
    exports edu.tric.danielyoo;
}
