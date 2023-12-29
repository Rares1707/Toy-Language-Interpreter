package view.finaltoylanguageinterpreter;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.MyException;
import model.ProgramState;
import model.myADTs.MyHeap;
import model.myADTs.MyIHeap;
import model.values.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramExecutionSceneController {
    private ProgramState previousProgramState;
    private Controller controller;
    public TableView<Pair<Integer, IValue>> heapTableView;
    public TableColumn<Pair<Integer, IValue>, Integer> heapAddressColumn;
    public TableColumn<Pair<Integer, IValue>, String> heapValueColumn;
    public ListView<String> outputListView;
    public ListView<String> fileListView;
    public ListView<Integer> programStatesListView;
    public ListView<String> executionStackListView;
    public TableView<Pair<String, IValue>> symbolTableView;
    public TableColumn<Pair<String, IValue>, String> symbolTableVariableNameColumn;
    public TableColumn<Pair<String, IValue>, String> symbolTableValueColumn;
    public TextField numberOfProgramStates;
    public Button executeOneStepButton;

    public void initialize()
    {
        previousProgramState = null;
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolTableVariableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        executeOneStepButton.setOnAction(actionEvent -> {
            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            if(getCurrentProgram() == null || getCurrentProgram().getStack().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try {
                previousProgramState = getCurrentProgram();
                controller.wrapperForExecuteOneStepForEachProgram();
                populateViews();
            } catch (MyException error) {
                Alert alert = new Alert(Alert.AlertType.ERROR, error.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        programStatesListView.setOnMouseClicked(mouseEvent -> populateViews());
    }

    private ProgramState getCurrentProgram()
    {
        if (controller.getListOfPrograms().isEmpty())
            return previousProgramState; //returns null if previousProgramState == null
        int indexOfChosenProgram = programStatesListView.getSelectionModel().getSelectedIndex();
        if (indexOfChosenProgram == -1)
            indexOfChosenProgram = 0;
        return controller.getListOfPrograms().get(indexOfChosenProgram);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populateViews();
    }

    private void populateViews()
    {
        //if (controller.getListOfPrograms().isEmpty())
           // return;
        populateExecutionStackView();
        populateSymbolTableView();
        populateOutputView();
        populateFileTableView();
        populateProgramIDsView();
        populateHeap();
    }

    private void populateHeap() {
        MyIHeap heap = new MyHeap();
        if (!controller.getListOfPrograms().isEmpty())
            heap = controller.getListOfPrograms().get(0).getHeap();
        else if (previousProgramState != null)
            heap = previousProgramState.getHeap();
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<Pair<Integer, IValue>>();
        for (var entry : heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateProgramIDsView() {
        List<ProgramState> listOfPrograms = controller.getListOfPrograms();
        List<Integer> idList = listOfPrograms.stream().map(ProgramState::getId).toList();
        programStatesListView.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText(Integer.toString(listOfPrograms.size()));
    }

    private void populateFileTableView() {
        ArrayList<String> namesOfFiles = new ArrayList<>();
        if (!controller.getListOfPrograms().isEmpty())
            namesOfFiles = new ArrayList<String>(controller.getListOfPrograms().get(0).getFileTable().keySet());
        else if (previousProgramState != null)
            namesOfFiles = new ArrayList<String>(previousProgramState.getFileTable().keySet());
        fileListView.setItems(FXCollections.observableArrayList(namesOfFiles));
    }

    private void populateOutputView() {
        List<String> output = new ArrayList<>();
        if (!controller.getListOfPrograms().isEmpty())
            output = controller.getListOfPrograms().get(0).getOutputList().getContentAsListOfStrings();
        else if (previousProgramState != null)
            output = previousProgramState.getOutputList().getContentAsListOfStrings();
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private void populateSymbolTableView() {
        ProgramState currentProgram = getCurrentProgram();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        if (currentProgram != null)
            for (Map.Entry<String, IValue> entry : currentProgram.getSymbolTable().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        else if ((previousProgramState != null)) {
            for (Map.Entry<String, IValue> entry : previousProgramState.getSymbolTable().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }

    private void populateExecutionStackView() {
        ProgramState currentProgram = getCurrentProgram();
        List<String> executionStackAsListOfStrings = new ArrayList<>();
        if (currentProgram != null)
            executionStackAsListOfStrings = currentProgram.getStack().toListOfStrings().reversed();
        else if (previousProgramState != null)
            executionStackAsListOfStrings = previousProgramState.getStack().toListOfStrings().reversed();
        executionStackListView.setItems(FXCollections.observableList(executionStackAsListOfStrings));
        executionStackListView.refresh();
    }
}
