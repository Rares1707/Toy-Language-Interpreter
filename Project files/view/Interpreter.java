package view;

import controller.Controller;
import model.MyException;
import model.expressions.*;
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

class Interpreter {
    public static void main(String[] args) {
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
                        new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\text.in"))),
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

        try
        {
            IRepository repository1 = new Repository(heapWritingExample, "C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\logFile1.txt");
            IRepository repository2 = new Repository(heapAllocationExample, "C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\logFile2.txt");
            IRepository repository3 = new Repository(heapReadingExample, "C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\logFile3.txt");
            IRepository repository4 = new Repository(whileExample, "C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\logFile4.txt");
            IRepository repository5 = new Repository(garbageCollectorExample, "C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\logFile5.txt");
            IRepository repository6 = new Repository(threadsExample, "C:\\Users\\Rares\\IdeaProjects\\ToyLanguageInterpreter\\src\\textFiles\\logFile6.txt");

            Controller controller1 = new Controller(repository1);
            Controller controller2 = new Controller(repository2);
            Controller controller3 = new Controller(repository3);
            Controller controller4 = new Controller(repository4);
            Controller controller5 = new Controller(repository5);
            Controller controller6 = new Controller(repository6);

            TextMenu menu = new TextMenu();

            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExampleCommand("1", heapWritingExample.toString(), controller1));
            menu.addCommand(new RunExampleCommand("2", heapAllocationExample.toString(), controller2));
            menu.addCommand(new RunExampleCommand("3", heapReadingExample.toString(), controller3));
            menu.addCommand(new RunExampleCommand("4", whileExample.toString(), controller4));
            menu.addCommand(new RunExampleCommand("5", garbageCollectorExample.toString(), controller5));
            menu.addCommand(new RunExampleCommand("6", threadsExample.toString(), controller6));

            menu.show();
        }
        catch (MyException exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}