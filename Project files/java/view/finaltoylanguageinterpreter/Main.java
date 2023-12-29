package view.finaltoylanguageinterpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader programSelectionSceneLoader = new FXMLLoader(Main.class.getResource("programSelectionScene.fxml"));
        Parent root = programSelectionSceneLoader.load();
        ProgramSelectionSceneController selectionSceneController = programSelectionSceneLoader.getController();
        primaryStage.setTitle("Program selection");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();

        FXMLLoader programExecutionSceneLoader = new FXMLLoader(Main.class.getResource("programExecutionScene.fxml"));
        Parent programExecutionRoot = programExecutionSceneLoader.load();
        ProgramExecutionSceneController programExecutionSceneController = programExecutionSceneLoader.getController();
        selectionSceneController.setProgramExecutionSceneController(programExecutionSceneController);
        Stage executionStage = new Stage();
        executionStage.setTitle("Program execution");
        executionStage.setScene(new Scene(programExecutionRoot, 600, 600));
        executionStage.show();
    }
}
