package view.finaltoylanguageinterpreter;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.MyException;
import model.expressions.*;
import model.myADTs.MyDictionary;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

public class ProgramSelectionSceneController {
    private ProgramExecutionSceneController programExecutionSceneController;
    private ObservableList<IStatement> initialStatements;
    private final String logFilePath = "C:\\Users\\Rares\\IdeaProjects\\FinalToyLanguageInterpreter\\src\\main\\java\\textFiles\\logFile.txt";
    @FXML
    public Button confirmChoiceOfStatementButton;
    @FXML
    public ListView<IStatement> listOfStatements;
    @FXML
    public void initialize()
    {
        populateListView();

        listOfStatements.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listOfStatements.getSelectionModel().selectIndices(0);

        confirmChoiceOfStatementButton.setOnAction(buttonPressed ->
        {
            int selectedStatementIndex = listOfStatements.getSelectionModel().getSelectedIndex();
            try {
                IStatement chosenStatement = initialStatements.get(selectedStatementIndex);
                chosenStatement.typecheck(new MyDictionary<>());
                IRepository repository = new Repository(chosenStatement, logFilePath);
                Controller controller = new Controller(repository);
                programExecutionSceneController.setController(controller);
            }
            catch (MyException error)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, error.getMessage(), ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        });
    }

    public void setProgramExecutionSceneController(ProgramExecutionSceneController programExecutionSceneController) {
        this.programExecutionSceneController = programExecutionSceneController;
    }

    private void populateListView()
    {
        IStatement programFAIL = new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v",
                new IntType()), new CompoundStatement(new AssignmentStatement("v",
                new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v")))),
                new VariableDeclarationStatement("v", new IntType()));


        IStatement program1 = new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v",
                new IntType()), new CompoundStatement(new AssignmentStatement("v",
                new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v")))),
                new CompoundStatement(new NopStatement(), new VariableDeclarationStatement("a", new BoolType())));

        IStatement program2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
                                new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                                new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                                        '+', new VariableExpression("a"), new ValueExpression(
                                        new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement program3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        IStatement programForFileOperationTesting =
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("C:\\Users\\Rares\\IdeaProjects\\FinalToyLanguageInterpreter\\src\\main\\java\\textFiles\\text.in"))),
                                new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                                        new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                                new CompoundStatement(new ReadNumberFromFile(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new CompoundStatement(new ReadNumberFromFile(new VariableExpression("varf"), "varc"),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                                new CloseReadFile(new VariableExpression("varf"))
                                                                        ))))))));

        IStatement heapAllocationExample = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))))
                        )
                )
        );

        IStatement heapReadingExample = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)))
                                                )
                                        )
                                )
                        )
                ));

        IStatement heapWritingExample = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5)))
                                        ))
                        )
                )
        );

        IStatement whileExample = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">",
                                new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('-',
                                                new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                )), new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        IStatement garbageCollectorExample = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new VariableDeclarationStatement("b", new ReferenceType(new IntType())),
                                                        new CompoundStatement(new HeapAllocationStatement("b", new ValueExpression(new IntValue(5))),
                                                                new CompoundStatement(new AssignmentStatement("b", new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))
                                                        )
                                                )
                                        )
                                ))));

        IStatement threadsExample = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(
                                                new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))
                                                        )

                                                )
                                        ), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );

        initialStatements = FXCollections.observableArrayList(programFAIL, program1, program2,
                program3, programForFileOperationTesting, heapAllocationExample, heapWritingExample, heapReadingExample,
                whileExample, garbageCollectorExample, threadsExample);
        listOfStatements.setItems(initialStatements);
    }
}
