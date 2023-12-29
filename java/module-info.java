module view.finaltoylanguageinterpreter {
    requires javafx.controls;
    requires javafx.fxml;


    opens view.finaltoylanguageinterpreter to javafx.fxml;
    exports view.finaltoylanguageinterpreter;
    opens model to javafx.fxml;
    exports model;

}